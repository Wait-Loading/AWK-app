package com.example.demo;

import java.util.LinkedList;
import java.util.Optional;
 
public class Parser 
{
	private TokenManager Manager;  //Making object of Token Manager class to handle the stream of tokens.
	/**
	 * 
	 * @param tokens: LinkedList of tokens as an input.
	 */
 public Parser(LinkedList<Token> tokens) //Constructor for Parser
 {
	 Manager = new TokenManager(tokens);
 }
 /**
  * AcceptSeperators for accepting the next line.
  * @return true if there is even a single separator for accepting else false
  */
 boolean AcceptSeperators()
 {
	 if(Manager.MoreTokens())
	 {
	 if((Manager.Peek(0).get().getType())==Token.TokenType.SEPERATOR)//Peeking for a seperator
	 {
		 while(Manager.MatchAndRemove(Token.TokenType.SEPERATOR).isPresent()&&Manager.MoreTokens())//Matching and removing SEPERATOR as we find them only if there are more tokens
				 {
			        
				 }
		 return true;
	 }
	 else
	 {
	return false;
     }
 }
	 else 
		 return false;
 }
 /**
  * Parse function returning Program_Node or giving an exception
  * @return The Program Node 
  */
public ProgramNode Parse()
{
	ProgramNode pn=new ProgramNode();//Creating a new Program node
	while(Manager.MoreTokens()) //Looping over parse calls
	{
		boolean Function=ParseFunction(pn);
		boolean Action= parseAction(pn);
	
		 if(Function ==false && Action==false)//if both of the Parse function and parse action is false then it should return an exception.
		 {	
			 if(Manager.Peek(0).isPresent())
		 {

			
			 System.out.println("Unknown Item is found at line "+Manager.Peek(0).get().getCurrent_Line()+" at position "+Manager.Peek(0).get().getIndex_of_firstchar()
					 +" That is at "+ Manager.Peek(0).get().getValue()+ Manager.Peek(0).get().getType());
			 break;
			
		 }
		 }
     }
	return pn;//return program node;
}
/**
 * 
 * @param programNode node created in Parse() 
 * @return true if the program node is added successfully else false (For parser 1 this method do not return false.)
 */

boolean parseAction(ProgramNode programNode) 
{	
	 
    AcceptSeperators();
   if (Manager.MatchAndRemove(Token.TokenType.BEGIN).isPresent()) //If the Begin token is present it should ParseBlock() (just returns a new BlockNode, for now) and return true
   {
       // This is a BEGIN block
       BlockNode beginBlock = ParseBlock();
       programNode.addStartBlock(beginBlock);
       
  	    AcceptSeperators();
       return true;
   }  
   else if (Manager.MatchAndRemove(Token.TokenType.END).isPresent()) //Else if the end token is present
   {
       // This is a END block
       BlockNode ENDblock = ParseBlock();
       programNode.addEndBlock(ENDblock);
  	    AcceptSeperators();
       return true;
   }
   else 
       {
        BlockNode block = ParseBlock();
        programNode.addBlock(block);
   	    AcceptSeperators();
        return true;
       }
	
}

/**
 * 
 * @param program node created in Parse() 
 * @return true if the function node is created else false
 */
boolean ParseFunction(ProgramNode pn) 
{
    if (Manager.MatchAndRemove(Token.TokenType.FUNCTION).isPresent())
    {
        FunctionDefinitionNode fn = new FunctionDefinitionNode();
        
        // Parse the function name
        String name = Manager.MatchAndRemove(Token.TokenType.WORDS).get().getValue();
        fn.addName(name);

        if (Manager.MatchAndRemove(Token.TokenType.OPENPARA).isPresent()) 
        {
            // Parse parameters
            while (true) {
                AcceptSeperators();
                Optional<Token> paramNameToken = Manager.MatchAndRemove(Token.TokenType.WORDS);

                if (paramNameToken.isPresent()) 
                {
                    fn.addParameters(paramNameToken.get().getValue());
                } 
               
                AcceptSeperators();
                Optional<Token> commaToken = Manager.MatchAndRemove(Token.TokenType.COMMA);

                if (!commaToken.isPresent()) {
                	AcceptSeperators();
                	if(Manager.MatchAndRemove(Token.TokenType.CLOSEPARA).isEmpty())//if no close parenthesis exists then its wrong
                		return false;
                	else
                    break; // No more parameters, exit the loop
                }
            }
            
            AcceptSeperators();//accepting any next line
            if(Manager.Peek(0)!=null)
            {
            if (Manager.Peek(0).get().getType()==	(Token.TokenType.OPENCURLY)) 
            {
                // Parse function body (block of statements)
                BlockNode blockNode = ParseBlock();
                fn.addStatement(blockNode.getStatements());
                pn.addFunctionNode(fn);
                return true;
            } 
            else
            {
                // returns false as we expected '{' for function body
                return false;
            }
            }
            else
            {
                // returns false as we expected '{' for function body
                return false;
            }
        } 
        else
        {
            // returns false as we expected '(' for parameter list
            return false;
        }
    }
    else
    {
    return false; // Not a function
    }
}

/**
 * To parse a block 
 * @return A block node
 */
 BlockNode ParseBlock() 
 {        	  
	    BlockNode blockNode = new BlockNode();
	    LinkedList<StatementNode> statements = new LinkedList<StatementNode>();

	    // Multi-line block
	    if (Manager.MatchAndRemove(Token.TokenType.OPENCURLY).isPresent()) {
	        AcceptSeperators();
	        while (true) {
	            AcceptSeperators();
	            if (Manager.MatchAndRemove(Token.TokenType.CLOSECURLY).isPresent()) {
	                break;
	            }
	            Optional<Node> statement = ParseStatement();
	            if (statement.isPresent()) {
	                statements.add((StatementNode) statement.get());
	                AcceptSeperators();
	            } else {
	                break;
	            }
	        }
	        blockNode.setStatements(statements);
	        return blockNode;
	    }
	    else 
	    {
	        AcceptSeperators();
	        Optional<Node> statement = ParseStatement();
	        if (statement.isPresent()) 
	        {
	            statements.add((StatementNode) statement.get());
	            AcceptSeperators();
	        }
	        blockNode.setStatements(statements);
	        return blockNode;
	    }
	}
/**
 * The parsing ParseStatement() that will try to parse each of the statement types, returning the first one that succeeds. In English, this is something like “are you an IF? No, OK, are you a WHILE? No, OK, are you a something else
 * @return the statement node of whatever the function there is present else the parse operation node
 */
private  Optional<Node> ParseStatement()
{
	if(Manager.MatchAndRemove(Token.TokenType.IF).isPresent())
	{
		Optional<Node> node= parseIF();//Making an if node by calling parse If
		return node;
	}
	else if(Manager.MatchAndRemove(Token.TokenType.FOR).isPresent())
	{
		Optional<Node> node= parseFor();//Making for node by calling parse for
		return node;

	}
	else if(Manager.MatchAndRemove(Token.TokenType.CONTINUE).isPresent())
	{
		Optional<Node> node= parseContinue();//Making Continue node by calling parse Continue
		return node;

	}
	else if(Manager.MatchAndRemove(Token.TokenType.BREAK).isPresent())
	{
		Optional<Node> node= parseBreak();//Making Break node by calling parse break
		return node;

	}
	else if(Manager.MatchAndRemove(Token.TokenType.DELETE).isPresent())
	{
		Optional<Node> node= parseDelete();
		return node;

	}
	else if(Manager.MatchAndRemove(Token.TokenType.WHILE).isPresent())
	{
		Optional<Node> node= parseWhile();
		return node;

	}
	else if(Manager.MatchAndRemove(Token.TokenType.DO).isPresent())
	{
		Optional<Node> node= parseDO_while();
		return node;

	}
	else if(Manager.MatchAndRemove(Token.TokenType.RETURN).isPresent())
	{
		Optional<Node> node= parseReturn();
		return node;
	}
	return parseOperation();
}
/**
 * Parses the continue method by making an continue node
 * @return The Continue Node 
 */
Optional<Node> parseContinue()
{
	ContinueNode cn= new ContinueNode();
	return Optional.of(cn);
}
/**
 * Makes an break node
 * @return The Break Node 
 */
Optional<Node> parseBreak()
{
	BreakNode BN= new BreakNode();
	return Optional.of(BN);
}
/**
 * Makes if node and keeps making else/if  node by implementing a linked list
 * @return the if node else nothing and displays an error message if there is an error. 
 */
public Optional<Node> parseIF() {
    Optional<Node> condition = parseOperation();
    Optional<BlockNode> block = Optional.of((BlockNode) ParseBlock());

    if (condition.isPresent()) {
        try {
            IfNode ifNode = new IfNode((OperationNode) condition.get(), block.get().getStatements());
            IfNode current = ifNode;

            while (Manager.Peek(0).get().getType() == Token.TokenType.ELSE && Manager.Peek(1).get().getType() == Token.TokenType.IF) {
                Manager.MatchAndRemove(Token.TokenType.ELSE);
                Manager.MatchAndRemove(Token.TokenType.IF);

                OperationNode elseIfCondition = (OperationNode) ParseAssignment().get();
                BlockNode elseIfBlock = (BlockNode) ParseBlock();

                IfNode elseIfNode = new IfNode(elseIfCondition, elseIfBlock.getStatements());
                current.setNext(elseIfNode);
                current = elseIfNode;  // Update the current node to the new elseIfNode
            }

            if (Manager.MatchAndRemove(Token.TokenType.ELSE).isPresent()) {
                BlockNode elseBlock = (BlockNode) ParseBlock();
                current.setNext(new IfNode(null, elseBlock.getStatements()));
            }

            return Optional.of(ifNode);
        } catch (ClassCastException e) {
            System.out.println("Expected condition at " + Manager.Peek(0).get().getValue() + " at line " + Manager.Peek(0).get().getCurrent_Line());
        }
    }

    return Optional.empty();
}



/*
 * 
Optional<Node> parseIF()
{
	Optional<Node> ON= parseOperation();//calling parseoperation to get the condition of the if statement 
	Optional<BlockNode> BN= Optional.of((BlockNode) ParseBlock());//Calling parse block to get what the if condition should do 
	if(ON.isPresent())//if the condition for the if node is present 
	{
		try {
	IF_NODE If_n = new IF_NODE((OperationNode) ON.get(), BN.get());
		
	
   if(Manager.Peek(0).get().getType()==Token.TokenType.ELSE && Manager.Peek(1).get().getType()==Token.TokenType.IF) 
    {
	  while( Manager.MatchAndRemove(Token.TokenType.ELSE).isPresent() && Manager.MatchAndRemove(Token.TokenType.IF).isPresent())//The while loop for if statements
	  {
	    OperationNode ON1= (OperationNode) ParseAssignment().get();
		BlockNode BN1= (BlockNode) ParseBlock();
		IF_NODE ElseIf_n = new IF_NODE(ON1, BN1);
	    If_n.AddElseIf(ElseIf_n); 
	  }
	   return Optional.of(If_n);
    }
   else if(Manager.MatchAndRemove(Token.TokenType.ELSE).isPresent())
   {
	   If_n.SetElseBlock((BlockNode) ParseBlock());
	   return Optional.of(If_n);
   }
		}
	catch(ClassCastException e)
	{
		System.out.println("Expected condition at"+ Manager.Peek(0).get().getValue()+" at line "+ Manager.Peek(0).get().getCurrent_Line());	
	}
	
}
	return Optional.empty();
}*/
/**
 * Parses both for loops the for a in array and the conventional for loop 
 * @return The node of the for loop if there is no error else the error message
 */
Optional<Node> parseFor()
{  
	if(Manager.MatchAndRemove(Token.TokenType.OPENPARA).isPresent())
	{
		Optional<Node> Assignment=parseOperation();//getting the assigment for the for loop 
     	  AcceptSeperators();
		Optional<Node> condition= parseOperation();
   	  AcceptSeperators();
		Optional<Node> operator= parseOperation();		
		if(condition.isPresent()&& operator.isPresent())//Checking if condition and operator node is present to create the for(  ;  ; )  node
		{
			
			if(!Manager.MatchAndRemove(Token.TokenType.CLOSEPARA).isPresent())
			{
				System.out.println("Expected Close paranthesis at"+ Manager.Peek(0).get().getValue()+" at line"+ Manager.Peek(0).get().getCurrent_Line());	

			}
		   	  AcceptSeperators();
			BlockNode BN= (BlockNode) ParseBlock();
			ForNode FN= new ForNode(Assignment.get(), condition.get(), operator.get(),BN);
			return Optional.of(FN);
		}
		else if (Assignment.isPresent())//if only the assignment is present then the node returned is for each node
		{
			if(!Manager.MatchAndRemove(Token.TokenType.CLOSEPARA).isPresent())
			{
				System.out.println("Expected Close paranthesis at"+ Manager.Peek(0).get().getValue()+" at line"+ Manager.Peek(0).get().getCurrent_Line());	

			}
			BlockNode BN= (BlockNode) ParseBlock();
			ForEachNode FN= new ForEachNode(Assignment.get(), BN);
			
			return Optional.of(FN);
		}
		else
			System.out.println("Expected Assignement after"+ Manager.Peek(0).get().getValue()+" at line"+ Manager.Peek(0).get().getCurrent_Line());	
		if(!Manager.MatchAndRemove(Token.TokenType.CLOSEPARA).isPresent())
		{
			System.out.println("Expected Close paranthesis at"+ Manager.Peek(0).get().getValue()+" at line"+ Manager.Peek(0).get().getCurrent_Line());	

		}
	}
	else
	{
		System.out.println("Expected openparameter after"+ Manager.Peek(0).get().getValue()+" at line"+ Manager.Peek(0).get().getCurrent_Line());
		System.exit(0);
	}
	return Optional.empty();
}
/**
 * parses the delete node
 * @return returns delete node
 */
Optional<Node> parseDelete()
{
	if(Manager.Peek(0).get().getType()==Token.TokenType.WORDS)
	{
		Node name=parseOperation().get();
		DeleteNode dn= new DeleteNode(name);
	    return Optional.of(dn);
	}
	else 
	{
		System.out.println("Expected a variable after"+ Manager.Peek(0).get().getValue()+" at line"+ Manager.Peek(0).get().getCurrent_Line());
		System.exit(0);
	return Optional.empty();	
	}
}
	/**
	 * Parses a while loop 
	 * @return the while node of the loop if the loop is written without any syntax error 
	 */
Optional<Node> parseWhile()
{
	Optional<Node> ON= parseOperation();
	Optional<BlockNode> BN= Optional.of((BlockNode) ParseBlock());
	if(ON.isPresent()&& BN.isPresent())
	{
	WhileNode W_n = new WhileNode(ON.get(), BN.get());
	return Optional.of(W_n);
    }
	return Optional.empty();
}
/**
 * Parses the do while Loop 
 * @return the do while node if the loop is written without any syntax error 
 */
Optional<Node> parseDO_while()
{
	Optional<BlockNode> BN= Optional.of((BlockNode) ParseBlock());
	if(Manager.MatchAndRemove(Token.TokenType.WHILE).isPresent())
	{
	Optional<Node> ON= parseOperation();
	if(ON.isPresent()&& BN.isPresent())
	{
	DoNode DW_n = new DoNode( BN.get(), ON.get());
	return Optional.of(DW_n);
    }
	else 
	{
	System.out.println("Expected (Condition) after"+ Manager.Peek(0).get().getValue()+" at line"+ Manager.Peek(0).get().getCurrent_Line());
	System.exit(0);
	return Optional.empty();
	}
	}
	else {
	System.out.println("Expected While(Condition) after"+ Manager.Peek(0).get().getValue()+" at line"+ Manager.Peek(0).get().getCurrent_Line());
	System.exit(0);
	return Optional.empty();
}
}
/**
 * Parses the return statement
 * @return The return node
 */
Optional<Node> parseReturn()
{
	
	ReturnNode Rn= new ReturnNode(parseOperation().get());
	return Optional.of(Rn);
}
/**
 * Parses the function call method
 * @return the node of the function call
 */
Optional<Node> parseFunctionCall()
{
	String Fname= "";

if(	Manager.Peek(0).get().getType()==Token.TokenType.WORDS)
{
	 Fname= Manager.MatchAndRemove(Token.TokenType.WORDS).get().getValue();
	 LinkedList<Node> parameters = new LinkedList<Node>();
     if(Manager.MatchAndRemove(Token.TokenType.OPENPARA).isPresent())
     {
     while (true) {
         Optional<Node> param = parseOperation();
         if (param.isPresent()) 
         {
             parameters.add(param.get());
             if (!Manager.MatchAndRemove(Token.TokenType.COMMA).isPresent())
             {
                 break;
             }
         } 
         else 
         {
             break;
         }
     }
     if (Manager.MatchAndRemove(Token.TokenType.CLOSEPARA).isPresent()) 
     {
         return Optional.of(new FunctionCallNode(Fname, parameters));
     }
     }
}
else {
if(Manager.Peek(0).get().getType()==Token.TokenType.PRINT )
{
	Manager.MatchAndRemove(Token.TokenType.PRINT);
	 Fname="print";
}
else if(Manager.Peek(0).get().getType()==Token.TokenType.GETLINE)
{
	Manager.MatchAndRemove(Token.TokenType.GETLINE);
	 Fname="getline";
}
else if(Manager.Peek(0).get().getType()==Token.TokenType.PRINTF)
{
	Manager.MatchAndRemove(Token.TokenType.PRINTF);
	 Fname="printf";
}
else if(Manager.Peek(0).get().getType()==Token.TokenType.NEXT)
{
	Manager.MatchAndRemove(Token.TokenType.NEXT);
	 Fname="next";
}
else if(Manager.Peek(0).get().getType()==Token.TokenType.EXIT)
{
	Manager.MatchAndRemove(Token.TokenType.EXIT);
	 Fname="exit";
}
else if(Manager.Peek(0).get().getType()==Token.TokenType.NEXTFILE)
{
	Manager.MatchAndRemove(Token.TokenType.NEXTFILE);
	 Fname="nextfile";
}
LinkedList<Node> parameters = new LinkedList<Node>();
if(Manager.MatchAndRemove(Token.TokenType.OPENPARA).isPresent())
{
while (true) {
    Optional<Node> param = parseOperation();
    if (param.isPresent()) 
    {
        parameters.add(param.get());
        if (!Manager.MatchAndRemove(Token.TokenType.COMMA).isPresent())
        {
            break;
        }
    } 
    else 
    {
        break;
    }
}
if (Manager.MatchAndRemove(Token.TokenType.CLOSEPARA).isPresent()) 
{
    return Optional.of(new FunctionCallNode(Fname, parameters));
}
else
{
	System.out.println("EXCEPTION : NO CLOSE PARENTHESIS at line " + Manager.Peek(0).get().getCurrent_Line()+ " index "+Manager.Peek(0).get().getIndex_of_firstchar());
	System.exit(0);
}
}

while (true) {
	
    Optional<Node> param = parseOperation();
    if (param.isPresent()) 
    {
        parameters.add(param.get());
        if (!Manager.MatchAndRemove(Token.TokenType.COMMA).isPresent() || AcceptSeperators())
        {
            break;
        }
    } 
    else 
    {
        break;
    }
}
return Optional.of(new FunctionCallNode(Fname, parameters));
}
	    return Optional.empty();
}
/**
 * To parse a operation (test for parser 2)
 * @return The bottom level node
 */
 Optional<Node> parseOperation() //Empty ParseOperation
{
 	 Optional<Node> ON= ParseAssignment();
	return ON;
}
/**
 * To parse the bottom level 
 * @return the operation node if it is an unary operation or constant node if the token is a string literal 
 *         or a number or pattern node if it is a 
 *         pattern else it will return parse L value which means we are not at the bottom level and we need to parse towards the bottom level;
 */
private Optional<Node> ParseBottomLevel()
{
	if(Manager.Peek(0).get().getType()==Token.TokenType.STRINGLITERAL)//if the token is of string literal
	{
		ConstantNode  cn = new ConstantNode(Manager.MatchAndRemove(Token.TokenType.STRINGLITERAL).get().getValue());//object of a constant node which is to be returned
	    return Optional.of(cn);
	}
	else if(Manager.Peek(0).get().getType()==Token.TokenType.NUMBERS)//else it it is a number  then the same thing as string literal
	{
		ConstantNode  cn = new ConstantNode(Manager.MatchAndRemove(Token.TokenType.NUMBERS).get().getValue());
		return Optional.of(cn);
	}
	else if(Manager.Peek(0).get().getType()==Token.TokenType.PATTERNS)//If its a pattern then return Pattern node having the necessary node
	{
		PatternNode pn= new PatternNode(Manager.MatchAndRemove(Token.TokenType.PATTERNS).get().getValue());
		return Optional.of(pn);
	}
	else if(Manager.Peek(0).get().getType()==Token.TokenType.OPENPARA)//if it is an openPARAnthesis the it will call parse operation and also check for the closePARAnthesis
	{
		Manager.MatchAndRemove(Token.TokenType.OPENPARA);
		Optional<Node> node= parseOperation();
		if(!Manager.MatchAndRemove(Token.TokenType.CLOSEPARA).isPresent())
		{
			System.out.println("EXCEPTION : NO CLOSE PARENTHESIS at line " + Manager.Peek(0).get().getCurrent_Line()+ " index "+Manager.Peek(0).get().getIndex_of_firstchar());
			System.exit(0);
		}
		return node;		
	}
	/*
	 * LPAREN ParseOperation() RPAREN -> result of ParseOperation
     * NOT ParseOperation() -> Operation(result of ParseOperation, NOT)
     * MINUS ParseOperation() -> Operation(result of ParseOperation, UNARYNEG)
     * PLUS ParseOperation() -> Operation(result of ParseOperation, UNARYPOS)
     * INCREMENT ParseOperation() -> Operation(result of ParseOperation, PREINC)
     * DECREMENT ParseOperation() -> Operation(result of ParseOperation, PREDEC)
     *
	 * Below is code impletaion of the above lines
	 */
	else if(Manager.MatchAndRemove(Token.TokenType.NOT).isPresent())
	{
		Optional<Node> po= parseOperation();
		OperationNode ON= new OperationNode(OperationNode.operations.NOT,po.get());
		return Optional.of(ON);
	}
	else if(Manager.MatchAndRemove(Token.TokenType.SUBTRACT).isPresent())
	{
		Optional<Node> po= parseOperation();
		
		OperationNode ON= new OperationNode(OperationNode.operations.UNARYNEG,po.get());
		return Optional.of(ON);
	}
	else if(Manager.MatchAndRemove(Token.TokenType.ADD).isPresent())
	{
		Optional<Node> po= parseOperation();
		OperationNode ON= new OperationNode(OperationNode.operations.UNARYPOS,po.get());
		return Optional.of(ON);
	}
	else if(Manager.MatchAndRemove(Token.TokenType.PLUS1).isPresent())
	{
		Optional<Node> po= parseOperation();
		OperationNode ON= new OperationNode(OperationNode.operations.PREINC,po.get());
		return Optional.of(ON);
	}
	else if(Manager.MatchAndRemove(Token.TokenType.MINUS1).isPresent())
	{
		Optional<Node> po= parseOperation();
		OperationNode ON= new OperationNode(OperationNode.operations.POSTDEC,po.get());
		return Optional.of(ON);
	}
	else if(Manager.Peek(0).isPresent() && Manager.Peek(1).isPresent())
	{
	 if (Manager.Peek(0).get().getType()==Token.TokenType.WORDS)
    {
		 if(Manager.MoreTokens())
		 {
		    if(Manager.Peek(1).get().getType()==Token.TokenType.OPENPARA)
		    {
		    	Optional<Node> po = parseFunctionCall();
		    	return po;
		    }
		 }
    }
	 else if(Manager.Peek(0).get().getType()==Token.TokenType.PRINT ||Manager.Peek(0).get().getType()==Token.TokenType.PRINTF ||Manager.Peek(0).get().getType()==Token.TokenType.EXIT ||Manager.Peek(0).get().getType()==Token.TokenType.NEXT || Manager.Peek(0).get().getType()==Token.TokenType.GETLINE ||Manager.Peek(0).get().getType()==Token.TokenType.NEXTFILE)
	 {
		 Optional<Node> po = parseFunctionCall();
	    	return po;
	 }
	 return ParseLValue();
	}
	 else if(Manager.Peek(0).get().getType()==Token.TokenType.PRINT ||Manager.Peek(0).get().getType()==Token.TokenType.PRINTF ||Manager.Peek(0).get().getType()==Token.TokenType.EXIT ||Manager.Peek(0).get().getType()==Token.TokenType.NEXT || Manager.Peek(0).get().getType()==Token.TokenType.GETLINE ||Manager.Peek(0).get().getType()==Token.TokenType.NEXTFILE)
	 {
		 Optional<Node> po = parseFunctionCall();
	    	return po;
	 }
	else
	return ParseLValue();
}
/**
 * parsing L value
 * @return Optional node of $ sign and value which it holds left node here can be 
 *         a constant node returned by the parse bottom level or a variable reference Node 
 */
Optional<Node> ParseLValue()
{
	if (Manager.MatchAndRemove(Token.TokenType.DOLLAR).isPresent()) //If $ is present then
	{
		Node Left = ParseBottomLevel().get();//calling Parsebottom level
		OperationNode  object=  new OperationNode(OperationNode.operations.DOLLAR, Left);//creating the object of the operation node
	   return Optional.of(object);
    }
	else if(Manager.Peek(0).get().getType()==Token.TokenType.WORDS)//if a word(variable reference ) is given then make a Variable reference Node 
    {
		Optional<Node> Right;
		String Variable_Name=Manager.MatchAndRemove(Token.TokenType.WORDS).get().getValue();
		if(Manager.MatchAndRemove(Token.TokenType.OPENBOX).isPresent())//checking for an array with open box token"["
    	{
            Right= parseOperation();
            VariableReferenceNode VRN= new VariableReferenceNode(Variable_Name, Right);//Variable name with the index of array
            if(!Manager.MatchAndRemove(Token.TokenType.CLOSEBOX).isPresent())//checking for an array with close box token "]"
            {
            	System.out.println("CLOSE BOX MISSING ");
            	System.exit(0);
            }
            return Optional.of(VRN);
        }
    	else//if no array 
    	{
			//System.out.println(Variable_Name);

            VariableReferenceNode VRN= new VariableReferenceNode(Variable_Name);//Variable Name with no index 
           //System.out.println( VRN.toString());
            return Optional.of(VRN);
    	}
    }
    return Optional.empty(); //if nothing is true then return empty .
}
/**
 * Making nodes for the inc and dec 
 * @return the lower level node
 */
Optional<Node> ParsepostINC_DEC()
{
	if(Manager.Peek(0).isPresent() &&  Manager.Peek(1).isPresent()) 
	{
	    if(Manager.Peek(0).get().getType()==Token.TokenType.WORDS && Manager.Peek(1).get().getType()==Token.TokenType.PLUS1)
	   {
		Node Variable= ParseBottomLevel().get();
		Manager.MatchAndRemove(Token.TokenType.PLUS1);
		OperationNode  object=  new OperationNode(OperationNode.operations.POSTINC, Variable);//creating the object of the operation node
		return Optional.of(object);
	   }
	else if(Manager.Peek(0).get().getType()==Token.TokenType.WORDS && Manager.Peek(1).get().getType()==Token.TokenType.MINUS1)
	    {
		Node Variable= ParseBottomLevel().get();

		Manager.MatchAndRemove(Token.TokenType.MINUS1);

		OperationNode  object=  new OperationNode(OperationNode.operations.POSTDEC, Variable);//creating the object of the operation node
		return Optional.of(object);
	    }
	else
	{
		return ParseBottomLevel();
	}
	}
	else 
	{
	return ParseBottomLevel();
	}
}
/**
 * Making the nodes of the exponents 
 * @return the lower level node
 */
Optional<Node> ParseExponents()
{
    Optional<Node> left= ParsepostINC_DEC();
    while(Manager.MoreTokens())
    {
    if(Manager.MatchAndRemove(Token.TokenType.POWER).isPresent())
    {
    	 Optional<Node> Right=  ParseExponents();
 		 OperationNode  object=  new OperationNode(left.get(),OperationNode.operations.EXPONENT, Right);//creating the object of the operation node
         return Optional.of(object);
    }
    else
    	break;
    }
	return left;
}
Optional<Node> ParseFactor() 
{
	while (Manager.MoreTokens())
	{
		Optional<Node> left = ParseExponents();
    return left; // Return Optional.ofNullable to handle null left
	}
	return Optional.empty();
}
/**
 * Handling "*,%,/" operations
 * @return the lower level node
 */
Optional<Node> ParseTerm()
{
	Optional<Node> left= ParseFactor();
	while(Manager.MoreTokens())
	{
	if(Manager.MatchAndRemove(Token.TokenType.MULTIPLY).isPresent())
	{
		Optional<Node>Right= ParseFactor();
		left =  Optional.of(new OperationNode(left.get(),OperationNode.operations.MULTIPLY, Right));//creating the object of the operation node 
        
	}
	else if(Manager.MatchAndRemove(Token.TokenType.MODULO).isPresent())
	{
		Optional<Node>Right= ParseFactor();
		left =  Optional.of(new OperationNode(left.get(),OperationNode.operations.MODULO, Right));//creating the object of the operation node
        
	}
	else if(Manager.MatchAndRemove(Token.TokenType.DIVIDE).isPresent())
	{
		Optional<Node>Right= ParseFactor();
		left=  Optional.of(new OperationNode(left.get(),OperationNode.operations.DIVIDE, Right));//creating the object of the operation node
     
	}
	 else
	    	break;
	}
	return left;
}
/**
 * Making nodes and handling "+,-"
 * @return the lower level node
 */
Optional<Node> ParseExpression()
{
	Optional<Node> left= ParseTerm();
	while(Manager.MoreTokens())
	{
	if(Manager.MatchAndRemove(Token.TokenType.ADD).isPresent())
	{
		Optional<Node>Right= ParseTerm();
		left=  Optional.of(new OperationNode(left.get(),OperationNode.operations.ADD, Right));//creating the object of the operation node
       
	}
	else if(Manager.MatchAndRemove(Token.TokenType.SUBTRACT).isPresent())
	{
		Optional<Node>Right= ParseTerm();
		left=  Optional.of(new OperationNode(left.get(),OperationNode.operations.SUBTRACT, Right));//creating the object of the operation node
	}
	 else
	    	break;
	}
	return left;
}
/**
 *Handling the string Concatenation
 * @return the lower level node
 */
Optional<Node> ParseConcatenation()
{
	Optional<Node> left=ParseExpression();
	while(Manager.MoreTokens())
	{
	 if(Manager.Peek(0).get().getType()==Token.TokenType.STRINGLITERAL||Manager.Peek(0).get().getType()==(Token.TokenType.WORDS))
	 {
		Optional<Node>Right= ParseExpression();
		left=  Optional.of(new OperationNode(left.get(),OperationNode.operations.CONCATENATION, Right));//creating the object of the operation node
	 }
	 else
	    	break;
	}
	 return left;
}
/**
 * Handling the "<,>,<=,>=,==,!=" and making a new node
 * @return the lower level node
 */
Optional<Node> ParseBooleanCOMPARE()
{
	Optional<Node> left=ParseConcatenation();
	while(Manager.MoreTokens())
	{
	if(Manager.MatchAndRemove(Token.TokenType.EQUALSTO).isPresent())
	{
		Optional<Node>Right= ParseConcatenation();
		left=  Optional.of(new OperationNode(left.get(),OperationNode.operations.EQ, Right));//creating the object of the operation node
		
	}
	else if(Manager.MatchAndRemove(Token.TokenType.LESSTHAN).isPresent())
	{
		Optional<Node>Right= ParseConcatenation();
		left=  Optional.of(new OperationNode(left.get(),OperationNode.operations.LT, Right));//creating the object of the operation node
		
	}
	else if(Manager.MatchAndRemove(Token.TokenType.LESSTHAN_EQUALTO).isPresent())
	{
		Optional<Node>Right= ParseConcatenation();
		left=  Optional.of(new OperationNode(left.get(),OperationNode.operations.LE, Right));//creating the object of the operation node
		
	}
	else if(Manager.MatchAndRemove(Token.TokenType.GREATERTHAN).isPresent())
	{
		Optional<Node>Right= ParseConcatenation();
		left=  Optional.of(new OperationNode(left.get(),OperationNode.operations.GT, Right));//creating the object of the operation node
		
	}
	else if(Manager.MatchAndRemove(Token.TokenType.GREATERTHAN_EQUALTO).isPresent())
	{
		Optional<Node>Right= ParseConcatenation();
		left= Optional.of(new OperationNode(left.get(),OperationNode.operations.GE, Right));//creating the object of the operation node
		
	}
	else if(Manager.MatchAndRemove(Token.TokenType.NOT_EQUALSTO).isPresent())
	{
		Optional<Node>Right= ParseConcatenation();
		left=  Optional.of(new OperationNode(left.get(),OperationNode.operations.NE, Right));//creating the object of the operation node
	}
	 else
	    	break;
	}
	return left;
}
/**
 * Handeling "~,!~" and making it's node
 * @return the Lower level node
 */
Optional<Node> ParseMatch()
{
	Optional<Node> left= ParseBooleanCOMPARE();
	while(Manager.MoreTokens())
	{
	if(Manager.MatchAndRemove(Token.TokenType.MATCH).isPresent())
	{
		Optional<Node>Right= ParseBooleanCOMPARE();
		left=  Optional.of(new OperationNode(left.get(),OperationNode.operations.MATCH, Right));//creating the object of the operation node
	}
	else if (Manager.MatchAndRemove(Token.TokenType.NOMATCH).isPresent())
	{
		Optional<Node>Right= ParseBooleanCOMPARE();
		left=  Optional.of(new OperationNode(left.get(),OperationNode.operations.NOTMATCH, Right));//creating the object of the operation node
	}
	 else
	    	break;
	}
	return left;
}
/**
 * Handling array membership
 * @return The lower level node
 */
Optional<Node> ParseArrayMembership()
{
	Optional<Node> left= ParseMatch();
	while(Manager.MoreTokens())
	{
	if(Manager.Peek(0).get().getType()==(Token.TokenType.IN))
	{
		Manager.MatchAndRemove(Token.TokenType.IN);
		Optional<Node>Right= ParseMatch();
		left=  Optional.of(new OperationNode(left.get(),OperationNode.operations.IN, Right));//creating the object of the operation node
	}
	 else
	    	break;
	}
	return left;
}
/**
 * Handling && and making operation node 
 * @return The lower level node
 */
Optional<Node> ParseAND()
{
	Optional<Node> left= ParseArrayMembership();
	while(Manager.MoreTokens())
	{
	if(Manager.MatchAndRemove(Token.TokenType.AND).isPresent())
	{
		Optional<Node>Right= ParseArrayMembership();
		left= Optional.of(new OperationNode(left.get(),OperationNode.operations.AND, Right));//creating the object of the operation node
	}
	 else
	    	break;
	}
	return left;
}
/**
 * Handling || and making operation node 
 * @return The lower level node
 */
Optional<Node> ParseOR()
{
	Optional<Node> left= ParseAND();
	while(Manager.MoreTokens())
	{
	if(Manager.MatchAndRemove(Token.TokenType.OR).isPresent())
	{
		Optional<Node>Right= ParseAND();
		left=  Optional.of(new OperationNode(left.get(),OperationNode.operations.OR, Right));//creating the object of the operation node
	}
	 else
	    	break;
	}
	return left;
}
/**
 * Handeling Ternary operations
 * @return The lower level node
 */
Optional<Node> ParseTernary()
{
	Optional<Node> left= ParseOR();
	while(Manager.MoreTokens())
	{
	if(Manager.MatchAndRemove(Token.TokenType.QUESTION).isPresent())
	{
		Node TRUE_node=ParseOR().get();
		if(Manager.MatchAndRemove(Token.TokenType.COLON).isPresent())
			{
			Node FALSE_node=ParseOR().get();
			TernaryNode TN = new TernaryNode(left.get(),TRUE_node,FALSE_node);
			return Optional.of(TN);
			}
		else
		{
			System.out.println("ERROE COLON expected");
		    System.exit(0);
		}
	}
	 else
	    	break;
	}
	return left;
}
/**
 * Handling the assignment node
 * @return The whole node
 */
Optional<Node> ParseAssignment()
{
	Optional<Node> left= ParseTernary();
	while(Manager.MoreTokens())
	{
	 if(Manager.MatchAndRemove(Token.TokenType.ASSIGN).isPresent())
	 {
		 Node Node=ParseTernary().get();
			AssignmentNode AN = new AssignmentNode(left.get(),Node);
			return Optional.of(AN); 
	 }
	 /**
	  *making the operation node 
	  *           lvalue ^= expr
      *           lvalue %= expr
      *         lvalue *= expr
      *        lvalue /= expr
      *         lvalue += expr
      *         by returning the necessary node
	  */
	 else if(Manager.MatchAndRemove(Token.TokenType.ADD_BY).isPresent())
	 {
		 Optional<Node> Node=ParseTernary();
		 Node Op= new OperationNode(left.get(),OperationNode.operations.ADD,Node) ;
		 
			AssignmentNode AN = new AssignmentNode(left.get(),Op);
			return Optional.of(AN); 
	 }
	 else if(Manager.MatchAndRemove(Token.TokenType.SUB_BY).isPresent())
	 {
		 Optional<Node> Node=ParseTernary();
		 Node Op= new OperationNode(left.get(),OperationNode.operations.SUBTRACT,Node) ;
		 
			AssignmentNode AN = new AssignmentNode(left.get(),Op);
			return Optional.of(AN);  
	 }
	 else if(Manager.MatchAndRemove(Token.TokenType.DIVIDE_BY).isPresent())
	 {
		 Optional<Node> Node=ParseTernary();
		 Node Op= new OperationNode(left.get(),OperationNode.operations.DIVIDE,Node) ;
		 
			AssignmentNode AN = new AssignmentNode(left.get(),Op);
			return Optional.of(AN); 
	 }
	 else if(Manager.MatchAndRemove(Token.TokenType.MULTIPLY_BY).isPresent())
	 {
		 Optional<Node> Node=ParseTernary();
		 Node Op= new OperationNode(left.get(),OperationNode.operations.MULTIPLY,Node) ;
		 
			AssignmentNode AN = new AssignmentNode(left.get(),Op);
			return Optional.of(AN); 
	 }
	 else if(Manager.MatchAndRemove(Token.TokenType.RAISED_TO).isPresent())
	 {
		 Optional<Node> Node=ParseTernary();
		 Node Op= new OperationNode(left.get(),OperationNode.operations.EXPONENT,Node) ;
		 
			AssignmentNode AN = new AssignmentNode(left.get(),Op);
			return Optional.of(AN); 
	 }
	 else
	    	break;
	 }
	return left;
}
}
