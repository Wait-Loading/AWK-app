package com.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
/**
 * Main2 class to run the program from the console
 * @author jayp1
 *The main  will ask for a awk program file and then ask if you want to process a text file if yes you will press 1 and enter after which it will ask for 
 *a file name with  extension .txt or any other file you want to process. if you do not wish to pass in a file to processs just pass in an awk program and do not
 *press 1.
 *
 *
 */
public class Main2 
{
	public static void main(String [] args) throws Exception
	{ 
		
		  Scanner sc=new Scanner(System.in);//Scanner object to get the input
		  Scanner sc1=new Scanner(System.in);//Scanner object to get the input

		  System.out.println("Type the awk file name ;) ");
		  String File_name = sc.nextLine();//sting to get the file name
		  String file=null;

		  System.out.println("Do you want to process a file press \"1\" for yes any other key for no");
		  String ch =sc.next();
		  if(ch.charAt(0)=='1')
		  {
		  System.out.println("Enter the name of the file to be processed");
		   file =sc1.nextLine();
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
