#!/usr/bin/bash

javac -d bin/ src/*.java

if [ $? -ne 0 ];
then
    echo "compilierung nicht erfolgreich"
    exit
fi
