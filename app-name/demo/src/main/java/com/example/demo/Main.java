package com.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
//import java.util.Scanner;
/**
 * Main class to run the program from the terminal There is also a Main2.java which takes input of the files from console
 * @author jayp1
 *The Main class can be compiled using the javac and then we can run awk codes in here. 
 *using <java Main file_name.awk file_to_process.txt> 
 *The tests used and the files provided by me are 
 *
 *1. java Main word_count.awk WCT.txt
 *2. java Main INTERPRETER_TEST.awk file1.txt
 *3. java Main AVERAGE.awk averageDATA.txt  
 *4. java Main sum.awk SUMDATA.txt 
 *5. java Main for.awk
 *
 *above are the commend line arguments to run the code
 */
public class Main {
	public static void main(String [] args) throws Exception
	{ 
		
		String file;
		if (args.length > 2 || args.length<1 )
		{
	        System.out.println("Write <java Main file_name.awk file_to_process.txt>");//Printing usage on console
	        return;
	    }
	    String File_name = args[0]; // Getting the file name from command-line argument
	    if(args.length==2)
	    {
	     file= args[1];//getting the file we want to process in awk from the command-line argument 
	    }
	    else//if no fine name is given to process 
	    {
	    	 file=null;// The file is null
	    }
	    Path myPath= Paths.get(File_name); 
	    String documnet = null;
		try {
			documnet = new String(Files.readAllBytes (myPath));//Reading the file using readALLBytes
		} catch (IOException e) 
		{
			System.out.println("File not found or is empty.");
			System.exit(0);
		}
		Lexer lexerobj = new Lexer(documnet); //creating lexer object for input.
	    List<Token> tokens = lexerobj.Lex();
	    Parser nparser_TEST62_1 = new Parser((LinkedList<Token>) tokens);
	      ProgramNode nprogramNode12_1 =nparser_TEST62_1.Parse() ;
	      Interpreter ip=new Interpreter(nprogramNode12_1, file );//The object of the interpreter class
	}
}
