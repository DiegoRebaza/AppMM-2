# syntax=docker/dockerfile:1

# ---------- Etapa 1: compilar ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# La caché de Maven se conserva entre builds (no vuelve a bajar dependencias cada vez)
RUN --mount=type=cache,target=/root/.m2 mvn -B -q clean package -DskipTests

# ---------- Etapa 2: imagen final liviana (solo JRE) ----------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# No se ejecuta como root dentro del contenedor
RUN addgroup -S app && adduser -S app -G app
USER app

COPY --from=build /app/target/*.jar app.jar

ENV JAVA_OPTS="-XX:MaxRAMPercentage=70"
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
