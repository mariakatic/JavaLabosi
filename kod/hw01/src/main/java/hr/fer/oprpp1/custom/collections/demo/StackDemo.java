package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {
	
	public static void main(String[] args) {
		
		ObjectStack stack = new ObjectStack();
		
		String entry = args[0];
		String[] expression = entry.split(" ");
		
		for (int i = 0; i < expression.length; i++) {
			
			String str = expression[i];
			
			if (isNumeric(str)) {
				
				Integer d = Integer.parseInt(str);
				stack.push(d);
				
			} else {

				Integer el1 = (Integer)stack.pop();
				Integer el2 = (Integer)stack.pop();
				
				switch(str) {
					case "+": 
                    stack.push(el2 + el1); 
                    break; 
                      
                    case "-": 
                    stack.push(el2 - el1); 
                    break; 
                      
                    case "%": 
                    stack.push(el2 % el1); 
                    break; 
                      
                    case "*": 
                    stack.push(el2 * el1); 
                    break; 
                    
                    case "/": 
                    if (el2.intValue() == 0) throw new ArithmeticException("Nedopušteno dijeljenje s nulom!");
                    else stack.push(el2 / el1); 
                    break; 
                    
                    default:
                	System.out.println("Expression is invalid");
                	return;
				}
			
			}
		}
		
		if (stack.size() == 1) System.out.println("Expression evaluates to " + stack.pop());
		else System.out.println("Expression is invalid");
	}
	
	public static boolean isNumeric(String s) {
		  return s.matches("-?\\d+(\\.\\d+)?"); 
	}
	
}
