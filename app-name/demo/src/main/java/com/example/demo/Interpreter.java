package com.example.demo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 public class Interpreter 
{
	 LineManager LM;
	 String mainreturn="";
     ProgramNode pn=null;
	 HashMap<String,InterpreterDataType> Variables= new HashMap<String,InterpreterDataType>();//The hash-map to hold the global variables
	 HashMap<String,FunctionDefinitionNode> Functions= new HashMap<String,FunctionDefinitionNode>();//The hash-map to hold the functions
	 /** 
	  * The constructor of the interpreter class intilizing the line manager, global variables and functions
	  * @param programNode The Node that is retuned by the parser which will have our AST
	  * @param filePath The file path 
	 * @throws Exception 
	  */
	 Interpreter(ProgramNode programNode, String filePath) throws Exception 
	 {
		 pn=programNode;
		 
		 if(filePath!=null)
		 {
			 
			   // Path myPath= Paths.get(filePath);
			   // List<String> file = filePath.readAllLines (filePath);//Reading the file using readALLBytes
			   List<String> file = new ArrayList<String>(Arrays.asList(filePath.split("\n")));
				 LM= new LineManager(file); 
				  //Initializing the global variables below
	                Variables.put("FS", new InterpreterDataType(" ")); 
				 Variables.put("OFMT", new InterpreterDataType("%.6g"));
		         Variables.put("OFS", new InterpreterDataType(""));
		         Variables.put("ORS", new InterpreterDataType("\n"))  ;
		 }
		 else
		 {
			  List<String> file = null;//If no path is given null 
			   LM= new LineManager(file);
			   //Default values of the global variables 
			     Variables.put("FS", new InterpreterDataType(" ")); 
		         Variables.put("OFMT", new InterpreterDataType("%.6g"));
		         Variables.put("OFS", new InterpreterDataType(""));
		         Variables.put("ORS", new InterpreterDataType("\n"))  ;
		 }	
         
         //THE pirnt function
             Functions.put("print", new BuiltInFunctionDefinitionNode(parameters ->
             {
                 String output = "";
                 for (InterpreterDataType value : parameters.values()) //getting the value of all the parameters and making an output
                 {
                     output += value.type + Variables.get("OFS").type;
                 }
				 mainreturn= mainreturn+output;
                 System.out.print(output); //This was a test of the function
                 return output;
             }, true));

			 //THE pirnt function
             Functions.put("println", new BuiltInFunctionDefinitionNode(parameters ->
             {
                 String output = "";
                 for (InterpreterDataType value : parameters.values()) //getting the value of all the parameters and making an output
                 {
                     output += value.type + Variables.get("OFS").type;
                 }
				 mainreturn= mainreturn+output+"\n";
                 System.out.println(output); //This was a test of the function
                 return output;
             }, true));
             //THE pirntf function
             Functions.put("printf", new BuiltInFunctionDefinitionNode(parameters -> {
            	    String format = "";
            	    List<String> output = new ArrayList<>();
            	    int temp = 0;
            	    for (InterpreterDataType value : parameters.values()) {
            	        if (temp == 0) {
            	            format = value.type;
            	            temp++;
            	        } else {
            	            output.add(value.type);
            	        }
            	    }
            	    System.out.printf(format,output.toArray()); 
					mainreturn= mainreturn+String.format(format, output.toArray());
            	    return String.format(format, output.toArray());
            	}, true));

             //The get line function
             Functions.put("getline", new BuiltInFunctionDefinitionNode(parameters -> 
             {
                 String output = "";
                if( LM.splitAndAssign())
                 output="1";
                 else
                	 output="-1";
					 mainreturn= mainreturn+output;

                 return output;
             }, false));
             
             //The next function
             Functions.put("next", new BuiltInFunctionDefinitionNode(parameters -> 
             {
                 String output = "";
                 LM.splitAndAssign();
				 mainreturn= mainreturn+output;

                 return output;
             }, false));
             
             //The gsub function
             Functions.put("gsub", new BuiltInFunctionDefinitionNode(parameters -> 
             {
            	 String output = null;
                if(parameters.size()!=2&&parameters.size()!=3)//checking if right amount of parameters are there in the function call
                {
                	return "TOO FEW or TOO Many parameter in function gsub";
                 }
                else
                {
                	if(parameters.size()==2)//if 2 parameters are given then we need to consider it like gsub ("o","1",$0)
                	{
                   	 LM.splitAndAssign();

                		  Matcher match = Pattern.compile(parameters.get("0").type).matcher(Variables.get("$0").type);  
                		 
                		  output= match.replaceAll(parameters.get("1").type);
                	}
                	else if(parameters.size()==3)
                	{
                		 Matcher match = Pattern.compile(parameters.get("0").type).matcher(parameters.get("2").type);                		
                		  output= match.replaceAll(parameters.get("1").type);
                	}
					mainreturn= mainreturn+output;

                	return output;
                } }, false));
            
             
             //The index function

             Functions.put("index", new BuiltInFunctionDefinitionNode(parameters -> 
             {
            	 if(parameters.size()!=2)//checking the right a=mount of parameters are there or not 
                 {
                 	return "TOO FEW or TOO Many parameter in function index";
                  }        
                 int index= parameters.get("0").type.indexOf(parameters.get("1").type);
                 if(index != -1)
                 {
                	 index++;//as awk index start with 1.
					 mainreturn= mainreturn+String.valueOf(index);

                	 return String.valueOf(index);
                 }
                 else
                	 return "0";//if not found the 0 is returned
             }, false));
             
             //The length function
             Functions.put("length", new BuiltInFunctionDefinitionNode(parameters -> 
             {
            	 if(parameters.size()!=1)
                 {
                 	return "TOO FEW or TOO Many parameter in function length";
                 }
            	 String len = String.valueOf(parameters.get("0").type.length());//Giving the length of the string
				 mainreturn= mainreturn+len;

            	 return len;
             }, false));
             
             //The match function test whether s contains a substring matched by r, return index or 0;
             Functions.put("match", new BuiltInFunctionDefinitionNode(parameters -> 
             {
            	 if(parameters.size()!=2)
                 {
                 	return "TOO FEW or TOO Many parameter in function index";
                  }                 
                 Pattern pattern = Pattern.compile(parameters.get("1").type);
                 Matcher matcher = pattern.matcher(parameters.get("0").type);
                 if(matcher.find())
                 {
                	 int index= matcher.start();
					 mainreturn= mainreturn+String.valueOf(index);

                	 return String.valueOf(index) ;
                 }
                 else
                	 return "0";
             }, false));
            /*
             * The split function that
             * split s into array a on FS, return number of fields
               splits into array a on field separator fs,
               return number of fields
             */
             Functions.put("split", new BuiltInFunctionDefinitionNode(parameters -> 
             {
            	 if(parameters.size()!=2&&parameters.size()!=3)
                 {
                 	return "TOO FEW or TOO Many parameter in function SPLIT";
                  }
            	 String s= parameters.get("0").type;
        		 String array[]= parameters.size()==2?s.split(Variables.get("FS").type):s.split(parameters.get("2").type);
        		 String arrayName= parameters.get("1").type;
        		 
         	    InterpreterArrayDataType a = new InterpreterArrayDataType();
         	      
            		 for(int i=0;i<array.length;i++)
            		 {
            			a.Variables.put(String.valueOf(i),new InterpreterDataType(array[i]));
            		 }
            		 Variables.put(arrayName,a);
            		 
            	    return String.valueOf(array.length);
            	}, false));
             /*
              * The sprintf function
              * return expr -list formatted according to format string 
              */
             
             Functions.put("sprintf", new BuiltInFunctionDefinitionNode(parameters -> 
             {
                 String output = "";
                 if(parameters.size()!=2)
                 {
                 	return "TOO FEW or TOO Many parameter in function SprintF";
                  }      
                 output= String.format(parameters.get("0").type,parameters.get("1").type);
                 return output;
             }, true));//it is true as it is a Variadic
             
             /*
              * The Sub function
              * 
              * 
              * sub(r ,s): substitutes for the leftmost longest substring of $0 matched by r.
              * sub(r ,s, t): substitute s for the leftmost longest substring of matched by r, 
              */
             Functions.put("sub", new BuiltInFunctionDefinitionNode(parameters -> 
             {

            	 String output = null;
                 if(parameters.size()!=2&&parameters.size()!=3)
                 {
                 	return "TOO FEW or TOO Many parameter in function sub";
                  }
                 else
                 {
                 	if(parameters.size()==2)
                 	{
                   	 LM.splitAndAssign();

                 		 Matcher match = Pattern.compile(parameters.get("0").type).matcher(Variables.get("$0").type);                		
                 		  output= match.replaceFirst(parameters.get("1").type);
                 	}
                 	else if(parameters.size()==3)
                 	{
                 		 Matcher match = Pattern.compile(parameters.get("0").type).matcher(parameters.get("2").type);                		
                 		  output= match.replaceFirst(parameters.get("1").type);
                 	}
                 	return output;
                 }
             }, false));
             /*
              * The sunstr function
              * substr (s ,p)     :  return suffix of s starting at position p 
              *     substr (s ,p,n) : return substring of s of length n starting at position p
              */
             Functions.put("substr", new BuiltInFunctionDefinitionNode(parameters -> 
             {
                 String output = "";
                 if(parameters.size()!=2&&parameters.size()!=3)
                 {
                 	return "TOO FEW or TOO Many parameter in function substr";
                 }
                 if(parameters.size()==2)
                 {
                	 output= parameters.get("0").type.substring(Integer.parseInt(parameters.get("1").type)-1);
                 }
                 else if(parameters.size()==3)
                 {
                	 output= parameters.get("0").type.substring(Integer.parseInt(parameters.get("1").type)-1,Integer.parseInt(parameters.get("2").type)+Integer.parseInt(parameters.get("1").type)-1);
                 }
                 return output;
             }, false));
             /*
              * The to lower function
              * converts the upper case to lower case letter and returns it 
              */
             Functions.put("tolower", new BuiltInFunctionDefinitionNode(parameters ->
             {
                 String output = "";
                 if(parameters.size()!=1)
                 {
                 	return "TOO FEW or TOO Many parameter in function tolower";
                 }    
                 output= parameters.get("0").type.toLowerCase();
                 return output;
             }, false));
             /*
              * The to upper function
              * converts the lower case to upper case letter and returns it 
              */
             Functions.put("toupper", new BuiltInFunctionDefinitionNode(parameters ->
             {
            	 String output = "";
                 if(parameters.size()!=1)
                 {
                 	return "TOO FEW or TOO Many parameter in function toupper";
                 }    
                 output= parameters.get("0").type.toUpperCase();
                 return output;
             }, false));
             if(programNode!=null)
    		 {
    		InterpretProgram(pn);
    		 }
    
     
	 }
	 /**
	  * Takes a Node and a hash map of local variables. It will evaluate the node and return an IDT. 
	  * @param node The Node (mostly a statement node that goes in and gets evaluated)
	  * @param local_variables The local variables of the function
	  * @return The IDT 
	  * @throws Exception
	  */
	 public InterpreterDataType getIDT(Node node,HashMap<String, InterpreterDataType> local_variables) throws Exception
	 {
		if(node instanceof AssignmentNode)//if the node is an assignment node
		{
			 AssignmentNode AN= (AssignmentNode)node;//converting it to an Assignment node
		      if(AN.getTarget() instanceof VariableReferenceNode)//Checking if the target is an Variable reference Node  
		      {
		    	 VariableReferenceNode VRN= (VariableReferenceNode) AN.getTarget();//getting the target
		    	  if((Variables.containsKey(VRN.getVAR_name()))&&VRN.getIndex_exp().isEmpty())
		    			  {
		    		    Variables.replace(VRN.getVAR_name(),getIDT(AN.getExpression(),local_variables));
                          return getIDT(AN.getExpression(),local_variables);
		    			  }

		    	 InterpreterDataType result= getIDT(VRN,local_variables);//getIDT on the target node
		    	 if(!(result instanceof InterpreterArrayDataType) )
		    	 {
		    	 if(Variables.containsKey(result.type))
		    		    Variables.replace(result.type,getIDT(AN.getExpression(),local_variables));
		    	 else
				    	Variables.put(result.type,getIDT(AN.getExpression(),local_variables));
		    	 }
		    	 else
		    	 {
		    		 InterpreterArrayDataType iadt= (InterpreterArrayDataType)result;
						String index= getIDT(VRN.getIndex_exp().get(), local_variables).type;
                        index= String.valueOf((int)Float.parseFloat(index));
		    		 if(iadt.getVariables().containsKey(index))
		    		 {
		    			 HashMap<String,InterpreterDataType> indexes= iadt.getVariables();
		    			 indexes.put(index, getIDT(AN.getExpression(),local_variables));
		    			 iadt.setVariables(indexes);
		    			 Variables.replace(VRN.getVAR_name(),iadt);
		    		 }
		    		 
		    		 
		    		 
		    	 }
		    	 return result;
		      }
		      else if(AN.getTarget() instanceof OperationNode)//if the target is an operationNode
		      {
					 OperationNode ON= (OperationNode)AN.getTarget();
					if( ON.getOp()!=OperationNode.operations.DOLLAR)//checking if The operation is the $ if not the give an error
					{
						System.out.println("The assignment node can only have a operation with $");
					}
					else
					{
				    	 InterpreterDataType result= getIDT(ON,local_variables);//getting the idt and evaluating the result
				    	 if(Variables.containsKey(result.type))
				    		    Variables.replace(result.type,getIDT(AN.getExpression(),local_variables));
				    	 else
						    	Variables.put(result.type,getIDT(AN.getExpression(), local_variables));
				    	 return result;
					}
		      }
		      else
		      {
		    	  System.out.println("Problem in Assignmnet it which shi=ould only have A variable reference or a $(expression)");
		    	  return null;
		      }
		}
		else if(node instanceof ConstantNode)//If the node is a ConstantNode
		{
	    	 InterpreterDataType result = new InterpreterDataType(((ConstantNode)node).getValue());//Returning the value of the constant value
	    	 return result;
		}
		else if(node instanceof FunctionCallNode)//If the node is a function call node
		{
			String functionreturned= RunFunctionCall((FunctionCallNode) node,local_variables);//calling the run function call
	    	 InterpreterDataType result = new InterpreterDataType(functionreturned);
	    	 return result;
		}
		else if(node instanceof PatternNode)//IF the node is a pattern node it is an error
		{
			throw new Exception("A pattern can't be passed to function or an assignment");
		}
		else if(node instanceof TernaryNode )//If it is a Ternary Node 
		{
			 InterpreterDataType result ;
			TernaryNode TN= (TernaryNode)node;
			if(getIDT(TN.getCondition(), local_variables).type!="0")
			{
				result = new InterpreterDataType(getIDT(TN.getTrueCase(), local_variables).type);//we store the true case and return it
		    }
			else
			{
				result = new InterpreterDataType(getIDT(TN.getFalseCase(), local_variables).type);//We Store the false case and return it
			}
			return result;
		}
		else if(node instanceof VariableReferenceNode )// if the node is a Variable reference node  
		{ 
			VariableReferenceNode VRN = (VariableReferenceNode) node;
			if(VRN.getIndex_exp().isPresent())//checking if it is a reference to an array variable
			{
				String index= getIDT(VRN.getIndex_exp().get(), local_variables).type;
				index=String.valueOf((int)Float.parseFloat(index));
				if(local_variables!=null)//if there are local variables
				{
					if (local_variables.containsKey(VRN.getVAR_name()))
					{
						if(!((local_variables.get(VRN.getVAR_name())) instanceof InterpreterArrayDataType) )
						{throw new Exception("Attempt to use scalar variable as an array");}
						else
						{
							HashMap<String,InterpreterDataType> INDEXES= ((InterpreterArrayDataType) (local_variables.get(VRN.getVAR_name()))).getVariables();
                           if(Integer.parseInt(index)>(INDEXES.size()-1))
                           {
                        	   for (int i= INDEXES.size()+1;i<Integer.parseInt(index);i++)
                        	   {
                        		   INDEXES.put(String.valueOf(i), new InterpreterDataType());
                        	   }
                        	  INDEXES.put(index,new InterpreterDataType());
                        	  ((InterpreterArrayDataType) (local_variables.get(VRN.getVAR_name()))).setVariables(INDEXES) ;                     
                        	  return INDEXES.get(index);
                           } 
                           else
                           {
                         	  return INDEXES.get(index);
                           }
						}	
					}	
				}
				else
				{
					if (Variables.containsKey(VRN.getVAR_name()))
					{
						if(!((Variables.get(VRN.getVAR_name())) instanceof InterpreterArrayDataType) )
						{throw new Exception("Attempt to use scalar variable as an array");}
						else
						{
							HashMap<String,InterpreterDataType> INDEXES= ((InterpreterArrayDataType) (Variables.get(VRN.getVAR_name()))).getVariables();
                           if((int)Float.parseFloat(index)>(INDEXES.size()-1))
                           {
                        	   for (int i= INDEXES.size()+1;i<Integer.parseInt(index);i++)
                        	   {
                        		   INDEXES.put(String.valueOf(i), new InterpreterDataType());
                        	   }
                        	  INDEXES.put(index,new InterpreterDataType());
                        	  ((InterpreterArrayDataType) (Variables.get(VRN.getVAR_name()))).setVariables(INDEXES) ; 
                        	  InterpreterArrayDataType IADT= new InterpreterArrayDataType();
      						IADT.setVariables(INDEXES);
      						
      						return IADT;
                           } 
                           else
                           {
                         	  return INDEXES.get(index);
                           }
						}	
					}	
					else
					{
						HashMap<String,InterpreterDataType> INDEXES= new HashMap<String,InterpreterDataType>(); 
						for(int i=0;i<Integer.parseInt(index);i++)
						{
							INDEXES.put(String.valueOf(i), new InterpreterDataType());
						}

						INDEXES.put(index,new InterpreterDataType() );
						InterpreterArrayDataType IADT= new InterpreterArrayDataType();
						IADT.setVariables(INDEXES);
						Variables.put(VRN.getVAR_name(), IADT);
						return IADT;
					}
				}
			}
			else//The same function as above but for variable names and not arrays
			{
				if(local_variables!=null)
				{
				if(local_variables.containsKey(VRN.getVAR_name()))
				{
			    	 InterpreterDataType result = new InterpreterDataType(local_variables.get(VRN.getVAR_name()).type);
					 return result;
 				}
				}
				 if(Variables.containsKey(VRN.getVAR_name()))
				{
			    	 InterpreterDataType result = new InterpreterDataType(Variables.get(VRN.getVAR_name()).type);
					 return result;
 				}
				else
				{
					Variables.put(VRN.getVAR_name(), null);
					InterpreterDataType result = new InterpreterDataType(VRN.getVAR_name());
					 return result;
				}
			}
		}
		else if(node instanceof OperationNode)//IF the node is an operaion node
		{
			OperationNode ON=(OperationNode)node;//we make the operation node
			OperationNode.operations operation= ON.getOp();//Get the operation to check what operation we want to work with
			 String left = getIDT(ON.getLeft(), local_variables).type;//get the left IDT
			 if(left==null)
			 {
				 left = getIDT(ON.getLeft(), local_variables).type;
			 }
			 String right="";
			 boolean Lfloat = true;//a bollean that checks if the left can be converted to float
			 float l=0;//variable to store the value of l
             float r=0;//variable to store the value of r
			 try//now we will try to convert to float if that is not possible then the Lfloat is false
			 {
				
			    l= Float.parseFloat(left);
			 } catch (NumberFormatException e) {
			     Lfloat = false;
			 }
			 boolean Rfloat = false ;//a bollean that checks if the right can be converted to float
			 if(ON.getRight().isPresent())//if the ON. has a right then it will try to check for the Rfloat
				{
				 if(!(ON.getRight().get() instanceof PatternNode)&&(!(ON.getOp()==OperationNode.operations.IN)))//if the ON is not an Pattern Node then we want to call the getIDT on the right side
				 {
					
								 right= getIDT(ON.getRight().get(), local_variables).type;
					 }
				 else 
				 {right="";}
					 Rfloat = true;

					 try {
						 r=  Float.parseFloat(right);//trying to convert the right to float if not possible then Rfloat is false
					 } catch (NumberFormatException e) {
					     Rfloat = false;
					 }
				}
			 //If the operation is any math operation the below is executed
			 if(operation==OperationNode.operations.ADD ||operation==OperationNode.operations.SUBTRACT ||operation==OperationNode.operations.MULTIPLY||operation==OperationNode.operations.DIVIDE||operation==OperationNode.operations.MODULO||operation==OperationNode.operations.EXPONENT||operation==OperationNode.operations.POSTDEC||operation==OperationNode.operations.POSTINC||operation==OperationNode.operations.PREDEC||operation==OperationNode.operations.PREINC||operation==OperationNode.operations.UNARYPOS||operation==OperationNode.operations.UNARYNEG)
			 {
			 if(Lfloat && Rfloat)//if both of them left and right are true then we will handle them as below
			 {
			switch(operation)
			{
			case ADD://If the case is ADD then we will get the both left and right float values 
			{
		    	float sum= l+r;//ADD left and right 
				InterpreterDataType result = new InterpreterDataType(String.valueOf(sum));
				return result;
			}
			case SUBTRACT://If the case is SUBTRACT then we will get the both left and right float values 
			{
		    	float sub= l-r;//WE sub that 
		    	InterpreterDataType result = new InterpreterDataType(String.valueOf(sub));
				return result;
			}
			case MULTIPLY://The multiplication case
			{
		    	float MUL= l*r;
		    	InterpreterDataType result = new InterpreterDataType(String.valueOf(MUL));
				return result;
			}
			case DIVIDE://The division case
			{
		    	float div= l/r;
		    	InterpreterDataType result = new InterpreterDataType(String.valueOf(div));
				return result;
			}
			case MODULO://The remainder or modulo case
			{
		    	float rem= l%r;
		    	InterpreterDataType result = new InterpreterDataType(String.valueOf(rem));
				return result;
			}
			case EXPONENT://The exponent case
			{
		    	float exp= (float) Math.pow(l,r);
		    	InterpreterDataType result = new InterpreterDataType(String.valueOf(exp));
				return result;
			}
			default://The default case we will just return 0
				InterpreterDataType result = new InterpreterDataType(String.valueOf(0));
				return result;
			}
			}
			 else if (Rfloat)//IF the right exists
			 {
				 switch(operation)
					{
			 case ADD:
				{
			    	float sum= +r;
					InterpreterDataType result = new InterpreterDataType(String.valueOf(sum));
					return result;
				}
				case SUBTRACT:
				{
			    	float sub= -r;
			    	InterpreterDataType result = new InterpreterDataType(String.valueOf(sub));
					return result;
				}
				default:
					InterpreterDataType result = new InterpreterDataType(String.valueOf(0));
					return result;
			 }
			 }
			 else if(Lfloat)//If only the left exists
			 {
				 String var="0";//this is if the post/pre inc/dec needs a variable to update in the global case
				 if (ON.getLeft() instanceof VariableReferenceNode)
				 {
					  var=((VariableReferenceNode) ON.getLeft()).getVAR_name();
				 }
				 switch(operation)
					{
			 case PREDEC://checking if the operation is predec
				{
			    	l--;// we will first decrees the value then store the result to return
					InterpreterDataType result = new InterpreterDataType(String.valueOf(l));
					Variables.put(var, result);//We will replace the value in the globals
					return result;
				}
				case PREINC:
				{
					l++;// we will first increase the value then store the result to return
			    	InterpreterDataType result = new InterpreterDataType(String.valueOf(l));
					Variables.put(var, result);//Update the value 

					return result;
				}
				case POSTDEC:
				{
			    	InterpreterDataType result = new InterpreterDataType(String.valueOf(l));// we will first  store the result to return and then decrease the value
			    	l--;//we will decrease
					Variables.put(var, new InterpreterDataType(String.valueOf(l)));//Update the value

			    	
					return result;
				}
				case POSTINC:
				{
			    	InterpreterDataType result = new InterpreterDataType(String.valueOf(l));// we will first  store the result to return and then Increase the value
			    	l++;//We will increase the value
					Variables.put(var,new InterpreterDataType(String.valueOf(l)));//Update the value

			    	
					return result;
				}
				case UNARYNEG://This will handle the -
				{
			    	float nl= -l;
			    	InterpreterDataType result = new InterpreterDataType(String.valueOf(nl));
					return result;
				}
				case UNARYPOS://This will handle the +
				{
			    	float nr= -r;
			    	InterpreterDataType result = new InterpreterDataType(String.valueOf(nr));
					return result;
				}
				default:
					InterpreterDataType result = new InterpreterDataType(String.valueOf(0));
					return result;
			 }
			 }
			}
			 //If the nodes are any type of comparisons
			 else if(operation==OperationNode.operations.EQ ||operation==OperationNode.operations.GT||operation==OperationNode.operations.GE||operation==OperationNode.operations.LT||operation==OperationNode.operations.LE||operation==OperationNode.operations.NE)
			 {
				 /**
				  * if we want to return true the we will return 1 else if it's false we will return the value 0
				  */
				 switch(operation)
					{
					case EQ://Equals node
					{
						InterpreterDataType result;
						if(Lfloat &&Rfloat)//if both of right and left then we can convert it to float so we will compare the float values
						{
							 result = (l==r)? new InterpreterDataType("1"):new InterpreterDataType("0");
						}
						else//else we will compare as strings
						{
						     result = (left==right)? new InterpreterDataType("1"):new InterpreterDataType("0");
						}
		                return	result;
						
					}
					case GE:
					{
						InterpreterDataType result;
						if(Lfloat &&Rfloat)//if both of right and left then we can convert it to float so we will compare the float values
						{
							 result = (l>=r)? new InterpreterDataType("1"):new InterpreterDataType("0");
						}
						else
						{
						     result = (left.compareTo(right) >=0)? new InterpreterDataType("1"):new InterpreterDataType("0");
						}
		                return	result;
					}
					case GT:
					{
						InterpreterDataType result;
						if(Lfloat &&Rfloat)//if both of right and left then we can convert it to float so we will compare the float values
						{
							 result = (l>r)? new InterpreterDataType("1"):new InterpreterDataType("0");
						}
						else
						{
						     result = (left.compareTo(right) >0)? new InterpreterDataType("1"):new InterpreterDataType("0");
						}
		                return	result;
					}
					case LE:
					{
						InterpreterDataType result;
						if(Lfloat &&Rfloat)//if both of right and left then we can convert it to float so we will compare the float values
						{
							 result = (l<=r)?new InterpreterDataType("1"):new InterpreterDataType("0");
						}
						else
						{
						     result = (left.compareTo(right) <=0)? new InterpreterDataType("1"):new InterpreterDataType("0");
						}
		                return	result;
					}
					case LT:
					{
						InterpreterDataType result;
						if(Lfloat &&Rfloat)//if both of right and left then we can convert it to float so we will compare the float values
						{
							 result = (l<r)? new InterpreterDataType("1"):new InterpreterDataType("0");
						}
						else
						{
						     result = (left.compareTo(right) <0)? new InterpreterDataType("1"):new InterpreterDataType("0");
						}
		                return	result;
					}
					case NE:
					{
						InterpreterDataType result;
						if(Lfloat &&Rfloat)//if both of right and left then we can convert it to float so we will compare the float values
						{
							 result = (l!=r)? new InterpreterDataType("1"):new InterpreterDataType("0");
						}
						else
						{
						     result = (left.compareTo(right)!=0)? new InterpreterDataType("1"):new InterpreterDataType("0");
						}
		                return	result;
					}
				default:
					break;
			 }
		}
			 /*
			  * IF the oprations are of boolean operations and or , not
			  */
			 else if(OperationNode.operations.AND==operation || OperationNode.operations.OR==operation || OperationNode.operations.NOT==operation)
			 {
					InterpreterDataType result;//Making the result
					 float defaultl=0;//checking l is a float 
		             float defaultr=0;//checking r is a float
					 try 
					 {
					    defaultl= Float.parseFloat(left);
					 } catch (NumberFormatException e) {
						 defaultl=0;
						 //int temp=1;
 					 }
					 try {
						    defaultr= Float.parseFloat(right);
						 } catch (NumberFormatException e) {
							 defaultr=0;
	 					 }
				 if(operation==OperationNode.operations.AND)//The AND operation 
				 {
					 if(defaultl!=0 && defaultr !=0)//if the l and r is not 0 which means if both are not false we will return 1 that is true else we will return false
					 {
						 result= new InterpreterDataType(String.valueOf(1));
					 }
					 else
					 {
						 result= new InterpreterDataType(String.valueOf(0));
					 }
				 }
				 else if(operation==OperationNode.operations.NOT)//not will just return the opposite of true or false
				 {
					 if(defaultl==0)
					 {
						 result= new InterpreterDataType(String.valueOf(1));
					 }
					 else 
					 {
						 result= new InterpreterDataType(String.valueOf(0));
					 }
				 }
				 else//else the or operation
				 {
					 if(defaultl==0 && defaultr==0)
					 {
						 result= new InterpreterDataType(String.valueOf(0));
					 }
					 else
					 {
						 result= new InterpreterDataType(String.valueOf(1));
					 }
				 }
				 return result;
			 }
			 else if(OperationNode.operations.MATCH==operation ||OperationNode.operations.NOTMATCH==operation )//The operation is Match or not Match 
			 {

			        // the right side must be a PatternNode
			        if (!(ON.getRight().get() instanceof PatternNode)) 
			        {
			            throw new Exception("Right side of a match operation must be a PatternNode.");
			        }
			        PatternNode patternNode = (PatternNode) ON.getRight().get();

			        // using Java's built-in Regex
			        boolean matches = Pattern.matches(patternNode.getValue(), left);

			        // if this is a NOT_MATCH operation, invert the result
			        if (ON.getOp() == OperationNode.operations.NOTMATCH)
			        {
			            matches = !matches;
			        }
			        return (new InterpreterDataType(matches ? "1" : "0")); 
			 }
			 /**
			  * If the operation is a dollar sign
			  */
			 else if(OperationNode.operations.DOLLAR==operation)
			 {
                        String addDOLLAR= "$"+String.valueOf((int)(Float.parseFloat(left)));//we will convert it to int to remove the ".0" and then evaluate the whole thing
                        //addDOLLAR is a variable name that we will check in the globals 
                       // The below checks the globals an locals and if not present it puts in the globals
                       if(local_variables!=null)
        			   {
                       if( local_variables.containsKey(addDOLLAR))
                       {
                    	  return local_variables.get(addDOLLAR);
                       }
                       }
                       else if(Variables.containsKey(addDOLLAR))
                       {
                    	   return Variables.get(addDOLLAR);
                       }  
                       else
                       {
                    	   Variables.put(addDOLLAR,new InterpreterDataType("0"));
                    	   return new InterpreterDataType(addDOLLAR);
                       }
			 }
			 //If the value is a concatation operation then we will  just concat the values and return them
			 else if (OperationNode.operations.CONCATENATION==operation)
			 {
				 String constr= left.concat(right);
				 return new InterpreterDataType(constr);
			 }
			 //if the operation is an IN type then we will check every exception we can and then we return it. 
			 else if(OperationNode.operations.IN==operation)
			 {
				  
				 if(ON.getRight().get() instanceof VariableReferenceNode)
				 {
					 VariableReferenceNode  RVRN= (VariableReferenceNode) ON.getRight().get() ;
					 if(local_variables!=null)
						{
						 if( local_variables.containsKey(RVRN.getVAR_name()))
	                         {
							 InterpreterDataType IDT= local_variables.get(RVRN.getVAR_name());

							 if(!(IDT instanceof InterpreterArrayDataType))
							 {
							      throw new Exception("Attempt to use scalar variable as an array");

							 }
							 else if(((InterpreterArrayDataType)IDT).getVariables().size()<Integer.parseInt(left))
							 {
							 InterpreterDataType result= new InterpreterDataType(String.valueOf("0"));
	                         return result;
							 }
							 else
							 {
								 InterpreterDataType result= new InterpreterDataType(String.valueOf("1"));
		                         return result;
							 }                     
                             }
						 
						}
					 else if(Variables.containsKey(RVRN.getVAR_name()))
                     {
						 InterpreterDataType IDT= Variables.get(RVRN.getVAR_name());

						 if(!(IDT instanceof InterpreterArrayDataType))
						 {
						      throw new Exception("Attempt to use scalar variable as an array");

						 }
						 else if(((InterpreterArrayDataType)IDT).getVariables().size()<Integer.parseInt(left))
						 {
						 InterpreterDataType result= new InterpreterDataType(String.valueOf("0"));
                         return result;
						 }
						 else
						 {
							 InterpreterDataType result= new InterpreterDataType(String.valueOf("1"));
	                         return result;
						 }
                     }
					 else
					 {
						 InterpreterDataType	 result= new InterpreterDataType(String.valueOf("0"));
                        return result;
					 }
					
				 }
				 else 
					{
						throw new Exception("The right of the IN is not an Variable reference");
					}
			 }
	 }
		return null;
	 }
			/**
			 * THE PREOCESS STATEMENT THAT WILL TAKE CARE OF STATEMENTS 
                  ProcessStatement break
                   ProcessStatement continue
                ProcessStatement delete
                ProcessStatement doWhile
                 ProcessStatement for
                    ProcessStatement foreach
               ProcessStatement if
              ProcessStatement return
             ProcessStatement while

			 * @param locals THE LOCAL VARIABLES OF A FUNCTION
			 * @param statement THE STATEMENTS TO BE PRECESSED 
			 * @return  The result of the process statement that can be either NORMAL,BREAK,CONTINUE,RETURN
			 * @throws Exception
			 */
	 public ReturnType ProcessStatement(HashMap<String, InterpreterDataType> locals, StatementNode statement) throws Exception 
	 {
	       //BreakNode: return with a return type of break
		 if (statement instanceof BreakNode)
		{
			ReturnType RT= new ReturnType(ReturnType.ReturnTypeEnum.BREAK);
			return RT;
		}
		else if (statement instanceof ContinueNode)//ContinueNode: return with a return type of continue
		{
			ReturnType RT= new ReturnType(ReturnType.ReturnTypeEnum.CONTINUE);
			return RT;
		}
		else if (statement instanceof DeleteNode)//DeleteNode: get the array from the variables (local, then global). If indices is set, delete them from the array, otherwise delete them all. 
		{
			DeleteNode DN= (DeleteNode)statement;
			VariableReferenceNode VRN=	((VariableReferenceNode)DN.getName());//The Variable Reference node of the delete 
			if(locals !=null)//checking if the local is not null
			{
			if (locals.containsKey(VRN.getVAR_name()))
			{				InterpreterDataType IDT= locals.get(VRN.getVAR_name());
			 if(!(IDT instanceof InterpreterArrayDataType))//if the variable is not an array we want to display an error
				 throw new Exception("Attempt to use scalar variable as an array");
			 if(VRN.getIndex_exp().isEmpty())//if the variable given to delete do not have a array
			 {
				 locals.remove(VRN.getVAR_name());//we remove and return that
				 ReturnType RT= new ReturnType(ReturnType.ReturnTypeEnum.NORMAL);
					return RT;
			 }
			     
			 int index = (int) Float.parseFloat(  getIDT(VRN.getIndex_exp().get(), locals).type);
					 if(((InterpreterArrayDataType)IDT).getVariables().containsKey(String.valueOf(index)))
					 {
						IDT= ((InterpreterArrayDataType)IDT).getVariables().replace(String.valueOf(index),new InterpreterDataType());
						locals.replace(VRN.getVAR_name(), IDT);
						ReturnType RT= new ReturnType(ReturnType.ReturnTypeEnum.NORMAL);
						return RT;
					 }
			}
			}
			 if (Variables.containsKey(VRN.getVAR_name()))//if the locals do not have then the globals are checked 
				{
				InterpreterDataType IDT= Variables.get(VRN.getVAR_name());
				 if(!(IDT instanceof InterpreterArrayDataType))//if not an array we want to give an error
				   {
				      throw new Exception("Attempt to use scalar variable as an array");
				   }
				 if(VRN.getIndex_exp().isEmpty())//if no array index is given delete the whole array
				 {
					 Variables.remove(VRN.getVAR_name());
					 ReturnType RT= new ReturnType(ReturnType.ReturnTypeEnum.NORMAL);
						return RT;
				 }
				 
				 int index = (int) Float.parseFloat(  getIDT(VRN.getIndex_exp().get(), locals).type);
				 if(((InterpreterArrayDataType)IDT).getVariables().containsKey(String.valueOf(index)))
				 {
					((InterpreterArrayDataType)IDT).getVariables().replace(String.valueOf(index),new InterpreterDataType());
					
					Variables.replace(VRN.getVAR_name(), IDT);
				 }
				}
		   
			ReturnType RT= new ReturnType(ReturnType.ReturnTypeEnum.NORMAL);
			return RT;
		}
		 /*
		  * DoWhileNode: call InterpretListOfStatements (we will write this in a little bit) 
		  * in a do-while loop, using GetIDT to evaluate the condition. 
		  * Check the return value of InterpretListOfStatements – 
		  * if it is Break, then break out of the loop, on return, return from ProcessStatement.
		  */
		 
		else if (statement instanceof DoNode)//The Do while node
		{
			DoNode DN= (DoNode)statement;
			do
			{
			ReturnType rt=	InterpretListOfStatements(DN.getBN().getStatements(),locals);
			 if (rt.getType() == ReturnType.ReturnTypeEnum.RETURN) 
	            { 
	                return rt;
	            }
			 else if(rt.getType()==ReturnType.ReturnTypeEnum.BREAK)
			 {
				 break;
			 }
			}while(getIDT(DN.getON(), locals).type=="1");
			return new ReturnType(ReturnType.ReturnTypeEnum.NORMAL);
		}
		 /*
		  * ForNode: If there is an initial, call processStatement on it. 
		  * Then create a while loop, using the forNode’s condition as the while’s condition. 
		  * Inside, call InterpretListOfStatements() on forNode’s statements. 
		  * Same as DoWhile – check the return code and do the same thing.
		  *   calling processStatement() on the forNode’s increment.
		  */
		 
		else if (statement instanceof ForNode)
		{
			ForNode FN= (ForNode)statement;
		     ProcessStatement(locals,  (StatementNode) FN.getAssignmet());
		    InterpreterDataType con=getIDT(FN.getCondition(), locals);
		    String condition= con.getType();
		   while(!(condition=="0"))
		   {
			   ReturnType rt=	InterpretListOfStatements(FN.getStatement().getStatements(),locals);
			   if (rt.getType() == ReturnType.ReturnTypeEnum.RETURN) 
	            { 
	                return rt;
	            }
			 else if(rt.getType()==ReturnType.ReturnTypeEnum.BREAK)
			 {
				 break;
			 }
				
				ProcessStatement(locals, (StatementNode) FN.getOperator());
				con=getIDT(FN.getCondition(), locals);
				condition=con.getType();
			    
		   }
		   return new ReturnType(ReturnType.ReturnTypeEnum.NORMAL);
		}
		 /*
		  * ForEachNode: Find the array, loop over every key in the array’s hashMap. 
		  * Set the variable to the key, then call InterpretListOfStatements on the forEach’s statements. 
		  * Follows the same return rules as doWhile.
		  */
		else if(statement instanceof ForEachNode )
		{
			ForEachNode FEN= (ForEachNode)statement;
			OperationNode on=(OperationNode)FEN.getAssignement();
			getIDT(on.getLeft(), locals);
			VariableReferenceNode VR= (VariableReferenceNode) on.getLeft();
			int i=0;
			Variables.put(VR.getVAR_name(), new InterpreterDataType("0"));
		  
		     while(getIDT(FEN.getAssignement(), locals).type=="1")
		     {
		    	 ReturnType rt=	InterpretListOfStatements(FEN.getBlocknode().getStatements(),locals);
				   if (rt.getType() == ReturnType.ReturnTypeEnum.RETURN) 
		            { 
		                return rt;
		            }
				 else if(rt.getType()==ReturnType.ReturnTypeEnum.BREAK)
				 {
					 break;
				 }
		    	 if(on.getLeft()instanceof VariableReferenceNode)
				   {
					   VariableReferenceNode VRN= (VariableReferenceNode) on.getLeft();
					   Variables.put(VRN.getVAR_name(), new InterpreterDataType(String.valueOf(0)));
					   i++;
						Variables.put(VR.getVAR_name(), new InterpreterDataType(String.valueOf(i)));
				   }
		     }
			   return new ReturnType(ReturnType.ReturnTypeEnum.NORMAL);

		}
		/*
		 * IfNode: Remember that ifNodes are a linked list. 
		 * Walk the linked list, looking for an IfNode where Condition is empty OR it evaluates to true. 
		 * When you find that, call InterpretListOfStatements on ifNode.statements. 
		 * If the return from InterpretListOfStatements is not None then return, passing that result back to the caller.
		 *  Why? Consider this code:
          while (!done) {
	          if (a==5) break;
          If a is 5, you want to break out of the while. To do that, the processing of the if node’s statements must pass back the return type.
		 */
		else if(statement instanceof IfNode)
		{
			
			IfNode ifNode = (IfNode) statement;
		    while (ifNode != null) 
		    {
		        if (ifNode.getCondition() == null || getIDT(ifNode.getCondition(), locals).type==("1")) 
		        {
		            ReturnType returnType = InterpretListOfStatements(ifNode.getStatements(), locals);
		            if (returnType.getType() != ReturnType.ReturnTypeEnum.NORMAL) 
		            {
		                return returnType;
		            }
		        }
		        ifNode = ifNode.getNext();
		    }
		    return new ReturnType(ReturnType.ReturnTypeEnum.NORMAL);
			   
		}
		else if(statement instanceof ReturnNode)
		{
			ReturnNode Rn= (ReturnNode) statement;
			if(Rn.getNode() ==null)
			{
	    	 ReturnType rt = new ReturnType(ReturnType.ReturnTypeEnum.RETURN);
	    	 return rt;		
			}
			else
			{
		    	 ReturnType rt = new ReturnType(ReturnType.ReturnTypeEnum.RETURN, getIDT(Rn.getNode(), locals).type);
                 return rt;
			}
				
		}
		 /*
		  * WhileNode: much like doWhile, but with a while loop instead of a do-while.
		  */
		else if(statement instanceof WhileNode)
		{
			WhileNode WN= (WhileNode)statement;
			while(getIDT(WN.getOn(), locals).type=="1")
			{
				ReturnType rt=	InterpretListOfStatements(WN.getStatements().getStatements(),locals);
				 if (rt.getType() == ReturnType.ReturnTypeEnum.RETURN) 
		            { 
		                return rt;
		            }
				 else if(rt.getType()==ReturnType.ReturnTypeEnum.BREAK)
				 {
					 break;
				 }
			}
			   return new ReturnType(ReturnType.ReturnTypeEnum.NORMAL);

		}
		
		ReturnType rt = new ReturnType(ReturnType.ReturnTypeEnum.NORMAL,getIDT(statement, locals).type );
   	 return rt;	
		}
/**
 * To Interpret List of statements of a block  and call process statement on each statemnet of the list
 * This is a simple loop over statements, calling processStatement() for each one, 
 * except that you should check the return type from each processStatement
 *  – if it is not None, return passing up the same ReturnType.
 * @param statements The linked list of the statements to be processed
 * @param locals the locals of a function
 * @return The return type of the process function
 * @throws Exception
 */
		public ReturnType InterpretListOfStatements(LinkedList<StatementNode> statements, HashMap<String, InterpreterDataType> locals) throws Exception 
		{
		    for (StatementNode statement : statements) 
		    {
		        ReturnType returnType = ProcessStatement(locals, statement);
		        if (returnType.getType() != ReturnType.ReturnTypeEnum.NORMAL) 
		        {
		            return returnType;
		        }
		    }
		    return new ReturnType(ReturnType.ReturnTypeEnum.NORMAL);
		}
			
		/**
		 * The "RunFunctionCall" method that takes the function call node and locals and returns a String
		 * @param node The function call node
		 * @param local_variables The locals 
		 * @return a string
		 * @throws Exception 
		 */

 private String RunFunctionCall(FunctionCallNode node, HashMap<String, InterpreterDataType> local_variables) throws Exception 
	 {
		 boolean foundfunction=false;
		 boolean matchparameretsnumbers=false;
		 FunctionDefinitionNode functionselected= null; 
		 if(Functions.containsKey(node.getFunctionName()))//IF THE FUNCTION IS A PREDEFINED FUNCTION
		 { 
			 BuiltInFunctionDefinitionNode BFDN = (BuiltInFunctionDefinitionNode) Functions.get(node.getFunctionName());
			 functionselected=BFDN;
			if( BFDN.Variadic)
			{
				matchparameretsnumbers=true;
			}
			else
			{
				//if(node.getParameters().size()==functionselected.getParameters().size())
				{
					matchparameretsnumbers=true;
				}
			}
			if(matchparameretsnumbers!=true)
			{
				 throw new Exception("The parameters of the function do not match");
			}
			 HashMap<String,InterpreterDataType> map= new HashMap<String,InterpreterDataType>();
			 int i=0;
             for(Node n: node.getParameters())
             {
            	 map.put(String.valueOf(i++), getIDT(n, null));
             }
             return BFDN.execute(map);
			
		 }
		 for(FunctionDefinitionNode FN : pn.getFunctionNodes())// IF NOT A USED DEFINED FUNCTION THEN WE WILL CHECK ALL THE FUNCTION NODEE AND TRY TO FIND THE FUNCTION WE WANT
		 {
			 String FNname=FN.getName();
			 String Nodename=node.getFunctionName();
			 if(FNname.equals(Nodename))
			 {
				 foundfunction= true;
				 if(FN.getParameters().size()==node.getParameters().size())
						 {
				 functionselected= FN;
				 matchparameretsnumbers=true;
				 break;
			        }
			 }
		 }
		 if(!foundfunction && !matchparameretsnumbers)
		 {
			 throw new Exception("The function defination do not exist");
		 }
		 else if(foundfunction && !matchparameretsnumbers)
		 {
			 throw new Exception("The parameters of the function do not match");
		 }
		 else
		 {
			 HashMap<String,InterpreterDataType> map= new HashMap<String,InterpreterDataType>();
			int i=0;
			 for(Node n: node.getParameters())
             {
            	 map.put(functionselected.getParameters().get(i++), getIDT(n, null));
             }
			return InterpretListOfStatements(functionselected.getStatements(), map).getValue();
		 }
	}
 /**
  * This It should run each of the "BEGIN" blocks (calling a new method called "InterpretBlock" for each one). 
  * It should then call LineManager’s SplitAndAssign, and for every record, call InterpretBlock() on every one of the blocks that 
  * are not BEGIN or END. Outside of that loop, it should call InterpretBlock() on each of the END blocks.
  * @param programNode THE NODE TO BE EXECUTED 
  * @throws Exception
  */
     public void InterpretProgram(ProgramNode programNode) throws Exception
     {
    	 LinkedList<BlockNode> BEGINblocks= programNode.getStartBlocks();
    	 LinkedList<BlockNode> blocks=programNode.getBlocks();
    	 LinkedList<BlockNode> ENDblocks= programNode.getEndBlocks();
    	 
    	 for(BlockNode  Bblocks : BEGINblocks)//THE BEGIN BLOCK
    	 {
    	 InrepreterBlock(Bblocks); 
    	 }
    	 while(LM.splitAndAssign())//CALLING THE BLOCK ON EACH AND EVER RECORD
    	 {
    	 for(BlockNode  N_blocks : blocks)
    	 {

    	 InrepreterBlock(N_blocks); 
    	 }}
    	 for(BlockNode  Eblocks : ENDblocks)//THE END BLOCK
    	 {
    	 InrepreterBlock(Eblocks); 
    	 }
    	 
     }
	/**
	 * THE FUNCTION INTERPRET BLOCK takes a BlockNode as a parameter. If the block has a condition,
	 *  we will test it to see if it is true. 
	 *  If there is no condition OR the test is true, then for each statement, we will call ProcessStatement.
	 * @param BN THE BLOCK NODE
	 * @throws Exception
	 */
     private void InrepreterBlock(BlockNode BN) throws Exception
	{
		if(BN.getCondition()!=null)
		{
			if(getIDT(BN, null).type=="1");
			InterpretListOfStatements(BN.getStatements(), null);
		}
		else
		{
			InterpretListOfStatements(BN.getStatements(), null);
		}
	}
	public class LineManager //The inner class 
	 {
	        private List<String> lines;//The list of string
	        private int currentLineIndex = 0;
            /*
             * The default constructor
             */
	        public LineManager(List<String> lines) 
	        {
	            this.lines = lines;
	        }
            /**
             * The slit and assign method that gets the next line and split it by looking at the global variables to find "FS"
             * @return true normally but false if there is no line to split any more
             */
	        public boolean splitAndAssign() 
	        {
	        	if(lines== null) {
	                return false;

	        	}
	            if (currentLineIndex >= lines.size()) 
	            {
	                return false;
	            }
	            String line = lines.get(currentLineIndex++);
	            String fs = Variables.get("FS").type;
	            String[] fields = line.split(fs);
                Variables.put("$0", new InterpreterDataType(line));
	            for (int i = 0; i < fields.length; i++) 
	            {
	                Variables.put("$"+(i+1), new InterpreterDataType(fields[i])); 
	            }
	            Variables.put("NR", new InterpreterDataType(String.valueOf(currentLineIndex)));

                Variables.put("NF", new InterpreterDataType(String.valueOf(fields.length)));
	            Variables.put("FNR", new InterpreterDataType(String.valueOf(currentLineIndex))); // Assuming FNR is same as NR
	            return true;
	        }
	    } 
		public String op()
		{
			return mainreturn;
		}
}