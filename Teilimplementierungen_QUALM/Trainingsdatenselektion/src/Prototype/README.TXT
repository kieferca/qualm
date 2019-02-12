run.bat: run the prototype in default mode using cosine
run_LSA.bat: run the regular mode, but use LSA instead of cosine for ranking
Eva_run.bat: run the evaluation mode

stopwords.txt contains the english stopwords list of NLTK

Known Issues:
*The presence of the pipe character "|" causes issues with LSA. Make sure that
neither the training data files nor input.txt contain this character if you want to use LSA.
Cosine similarty, and therefore the default mode, are not affected by this.

*Creating the .jar:
The .jar has to be created with the option "Copy required libraries into a sub-folder next to the generated JAR".
This means that required libraries are not packaged into the jar, but stored in a separate folder. 

This is due to complicated issues with DKPro, it is not possible to bundle everything into one executable .jar.
See this for reference: https://groups.google.com/forum/#!topic/dkpro-core-user/Gl8ty8ThiSg

*the JVM should be run using english locale, otherwise there might be problems with LSA. This is due different symbols for separating decimal spaces, such as the comma "," instead of the dot "." in german.

This is done by running the JVM with these two parameters:
-Duser.country=US -Duser.language=en

The .bat files already take this into account.