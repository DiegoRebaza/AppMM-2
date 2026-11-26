@echo off
title Creamy - Abrir microservicios
echo Iniciando microservicios de Pasteleria Creamy...

set JWT_SECRET=8d5hg01g8yt91p6tczc5auxkao4u8og9m9e02x7lv64q4ref97ahh2il5sgsfck3h4h94sj9wlardkeh

start "AUTH (9080)" cmd /k "cd auth && mvnw.cmd spring-boot:run"
timeout /t 6 /nobreak > nul

start "PRODUCTO (9081)" cmd /k "cd producto && mvnw.cmd spring-boot:run"
timeout /t 6 /nobreak > nul

start "COMPRA (9082)" cmd /k "cd compra && mvnw.cmd spring-boot:run"
timeout /t 6 /nobreak > nul

start "CONTENIDO (9083)" cmd /k "cd contenido && mvnw.cmd spring-boot:run"
timeout /t 6 /nobreak > nul

start "GATEWAY (8080)" cmd /k "cd gateway && mvnw.cmd spring-boot:run"

echo Todos los servicios fueron lanzados. Revisa las ventanas individuales.
pause