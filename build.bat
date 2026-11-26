@echo off
rem Compila todos los microservicios del Caso Semestral
cd auth
call .\mvnw.cmd clean package -DskipTests
echo.
echo Compilacion finalizada para todos los microservicios.
pause
