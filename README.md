#Simple Bouncing Ball#
---

##Description##

A simple controllable ball that bounces off the edges of the window/shaded area. Includes both a Java and Javascript version.

##Building and Running##

###Javascript###

The Javascript folder includes a simple html page to run the script. Download the Javascript folder and open the page in your browser of choice.

###Java###

The Java version of Simple Bouncing Ball has been constructed with only the JavaSE libraries so only the JDK is required. It has been built and tested using version 1.8 of the JVM, but earlier versions may work. YMMV. 

Create a directory for the compiled source and copy config.ini into it. Then from terminal run:

`javac -d :YOUR_DIR_HERE: simplebouncingball/Frame.java`

If you change to the directory you've created you can then run the file with:

`java simplebouncingball/Frame`

or create a jar file with 

`jar cfe BouncingBall.jar simplebouncingball.Frame simplebouncingball`

and run it using

`java -jar BouncingBall.jar`