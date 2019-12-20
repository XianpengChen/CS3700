# Project 1: Socket Basics

## Description
Please refer to https://course.ccs.neu.edu/cs3700f19/projects/project1.html for details.


## Approach
firstly, take in command line arguments to specify parameters such as: port, SSLEncrypted, hostname and NEU ID. These would be used to create a socket connecting to the server.

Secondly, use a while loop to send out the HELLO message first. According to the messages received from the server, if it is a FIND message, it will send out a COUNT message; if it is a BYE message, it will print out the secret flag or "Unknown_Husky_ID"; if it is neither, it will close the connection and exit the program.

Thirdly, a helper method was created to count the occurrence of a single symbol in a string of random characters.

## Challenges
1. have not figure out how to use SSL in Java to satisfy this project's demand. (figured out now from https://stackoverflow.com/questions/2893819/accept-servers-self-signed-ssl-certificate-in-java-client)
2. need to create a Makefile which would compile the java file to java class or jar file.
    
   

##Build
using the Makefile: "make" to build; "make clean" to clean;

To run, use command: ./client <-p port> <-s> [hostname] [NEU ID]

## Testing
using Makefile to build on a local linux machine(WSL Ubuntu) to verify the correctness of the program. Successfully received the secret flag by executing the shell script client. 
