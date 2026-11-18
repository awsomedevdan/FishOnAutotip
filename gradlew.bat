@echo off
setlocal
set "VERSION=9.5.1"
set "BASE=%USERPROFILE%\.gradle\fishonautotip-bootstrap"
set "DIST=%BASE%\gradle-%VERSION%"
if not exist "%DIST%\bin\gradle.bat" (
  if not exist "%BASE%" mkdir "%BASE%"
  echo Downloading Gradle %VERSION%...
  powershell -NoProfile -Command "$ErrorActionPreference = 'Stop'; Invoke-WebRequest -Uri 'https://services.gradle.org/distributions/gradle-%VERSION%-bin.zip' -OutFile '%BASE%\gradle.zip'; Expand-Archive -Force -Path '%BASE%\gradle.zip' -DestinationPath '%BASE%'"
  if errorlevel 1 exit /b 1
)
cd /d "%~dp0"
call "%DIST%\bin\gradle.bat" %*
