#!/usr/bin/bash

if [ $# -ne 1 ]
then
  echo "usage:  <Filename of the file for classification>"
  exit
fi

java -cp bin/ Main $1