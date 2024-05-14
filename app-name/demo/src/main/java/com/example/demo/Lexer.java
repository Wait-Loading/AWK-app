package com.example.demo;

/**
 * 
 * ICSI 311 LEXER Part-1
 * Name: Jay Patel
 * net_id: JP693854
 * EMAIL: jpatel8@albany.edu
 *
 * @author jayp1
 *
 */
import java.util.*;
public class Lexer 
{
	private static READ_handler handler;// READ_handler object to call class READ_handler when needed.
	private int CurrentLINE;// INT variable to store the position of the current line.
	int CurrentPOS;//INT variable to store the current position in the line.
    ArrayList<String> Comments = new ArrayList<String>();
	HashMap<String,Token.TokenType> map=new HashMap<>();//Creating a hashmap of key words
	HashMap<String,Token.TokenType> twocharsymbol=new HashMap<>();//Hashmap of 2 charACTER SYMBOLS
	HashMap<String,Token.TokenType> onecharsymbol=new HashMap<>();//Hashmap of 1 charACTER SYMBOLS
	/**
	 * Constructor 
	 * @param input takes the file name as an input.
	 */
	Lexer(String input)
	{
		handler =new READ_handler(input);//Creating a read Handler.
		CurrentPOS=1;
		CurrentLINE=1;
		//adding keys to hash map
	    map.put("while",Token.TokenType.WHILE);
	    
	    map.put("do",Token.TokenType.DO);

	    map.put("if",Token.TokenType.IF);

	    map.put("for",Token.TokenType.FOR);

	    map.put("break",Token.TokenType.BREAK);

	    map.put("continue",Token.TokenType.CONTINUE);

	    map.put("else",Token.TokenType.ELSE);

	    map.put("return",Token.TokenType.RETURN);

	    map.put("BEGIN",Token.TokenType.BEGIN);

	    map.put("END",Token.TokenType.END);

	    map.put("print",Token.TokenType.PRINT);
        
	    map.put("printf",Token.TokenType.PRINTF);

	    map.put("next",Token.TokenType.NEXT);

	    map.put("in",Token.TokenType.IN);

	    map.put("delete",Token.TokenType.DELETE);

	    map.put("getline",Token.TokenType.GETLINE);
	    
	    map.put("exit",Token.TokenType.EXIT);
	    
	    map.put("nextfile",Token.TokenType.NEXTFILE);

	    map.put("function",Token.TokenType.FUNCTION);
	    
        twocharsymbol.put(">=",Token.TokenType.GREATERTHAN_EQUALTO);
        twocharsymbol.put("<=",Token.TokenType.LESSTHAN_EQUALTO);
        twocharsymbol.put("++",Token.TokenType.PLUS1);
        twocharsymbol.put("--",Token.TokenType.MINUS1);
        twocharsymbol.put("==",Token.TokenType.EQUALSTO);
        twocharsymbol.put("!=",Token.TokenType.NOT_EQUALSTO); 
        twocharsymbol.put("^=",Token.TokenType.RAISED_TO);
        twocharsymbol.put("%=",Token.TokenType.REMAINDER_TO);
        twocharsymbol.put("*=",Token.TokenType.MULTIPLY_BY);
        twocharsymbol.put("/=",Token.TokenType.DIVIDE_BY);
        twocharsymbol.put("+=",Token.TokenType.ADD_BY);
        twocharsymbol.put("-=",Token.TokenType.SUB_BY);
        twocharsymbol.put("&&",Token.TokenType.AND);
        twocharsymbol.put("||",Token.TokenType.OR);
        twocharsymbol.put(">>",Token.TokenType.APPEND);
        twocharsymbol.put("!~",Token.TokenType.NOMATCH);
        
        onecharsymbol.put("{", Token.TokenType.OPENCURLY);
        onecharsymbol.put("}", Token.TokenType.CLOSECURLY);
        onecharsymbol.put("[", Token.TokenType.OPENBOX);
        onecharsymbol.put("]", Token.TokenType.CLOSEBOX);
        onecharsymbol.put("(", Token.TokenType.OPENPARA);
        onecharsymbol.put(")", Token.TokenType.CLOSEPARA);
        onecharsymbol.put("$", Token.TokenType.DOLLAR);
        onecharsymbol.put("~", Token.TokenType.MATCH);
        onecharsymbol.put("=", Token.TokenType.ASSIGN);
        onecharsymbol.put("<", Token.TokenType.LESSTHAN);
        onecharsymbol.put(">", Token.TokenType.GREATERTHAN);
        onecharsymbol.put("!", Token.TokenType.NOT);
        onecharsymbol.put("+", Token.TokenType.ADD);
        onecharsymbol.put("^", Token.TokenType.POWER);
        onecharsymbol.put("-", Token.TokenType.SUBTRACT);
        onecharsymbol.put("?", Token.TokenType.QUESTION);
        onecharsymbol.put(":", Token.TokenType.COLON);
        onecharsymbol.put("*", Token.TokenType.MULTIPLY);
        onecharsymbol.put("/", Token.TokenType.DIVIDE);
        onecharsymbol.put("%", Token.TokenType.MODULO);
        onecharsymbol.put(";", Token.TokenType.SEPERATOR);
        onecharsymbol.put("\n", Token.TokenType.SEPERATOR);
        onecharsymbol.put("|", Token.TokenType.PIPE);
        onecharsymbol.put(",", Token.TokenType.COMMA);
        onecharsymbol.put("&", Token.TokenType.and);
	}
	/**
	 * 	  this is the main which will break the data from READ handler into a linked list of tokens.  

	 * @return The linked list of the tokens
	 */
	 List<Token> Lex()
	{
		 char ch;//A variable to store the characters we will peek at
		 List<Token> tokens = new LinkedList<>();//Linked list of tokens
		 //while loop that will run till the index has reached the file end.
		 while(handler.ISDone()==false)
		 {
			 
			 ch= handler.Peek(0);//Peeking at character
			 if(ch=='\r')//If the character is a carriage return (\r), we will ignore it.
			 {
				 handler.Swallow(1);//moving index without doing anything
			 }
			 else if(ch==' ')//If the character is a space or tab, we will just move past it (increment position). 
			 {
				 handler.Swallow(1);//moving index without doing anything
				 if(handler.ISDone())
					 break;
				 CurrentPOS++;//moving the position to the next index.
			 }
			 else if(ch=='\n')//If the character is a linefeed (\n), we will create a new SEPERATOR token with no value and add it to token list. 
			 {
				 tokens.add(new Token(Token.TokenType.SEPERATOR,CurrentLINE,CurrentPOS));//
				 CurrentPOS=1;//changing the position to 1 for the new line.
				 handler.Swallow(1);
				 if(handler.ISDone())
					 break;
				 CurrentLINE++;//updating to the new line
			 }
			 else if(ch=='"')
			 {
				 
				 Token token = HandleStringLiteral();//creating a token object
	                tokens.add(token); 
	           	 handler.Swallow(1);
                  CurrentPOS++;
			 }
			 else if(ch=='`')
			 {
				 Token token = HandlePATTERNS();//creating a token object
	                tokens.add(token); 
	           	 handler.Swallow(1);
               CurrentPOS++;
			 }
			 else if (((int)ch>=65&&(int)ch<=90)||((int)ch>=97&&(int)ch<=122))//If the character is a letter, we need to call ProcessWord and add the result to our list of tokens
			 {
				 Token token = ProcessWord();//creating a token object
	                tokens.add(token);
			 }
			 else if((Character.isDigit(ch)|| ch == '.'))//If the character is a digit, we need to call ProcessDigit and add the result to our list of tokens.
			 {
				 Token token = ProcessNumber();//creating a token object
	                tokens.add(token); 
			 }
			 else if(ch=='#')//if the character is a comment in awk.
			 {
				 String srt="";//A string to store the comment
				 while(ch!='\n')
				 {
					 if(handler.ISDone())
						 break;
					 ch= handler.Peek(0);
					 srt=srt+ch;
					 handler.Swallow(1);
				 }
				// System.out.println("Comment on line "+CurrentLINE+": "+srt);
				 CurrentLINE++;//updating to the new line
			 }
			 
			 else if(onecharsymbol.containsKey(Character.toString(ch)))
			 {
				Token token=ProcessSymbols();
				tokens.add(token);
			 }
			 else
			 {
				 System.out.println("EXCEPTION: CHARACTER NOT RECOGNISED AT LINE "+ CurrentLINE +" INDEX: " + CurrentPOS);//Throwing a exception if the character is not recognized
				 handler.Swallow(1);
			 }

		 }
		return tokens;
	} 
	 /**
	  * Method to process word and return its token
	  * @return  token of words
	  */
	private Token ProcessWord() 
	{   			
        
		 int firstindex=CurrentPOS;//Storing the index of first character of each token.
		String value= "";
		char ch=handler.Peek(0);  
        while ((Character.isLetterOrDigit(ch)||ch=='_')&&(handler.ISDone()==false))
        {	 
        	value= value+ch; 
        	handler.Swallow(1);
        	if(handler.ISDone())
				 break;
        	ch=handler.Peek(0);
        	CurrentPOS++;
        }
        if(map.containsKey(value))//checking for keywords
        {
        	Token keyword=new Token(map.get(value),CurrentLINE,firstindex);
        	return keyword;
        }
        else
        {
        return (new Token(Token.TokenType.WORDS,CurrentLINE,firstindex,value));
        }
	}
	
	 /**
	  * Method to process number and return its token
	  * @return number of tokens
	  */
	private Token ProcessNumber() 
	{
		int firstindex=CurrentPOS;
		String value="";
		char ch=handler.Peek(0);
        while ((Character.isDigit(ch) || ch == '.')&&(handler.ISDone()==false))
        { 
        	value= value+ch;
        	handler.Swallow(1);
        	if(handler.ISDone())
				 break;
        	ch=handler.Peek(0);
        	CurrentPOS++;
        }
        
        return (new Token(Token.TokenType.NUMBERS,CurrentLINE,firstindex,value));
	}
	
/**
 * Method to handle StringLiterals
 * @return the string literals  passed within ""  
 */
public Token HandleStringLiteral()
{
	int firstindex=(++CurrentPOS);//storing first index
	 handler.Swallow(1);//Swallowing the " 

    String Literal="";//Variable to store literal
char ch=handler.Peek(0);//variable to store the peek variable
int temp=0;
while(ch!='"')
{
	if(ch=='\\')//checking for '/'
		{
		CurrentPOS++;
		 handler.Swallow(1);
		 if(handler.ISDone())
			 break;
	     ch=handler.Peek(0);
	     if(ch!='"')
	    	 temp=1;
	     else
	    	 temp=2;
	     }
	if(temp==1)
	{
		Literal=Literal+"\\"+ch;
	}
	else
	{
	Literal=Literal+ch;
	}
	handler.Swallow(1);
	if(handler.ISDone())
		 break;
	ch=handler.Peek(0);
	CurrentPOS++;
}

return (new Token(Token.TokenType.STRINGLITERAL,CurrentLINE,firstindex,Literal));
}

/**
 * Method to handle patterns
 * @return the pattern in a Tokentype
 */
public Token HandlePATTERNS()
{
	int firstindex=(++CurrentPOS);
	 handler.Swallow(1);

    String PATTERN="";
char ch=handler.Peek(0);

while(ch!='`')
{
	PATTERN=PATTERN+ch;
	handler.Swallow(1);
	if(handler.ISDone())
		 break;
	ch=handler.Peek(0);
	CurrentPOS++;
}
return (new Token(Token.TokenType.PATTERNS,CurrentLINE,firstindex,PATTERN));	
}

/**
 * Method  to process symbols and give symbols
 * @return TokenType of symbols
 */
public Token ProcessSymbols()
{
	int firstindex=CurrentPOS;
	String symbol=handler.PeekString(2);
	String ch=handler.PeekString(1);
	if(twocharsymbol.containsKey(symbol))//checking the 2 character symbols first.
	{
		CurrentPOS +=2;
		handler.Swallow(2);
		return (new Token(twocharsymbol.get(symbol),CurrentLINE,firstindex,symbol));
	}
	else if(onecharsymbol.containsKey(ch))//checking the one character symbols
	{
		CurrentPOS++;
		if (ch=="\n"||ch==";")
			CurrentLINE++;
		handler.Swallow(1);
		return (new Token(onecharsymbol.get(ch),CurrentLINE,firstindex,ch));
	}
	return null;
}
}
