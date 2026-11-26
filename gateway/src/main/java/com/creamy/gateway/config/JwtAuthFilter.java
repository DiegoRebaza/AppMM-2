package com.creamy.gateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    @Value("${jwt.secret}")
    private String secret;

    public JwtAuthFilter() {
        super(Config.class);
    }

    public static class Config {}

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            HttpMethod method = request.getMethod();

            // 1. Permitir preflight CORS y rutas públicas
            if (method == HttpMethod.OPTIONS || esRutaPublica(path, method)) {
                return chain.filter(exchange);
            }

            // 2. Verificar cabecera Authorization
            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return responderError(exchange, HttpStatus.UNAUTHORIZED, "Token JWT no proporcionado");
            }

            String token = authHeader.substring(7);
            try {
                SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                String email = claims.getSubject();
                String rol = claims.get("rol", String.class);

                // 3. Validar permisos de administrador para gestionar productos
                if (path.startsWith("/productos") && method != HttpMethod.GET) {
                    if (rol == null || !rol.equalsIgnoreCase("admin")) {
                        return responderError(exchange, HttpStatus.FORBIDDEN, "Acceso denegado: requiere rol admin");
                    }
                }

                // 4. Reenviar identidad del usuario hacia los microservicios internos
                ServerHttpRequest mutatedRequest = request.mutate()
                        .header("X-User-Email", email != null ? email : "")
                        .header("X-User-Role", rol != null ? rol : "")
                        .build();

                return chain.filter(exchange.mutate().request(mutatedRequest).build());

            } catch (Exception e) {
                return responderError(exchange, HttpStatus.UNAUTHORIZED, "Token JWT inválido o expirado");
            }
        };
    }

    private boolean esRutaPublica(String path, HttpMethod method) {
        if (path.startsWith("/auth/")) return true;
        if (path.startsWith("/productos") && method == HttpMethod.GET) return true;
        if (path.startsWith("/contenido/blogs") && method == HttpMethod.GET) return true;
        if (path.equals("/contenido/contacto") && method == HttpMethod.POST) return true;
        return false;
    }

    private Mono<Void> responderError(ServerWebExchange exchange, HttpStatus status, String mensaje) {
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] bytes = ("{\"error\": \"" + mensaje + "\"}").getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}