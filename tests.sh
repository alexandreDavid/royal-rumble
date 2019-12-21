#!/bin/bash
echo "Beginning of tests \\n"
for file in ./input*.txt
do
  i=${file//[^0-9]/}
  rm -f exit$i.txt
  java -cp . Main $file >> exit$i.txt
  DIFF=$(diff ./exit$i.txt ./output$i.txt) 
  if [ "$DIFF" != "" ] 
  then
      echo "$file failure"
      echo $DIFF
  else
      echo "$file Test success"
  fi
  rm -f exit$i.txt
done
echo "\\nEnd of tests"