package com.example.demo;

import java.util.LinkedList;
import static org.junit.Assert.*;
import org.junit.Test;
/**
 * 
 * @author jayp1
 * The the tests below we are printing the AWK code first in the console and then we are printing it's output thus console can be used to check every thing
 * We can also test the program using the Main class
 */
public class TEST_INTERPRETER_4
{
 
	/*
	 * The begin block test with for loop
	 */
	 @Test
	   public void testInterpreter_4_FOR_loop() throws Exception
	   {
		 System.out.println("\n\n\n\n\nBELOW IS THE OUTPUT FOR THE TEST to check BEGIN,FOR LOOP AND PRINT\n\n\n\n");
		 //The below script prints the value of i that is 1,2,3,4,5
		 System.out.println("The awk code is: [\n"+" BEGIN { for (i = 1; i <= 5; i++) print i } \n]");
			 System.out.println("\n______________________________________\n ITS OUTPUT WAS:\n");
		   String document2_1 ="BEGIN { for (i = 1; i <= 5; i++) print i }";
		   	 Lexer nlexer62_1 = new Lexer(document2_1);
		  	    LinkedList<Token> ntokens_52_1= (LinkedList<Token>) nlexer62_1.Lex();//Lexing the document and storing it in a linked list of tokens
		  	    Parser nparser_TEST62_1 = new Parser(ntokens_52_1);
		      ProgramNode nprogramNode12_1 =nparser_TEST62_1.Parse() ;
		      Interpreter ip=new Interpreter(nprogramNode12_1, null);//The object of the interpreter class

		      System.out.println("\n______________________________________\n");
		    
	   }
	 /*
		 * The begin block test with WHILE loop
		 */
		 @Test
		   public void testInterpreter_4_while_loop() throws Exception
		   {
			 System.out.println("\n\n\n\n\nBELOW IS THE OUTPUT FOR THE TEST to check BEGIN,WHILE LOOP AND PRINT\n\n\n\n");
			 //The below script prints the value of i that is 1,2,3,4,5
			 System.out.println("The awk code is: [\n"+" BEGIN {\r\n"
			 		+ "    i = 1\r\n"
			 		+ "    while (i <= 5) {\r\n"
			 		+ "        print i\r\n"
			 		+ "        i++\r\n"
			 		+ "    }\r\n"
			 		+ "}\r\n"
			 		+ "\n]");
				 System.out.println("\n______________________________________\n ITS OUTPUT WAS:\n");
			   String document2_1 ="BEGIN {\r\n"
			   		+ "    i = 1\r\n"
			   		+ "    while (i <= 5) {\r\n"
			   		+ "        print i\r\n"
			   		+ "        i++\r\n"
			   		+ "    }\r\n"
			   		+ "}\r\n"
			   		+ "";
			   	 Lexer nlexer62_1 = new Lexer(document2_1);
			  	    LinkedList<Token> ntokens_52_1= (LinkedList<Token>) nlexer62_1.Lex();//Lexing the document and storing it in a linked list of tokens
			  	    Parser nparser_TEST62_1 = new Parser(ntokens_52_1);
			      ProgramNode nprogramNode12_1 =nparser_TEST62_1.Parse() ;
			      Interpreter ip=new Interpreter(nprogramNode12_1, null);//The object of the interpreter class

			      System.out.println("\n______________________________________\n");
			    
		   }
		 /*
			 * The begin block test with DO while loop
			 */
			 @Test
			   public void testInterpreter_4_do_while_loop() throws Exception
			   {
				 System.out.println("\n\n\n\n\nBELOW IS THE OUTPUT FOR THE TEST to check BEGIN,DO WHILE LOOP AND PRINT\n\n\n\n");
				 //The below script prints the value of i that is 1,2,3,4,5
				 System.out.println("The awk code is: [\n"+"BEGIN {\r\n"
				 		+ "    i = 1\r\n"
				 		+ "    do {\r\n"
				 		+ "        print i\r\n"
				 		+ "        i++\r\n"
				 		+ "    } while (i <= 5)\r\n"
				 		+ "}\r\n"
				 		+ "\n]");
					 System.out.println("\n______________________________________\n ITS OUTPUT WAS:\n");
				   String document2_1 ="BEGIN {\r\n"
				   		+ "    i = 1\r\n"
				   		+ "    do {\r\n"
				   		+ "        print i\r\n"
				   		+ "        i++\r\n"
				   		+ "    } while (i <= 5)\r\n"
				   		+ "}\r\n"
				   		+ "";
				   	 Lexer nlexer62_1 = new Lexer(document2_1);
				  	    LinkedList<Token> ntokens_52_1= (LinkedList<Token>) nlexer62_1.Lex();//Lexing the document and storing it in a linked list of tokens
				  	    Parser nparser_TEST62_1 = new Parser(ntokens_52_1);
				      ProgramNode nprogramNode12_1 =nparser_TEST62_1.Parse() ;
				      Interpreter ip=new Interpreter(nprogramNode12_1, null);//The object of the interpreter class

				      System.out.println("\n______________________________________\n");
				    
			   }
	/**
	 * The test for the functions made by the user
	 * @throws Exception
	 */
	 @Test
	   public void testInterpreter_4_1() throws Exception
	   {
		 System.out.println("\n\n\n\n\nBELOW IS THE OUTPUT FOR THE TEST to check user defined functions without parameters , FOR LOOP AND PRINT\n\n\n\n");
/*
 * The below is the script in awk that calls a method that prints word "yes" four times:
 *           function printYes() 
                      {
	      		       for (i = 1; i <= 4; i++) 
	      		          {
	      		            print "yes"
	      		          }
	      		       }
	      		     BEGIN 
	      		        {
	      		        printYes()
	      		        }
 * 
 */
		 System.out.println("The awk code is: [\n"+" function printYes() \r\n"
		 		+ "                      {\r\n"
		 		+ "	      		       for (i = 1; i <= 4; i++) \r\n"
		 		+ "	      		          {\r\n"
		 		+ "	      		            print \"yes\"\r\n"
		 		+ "	      		          }\r\n"
		 		+ "	      		       }\r\n"
		 		+ "	      		     BEGIN \r\n"
		 		+ "	      		        {\r\n"
		 		+ "	      		        printYes()\r\n"
		 		+ "	      		        }]");
		 System.out.println("\n______________________________________\n ITS OUTPUT WAS:\n");
	  String document2_1 ="function printYes() {\r\n"
	      		+ "        for (i = 1; i <= 4; i++) {\r\n"
	      		+ "            print \"yes\"\r\n"
	      		+ "        }\r\n"
	      		+ "     }\r\n"
	      		+ "     BEGIN {\r\n"
	      		+ "        printYes()\r\n"
	      		+ "     }";
		   	 Lexer nlexer62_1 = new Lexer(document2_1);
		  	    LinkedList<Token> ntokens_52_1= (LinkedList<Token>) nlexer62_1.Lex();//Lexing the document and storing it in a linked list of tokens
		  	    Parser nparser_TEST62_1 = new Parser(ntokens_52_1);
		      ProgramNode nprogramNode12_1 =nparser_TEST62_1.Parse() ;
		      Interpreter ip=new Interpreter(nprogramNode12_1, null);//The object of the interpreter class
		      System.out.println("\n______________________________________\n");

	   }
	 /**
		 * The test for the functions made by the user with one parameter
		 * @throws Exception
		 */
	 
	 @Test
	   public void testInterpreter_4_user_defined_function_with_parameters() throws Exception
	   {
		 System.out.println("\n\n\n\n\nBELOW IS THE OUTPUT FOR THE TEST to check user defined function WITH one parameter , FOR LOOP AND PRINT\n\n\n\n");
/*
* The below is the script in awk that calls a method which requires a parameter that prints word "yes" four times:
*         
*            # Define the function
    function print_yes(n) 
    {
        for (i = 1; i <= n; i++)
            print "yes"
    }
             BEGIN {
    # Call the function with a number
    print_yes(5)
}

* 
*/
			System.out.println("The awk code is: [\n"+" # Define the function\r\n"
					+ "    function print_yes(n) \r\n"
					+ "    {\r\n"
					+ "        for (i = 1; i <= n; i++)\r\n"
					+ "            print \"yes\"\r\n"
					+ "    }\r\n"
					+ "             BEGIN {\r\n"
					+ "    # Call the function with a number\r\n"
					+ "    print_yes(5)\r\n"
					+ "}]");
		 System.out.println("\n______________________________________\n ITS OUTPUT WAS:\n");
	  String document2_1 ="   # Define the function\r\n"
	  		+ "    function print_yes(n) \r\n"
	  		+ "    {\r\n"
	  		+ "        for (i = 1; i <= n; i++)\r\n"
	  		+ "            print \"yes\"\r\n"
	  		+ "    }\r\n"
	  		+ "             BEGIN {\r\n"
	  		+ "    # Call the function with a number\r\n"
	  		+ "    print_yes(5)\r\n"
	  		+ "}";
		   	 Lexer nlexer62_1 = new Lexer(document2_1);
		  	    LinkedList<Token> ntokens_52_1= (LinkedList<Token>) nlexer62_1.Lex();//Lexing the document and storing it in a linked list of tokens
		  	    Parser nparser_TEST62_1 = new Parser(ntokens_52_1);
		      ProgramNode nprogramNode12_1 =nparser_TEST62_1.Parse() ;
		      Interpreter ip=new Interpreter(nprogramNode12_1, null);//The object of the interpreter class
		      System.out.println("\n______________________________________\n");

	   }
	 
	 
	 
	 /**
		 * The test for the functions made by the user with multi parameter
		 * @throws Exception
		 */
	 
	 @Test
	   public void testInterpreter_4_user_defined_function_with_multi_parameters() throws Exception
	   {
		 System.out.println("\n\n\n\n\nBELOW IS THE OUTPUT FOR THE TEST to check user defined function WITH MULTI parameters AND ALSO IF STATEMENTS\n\n\n\n");
/*
* The below is the script in awk that calls a method which requires 3 parameters prints the maximum one:
*         
*         
*          # Define the function
    function print_max(a, b, c) {
        max = a

        if (b > max)
            max = b
        if (c > max)
            max = c

        print "The maximum number is "  max
    }

          BEGIN {
    # Call the function with three numbers
    print_max(3, 7, 5)
}


* 
*/
			System.out.println("The awk code is: [\n"+"  # Define the function\r\n"
					+ "    function print_max(a, b, c) {\r\n"
					+ "        max = a\r\n"
					+ "\r\n"
					+ "        if (b > max)\r\n"
					+ "            max = b\r\n"
					+ "        if (c > max)\r\n"
					+ "            max = c\r\n"
					+ "\r\n"
					+ "        print \"The maximum number is \"  max\r\n"
					+ "    }\r\n"
					+ "\r\n"
					+ "          BEGIN {\r\n"
					+ "    # Call the function with three numbers\r\n"
					+ "    print_max(3, 7, 5)\r\n"
					+ "}]");
		 System.out.println("\n______________________________________\n ITS OUTPUT WAS:\n");

	  String document2_1 ="  # Define the function\r\n"
	  		+ "    function print_max(a, b, c) {\r\n"
	  		+ "        max = a\r\n"
	  		+ "\r\n"
	  		+ "        if (b > max)\r\n"
	  		+ "            max = b\r\n"
	  		+ "        if (c > max)\r\n"
	  		+ "            max = c\r\n"
	  		+ "\r\n"
	  		+ "        print \"The maximum number is \"  max\r\n"
	  		+ "    }\r\n"
	  		+ "\r\n"
	  		+ "          BEGIN {\r\n"
	  		+ "    # Call the function with three numbers\r\n"
	  		+ "    print_max(3, 7, 5)\r\n"
	  		+ "}";
		   	 Lexer nlexer62_1 = new Lexer(document2_1);
		  	    LinkedList<Token> ntokens_52_1= (LinkedList<Token>) nlexer62_1.Lex();//Lexing the document and storing it in a linked list of tokens
		  	    Parser nparser_TEST62_1 = new Parser(ntokens_52_1);
		      ProgramNode nprogramNode12_1 =nparser_TEST62_1.Parse() ;
		      Interpreter ip=new Interpreter(nprogramNode12_1, null);//The object of the interpreter class
		      System.out.println("\n______________________________________\n");

	   }
	 
	 /**
	  * The test for all the in-built functions
	  * @throws Exception
	  */
	 @Test
	   public void testInterpreter_4_2() throws Exception
	   {
		 System.out.println("\n\n\n\n\nBELOW IS THE OUTPUT FOR THE TEST FOR all the PRE-defined functions\n\n\n\n");
		 /*
		  * The contents of the file being processed is 
John,Doe
Jane,Doe
Richard,Roe
Emily,Johnson
Michael,Smith
Olivia,Williams
James,Brown
Sophia,Davis
Liam,Miller
Ava,Wilson
Noah,Moore
Isabella,Taylor
Lucas,Anderson

		  * THW AWK SCRIEPT USED IS INTERPRETER_TEST.awk WHICH IS PROVIDED WITH THE SUBMISSION and also the file1.txt is submitted with the code
		  * 
		  * 
BEGIN {
    FS = ","
}

{
    print "Processing record number " NR

    # Use printf to format output
    printf "First field: %s, Second field: %s\n", $1, $2

    # Use getline to read the next line into variable 'line'
    getline
    print "Next line is: " $0

    # Use next to skip processing the rest of the current record
    if (NR == 2) {
        print "Skipping processing of second record"
        next
    }

    # Use gsub to replace all occurrences of 'a' with 'b' in first field
    gsub("a", "b", $1)
    print "First field after gsub: ",$1

    # Use index to find position of 'b' in first field
    idx = index($1, "i")
    print "Position of 'i' in first field: " idx

    # Use length to get length of first field
    len = length($1)
    print "Length of first field: " len

    # Use match to find position of regex match in first field
    j=match($1, "b")
    print "Position of regex match in first field: " j 

    # Use split to split first field into array 'a' on 'b'
    n = split($1, a, "b")
    print "First field split into " n " parts"

    # Use sprintf to format a string and assign to variable 's'
    s = sprintf("First part of split: %s" , a[0])
    print s

    # Use sub to replace first occurrence of 'b' with 'a' in first field
    sub("b", "a",  $1)
    print "First field after sub: ", $1

    # Use substr to get a substring of first field
    sub_str = substr($1, 1, 3)
    print "Substring of first field: " ,sub_str

    # Use tolower to convert first field to lower case
    lower = tolower($1)
    print "First field in lower case: ", lower

    # Use toupper to convert first field to upper case
    upper = toupper($1)
    print "First field in upper case: ", upper
}

		  */
		System.out.println("The awk code is: [\n"+"BEGIN {\r\n"
				+ "    FS = \",\"\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "{\r\n"
				+ "    print \"Processing record number \" NR\r\n"
				+ "\r\n"
				+ "    # Use printf to format output\r\n"
				+ "    printf \"First field: %s, Second field: %s\\n\", $1, $2\r\n"
				+ "\r\n"
				+ "    # Use getline to read the next line into variable 'line'\r\n"
				+ "    getline\r\n"
				+ "    print \"Next line is: \" $0\r\n"
				+ "\r\n"
				+ "    # Use next to skip processing the rest of the current record\r\n"
				+ "    if (NR == 2) {\r\n"
				+ "        print \"Skipping processing of second record\"\r\n"
				+ "        next\r\n"
				+ "    }\r\n"
				+ "\r\n"
				+ "    # Use gsub to replace all occurrences of 'a' with 'b' in first field\r\n"
				+ "    gsub(\"a\", \"b\", $1)\r\n"
				+ "    print \"First field after gsub: \",$1\r\n"
				+ "\r\n"
				+ "    # Use index to find position of 'b' in first field\r\n"
				+ "    idx = index($1, \"i\")\r\n"
				+ "    print \"Position of 'i' in first field: \" idx\r\n"
				+ "\r\n"
				+ "    # Use length to get length of first field\r\n"
				+ "    len = length($1)\r\n"
				+ "    print \"Length of first field: \" len\r\n"
				+ "\r\n"
				+ "    # Use match to find position of regex match in first field\r\n"
				+ "    j=match($1, \"b\")\r\n"
				+ "    print \"Position of regex match in first field: \" j \r\n"
				+ "\r\n"
				+ "    # Use split to split first field into array 'a' on 'b'\r\n"
				+ "    n = split($1, a, \"b\")\r\n"
				+ "    print \"First field split into \" n \" parts\"\r\n"
				+ "\r\n"
				+ "    # Use sprintf to format a string and assign to variable 's'\r\n"
				+ "    s = sprintf(\"First part of split: %s\" , a[0])\r\n"
				+ "    print s\r\n"
				+ "\r\n"
				+ "    # Use sub to replace first occurrence of 'b' with 'a' in first field\r\n"
				+ "    sub(\"b\", \"a\",  $1)\r\n"
				+ "    print \"First field after sub: \", $1\r\n"
				+ "\r\n"
				+ "    # Use substr to get a substring of first field\r\n"
				+ "    sub_str = substr($1, 1, 3)\r\n"
				+ "    print \"Substring of first field: \" ,sub_str\r\n"
				+ "\r\n"
				+ "    # Use tolower to convert first field to lower case\r\n"
				+ "    lower = tolower($1)\r\n"
				+ "    print \"First field in lower case: \", lower\r\n"
				+ "\r\n"
				+ "    # Use toupper to convert first field to upper case\r\n"
				+ "    upper = toupper($1)\r\n"
				+ "    print \"First field in upper case: \", upper\r\n"
				+ "} ]");
System.out.println("\n______________________________________\n ITS OUTPUT WAS:\n");
	  String document2_1 ="BEGIN {\r\n"
	  		+ "    FS = \",\"\r\n"
	  		+ "}\r\n"
	  		+ "\r\n"
	  		+ "{\r\n"
	  		+ "    print \"Processing record number \" NR\r\n"
	  		+ "\r\n"
	  		+ "    # Use printf to format output\r\n"
	  		+ "    printf \"First field: %s, Second field: %s\\n\", $1, $2\r\n"
	  		+ "\r\n"
	  		+ "    # Use getline to read the next line into variable 'line'\r\n"
	  		+ "    getline\r\n"
	  		+ "    print \"Next line is: \" $0\r\n"
	  		+ "\r\n"
	  		+ "    # Use next to skip processing the rest of the current record\r\n"
	  		+ "    if (NR == 2) {\r\n"
	  		+ "        print \"Skipping processing of second record\"\r\n"
	  		+ "        next\r\n"
	  		+ "    }\r\n"
	  		+ "\r\n"
	  		+ "    # Use gsub to replace all occurrences of 'a' with 'b' in first field\r\n"
	  		+ "    gsub(\"a\", \"b\", $1)\r\n"
	  		+ "    print \"First field after gsub: \",$1\r\n"
	  		+ "\r\n"
	  		+ "    # Use index to find position of 'b' in first field\r\n"
	  		+ "    idx = index($1, \"i\")\r\n"
	  		+ "    print \"Position of 'i' in first field: \" idx\r\n"
	  		+ "\r\n"
	  		+ "    # Use length to get length of first field\r\n"
	  		+ "    len = length($1)\r\n"
	  		+ "    print \"Length of first field: \" len\r\n"
	  		+ "\r\n"
	  		+ "    # Use match to find position of regex match in first field\r\n"
	  		+ "j=match($1, \"b\")"
	  		+ "    print \"Position of regex match in first field: \" j \r\n"
	  		+ "\r\n"
	  		+ "    # Use split to split first field into array 'a' on 'b'\r\n"
	  		+ "    n = split($1, a, \"b\")\r\n"
	  		+ "    print \"First field split into \" n \" parts\"\r\n"
	  		+ "\r\n"
	  		+ "    # Use sprintf to format a string and assign to variable 's'\r\n"
	  		+ "    s = sprintf(\"First part of split: %s\" , a[0])\r\n"
	  		+ "    print s\r\n"
	  		+ "\r\n"
	  		+ "    # Use sub to replace first occurrence of 'b' with 'a' in first field\r\n"
	  		+ "    sub(\"b\", \"a\",  $1)\r\n"
	  		+ "    print \"First field after sub: \", $1\r\n"
	  		+ "\r\n"
	  		+ "    # Use substr to get a substring of first field\r\n"
	  		+ "    sub_str = substr($1, 1, 3)\r\n"
	  		+ "    print \"Substring of first field: \" ,sub_str\r\n"
	  		+ "\r\n"
	  		+ "    # Use tolower to convert first field to lower case\r\n"
	  		+ "    lower = tolower($1)\r\n"
	  		+ "    print \"First field in lower case: \", lower\r\n"
	  		+ "\r\n"
	  		+ "    # Use toupper to convert first field to upper case\r\n"
	  		+ "    upper = toupper($1)\r\n"
	  		+ "    print \"First field in upper case: \", upper\r\n"
	  		+ "}\r\n"
	  		+ "";
		   	 Lexer nlexer62_1 = new Lexer(document2_1);
		  	    LinkedList<Token> ntokens_52_1= (LinkedList<Token>) nlexer62_1.Lex();//Lexing the document and storing it in a linked list of tokens
		  	    Parser nparser_TEST62_1 = new Parser(ntokens_52_1);
		      ProgramNode nprogramNode12_1 =nparser_TEST62_1.Parse() ;
		      Interpreter ip=new Interpreter(nprogramNode12_1, "file1.txt" );//The object of the interpreter class, "file1.txt" is submitted with the assignment
		      System.out.println("\n______________________________________\n");

	   }
	 
	 
     	 /*The program is 
     	  * Sum by line and then by file a simple CSV (comma seperated value) file:

Input:

1,2,3,4,5

10,20,30,40

Output:

Line 1: 15

Line 2: 100

Grand total: 115
sum.awk  and SUMDATA.txt is submitted with the submission
		 * 
		 */
		 @Test
		   public void testInterpreter_4_sum() throws Exception
		   {
			 System.out.println("\n\n\n\n\nBELOW IS THE OUTPUT FOR THE TEST OF sum.awk the first program\n\n\n\n");
			 //The below script prints the value of i that is 1,2,3,4,5
			 System.out.println("#This is begining of the code\r\n"
			 		+ "BEGIN {FS=\",\"; total_sum =0}\r\n"
			 		+ "# The following block holds the code which will give output(if asked in there) for every single line of input.\r\n"
			 		+ "{     n=0\r\n"
			 		+ "      sum=0\r\n"
			 		+ "      \r\n"
			 		+ "        for (i=1;i<=NF;i++)\r\n"
			 		+ "        {\r\n"
			 		+ "             sum=sum +  $i    \r\n"
			 		+ "             total_sum = total_sum + $i\r\n"
			 		+ "        }\r\n"
			 		+ "             n= NR\r\n"
			 		+ "print \"Line \" n \" is \" sum\r\n"
			 		+ "}\r\n"
			 		+ "END{print \"The total is \" total_sum}\r\n"
			 		+ "");
				 System.out.println("\n______________________________________\n ITS OUTPUT WAS:\n");
			   String document2_1 ="#This is begining of the code\r\n"
			   		+ "BEGIN {FS=\",\"; total_sum =0}\r\n"
			   		+ "# The following block holds the code which will give output(if asked in there) for every single line of input.\r\n"
			   		+ "{     n=0\r\n"
			   		+ "      sum=0\r\n"
			   		+ "      \r\n"
			   		+ "        for (i=1;i<=NF;i++)\r\n"
			   		+ "        {\r\n"
			   		+ "             sum=sum +  $i    \r\n"
			   		+ "             total_sum = total_sum + $i\r\n"
			   		+ "        }\r\n"
			   		+ "             n= NR\r\n"
			   		+ "print \"Line \" n \" is \" sum\r\n"
			   		+ "}\r\n"
			   		+ "END{print \"The total is \" total_sum}\r\n"
			   		+ "";
			   	 Lexer nlexer62_1 = new Lexer(document2_1);
			  	    LinkedList<Token> ntokens_52_1= (LinkedList<Token>) nlexer62_1.Lex();//Lexing the document and storing it in a linked list of tokens
			  	    Parser nparser_TEST62_1 = new Parser(ntokens_52_1);
			      ProgramNode nprogramNode12_1 =nparser_TEST62_1.Parse() ;
			      Interpreter ip=new Interpreter(nprogramNode12_1, "SUMDATA.txt");//The object of the interpreter class, "SUMDATA.txt is submitted with the submission

			      System.out.println("\n______________________________________\n");
			    
		   }
	      
}
