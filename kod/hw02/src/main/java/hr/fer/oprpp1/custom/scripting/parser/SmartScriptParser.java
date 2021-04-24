package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;

/**
 * Razred predstavlja parser koji pomocu leksera stvara stablastu strukturu dokumenta.
 * @author Maria
 *
 */
public class SmartScriptParser {
	
	private Lexer lexer;

	private Node node;
	
	private ObjectStack stack;
	
	/**
	 * Stvara novi parser i lekser i vrsi parsiranje teksta.
	 * @param body tekst koji je potrebno parsirati
	 */
	public SmartScriptParser(String body) {
		lexer = new Lexer(body);
		node = new DocumentNode();
		stack = new ObjectStack();
		stack.push(node);
		this.parse();
	}
	
	/**
	 * Metoda koja obavlja parsiranje.
	 */
	public void parse() {
		boolean flag = false;
		try {
			
			while (true) {
				
				if (flag) {
					if (lexer.getToken().getType() == TokenType.EOF)
						break;
				}
				
				
				if (lexer.getState() == LexerState.TEXT) {
					flag = true;
					Token token = lexer.nextToken();
					
					if (token.getType() != TokenType.EOF && token.getValue() != "") {
						TextNode tNode = new TextNode(token.getValue().toString());
						((Node)stack.peek()).addChildNode(tNode);
					}
					
					lexer.setState(LexerState.TAG);
					
					
				} else {
					
					Token token = lexer.nextToken();
					
					if (token.getType() == TokenType.EOF) break;
					
					if (token.getType() == TokenType.TAG && token.getValue().equals("{$")) {
						token = lexer.nextToken();
					} else {
						throw new SmartScriptParserException();
					}
					
					if (token.getType() == TokenType.EQUAL) {
						
						ArrayIndexedCollection col = new ArrayIndexedCollection();
						
						while (token.getType() != TokenType.TAG) {
							token = lexer.nextToken();
							
							if (token.getType() == TokenType.VARIABLE) {
								ElementVariable var = new ElementVariable(token.getValue().toString());
								col.add(var);
								
							} else if (token.getType() == TokenType.OPERATOR) {
								ElementOperator var = new ElementOperator(token.getValue().toString());
								col.add(var);
								
							} else if (token.getType() == TokenType.STRING) {
								ElementString var = new ElementString(token.getValue().toString());
								col.add(var);
								
							} else if (token.getType() == TokenType.CONST_DOUBLE) {
								ElementConstantDouble var = new ElementConstantDouble(Double.parseDouble(token.getValue().toString()));
								col.add(var);
								
							} else if (token.getType() == TokenType.CONST_INTEGER) {
								ElementConstantInteger var = new ElementConstantInteger(Integer.parseInt(token.getValue().toString()));
								col.add(var);
								
							} else if (token.getType() == TokenType.FUNCTION) {
								ElementFunction var = new ElementFunction(token.getValue().toString());
								col.add(var);
								
							} else if (token.getType() != TokenType.TAG){
								throw new SmartScriptParserException();
							}
							
						}
						
						Element[] elements = new Element[col.size()];
						for (int i = 0; i < col.size(); i++) {
							elements[i] = (Element) col.get(i);
						}
						
						EchoNode echo = new EchoNode(elements);
						((Node)stack.peek()).addChildNode(echo);
						
						if (token.getType() == TokenType.TAG && token.getValue().equals("$}")) {
							lexer.setState(LexerState.TEXT);
							continue;
						} else {
							throw new SmartScriptParserException();
						}
						
					} else if (token.getType() == TokenType.VARIABLE) {
						
						if (token.getValue().toString().toUpperCase().equals("FOR")) {
							ElementVariable variable;
							Element startExpression;
							Element endExpression;
							Element stepExpression;
							
							
							token = lexer.nextToken();
							
							if (token.getType() != TokenType.VARIABLE)
								throw new SmartScriptParserException();
							
							variable = new ElementVariable(token.getValue().toString());
							
							token = lexer.nextToken();
							if (token.getType() == TokenType.VARIABLE) {
								startExpression = new ElementVariable(token.getValue().toString());
								
							}  else if (token.getType() == TokenType.STRING) {
								startExpression = new ElementString(token.getValue().toString());
								
							} else if (token.getType() == TokenType.CONST_DOUBLE) {
								startExpression = new ElementConstantDouble(Double.parseDouble(token.getValue().toString()));
								
							} else if (token.getType() == TokenType.CONST_INTEGER) {
								startExpression = new ElementConstantInteger(Integer.parseInt(token.getValue().toString()));
								
							} else {
								throw new SmartScriptParserException();
							}
							
							token = lexer.nextToken();
							if (token.getType() == TokenType.VARIABLE) {
								stepExpression = new ElementVariable(token.getValue().toString());
								
							}  else if (token.getType() == TokenType.STRING) {
								stepExpression = new ElementString(token.getValue().toString());
								
							} else if (token.getType() == TokenType.CONST_DOUBLE) {
								stepExpression = new ElementConstantDouble(Double.parseDouble(token.getValue().toString()));
								
							} else if (token.getType() == TokenType.CONST_INTEGER) {
								stepExpression = new ElementConstantInteger(Integer.parseInt(token.getValue().toString()));
								
							} else {
								throw new SmartScriptParserException();
							}
							
							token = lexer.nextToken();
							if (token.getType() == TokenType.VARIABLE) {
								endExpression = new ElementVariable(token.getValue().toString());
								
							}  else if (token.getType() == TokenType.STRING) {
								endExpression = new ElementString(token.getValue().toString());
								
							} else if (token.getType() == TokenType.CONST_DOUBLE) {
								endExpression = new ElementConstantDouble(Double.parseDouble(token.getValue().toString()));
								
							} else if (token.getType() == TokenType.CONST_INTEGER) {
								endExpression = new ElementConstantInteger(Integer.parseInt(token.getValue().toString()));
								
							} else if (token.getType() == TokenType.TAG) {
								endExpression = stepExpression;
								stepExpression = null;
								
							} else {
								throw new SmartScriptParserException();
							}
							
							if (token.getType() != TokenType.TAG) token = lexer.nextToken();
							ForLoopNode forNode = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
							((Node)stack.peek()).addChildNode(forNode);
							stack.push(forNode);
							
							if (token.getType() == TokenType.TAG && token.getValue().equals("$}")) {
								lexer.setState(LexerState.TEXT);
								continue;
							} else {
								throw new SmartScriptParserException();
							}
							
							
						} else if (token.getValue().toString().toUpperCase().equals("END")) {
							token = lexer.nextToken();
							if (token.getType() == TokenType.TAG && token.getValue().equals("$}")) {
								if (!stack.isEmpty()) {
									stack.pop();
									lexer.setState(LexerState.TEXT);
									continue;
								} else {
									throw new SmartScriptParserException();
								}
							} else {
								throw new SmartScriptParserException();
							}
						}
	
					} 
					
					throw new SmartScriptParserException();
					
				}
				
				
			}
			
			if (stack.size() != 1) 
				throw new SmartScriptParserException();
			return;
		} catch(Exception ex) {
			throw new SmartScriptParserException();
		}
	
	}
	
	/**
	 * Vraca pocetni cvor koji je parser izgenerirao.
	 * @return
	 */
	public DocumentNode getDocumentNode() {
		return (DocumentNode)node;
	}
}
