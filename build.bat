@echo off
rem Compila todos los microservicios del Caso Semestral
cd auth
call mvnw.cmd spring-boot:run
echo.
echo Compilacion finalizada para todos los microservicios.
pause
