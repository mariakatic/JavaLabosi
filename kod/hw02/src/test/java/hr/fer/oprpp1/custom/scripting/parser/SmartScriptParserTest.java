package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;


import hr.fer.oprpp1.custom.scripting.nodes.Node;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

public class SmartScriptParserTest {

	@Test
	public void test1() {
		String text = readExample(1);
		SmartScriptParser parser = new SmartScriptParser(text);
		Node node = parser.getDocumentNode();
		int numTextNodes = 0;
		for (int i = 0; i < node.numberOfChildren(); i++) {
			if (node.getChild(i).getClass().equals(TextNode.class)) numTextNodes++;
		}
		assertEquals(numTextNodes, 1);
	}
	
	@Test
	public void test2() {
		String text = readExample(2);
		SmartScriptParser parser = new SmartScriptParser(text);
		Node node = parser.getDocumentNode();
		int numTextNodes = 0;
		for (int i = 0; i < node.numberOfChildren(); i++) {
			if (node.getChild(i).getClass().equals(TextNode.class)) numTextNodes++;
		}
		assertEquals(numTextNodes, 1);
	}
	
	@Test
	public void test3() {
		String text = readExample(3);
		SmartScriptParser parser = new SmartScriptParser(text);
		Node node = parser.getDocumentNode();
		int numTextNodes = 0;
		for (int i = 0; i < node.numberOfChildren(); i++) {
			if (node.getChild(i).getClass().equals(TextNode.class)) numTextNodes++;
		}
		assertEquals(numTextNodes, 1);
	}
	
	@Test
	public void test4() {
		String text = readExample(4);
		SmartScriptParser parser = new SmartScriptParser(text);
		Node node = parser.getDocumentNode();
		int numTextNodes = 0;
		for (int i = 0; i < node.numberOfChildren(); i++) {
			if (node.getChild(i).getClass().equals(TextNode.class)) numTextNodes++;
		}
		assertEquals(numTextNodes, 1);
	}
	
	@Test
	public void test5() {
		String text = readExample(5);
		SmartScriptParser parser = new SmartScriptParser(text);
		Node node = parser.getDocumentNode();
		int numTextNodes = 0;
		for (int i = 0; i < node.numberOfChildren(); i++) {
			if (node.getChild(i).getClass().equals(TextNode.class)) numTextNodes++;
		}
		assertEquals(numTextNodes, 1);
	}
	
	@Test
	public void test6() {
		String text = readExample(6);
		SmartScriptParser parser = new SmartScriptParser(text);
		Node node = parser.getDocumentNode();
		int numTextNodes = 0;
		for (int i = 0; i < node.numberOfChildren(); i++) {
			if (node.getChild(i).getClass().equals(TextNode.class)) numTextNodes++;
		}
		assertEquals(numTextNodes, 1);
	}
	
	@Test
	public void test7() {
		String text = readExample(7);
		SmartScriptParser parser = new SmartScriptParser(text);
		Node node = parser.getDocumentNode();
		int numTextNodes = 0;
		for (int i = 0; i < node.numberOfChildren(); i++) {
			if (node.getChild(i).getClass().equals(TextNode.class)) numTextNodes++;
		}
		assertEquals(numTextNodes, 1);
	}
	
	@Test
	public void test8() {
		String text = readExample(8);
		SmartScriptParser parser = new SmartScriptParser(text);
		Node node = parser.getDocumentNode();
		int numTextNodes = 0;
		for (int i = 0; i < node.numberOfChildren(); i++) {
			if (node.getChild(i).getClass().equals(TextNode.class)) numTextNodes++;
		}
		assertEquals(numTextNodes, 1);
	}
	
	@Test
	public void test9() {
		String text = readExample(9);
		SmartScriptParser parser = new SmartScriptParser(text);
		Node node = parser.getDocumentNode();
		int numTextNodes = 0;
		for (int i = 0; i < node.numberOfChildren(); i++) {
			if (node.getChild(i).getClass().equals(TextNode.class)) numTextNodes++;
		}
		assertEquals(numTextNodes, 1);
	}
	
	private String readExample(int n) {
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
			if(is==null) 
				throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
			byte[] data = is.readAllBytes();
			String text = new String(data, StandardCharsets.UTF_8);
			return text;
		} catch(IOException ex) {
			throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		}
	}


}
