#!/bin/bash
# Compilation script for Library Management System

echo "Compiling Library Management System..."
javac -d out src/main/java/com/library/*.java src/main/java/com/library/**/*.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "To run the application, execute: java -cp out com.library.LibraryApp"
else
    echo "Compilation failed!"
    exit 1
fi
