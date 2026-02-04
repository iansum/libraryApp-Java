@echo off
REM Run script for Library Management System (Windows)

if not exist "out" (
    echo Compiled classes not found. Running compilation...
    call compile.bat
)

echo Starting Library Management System...
java -cp out com.library.LibraryApp
