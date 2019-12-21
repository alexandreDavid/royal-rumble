#!/bin/bash

for file in ./input*.txt
do
  java -cp . Main $file
  printf "\n"
done