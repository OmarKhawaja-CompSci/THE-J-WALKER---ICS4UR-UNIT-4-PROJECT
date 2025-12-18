@echo off
REM Simple build script for the game project
if not exist bin mkdir bin
javac -d bin src\*.java
if %errorlevel% neq 0 (
  echo Build failed with exit code %errorlevel%
  exit /b %errorlevel%
)
echo Build successful
