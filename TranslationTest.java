package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class TranslationTest {

	ArrayList<String> a = new ArrayList<String>();
	ArrayList<String> b = new ArrayList<String>();
	ArrayList<String> c = new ArrayList<String>();
	ArrayList<String> d = new ArrayList<String>();
	
	@Test
	void test() {
		
		System.out.println("Checking Translate Method");
		System.out.println("");
		
//		One letter
		Translation t = new Translation();
		a.add("short");
		a.add("short");
		a.add("short");	
		System.out.println("Check if one letter Translation works:");
		System.out.println("S = " + t.Translate(a));
		assertEquals("S",t.Translate(a));
		
//		Two letters
		a.add(" ");
		a.add("long");
		a.add("short");
		System.out.println("Check if two letter Translation works:");
		System.out.println("S N = " + t.Translate(a));
		assertEquals("S N", t.Translate(a));
		
//		Start with a space
		c.add(" ");
		c.add("short");
		c.add("short");
		System.out.println("Check if leading with space Translation works:");
		System.out.println("  I = " + t.Translate(c));
		assertEquals(" I", t.Translate(c));
		
//		Translate a number
		b.add("short");
		b.add("short");
		b.add("short");	
		b.add("short");
		b.add("long");
		System.out.println("Check if one number Translation works:");
		System.out.println("4 = " + t.Translate(b));
		assertEquals("4",t.Translate(b));
		
//		Two spaces between number and letter
		b.add(" ");
		b.add(" ");
		b.add("long");
		b.add("short");
		System.out.println("Check if two spaces between characters Translation works:");
		System.out.println("4  N = " + t.Translate(b));
		assertEquals("4  N",t.Translate(b));
		
//		Translate message that insn't real Morse
		d.add("short");
		d.add("short");
		d.add("short");
		d.add("long");
		d.add("short");
		System.out.println("Check non morse Translation works");
		assertEquals("", t.Translate(d));
		
//		Long message
		d.clear();
		d.add("short");
		d.add("long");
	    d.add("long");
        d.add("long");
        d.add(" ");
        d.add("short");
		d.add("long");
		d.add(" ");
		d.add("short");
		d.add("short");
		d.add("short");
		d.add(" ");
		d.add("long");
		d.add("long");
		d.add("long");
		d.add(" ");
		d.add("long");
		d.add("short");
		System.out.println("Check long message Translation works:");
		System.out.println("J A S O N = " + t.Translate(d));
		assertEquals("J A S O N",t.Translate(d));
		
		System.out.println("");
		System.out.println("Checking translation Method");
		System.out.println("");
		
//		Convert letter
		a.clear();
		a.add("short");
		a.add("short");
		a.add("short");	
		System.out.println("Check letter conversion:");
		System.out.println("S = " + t.translation(a));
		assertEquals("S",t.translation(a));
		
//		Convert number
		a.clear();
		a.add("short");
		a.add("short");
		a.add("short");	
		a.add("short");
		a.add("short");	
		System.out.println("Check number conversion:");
		System.out.println("5 = " + t.translation(a));
		assertEquals("5",t.translation(a));
		
//		Convert incorrect Morse
		a.add("short");
		System.out.println("Check incorrect Morse conversion");
		assertEquals("",t.translation(a));
		
		System.out.println("");
		System.out.println("Checking arrayToMessage Method");
		System.out.println("");
		
//		Convert letter array to string
		a.clear();
		a.add("J");
		System.out.println("Check letter array to string conversion:");
		System.out.println("J = " + t.arrayToMessage(a));
		assertEquals("J",t.arrayToMessage(a));
		
		
//		Convert word array to string
		a.clear();
		a.add("J");
		a.add("A");
		a.add("S");
		a.add("O");
		a.add("N");
		System.out.println("Check word array to string conversion:");
		System.out.println("JASON = " + t.arrayToMessage(a));
		assertEquals("JASON",t.arrayToMessage(a));
		
//		Convert number array to string
		a.clear();
		a.add("1");
		System.out.println("Check number array to string conversion:");
		System.out.println("1 = " + t.arrayToMessage(a));
		assertEquals("1",t.arrayToMessage(a));
		
//		Convert numbers and letters array to string
		a.add("J");
		a.add("3");
		a.add("D");
		System.out.println("Check numbers and letters array to string conversion:");
		System.out.println("1J3D = " + t.arrayToMessage(a));
		assertEquals("1J3D",t.arrayToMessage(a));
		
//		Convert with spaces
		a.clear();
		a.add(" ");
		a.add("1");
		a.add(" ");
		a.add("J");
		a.add(" ");
		a.add("3");
		a.add(" ");
		a.add("D");
		a.add(" ");
		System.out.println("Check numbers and letters array to string conversion:");
		System.out.println(" 1 J 3 D = " + t.arrayToMessage(a));
		assertEquals(" 1 J 3 D ",t.arrayToMessage(a));
		
	}

}
