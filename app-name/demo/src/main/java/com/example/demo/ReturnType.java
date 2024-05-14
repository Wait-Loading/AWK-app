package com.example.demo;


/**
 * @author jayp1
 *
 */
public class ReturnType
{
	/*
	 * We will deal with this by making a new class which is the result of executing an instruction.
	 *  It will hold both a string (the return value) and an enum 
	 * – what happened, was it: Normal, Break, Continue, Return
	 */
	enum ReturnTypeEnum
	{
	    NORMAL, BREAK, CONTINUE, RETURN
	}
    private ReturnTypeEnum type;//The type of the statement
    public String getValue() {
		return value;
	}

	private String value;

    public ReturnType(ReturnTypeEnum type) 
    {
        this.type = type;
    }

    public ReturnType(ReturnTypeEnum type, String value)
    {
        this.type = type;
        this.value = value;
    }
    public String toString() 
    {
        return( "ReturnType{ type=" + type +", value= " + value+" }");
    }

	public ReturnTypeEnum getType() {
		return type;
	}
}


