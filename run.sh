#!/bin/bash
# Run script for Library Management System

if [ ! -d "out" ]; then
    echo "Compiled classes not found. Running compilation..."
    ./compile.sh
fi

echo "Starting Library Management System..."
java -cp out com.library.LibraryApp
