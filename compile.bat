@echo off
REM Compilation script for Library Management System (Windows)

echo Compiling Library Management System...
javac -d out src\main\java\com\library\*.java src\main\java\com\library\model\*.java src\main\java\com\library\service\*.java src\main\java\com\library\util\*.java

if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
    echo To run the application, execute: java -cp out com.library.LibraryApp
) else (
    echo Compilation failed!
    exit /b 1
)
