# Introduction

The grep Java program is a command that searches for specific words. egrep (Extended Global Regular Expressions Print) is a UNIX command that is cloned in this  project. This command will go through all of the files in the current directory as well as every directory below it, looking for the regular expression that was supplied to be searched in each one. The output is saved to an output file using command-line argument. 

This application was written in Java with Maven as the build tool (using Streams, Lambda, and RegEx pattern matching). It's set up to run from the command line with the JRE installed, or from a Docker container.

# Quick Start

1. Run with JAR file

```
mvn clean package
java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp [regex] [rootDirectory] [outFile]
```

2. Run with Docker

```
docker pull rpolisuk/grep
docker run --rm -v `pwd`/data:/data -v `pwd`/out:/out rpolisuk/grep [regex] [rootDirectory] [outFile]
```

# Implementation

## Pseudocode

```
storedLines = [] 
for file in listFilesRecursively(rootDir)
  for fileLine in readLines(file)
      if matchesPattern(fileLine)
        storedLines.add(fileLine)
saveToFile(storedLines)
```

## Performance Issue

This software has a problem in that it reads the full file into memory and stores it in an ArrayList.
As a result, there must be enough RAM to store and process this array. Using a java.util.Scanner to process the contents of the file and retrieve lines serially, one by one, can help with this. The amount of RAM used will be reduced as a result.

# Test

The grep application was manually tested using an example data text document, using the readLines method to read the lines and comparing the matchedLines produced to the outfile. The application's regex pattern was checked using an online regex tester to ensure that the right matched line result was being sent to the outfile based on the files in the root directory. Breakpoints and the watch window for variables in the IntelliJ IDE were also set up to guarantee that the proper if statements were being executed based on the file line's contents.

Finally, the algorithm was put to the test against a single file containing a complete copy of William Shakespeare's play. To guarantee that the file was returned accurately, a string was used to search it. To validate that it matched, it was compared to the egrep command.

# Deployment

The software was dockerized using a Docker file that used the Alpine JDK image, transferring the JAR file (which was created using Maven from an UberJAR file), and then configured the entry point to run the Java process using the JAR file. The Docker container was configured using the command line options. This was then uploaded to Docker-Hub so that it could be easily accessed.

# Improvements

Three possible improvements can be made to this project:

1. Implement the java.util.Scanner class to process the file as a stream and process each line one at
   a time.
2. Add a line numbering option to the output file so the user knows where the match came from.
3. Instead of LOG4j, we can use different logging jars.

