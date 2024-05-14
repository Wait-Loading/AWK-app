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
public class Token 
{
	enum TokenType {WORDS, NUMBERS, SEPERATOR, WHILE, DO, IF, FOR, BREAK, CONTINUE, ELSE, RETURN, BEGIN, END, 
		PRINT, PRINTF, NEXT, IN, DELETE, GETLINE, EXIT, NEXTFILE, FUNCTION, STRINGLITERAL, PATTERNS, GREATERTHAN_EQUALTO, 
		LESSTHAN_EQUALTO, PLUS1, MINUS1, EQUALSTO, NOT_EQUALSTO, RAISED_TO, REMAINDER_TO, MULTIPLY_BY, DIVIDE_BY, ADD_BY, NOMATCH, 
		APPEND, OR, AND, SUB_BY, OPENCURLY, CLOSECURLY, OPENBOX, CLOSEBOX, OPENPARA, CLOSEPARA, DOLLAR, MATCH, ASSIGN, LESSTHAN, GREATERTHAN,
		NOT, ADD, POWER, SUBTRACT, QUESTION, COLON, MULTIPLY, DIVIDE, MODULO, PIPE, COMMA, and }//enumeration of token type 
   private String Value;//The value of the file to be associated with the token type.
   private int Current_Line;//Stores the line on which the token is.
   private int index_of_firstchar;//Stores the index of the first character of the token (value).
    private TokenType type;// Stores the type of the token.
    /** 
     * @param type To store the type of the token
     * @param Current_Line To store the line on which the token is.
     * @param index_of_firstchar To store the index of the first character of the token
     */
    Token(TokenType type,int Current_Line,int index_of_firstchar)
    {
    	this.setType(type);
        this.setCurrent_Line(Current_Line); 
        this.setIndex_of_firstchar(index_of_firstchar);
     }
    /**
     * 
     * @param type To store the type of the token.
     * @param Current_Line To store the line on which the token is.
     * @param index_of_firstchar To store the index of the first character of the token.
     * @param Value To store the token as string.
     */
    Token(TokenType type,int Current_Line,int index_of_firstchar,String Value)
    {
    	this.setType(type);
        this.setCurrent_Line(Current_Line); 
        this.setIndex_of_firstchar(index_of_firstchar);
        this.setValue(Value);
     }
   /**
    * 
    * @return to return the output as given in the assignment.
    */
    public String toString() 
    {
    	String output="";
        while (getValue() != null&& getValue()!=";"&&getValue()!="\n")
        {
        	output=getType()+"("+ getValue() +")   ";
            return output;
        }
        if(getType()==TokenType.SEPERATOR)
            return (getType().toString()+"\n");
        else
        	return (getType()+" ");
    }
    /**
     * 
     * mutators and accessory generated while making j-unite test as the members are private
     */
public String getValue()
{
	return Value;
}
public void setValue(String value)
{
	Value = value;
}
public TokenType getType() 
{
	return type;
}
public void setType(TokenType type) 
{
	this.type = type;
}
public int getCurrent_Line()
{
	return Current_Line;
}
public void setCurrent_Line(int current_Line) 
{
	Current_Line = current_Line;
}
public int getIndex_of_firstchar() 
{
	return index_of_firstchar;
}
public void setIndex_of_firstchar(int index_of_firstchar)
{
	this.index_of_firstchar = index_of_firstchar;
} 
}
