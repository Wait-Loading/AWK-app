package com.example.demo;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author jayp1
 * 
 * test data:
 * 
 * 
 * 
  5 goodbye
  5.23 8.5 3
  $0 = tolower($0)<=;
  "She asked \"How are you\" to him" if while hello do
 * 
 *
 */
public class LexerandParserTest 
{
    private Lexer lexer;
    private TokenManager TM;
    private Parser parser;

    @Before
    public void setUp() 
    {
        // Initialization of the Lexer with the sample input
    	lexer = new Lexer("\r\n"
        		+ "5 goodbye\r\n"
        		+ "5.23 8.5 3\r\n"
        		+ "$0 = tolower($0)<=;\r\n"
        		+ "\"She asked \\\"How are you\\\" to him\""+" if while hello do" +" `.*`");
    	
      //  lexer = new Lexer("$0 = tolower($0)<=;");
    }
    public void setUp2() 
    {
        // Initialize tokenManager and parser with sample tokens 
        LinkedList<Token> tokens = new LinkedList<>();
         TM =  new TokenManager(tokens);
        parser = new Parser(tokens);
    }
    @Test
    public void testLexing()
    {
        List<Token> tokens = lexer.Lex();
        // Making sure the list of tokens is not null
        assertNotNull(tokens);
        // Checking if the number of tokens is correct
        assertEquals(25, tokens.size());
        // Checking the types and values of the tokens
        assertEquals(Token.TokenType.SEPERATOR, tokens.get(0).getType());
        assertEquals(1,tokens.get(0).getCurrent_Line());
        assertEquals(Token.TokenType.NUMBERS, tokens.get(1).getType());
        assertEquals("5", tokens.get(1).getValue());
        assertEquals(2,tokens.get(1).getCurrent_Line());
        assertEquals(Token.TokenType.WORDS, tokens.get(2).getType());
        assertEquals("goodbye", tokens.get(2).getValue());
        assertEquals(Token.TokenType.SEPERATOR, tokens.get(3).getType());
        assertEquals(Token.TokenType.NUMBERS, tokens.get(4).getType());
        assertEquals("5.23", tokens.get(4).getValue());
        assertEquals(3,tokens.get(4).getCurrent_Line());
        assertEquals(Token.TokenType.NUMBERS, tokens.get(5).getType());
        assertEquals("8.5", tokens.get(5).getValue());
        assertEquals(Token.TokenType.NUMBERS, tokens.get(6).getType());
        assertEquals("3", tokens.get(6).getValue());
        assertEquals(Token.TokenType.SEPERATOR, tokens.get(7).getType());
       
        //checking the sample unite-test professor gave
        assertEquals(Token.TokenType.DOLLAR, tokens.get(8).getType());//checking the symbols 
        assertEquals(4,tokens.get(8).getCurrent_Line());
        assertEquals(Token.TokenType.NUMBERS, tokens.get(9).getType());
        assertEquals(Token.TokenType.ASSIGN, tokens.get(10).getType());
        assertEquals(Token.TokenType.WORDS, tokens.get(11).getType());
        assertEquals("tolower", tokens.get(11).getValue());
        assertEquals(Token.TokenType.OPENPARA, tokens.get(12).getType());
        assertEquals(Token.TokenType.DOLLAR, tokens.get(13).getType());
        assertEquals(Token.TokenType.NUMBERS, tokens.get(14).getType());
        assertEquals(Token.TokenType.CLOSEPARA, tokens.get(15).getType());
        assertEquals(Token.TokenType.LESSTHAN_EQUALTO, tokens.get(16).getType());
        assertEquals(Token.TokenType.SEPERATOR, tokens.get(17).getType());
        //Checking first index
        assertEquals(1,tokens.get(0).getIndex_of_firstchar());
        assertEquals(1,tokens.get(1).getIndex_of_firstchar());
        assertEquals(3,tokens.get(2).getIndex_of_firstchar());
        assertEquals(10,tokens.get(3).getIndex_of_firstchar());
        assertEquals(1,tokens.get(4).getIndex_of_firstchar());
        assertEquals(6,tokens.get(5).getIndex_of_firstchar());
        assertEquals(6,tokens.get(11).getIndex_of_firstchar());
        //checking string literal 
        assertEquals(Token.TokenType.STRINGLITERAL,tokens.get(19).getType());
        assertEquals("She asked \"How are you\" to him", tokens.get(19).getValue());
        //checking the process word method
        assertEquals(Token.TokenType.IF, tokens.get(20).getType());
        assertEquals(Token.TokenType.WHILE, tokens.get(21).getType());
        assertEquals(Token.TokenType.WORDS, tokens.get(22).getType());
        assertEquals(Token.TokenType.DO, tokens.get(23).getType());
        assertEquals(5,tokens.get(22).getCurrent_Line());//checking the line number 
        assertEquals(Token.TokenType.PATTERNS,tokens.get(24).getType());
        assertEquals(".*",tokens.get(24).getValue());//checking the pattern method.
        
        
        lexer = new Lexer(" \"how are you? \\n\"");
        List<Token> tokens1 = lexer.Lex();

        
        
    }
    //testing for all the token_manager method
    @Test
    public void Test_Token_Manager() 
    {
    	LinkedList<Token> tokens_MANAGE_Test = new LinkedList<>();
    	
        tokens_MANAGE_Test.add(new Token(Token.TokenType.FUNCTION, 0, 0, "function"));
        tokens_MANAGE_Test.add(new Token(Token.TokenType.WORDS, 0, 1, "Function_name"));
        tokens_MANAGE_Test.add(new Token(Token.TokenType.OPENPARA, 0, 1, "("));
        tokens_MANAGE_Test.add(new Token(Token.TokenType.WORDS, 0, 1, "i"));
        tokens_MANAGE_Test.add(new Token(Token.TokenType.COMMA, 0, 1, ","));
        tokens_MANAGE_Test.add(new Token(Token.TokenType.WORDS, 0, 1, "j"));
        tokens_MANAGE_Test.add(new Token(Token.TokenType.CLOSEPARA, 0, 1, ")"));
        tokens_MANAGE_Test.add(new Token(Token.TokenType.SEPERATOR, 0, 1, "\n"));
        tokens_MANAGE_Test.add(new Token(Token.TokenType.SEPERATOR, 0, 1, "\n"));
        tokens_MANAGE_Test.add(new Token(Token.TokenType.OPENCURLY, 0, 1, "{"));
    	TM =new TokenManager(tokens_MANAGE_Test);
        assertTrue(TM.MoreTokens());//Returns true as it has more tokens
        assertTrue(TM.MatchAndRemove(Token.TokenType.FUNCTION).isPresent());
        assertTrue(TM.MatchAndRemove(Token.TokenType.WORDS).isPresent());
        assertTrue(TM.MatchAndRemove(Token.TokenType.OPENPARA).isPresent());
        assertTrue(TM.MatchAndRemove(Token.TokenType.WORDS).isPresent());
        assertTrue(TM.MatchAndRemove(Token.TokenType.COMMA).isPresent());
        assertTrue(TM.MatchAndRemove(Token.TokenType.WORDS).isPresent());
        assertTrue(TM.MatchAndRemove(Token.TokenType.CLOSEPARA).isPresent());
        assertTrue(TM.Peek(0).get().getType()==Token.TokenType.SEPERATOR);//checking the peeking 
        assertTrue(TM.MatchAndRemove(Token.TokenType.SEPERATOR).isPresent());
        assertTrue(TM.MatchAndRemove(Token.TokenType.SEPERATOR).isPresent());
        assertTrue(TM.MatchAndRemove(Token.TokenType.OPENCURLY).isPresent());
        assertFalse(TM.MoreTokens());//Returning false as it has more tokens 
    }
   
    public void Test_Accept_Seperators()
    {
        // Test when there are separators (newline or semi-colon)
        LinkedList<Token> tokens = new LinkedList<>();
        tokens.add(new Token(Token.TokenType.SEPERATOR, 0, 0));
        tokens.add(new Token(Token.TokenType.SEPERATOR, 1,0 , ";"));
        parser = new Parser(tokens);
        
        assertTrue(parser.AcceptSeperators());
        
        // Test when there are no separators
        LinkedList<Token> tokens2 = new LinkedList<>();
        tokens2.add(new Token(Token.TokenType.WORDS, 0, 0, "hello"));
        parser = new Parser(tokens2);
        
        assertFalse(parser.AcceptSeperators());
    }
    /**
     * The test of the function method
     */
    public void Test_Parse_Function() 
    {
        ProgramNode programNode = new ProgramNode();

        // Test parsing a valid function definition
        LinkedList<Token> Valid_FUNCTION_Token = new LinkedList<>();
        Valid_FUNCTION_Token.add(new Token(Token.TokenType.FUNCTION, 0, 0, "function"));
        Valid_FUNCTION_Token.add(new Token(Token.TokenType.WORDS, 0, 1, "Function_name"));
        Valid_FUNCTION_Token.add(new Token(Token.TokenType.OPENPARA, 0, 1, "("));
        Valid_FUNCTION_Token.add(new Token(Token.TokenType.WORDS, 0, 1, "i"));
        Valid_FUNCTION_Token.add(new Token(Token.TokenType.COMMA, 0, 1, ","));
        Valid_FUNCTION_Token.add(new Token(Token.TokenType.WORDS, 0, 1, "j"));
        Valid_FUNCTION_Token.add(new Token(Token.TokenType.CLOSEPARA, 0, 1, ")"));
        Valid_FUNCTION_Token.add(new Token(Token.TokenType.SEPERATOR, 0, 1, "\n"));
        Valid_FUNCTION_Token.add(new Token(Token.TokenType.OPENCURLY, 0, 1, "{"));
        parser = new Parser(Valid_FUNCTION_Token);
       
        assertTrue(parser.ParseFunction(programNode));
        assertEquals("Function_name", programNode.getFunctionNodes().getFirst().getName());
        assertEquals(2, programNode.getFunctionNodes().get(0).getParameters().size());

        // Test parsing an invalid function definition
        LinkedList<Token> Invalid_FUNCTION_Token = new LinkedList<>();
        Invalid_FUNCTION_Token.add(new Token(Token.TokenType.WORDS, 0, 0, "function_name"));
        Invalid_FUNCTION_Token.add(new Token(Token.TokenType.OPENPARA, 0, 0, "("));
        Invalid_FUNCTION_Token.add(new Token(Token.TokenType.CLOSEPARA, 0, 0, ")"));
        Invalid_FUNCTION_Token.add(new Token(Token.TokenType.SEPERATOR, 0, 0, "\n"));
        parser = new Parser(Invalid_FUNCTION_Token);

        assertFalse(parser.ParseFunction(programNode));
    }

    @Test
    public void Test_Parse_Action() 
    {
        ProgramNode programNode = new ProgramNode();
        // Create a Parser instance with the tokens to parse
        LinkedList<Token> tokens = new LinkedList<>();
        // Populate tokens with the tokens to parse
        
        // For example to parse a simple assignment statement:
        tokens.add(new Token(Token.TokenType.WORDS, 0, 0, "i"));
        tokens.add(new Token(Token.TokenType.ASSIGN, 0, 0, "="));
        tokens.add(new Token(Token.TokenType.NUMBERS, 0, 0, "0"));
        tokens.add(new Token(Token.TokenType.SEPERATOR, 0, 0, ";"));
        Parser parser = new Parser(tokens);
        
      
        
        
        // Assert the result of the parsing action
        assertTrue(parser.parseAction(programNode)); // Assuming the parsing is successful
        assertEquals(1,programNode.getBlocks().size()); // Checking if the BEGIN block is added or not.

        //Checking the begin block of the program.
        LinkedList<Token> tokens2= new LinkedList<>();
        tokens2.add(new Token(Token.TokenType.BEGIN,0,0,"BEGIN"));
        tokens2.add(new Token(Token.TokenType.BEGIN,0,0,"\n"));
        tokens2.add(new Token(Token.TokenType.OPENCURLY,0,0,"{"));
        tokens2.add(new Token(Token.TokenType.WORDS, 0, 0, "i"));
        tokens2.add(new Token(Token.TokenType.ASSIGN, 0, 0, "="));
        tokens2.add(new Token(Token.TokenType.NUMBERS, 0, 0, "0"));
        tokens2.add(new Token(Token.TokenType.SEPERATOR, 0, 0, ";"));
        tokens2.add(new Token(Token.TokenType.CLOSECURLY,0,0,"}"));
         parser = new Parser(tokens2);
        // Assert the result of the parsing action
        assertTrue(parser.parseAction(programNode)); // Assuming the parsing is successful
        assertEquals(1,programNode.getStartBlocks().size()); // Checking if the BEGIN block is added or not.
        // Checking the "end" block of the program
       LinkedList<Token> endTokens = new LinkedList<>();
        endTokens.add(new Token(Token.TokenType.END, 0, 0, "END"));
        endTokens.add(new Token(Token.TokenType.BEGIN, 0, 0, "\n"));
        endTokens.add(new Token(Token.TokenType.OPENCURLY, 0, 0, "{"));
        endTokens.add(new Token(Token.TokenType.WORDS, 0, 0, "i"));
        endTokens.add(new Token(Token.TokenType.ASSIGN, 0, 0, "="));
        endTokens.add(new Token(Token.TokenType.NUMBERS, 0, 0, "0"));
        endTokens.add(new Token(Token.TokenType.SEPERATOR, 0, 0, ";"));
        endTokens.add(new Token(Token.TokenType.CLOSECURLY, 0, 0, "}"));
        // Create a new parser instance for the "end" block
       Parser endParser = new Parser(endTokens);
         
         
        assertTrue(endParser.parseAction(programNode));
        assertEquals(1,programNode.getEndBlocks().size());// Checking if the END block is added or not.
        assertFalse(programNode.getEndBlocks().size()==3);//checking the size of the block.
    } 
    @Test
    public void Test_ParseL_Value()
    {
    	
         LinkedList<Token> tokens = new LinkedList<>();
         // Populate tokens with the tokens to parse
         
         //Testing the calling of Variable definition Node by ParseLvalue
         tokens.add(new Token(Token.TokenType.WORDS, 0, 0, "i"));
         Parser parser = new Parser(tokens);
         VariableReferenceNode VN=(VariableReferenceNode) parser.parseOperation().get();
         assertEquals("i",VN.getVAR_name());
        
         //Testing the calling of Constant Node by ParseLvalue 
         LinkedList<Token> tokens2 = new LinkedList<>();
         tokens2.add(new Token(Token.TokenType.NUMBERS, 0, 0, "0"));//Adding a number node in token to work with Constant node
         Parser parser2 = new Parser(tokens2);   
         ConstantNode CN=(ConstantNode) parser2.parseOperation().get();
         assertEquals("0",CN.getValue());
         
         //Testing the calling of PatternNode by ParseLvalue
         LinkedList<Token> tokens3 = new LinkedList<>();
         tokens3.add(new Token(Token.TokenType.PATTERNS, 0, 0, "`a/*-/b`"));// Adding a pattern node in token
         Parser parser3 = new Parser(tokens3);   
         PatternNode PN=(PatternNode) parser3.parseOperation().get();
         assertEquals("`a/*-/b`",PN.getValue());
         
         
         //Testing the calling of Variable definition Node by ParseLvalue with the array open and close 
         LinkedList<Token> tokens4 = new LinkedList<>();
         tokens4.add(new Token(Token.TokenType.WORDS, 0, 0, "i"));
         tokens4.add(new Token(Token.TokenType.OPENBOX, 0, 0, "["));
         tokens4.add(new Token(Token.TokenType.NUMBERS, 0, 0, "3"));
         tokens4.add(new Token(Token.TokenType.CLOSEBOX, 0, 0, "]"));
         Parser parser4 = new Parser(tokens4);
         VariableReferenceNode VN1=(VariableReferenceNode) parser4.parseOperation().get();
         assertEquals("i",VN1.getVAR_name());
         ConstantNode CN4=(ConstantNode) VN1.getIndex_exp().get();
         assertEquals("3",CN4.getValue());
    }
    @Test
    public void Test_ParseBottomLevel()
    {
         LinkedList<Token> tokens = new LinkedList<>();
         // Populate tokens with the tokens to parse
         
         //Testing the calling of Variable definition Node by ParseLvalue
         tokens.add(new Token(Token.TokenType.STRINGLITERAL, 0, 0, "WORD"));
         Parser parser = new Parser(tokens);
         ConstantNode CN=(ConstantNode) parser.parseOperation().get();
         assertEquals("WORD",CN.getValue());
         
         
         LinkedList<Token> tokens2 = new LinkedList<>();
         //Testing the calling of Variable definition Node by ParseLvalue
         tokens2.add(new Token(Token.TokenType.NOT, 0, 0, "!"));
         tokens2.add(new Token(Token.TokenType.WORDS, 0, 0, "i"));
         Parser parser2 = new Parser(tokens2);
         OperationNode ON=(OperationNode) parser2.parseOperation().get();
         assertEquals(OperationNode.operations.NOT,ON.getOp());
    }
    @Test
    public void Test_Parser3()
    {
    	 LinkedList<Token> tokens = new LinkedList<>();
         // Populate tokens with the tokens to parse
    	// Add tokens for the expression a+b)/a-b
    	 tokens.add(new Token(Token.TokenType.WORDS, 0, 0, "a"));
    	 tokens.add(new Token(Token.TokenType.ADD, 0, 0, "+"));
    	 tokens.add(new Token(Token.TokenType.WORDS, 0, 0, "b"));
    	 tokens.add(new Token(Token.TokenType.DIVIDE, 0, 0, "/"));
    	 tokens.add(new Token(Token.TokenType.WORDS, 0, 0, "a"));
    	 tokens.add(new Token(Token.TokenType.SUBTRACT, 0, 0, "-"));
    	 tokens.add(new Token(Token.TokenType.WORDS, 0, 0, "b"));
    	 Parser parser_TEST1 = new Parser(tokens);//The first test
         OperationNode ON=(OperationNode) parser_TEST1.parseOperation().get();
         /**
          * The asserts equals here is checking for the following tree
          * first we print all the left nodes and then operations if any and lastly the right nodes 
          *       -
          *      / \
          *     +   b
          *    / \
          *   a   ÷
          *      / \
          *     b   a
          *       
          */
         assertEquals("OperationNode:\n"
         		+ "  Left Node: OperationNode:\n"
         		+ "  Left Node: The variable node  is a\n"
         		+ "  Operation: ADD\n"
         		+ "  Right Node: OperationNode:\n"
         		+ "  Left Node: The variable node  is b\n"
         		+ "  Operation: DIVIDE\n"
         		+ "  Right Node: The variable node  is a\n"
         		+ "\n"
         		+ "\n"
         		+ "  Operation: SUBTRACT\n"
         		+ "  Right Node: The variable node  is b\n"
         		+ "",ON.toString());
         
         //Second test also testing the assignment node
         Lexer lexer2 = new Lexer("a^=b^y^x");//Lexing the Expression 
    	 LinkedList<Token> tokens_1= (LinkedList<Token>) lexer2.Lex();
    	 assertEquals(7, tokens_1.size());
    	 assertEquals(Token.TokenType.WORDS, tokens_1.get(0).getType());
    	 assertEquals(Token.TokenType.RAISED_TO, tokens_1.get(1).getType());
    	 assertEquals(Token.TokenType.WORDS, tokens_1.get(2).getType());
    	 assertEquals(Token.TokenType.POWER, tokens_1.get(3).getType());
    	 assertEquals(Token.TokenType.WORDS, tokens_1.get(4).getType());
    	 assertEquals(Token.TokenType.POWER, tokens_1.get(5).getType());
    	 assertEquals(Token.TokenType.WORDS, tokens_1.get(6).getType());
    	 Parser parser_TEST2 = new Parser( tokens_1);
         Node ON2=parser_TEST2.parseOperation().get();
         String output=ON2.toString();//Storing the output 
         /**
          * This is an expression "a^=b^y^x"
          * That will have 
          *                      =
          *                    /   \
          *                   a     ^
          *                       /   \
          *                      b     ^
          *                          /   \ 
          *                         y     x
          *                       
          *                       
          *                       
          */
         assertEquals("Target:The variable node  is a\n"
         		+ " EXPRESSION= OperationNode:\n"
         		+ "  Left Node: The variable node  is a\n"
         		+ "  Operation: EXPONENT\n"
         		+ "  Right Node: OperationNode:\n"
         		+ "  Left Node: The variable node  is b\n"
         		+ "  Operation: EXPONENT\n"
         		+ "  Right Node: OperationNode:\n"
         		+ "  Left Node: The variable node  is y\n"
         		+ "  Operation: EXPONENT\n"
         		+ "  Right Node: The variable node  is x\n"
         		+ "\n"
         		+ "\n"
         		+ "",output);
         
         //Test 3
         Lexer lexer3 = new Lexer("a= (2^3)^5");
    	 LinkedList<Token> tokens_2= (LinkedList<Token>) lexer3.Lex();
    	 Parser parser_TEST3 = new Parser( tokens_2);
         Node ON3=parser_TEST3.parseOperation().get();
         String output1=ON3.toString();
         assertEquals("Target:The variable node  is a\n"
         		+ " EXPRESSION= OperationNode:\n"
         		+ "  Left Node: OperationNode:\n"
         		+ "  Left Node: The Value in this node is 2\n"
         		+ "  Operation: EXPONENT\n"
         		+ "  Right Node: The Value in this node is 3\n"
         		+ "\n"
         		+ "  Operation: EXPONENT\n"
         		+ "  Right Node: The Value in this node is 5\n"
         		 ,output1);
         
         //Test 4
         /**
          * The tree for the expression((a+b)*c/d-5) is 
          *                         -
          *                       /   \
          *                      ÷     5
          *                    /   \
          *                   *     d
          *                 /   \
          *                +     c
          *              /   \
          *             a     b
          * 
          * 
          * 
          */
         Lexer lexer4 = new Lexer("((a+b)*c/d-5);");
    	 LinkedList<Token> tokens_3= (LinkedList<Token>) lexer4.Lex();
    	 Parser parser_TEST4 = new Parser(tokens_3);
         Node ON4=parser_TEST4.parseOperation().get();
         String output2=ON4.toString();
        assertEquals("OperationNode:\n"
        		+ "  Left Node: OperationNode:\n"
        		+ "  Left Node: OperationNode:\n"
        		+ "  Left Node: OperationNode:\n"
        		+ "  Left Node: The variable node  is a\n"
        		+ "  Operation: ADD\n"
        		+ "  Right Node: The variable node  is b\n"
        		+ "\n"
        		+ "  Operation: MULTIPLY\n"
        		+ "  Right Node: The variable node  is c\n"
        		+ "\n"
        		+ "  Operation: DIVIDE\n"
        		+ "  Right Node: The variable node  is d\n"
        		+ "\n"
        		+ "  Operation: SUBTRACT\n"
        		+ "  Right Node: The Value in this node is 5\n"
        		+ ""
         		 ,output2);
        
        //Test 5 for ternary operator
        Lexer lexer5 = new Lexer("y = (a>b)?a:b;");
   	    LinkedList<Token> tokens_4= (LinkedList<Token>) lexer5.Lex();
   	    Parser parser_TEST5 = new Parser(tokens_4);
        Node ON5=parser_TEST5.parseOperation().get();
        String output3=ON5.toString();
       assertEquals("Target:The variable node  is y\n"
       		+ " EXPRESSION= CONDITION: OperationNode:\n"
       		+ "  Left Node: The variable node  is a\n"
       		+ "  Operation: GT\n"
       		+ "  Right Node: The variable node  is b\n"
       		+ "\n"
       		+ "TRUECASE:The variable node  is a\n"
       		+ "FALSECASE:The variable node  is b",output3);
       
     //Test 6 for String Concat
       /**
        *          =
        *         /  \
        *        y   Con
        *           /  \
        *          a    b
        */
       Lexer lexer6 = new Lexer("print \"Number: \" count\r\n"
       		+ "count++\r\n");
  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();
  	
  	    Parser parser_TEST6 = new Parser(tokens_5);
  	  Node ON6=parser_TEST6.parseOperation().get();
  	//assertEquals(OperationNode.operations.POSTINC,((OperationNode)ON6).op.POSTINC);
      String output4=ON6.toString();
 
     //System.out.println(output4);
    }
    /*
     * The test file is called "SUM1.awk" which is submitted with this assignment the test data is also given in the comment below
     */
    /*
     * 
# BEGIN block - Initialize the sum
BEGIN {
    sum3 = 0
    sum4 = 0
}
# MIDDLE block - Process each line of input
{
    # Add the values of field 3 and field 4 to their respective sums
    sum3 += $3
    sum4 += $4
}
# END block - Output the sums
END{a=8;}
     * 
     * 
     */
   @Test
    public void test_Parser4() throws IOException
    {
    	String File_name ="SUM1.awk" ; //This can be used to test the file I have provided with the submission
	    Path myPath= Paths.get(File_name); 
		//String document = new String(Files.readAllBytes (myPath));//Reading the file using readALLBytes
	    /*
	     * Setting the document string to the contents in the file mentioned above 
	     */
	    String document ="# BEGIN block - Initialize the sum\r\n"
	    		+ "BEGIN {\r\n"
	    		+ "    sum3 = 0\r\n"
	    		+ "    sum4 = 0\r\n"
	    		+ "}\r\n"
	    		+ "# MIDDLE block - Process each line of input\r\n"
	    		+ "{\r\n"
	    		+ "    # Add the values of field 3 and field 4 to their respective sums\r\n"
	    		+ "    sum3 += $3\r\n"
	    		+ "    sum4 += $4\r\n"
	    		+ "}\r\n"
	    		+ "# END block - Output the sums\r\n"
	    		+ "END{a=8;}\r\n"
	    		+ "";
    	 Lexer lexer6 = new Lexer(document);
    	
   	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
   	    Parser parser_TEST6 = new Parser(tokens_5);
   	    
       ProgramNode programNode1 =parser_TEST6.Parse() ;//Parsing the program node
      // System.out.println( programNode1.toString());//Printing the program Node contents 

       LinkedList<BlockNode> Startblock= programNode1.getStartBlocks();//getting the start block of the program 
       assertEquals(1,Startblock.size());//checking it's size
       for (BlockNode BN : Startblock) 
	    {
          StatementNode SN= BN.getStatements().get(0);//The First statement in the start block  sum3 = 0
    	   Optional<AssignmentNode> AN= Optional.of((AssignmentNode)SN);//It's and assignment node with a target variable node sum3 and a constant node "0" which is tested below.
    	   Optional<VariableReferenceNode> VN= Optional.of((VariableReferenceNode)AN.get().getTarget()); //The variable node
    	   Optional<ConstantNode> EN= Optional.of((ConstantNode)AN.get().getExpression());//The expression node having the constant node "0"
    	   assertEquals("sum3",VN.get().getVAR_name());
    	   assertEquals("0",EN.get().getValue());

    	   StatementNode SN2= BN.getStatements().get(1);//The second statement in the start  block  sum4 = 0
    	   /*
    	    * checks the same as above for second statement 
    	    */
    	   Optional<AssignmentNode> AN1= Optional.of((AssignmentNode)SN2);
    	   Optional<VariableReferenceNode> VN1= Optional.of((VariableReferenceNode)AN1.get().getTarget());
    	   Optional<ConstantNode> EN1= Optional.of((ConstantNode)AN1.get().getExpression());
    	   assertEquals("sum4",VN1.get().getVAR_name());
    	   assertEquals("0",EN1.get().getValue());
	    }
       LinkedList<BlockNode> block= programNode1.getBlocks();
       assertEquals(1,block.size());
       
       for (BlockNode BN : block) 
	    {
          StatementNode SN= BN.getStatements().get(0);//The first statement Node of the block node   sum3 += $3  that is in theory sum3 = sum3+ $3
    	   Optional<AssignmentNode> AN= Optional.of((AssignmentNode)SN);//The assignment node of the with target as  sum 3  and expression as sum3+ $3
    	   Optional<VariableReferenceNode> VN= Optional.of((VariableReferenceNode)AN.get().getTarget());//The variable reference Node of the Assignment node's target.
    	   Optional<OperationNode> ON= Optional.of((OperationNode)AN.get().getExpression());//The Operation Node of the expression  sum3 + $3
    	   Optional<VariableReferenceNode> Left_of_ON= Optional.of((VariableReferenceNode)ON.get().getLeft());//left node having sum3
           OperationNode.operations operation= ON.get().getOp();//OperationNode having + operator 
           Optional<Node> Rifght_of_ON=  ON.get().getRight();//The Right node having $3 operation 
           Optional<ConstantNode> Left_of_ON2= Optional.of((ConstantNode)((OperationNode) Rifght_of_ON.get()).getLeft());
           OperationNode.operations operation2= ((OperationNode) Rifght_of_ON.get()).getOp();
    	   assertEquals("sum3",VN.get().getVAR_name());
    	   assertEquals("sum3",Left_of_ON.get().getVAR_name());
    	   assertEquals(OperationNode.operations.ADD,operation);
    	   assertEquals("3",Left_of_ON2.get().getValue());
    	   assertEquals(OperationNode.operations.DOLLAR,operation2);
    	
    	   StatementNode SN1= BN.getStatements().get(1);//The second statement of the block node that is    sum4 += $4 theory sum4 = sum4 + $4
    	   /**
    	    * Tests the same function as the above lines of code 
    	    */
    	   Optional<AssignmentNode> AN1= Optional.of((AssignmentNode)SN1);
    	   Optional<VariableReferenceNode> VN1= Optional.of((VariableReferenceNode)AN1.get().getTarget());
    	   Optional<OperationNode> ON1= Optional.of((OperationNode)AN1.get().getExpression());
    	   Optional<VariableReferenceNode> Left_of_ON1= Optional.of((VariableReferenceNode)ON1.get().getLeft());
           OperationNode.operations operation1= ON1.get().getOp();
           Optional<Node> Rifght_of_ON1=  ON1.get().getRight();
           Optional<ConstantNode> Left_of_ON2_1= Optional.of((ConstantNode)((OperationNode) Rifght_of_ON1.get()).getLeft());
           OperationNode.operations operation2_1= ((OperationNode) Rifght_of_ON1.get()).getOp();
    	   assertEquals("sum4",VN1.get().getVAR_name());
    	   assertEquals("sum4",Left_of_ON1.get().getVAR_name());
    	   assertEquals(OperationNode.operations.ADD,operation1);
    	   assertEquals("4",Left_of_ON2_1.get().getValue());
    	   assertEquals(OperationNode.operations.DOLLAR,operation2_1);
    	   
	    }
       LinkedList<BlockNode> endblock= programNode1.getEndBlocks();//Getting the end block contents checking it's size 
       assertEquals(1,endblock.size());//Checking it's size
       for (BlockNode BN : endblock) 
	    {
    	   StatementNode SN= BN.getStatements().get(0);//The first statement of the ends block node a=8;
    	   Optional<AssignmentNode> AN= Optional.of((AssignmentNode)SN);//The assignment node having a as the target and 8 as the expression.
    	   Optional<VariableReferenceNode> VN= Optional.of((VariableReferenceNode)AN.get().getTarget());//The variable reference node having "a"
    	   Optional<ConstantNode> EN= Optional.of((ConstantNode)AN.get().getExpression());//The constant node having "8".
    	   assertEquals("a",VN.get().getVAR_name());
    	   assertEquals("8",EN.get().getValue());
	    }
        }
   //Test for For loop
   @Test
   public void test_Parser4_1() 
   {
	   String document =""
	   		+ "BEGIN {\r\n"
	   		+ "    for (i = 1; i <= 5; ++i) {\r\n"
	   		+ "        print (-i) \r\n"
	   		+ "    }\r\n"
	   		+ "}\r\n"
	   		+ "";
   	 Lexer lexer6 = new Lexer(document);
   	
  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
  	    Parser parser_TEST6 = new Parser(tokens_5);
  	    
      ProgramNode programNode1 =parser_TEST6.Parse() ;//Parsing the program node
     // assertTrue(parser_TEST6.parseAction(programNode1));
    //System.out.println(programNode1.toString());//Printing the program Node contents 

   }
   @Test
   public void testfunctiondefination() 
   {
	   String document ="function printYes() {\r\n"
	      		+ "        for (i = 1; i <= 4; i++) {\r\n"
	      		+ "            print \"yes\"\r\n"
	      		+ "        }\r\n"
	      		+ "     }\r\n"
	      		+ "     BEGIN {\r\n"
	      		+ "        printYes()\r\n"
	      		+ "     }";
   	 Lexer lexer6 = new Lexer(document);
   	
  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
  	    Parser parser_TEST6 = new Parser(tokens_5);
  	    
      ProgramNode programNode1 =parser_TEST6.Parse() ;//Parsing the program node
     // assertTrue(parser_TEST6.parseAction(programNode1));
   // System.out.println(programNode1.toString());//Printing the program Node contents 
   }
   @Test
   public void testfornode() 
   {
	   String document ="for (i in a)";
   	 Lexer lexer6 = new Lexer(document);
   	
  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
  	    Parser parser_TEST6 = new Parser(tokens_5);
  	    
      ProgramNode programNode1 =parser_TEST6.Parse() ;//Parsing the program node
     // assertTrue(parser_TEST6.parseAction(programNode1));
    //System.out.println(programNode1.toString());//Printing the program Node contents 

   }
   @Test
   public void test_Parser4_1_2() 
   {
	   String document =""
	   		+ "delete a[1]";
   	 Lexer lexer6 = new Lexer(document);
   	
  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
  	    Parser parser_TEST6 = new Parser(tokens_5);
  	    
      ProgramNode programNode1 =parser_TEST6.Parse() ;//Parsing the program node
     // assertTrue(parser_TEST6.parseAction(programNode1));
     // System.out.println(((DeleteNode)programNode1.getBlocks().getFirst().getStatements().getFirst()).getName());
    // System.out.println(programNode1.toString());//Printing the program Node contents 

   }
   //Test for if node

   @Test
   public void test_Parser4_2() 
   {
	   String document ="BEGIN {\r\n"
	   		+ "    print \"Enter three numbers:\"\r\n"
	   		+ "    getline a\r\n"
	   		+ "    getline b\r\n"
	   		+ "    getline c\r\n"
	   		+ "b=-a\r\n"
	   		+ "    if (a >= b && a >= c)\r\n"
	   		+ "        print \"Greatest number is: \" a\r\n"
	   		+ "    else if (b >= a && b >= c)\r\n"
	   		+ "        print \"Greatest number is: \" b\r\n"
	   		+ "    else\r\n"
	   		+ "        print \"Greatest number is: \" c\r\n"
	   		+ "}\r\n"
	   		+ "";
   	 Lexer lexer6 = new Lexer(document);
   	
  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
  	    Parser parser_TEST6 = new Parser(tokens_5);
  	    
      ProgramNode programNode1 =parser_TEST6.Parse() ;//Parsing the program node
      //assertTrue(parser_TEST6.parseAction(programNode1));
    // System.out.println( programNode1.toString());//Printing the program Node contents 
   }
   //Testing the parse while 
   @Test
   public void test_Parser4_3() 
   {
	   String document ="# Initialize a variable\r\n"
	   		+ "BEGIN {"
	   		+ "i = 1\r\n"
	   		+ "\r\n"
	   		+ "# Start a while loop\r\n"
	   		+ "while (i <= 5) {\r\n"
	   		+ "    # Print the current value of 'i'\r\n"
	   		+ "    print(i)\r\n"
	   		+ "\r\n"
	   		+ "    # Increment 'i' by 1\r\n"
	   		+ "    i++\r\n"
	   		+ "}}\r\n"
	   		+ "";
   	 Lexer lexer6 = new Lexer(document);
   	
  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
  	    Parser parser_TEST6 = new Parser(tokens_5);
  	    
      ProgramNode programNode1 =parser_TEST6.Parse() ;//Parsing the program node
     // assertTrue(parser_TEST6.parseAction(programNode1));
   // System.out.println( programNode1.toString());//Printing the program Node contents 
   }
   //Testing parse do while loop  and also parse function call as print(....) is a function 
   
   
   /**
    * THE TESTING FOR INTERPRETER: 1 begins here
    *   The process of parsing a buit-in function in parser without needing (.....) the parenthesis is tested here
    */
   @Test
   public void test_Parser4_4and_INTERPRETER_1()  
   {
	   String document ="# AWK program to print 5 numbers using a do-while loop\r\n"
	   		+ "\r\n"
	   		+ "BEGIN {\r\n"
	   		+ "    count = 1  # Initialize a counter\r\n"
	   		+ "}\r\n"
	   		+ "\r\n"
	   		+ "# Start the do-while loop\r\n"
	   		+ "{\r\n"
	   		+ "    do {\r\n"
	   		+ "        print \"Number: \" count\r\n"       //Interpreter 1 parser changes test
	   		+"print(\"A test for function call\")\r\n"
	   		+ "        count++\r\n"
	   		+ "    } while(count <= 5)\r\n"
	   		+ "}\r\n"
	   		+ "";
   	 Lexer lexer6 = new Lexer(document);
   	
  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
  	    Parser parser_TEST6 = new Parser(tokens_5);
  	    
      ProgramNode programNode1 =parser_TEST6.Parse() ;//Parsing the program node
     // assertTrue(parser_TEST6.parseAction(programNode1));
   //System.out.println( programNode1.toString());//Printing the program Node contents 
   }
   /*
    * This is a test for the built-in function of the interpreter
    */
   @Test
   public void testInterpreter_1() throws Exception
   {
        Interpreter IT= new Interpreter(null, null);	//making the interpreter constructor
        HashMap<String, InterpreterDataType> params = new HashMap<>();
        params.put("param1", new InterpreterDataType("String1 "));
        params.put("param2", new InterpreterDataType("String2"));
            BuiltInFunctionDefinitionNode function = (BuiltInFunctionDefinitionNode) IT.Functions.get("print");//checking the working of the print function
            assertEquals("String1 String2",  function.execute(params));
        
        params.clear();//clearing the params every time to check the new function
        
        params.put("param1", new InterpreterDataType("Hello %s"));
        params.put("param2", new InterpreterDataType("World"));
             function = (BuiltInFunctionDefinitionNode) IT.Functions.get("printf");//Checking the working of the printf function with the above parameters
            assertEquals("Hello World",  function.execute(params));
        
        /**
         * file.txt has one line
         * Contents of file.txt is
         * 
         * All "i" in this string should be replaced with the other string
         * 
         */
        params.clear();
        IT= new Interpreter(null,"file.txt");//Setting $0 as a the input of the file above 
        params.put("0", new InterpreterDataType("i"));
        params.put("1", new InterpreterDataType("b"));
      
             function = (BuiltInFunctionDefinitionNode) IT.Functions.get("gsub");
            assertEquals("All \"b\" bn thbs strbng should be replaced wbth the other strbng",  function.execute(params));//Checking the function gsub with the above parameters and a file input
        params.clear();
        
        IT= new Interpreter(null,"file.txt");
        params.put("0", new InterpreterDataType("All \"i\" in this string should be replaced with the other string"));
        params.put("1", new InterpreterDataType("i"));
            BuiltInFunctionDefinitionNode function1 = (BuiltInFunctionDefinitionNode) IT.Functions.get("index");//checking the index function of i in the above string 
            assertEquals("6",  function1.execute(params)); 
        
        
        params.clear();
        
            params.put("0", new InterpreterDataType("four"));
             function1 = (BuiltInFunctionDefinitionNode) IT.Functions.get("length");
            assertEquals("4",  function1.execute(params));//Checking  the length function
        
        params.clear();
        IT= new Interpreter(null,"file.txt");
        params.put("0", new InterpreterDataType("All \"i\" in this string should be replaced with the other string"));
        params.put("1", new InterpreterDataType("str"));
             function1 = (BuiltInFunctionDefinitionNode) IT.Functions.get("match");
            assertEquals("16",  function1.execute(params));//Checking the match function
        
        
        params.clear();
        IT= new Interpreter(null,"file.txt");
        params.put("0", new InterpreterDataType("This0is0a0test"));
        params.put("1", new InterpreterDataType("array"));
        params.put("2", new InterpreterDataType("0"));
             function1 = (BuiltInFunctionDefinitionNode) IT.Functions.get("split");
            assertEquals("4",  function1.execute(params));//checking the split function
        
        
        params.clear();
        IT= new Interpreter(null,"file.txt");
        params.put("0", new InterpreterDataType("i"));
        params.put("1", new InterpreterDataType("b"));
             function1 = (BuiltInFunctionDefinitionNode) IT.Functions.get("sub");
            assertEquals("All \"b\" in this string should be replaced with the other string",  function1.execute(params));//checking the sub function
        
    
        params.clear();
        IT= new Interpreter(null,"file.txt");
        params.put("0", new InterpreterDataType("ThisSubstring"));
        params.put("1", new InterpreterDataType("5"));
        params.put("2", new InterpreterDataType("3"));

        
             function1 = (BuiltInFunctionDefinitionNode) IT.Functions.get("substr");
            assertEquals("Sub",  function1.execute(params));//checking the substr function
        
        params.clear();
        IT= new Interpreter(null,"file.txt");
        params.put("0", new InterpreterDataType("This_STRING"));
             function1 = (BuiltInFunctionDefinitionNode) IT.Functions.get("tolower"); //checking the to lower function
            assertEquals("this_string",  function1.execute(params));
        
        params.clear();
        IT= new Interpreter(null,"file.txt");
        params.put("0", new InterpreterDataType("This_STRING"));
             function1 = (BuiltInFunctionDefinitionNode) IT.Functions.get("toupper");//Checking the to Upper function 
            assertEquals("THIS_STRING",  function1.execute(params));
   }
   /**
    * This is an sample test to check what will be in the program node of a=2+2 as we will use it to test Interpreter -2
    */
   @Test 
   public void printnode()
   {
	   String document ="a=2+2";
	   	 Lexer lexer6 = new Lexer(document);
	   	
	  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
	  	    Parser parser_TEST6 = new Parser(tokens_5);
	  	    
	      ProgramNode programNode1 =parser_TEST6.Parse() ;
	     // System.out.println( programNode1.toString());
   }
   /*
    * The first test of interpreter-2 the expression is a=2+2 which should make a variable a with value 4  
    * 
    */
   @Test
   public void testInterpreter_2() throws Exception
   {
	   String document ="a=2+2";//The expression
	   	 Lexer lexer6 = new Lexer(document);
	   	
	  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
	  	    Parser parser_TEST6 = new Parser(tokens_5);
	  	    
	      ProgramNode programNode1 =parser_TEST6.Parse() ;//This will make our AST  for the above statement
	      /**
	       * Below is the implementation of how the professor wanted for us to test the interpreter with manually making the tree and inputing it.
	       */
		  // Optional<Node> right=Optional.of(new ConstantNode("2"));
	      //Node node = new AssignmentNode(new VariableReferenceNode("a"),new OperationNode( new ConstantNode("2"),OperationNode.operations.ADD,right));
	      Node   node = programNode1.getBlocks().getFirst().getStatements().getFirst();//This will extract the AST from the program node
	      Interpreter ip=new Interpreter(null, null);//Making an new interpreter Object
          ip.getIDT(node, null);//calling the getIDT on node
          assertEquals("4.0",ip.Variables.get("a").type);//checking if the global variables have the variable a with the value of 4.0 or not .
   }
   /*
    * The second test will check if the interpreter is able to check for the variable and retrieve its value if it already exists in the global variables
    * There are 2 expressions here first is b= 3+3
    * and the second is : a=b+2
    * Thus, this means we will want a variable called a in globals which holds a value of 8, 3+3+2.
    */
   @Test
   public void testInterpreter_2_2() throws Exception
   {
	   String document ="a=b+2";//THE second expression
	   	 Lexer lexer6 = new Lexer(document);//lexer to make tokens 
	   	
	  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
	  	    Parser parser_TEST6 = new Parser(tokens_5);//Passing the tokens to make AST 
	  	    
	       ProgramNode programNode1 =parser_TEST6.Parse() ;//getting the prgram node
		   Optional<Node> right=Optional.of(new ConstantNode("3"));//The constant node for right of first expression
	       Node node1 = new AssignmentNode(new VariableReferenceNode("b"),new OperationNode( new ConstantNode("3"),OperationNode.operations.ADD,right));//The node of first expression
	       Node   node2 = programNode1.getBlocks().getFirst().getStatements().getFirst();//Getting the second expression
	       Interpreter ip=new Interpreter(null, null);//making the interpreter object
	      ip.getIDT(node1, null);//we will first call the getIDT on node1
          ip.getIDT(node2, null);//Now we will call that on the node2
         assertEquals("8.0",ip.Variables.get("a").type);//a should have 8.0 as it's value
   }
   /**
    * the third test will check for the compression "<"
    * this will have an expression 1<2
    * it should return 1 that's the value assigned to true for the interpreter.
    * @throws Exception
    */
   @Test
   public void testInterpreter_2_3() throws Exception
   {
	   String document ="1<2";//The expression 1<2 which should return true i.e 1 
	   	 Lexer lexer6 = new Lexer(document);//lexer to make tokens
	  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
	  	    Parser parser_TEST6 = new Parser(tokens_5);//Parser to make AST
	  	    
	      ProgramNode programNode1 =parser_TEST6.Parse() ;//The making of AST
	      Node   node2 = programNode1.getBlocks().getFirst().getStatements().getFirst();//getting the node of 1<2
	     Interpreter ip=new Interpreter(null, null);//The interpreter object 
	   
         InterpreterDataType idt= ip.getIDT(node2, null);//getting the value as we can't check this in globals unless we assign an variable to it
         
         assertEquals("1",idt.type);//Checking if it's true or not
         }
   /*
    * The fourth test will check if the division is performed correctly or not. An test for math function again
    * Expression: a=12/2
    * the globals has a with a value of 6.0
    */
   @Test
   public void testInterpreter_2_4() throws Exception
   {
	   String document ="a=12/2";
	   	 Lexer lexer6 = new Lexer(document);
	   	
	  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
	  	    Parser parser_TEST6 = new Parser(tokens_5);
	  	    
	      ProgramNode programNode1 =parser_TEST6.Parse() ;
	      Node   node = programNode1.getBlocks().getFirst().getStatements().getFirst();
	      Interpreter ip=new Interpreter(null, null);
          ip.getIDT(node, null);
          assertEquals("6.0",ip.Variables.get("a").type);
   }
   /**
    * This a test for the boolean expressions 
    * The expression holds !,&& and ||  
    * !((8>2 && 9<2)||(9<10))
    * this test all AND OR and NOT
    * and should return false i.e 0
    * @throws Exception
    */
   @Test
   public void testInterpreter_2_5() throws Exception
   {
	   String document ="!((8>2 && 9<2)||(9<10))";
	   	 Lexer lexer6 = new Lexer(document);
	   	
	  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
	  	    Parser parser_TEST6 = new Parser(tokens_5);
	  	    
	      ProgramNode programNode1 =parser_TEST6.Parse() ;
	      Node   node = programNode1.getBlocks().getFirst().getStatements().getFirst();
	      Interpreter ip=new Interpreter(null, null);
	      InterpreterDataType idt= ip.getIDT(node, null);
	         assertEquals("0",idt.type);
   }
   /**
    * This will check for the Unary nodes that is -a, -b or ++a, ++b
    * The expression is a=-(b+2)
    * The value of b is 6 
    * Therefore a should have a value of -8
    * @throws Exception
    */
   @Test
   public void testInterpreter_2_6() throws Exception
   {
	   Optional<Node> right=Optional.of(new ConstantNode("3"));
	   String document ="a=-(b+2)";
	   	 Lexer lexer6 = new Lexer(document);
	   	
	  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
	  	    Parser parser_TEST6 = new Parser(tokens_5);
	  	    
	      ProgramNode programNode1 =parser_TEST6.Parse() ;
	   Node node1 = new AssignmentNode(new VariableReferenceNode("b"),new OperationNode( new ConstantNode("3"),OperationNode.operations.ADD,right));
	      Node   node2 = programNode1.getBlocks().getFirst().getStatements().getFirst();
	     Interpreter ip=new Interpreter(null, null);
	     ip.getIDT(node1, null);
          ip.getIDT(node2, null);
         assertEquals("-8.0",ip.Variables.get("a").type);
   }
   /**
    * This will check the post dec node 
    * the expression is a=b-- with the initial value of b as 6
    * there a will have the original value of b that is 6 and then the new value of b is 5
    * @throws Exception
    */
   @Test
   public void testInterpreter_2_6_1() throws Exception
   {
	   Optional<Node> right=Optional.of(new ConstantNode("3"));
	   String document ="a=b--";
	   	 Lexer lexer6 = new Lexer(document);
	   	
	  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
	  	    Parser parser_TEST6 = new Parser(tokens_5);
	  	    
	      ProgramNode programNode1 =parser_TEST6.Parse() ;
	   Node node1 = new AssignmentNode(new VariableReferenceNode("b"),new OperationNode( new ConstantNode("3"),OperationNode.operations.ADD,right));
	      Node   node2 = programNode1.getBlocks().getFirst().getStatements().getFirst();
	     Interpreter ip=new Interpreter(null, null);
	     ip.getIDT(node1, null);
          ip.getIDT(node2, null);
       assertEquals("6.0",ip.Variables.get("a").type);//a is 6 the original value of b
         assertEquals("5.0",ip.Variables.get("b").type);//b is 5 the new value after decreasing

   }
   /*
    * This will test the $ assignment
    * the global variables $7 will have 8 as it's value
    */
   @Test
   public void testInterpreter_2_7() throws Exception
   {
	   String document ="$(6+1)=8";
	   	 Lexer lexer6 = new Lexer(document);
	   	
	  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
	  	    Parser parser_TEST6 = new Parser(tokens_5);
	  	    
	      ProgramNode programNode1 =parser_TEST6.Parse() ;
	      Node   node = programNode1.getBlocks().getFirst().getStatements().getFirst();
	      Interpreter ip=new Interpreter(null, null);
          ip.getIDT(node, null);
          assertEquals("8",ip.Variables.get("$7").type);
   }
   /*
    * This is test for the concat operation 
    * the espression is (a= "con" "cat")
    *there for a is "concat" in the global variables
    */
   @Test
   public void testInterpreter_2_8() throws Exception
   {
	   String document ="a=\"Con\" \"cat\"";
	   	 Lexer lexer6 = new Lexer(document);
	   	
	  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
	  	    Parser parser_TEST6 = new Parser(tokens_5);
	  	    
	      ProgramNode programNode1 =parser_TEST6.Parse() ;
	      Node   node = programNode1.getBlocks().getFirst().getStatements().getFirst();
	      Interpreter ip=new Interpreter(null, null);
          ip.getIDT(node, null);
          assertEquals("Concat",ip.Variables.get("a").type);
   }
   /**
    * This is a test for the match operation 
    * the expression is "foo" ~ `foo`
    * the return should be 1 that is true
    * @throws Exception
    */
   @Test
   public void testInterpreter_2_9() throws Exception
   {
	   String document =" \"foo\" ~ `foo` ";
	   	 Lexer lexer6 = new Lexer(document);
	   	
	  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
	  	    Parser parser_TEST6 = new Parser(tokens_5);
	  	    
	      ProgramNode programNode1 =parser_TEST6.Parse() ;
	      Node   node = programNode1.getBlocks().getFirst().getStatements().getFirst();
	      Interpreter ip=new Interpreter(null, null);
	      InterpreterDataType idt= ip.getIDT(node, null);
	         assertEquals("1",idt.type);
   }
   @Test
   public void testInterpreter_2_10() throws Exception
   {
	   Optional<Node> right=Optional.of(new ConstantNode("3"));//The constant node for right of first expression
       Node node1 = new AssignmentNode(new VariableReferenceNode("a",Optional.of( new ConstantNode("7"))),new OperationNode( new ConstantNode("3"),OperationNode.operations.ADD,right));//The node of first expression
	   String document ="a[8]=78 ";
	   	 Lexer lexer6 = new Lexer(document);
	   	
	  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
	  	    Parser parser_TEST6 = new Parser(tokens_5);
	  	    
	      ProgramNode programNode1 =parser_TEST6.Parse() ;
	      Node   node = programNode1.getBlocks().getFirst().getStatements().getFirst();
	      Interpreter ip=new Interpreter(null, null);
	      ip.getIDT(node1, null);
	      ip.getIDT(node, null);
	      InterpreterArrayDataType adt=  (InterpreterArrayDataType)ip.Variables.get("a");
		     assertEquals("6.0" ,(adt.Variables.get("7").type));
   }
   @Test
   public void testInterpreter_2_11() throws Exception
   {
	   Optional<Node> right=Optional.of(new ConstantNode("3"));//The constant node for right of first expression
       Node node1 = new AssignmentNode(new VariableReferenceNode("a",Optional.of( new ConstantNode("7"))),new OperationNode( new ConstantNode("3"),OperationNode.operations.ADD,right));//The node of first expression
	   String document ="4 in a ";
	   	 Lexer lexer6 = new Lexer(document);
	   	
	  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
	  	    Parser parser_TEST6 = new Parser(tokens_5);
	  	    
	      ProgramNode programNode1 =parser_TEST6.Parse() ;
	      Node   node = programNode1.getBlocks().getFirst().getStatements().getFirst();
	      Interpreter ip=new Interpreter(null, null);
	      ip.getIDT(node1, null);
	    //  ip.getIDT(node, null);
	      InterpreterDataType idt= ip.getIDT(node, null);
	         assertEquals("1",idt.type);
   }
   /**
    * The test for interpreter-3 which shouldn't exist but it can help make things easier in the head
    * @throws Exception
    */
   @Test
   public void testInterpreter_3() throws Exception
   {
	      Interpreter ip=new Interpreter(null, null);//The object of the interpreter class

	   Optional<Node> right=Optional.of(new ConstantNode("3"));//The constant node for right of first expression
       Node node1 = new AssignmentNode(new VariableReferenceNode("b"),new OperationNode( new ConstantNode("3"),OperationNode.operations.ADD,right));//The node of first expression
       ip.getIDT(node1, null);
       String document ="for (i=0;i<81;i++){a[i]=(i+1); if(a[i]==5)break;}";//This node will check the for loop, if statement and the break;
	   	 Lexer lexer6 = new Lexer(document);
	   	
	  	    LinkedList<Token> tokens_5= (LinkedList<Token>) lexer6.Lex();//Lexing the document and storing it in a linked list of tokens
	  	    Parser parser_TEST6 = new Parser(tokens_5);
	         ProgramNode programNode1 =parser_TEST6.Parse() ;
	         LinkedList<StatementNode>   node = programNode1.getBlocks().getFirst().getStatements();
		     ip.InterpretListOfStatements(node, null );
		     InterpreterArrayDataType adt=  (InterpreterArrayDataType)ip.Variables.get("a");
		    
		     
		     
		     assertEquals("3.0" ,(adt.Variables.get("2").type));//check if a[2] has 3 in it or not
		      assertFalse(adt.Variables.containsKey("6"));//Checking  if the break works 

		     
	      String ndocument ="delete a[2]";//delete node check
		   	 Lexer nlexer6 = new Lexer(ndocument);
		  	    LinkedList<Token> ntokens_5= (LinkedList<Token>) nlexer6.Lex();//Lexing the document and storing it in a linked list of tokens
		  	    Parser nparser_TEST6 = new Parser(ntokens_5);
		      ProgramNode nprogramNode1 =nparser_TEST6.Parse() ;
		      LinkedList<StatementNode>   n_node = nprogramNode1.getBlocks().getFirst().getStatements();
			     ip.InterpretListOfStatements(n_node, null );
			     
			     adt=  (InterpreterArrayDataType)ip.Variables.get("a");
			      assertEquals(null ,(adt.Variables.get("2").type));//deletes only the second
			      assertEquals("4.0" ,(adt.Variables.get("3").type));
			      assertEquals("4.0" ,(ip.Variables.get("i").type));
			      
			      
			      
			      /**
			       * Checking the for in loop
			       */
			     String document2 ="for (i in a){b=i;}";
			   	 Lexer nlexer62 = new Lexer(document2);
			  	    LinkedList<Token> ntokens_52= (LinkedList<Token>) nlexer62.Lex();//Lexing the document and storing it in a linked list of tokens
			  	    Parser nparser_TEST62 = new Parser(ntokens_52);
			      ProgramNode nprogramNode12 =nparser_TEST62.Parse() ;
			      LinkedList<StatementNode>   n_node2 = nprogramNode12.getBlocks().getFirst().getStatements();
				     ip.InterpretListOfStatements(n_node2, null );
				      assertEquals("5" ,(ip.Variables.get("b").type));
				      /*
				       * The delete the whole array  check
				       * 
				       */
				      
				      String document2_1 ="delete a";
					   	 Lexer nlexer62_1 = new Lexer(document2_1);
					  	    LinkedList<Token> ntokens_52_1= (LinkedList<Token>) nlexer62_1.Lex();//Lexing the document and storing it in a linked list of tokens
					  	    Parser nparser_TEST62_1 = new Parser(ntokens_52_1);
					      ProgramNode nprogramNode12_1 =nparser_TEST62_1.Parse() ;
					      LinkedList<StatementNode>   n_node2_1= nprogramNode12_1.getBlocks().getFirst().getStatements();
						     ip.InterpretListOfStatements(n_node2_1, null );
				      
				      assertFalse(ip.Variables.containsKey("a"));

		     
   }
   
   
}