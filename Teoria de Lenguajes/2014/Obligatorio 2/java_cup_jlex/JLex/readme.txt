*************************************************************
*                     JLex README   Version 1.2             *
*************************************************************

Written by Elliot Berk  [edited by A. Appel] [revised by C. Scott Ananian].
Contact cananian@alumni.princeton.edu with any problems relating to JLex.

The following steps describe the compilation and usage
of JLex.

(1) Choose some directory that is on your CLASSPATH, where
    you install Java utilities such as JLex.  I will refer
    to this directory as "J", for example.

(2) Make a directory "J/JLex" and put the sourcefile Main.java
    in J/JLex.

(3) Compile Main.java as you would any Java source file:
	javac Main.java
This should produce a number of Java class files, including Main.class,
in the "J/JLex" directory, where "J" is in your CLASSPATH.

(4) To run JLex with a JLex specification file,
the usage is:
	java JLex.Main <filename>
where <filename> is the name of the JLex 
specification file.  If java complains that
it can't find JLex.Main, then the directory
"J" (which contains the subdirectory "JLex"
which contains the class files) isn't in your
CLASSPATH; go back and read steps 1-3 more 
carefully, please.

JLex will produce diagnostic output to inform 
you of its progress and, upon completion, will 
produce a Java source file that contains the 
lexical analyzer.  The name of the lexical 
analyzer file will be the name of the JLex 
specification file, with the string ".java"
added to the end.  (So if the JLex specification
file is called foo.lex, the lexical analyzer source file
that JLex produces will be called foo.lex.java.)

(5) The resulting lexical analyzer source file
should be compiled with the Java compiler:
	javac <filename>
where <filename> is the name of the lexical analyzer
source file. This produces a lexical analyzer class file, 
which can then be used in your applications.
If the default settings have not been changed,
the lexical analyzer class will be called Yylex
and the classs files will named Yylex.class and Yytoken.class.

(6) As an example, there is a sample lexical specification
on the JLex web site:
  http://www.cs.princeton.edu/~appel/modern/java/JLex/
named 'sample.lex'.  Transfer this to your system and use
the command:
  java JLex.Main sample.lex
to generate a file named 'sample.lex.java'. Compile this
with:
  javac -d J sample.lex.java
where "J" is the above mentioned path to a directory in
your CLASSPATH.  If '.' is in your CLASSPATH, you can
use "-d .".  Run the generated lexer with:
  java Sample
which expects input on stdin.  The lexer parses tokens
that resemble those for a typical programming language;
whitespace is generally ignored.  Java buffers input from
stdin a line at a time, so you won't see any output until
you type enter. Try inputting things like:
 an_identifier
 "a string"
 123124
 (1+2)
 { /* comment */ a := b & c; }

Look at the sample.lex input file for more information on
the operation of this example scanner.


