@echo off
javac -d bin/ src/*.java

if %errorlevel% neq 0 (
    echo compilierung nicht erfolgreich
    exit /b
)