package it.polito.oop.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Utility assertion methods
 * 
 * specialized assert methods, make the test easier to read
 */
public class Assertions {

	private Assertions() {}

	/**
	 * Assert that a sub string is contained in the actual string
	 * 
	 * @param expectedSubStr the expected sub string
	 * @param actualStr      the actual string
	 */
	public static void assertContained(String expectedSubStr, String actualStr) {
		assertContained(expectedSubStr,actualStr, null);
	}
	
	
	/**
	 * Assert that a sub string is contained in the actual string
	 * 
	 * @param expectedSubStr the expected sub string
	 * @param actualStr      the actual string
	 */
	public static void assertContained(String expectedSubStr, String actualStr, String msg) {
		assertTrue((actualStr != null && actualStr.contains(expectedSubStr)),
				   (msg==null?"":msg+": ") + "expected sub string [" + expectedSubStr + "] is not contained in "+fmt(actualStr)+"");
	}
		
    public static void assertCountLines(String s, int expectedLines, String msg){
        int lines = -1;
		if(s==null) lines = 0;
        else lines = 1 + s.trim().replaceAll("[^\n]", "").length();
        assertEquals(expectedLines, lines, msg);
	}

	private static String fmt(String s) {
		if(s==null) return "'null'";
		else return "[" + s + "]";
	}

	/**
	 * Checks wether an array {@code a} contains element {@code e}
	 * 
	 * @param <T>	type of array elements
	 * @param a		the array
	 * @param e		the element
	 * @param msg	message in case of failure
	 */
	public static <E> void assertArrayContains(E[] a, E e, String msg) {
        if (e == null) fail("null is not contained in array");
        for (E el : a) {
            if (e.equals(el)) {
                return;
            }
        }
        fail("Array does not contain element " + e + " : " + msg);
    }

	@SafeVarargs
	public static <T> T[] all(T... elements){
		return elements;
	}

	/**
	 * Asserts that an array contains all the given elements
	 * 
	 * @param ary 		the reference array to be checked against
	 * @param elements	the elements to be matched in the reference array
	 * @param msg		the failure message
	 */
	public static <E> void assertArrayContainsAll(E[] ary, E[] elements, String msg) {
		if(ary==null) fail(msg + " null array.");
		for(int i=0; i<elements.length; ++i) {
			boolean found = false;
			E e = elements[i];
			for(int j=0; j<ary.length; ++j) {
				E r = ary[i];
				if( r == null ){
					fail(msg + " null element at index " + j + " in reference array.");
				}
				if(e.equals(ary[i])) {
					found = true;
					break;
				}
			}
			if(!found){
				fail(msg + ". Reference array does not contain element at index " + i + " : " + e);
			}
		}
	}
}
