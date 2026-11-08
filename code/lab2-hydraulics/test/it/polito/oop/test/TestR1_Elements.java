package it.polito.oop.test;

import static it.polito.oop.utils.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import hydraulic.*;

class TestR1_Elements {

	@Test
	void testEmptySystem(){
		HSystem s = new HSystem();

		Element[] elements = s.getElements();

		assertEquals(0, s.size(), "There should be no element");

		assertNotNull(elements, "Missing elements");

		assertEquals(0, elements.length, "There should be any elements at first");
	}

	@Test
	void testGetElements(){
		HSystem s = new HSystem();

		Element el1 = new Source("Test");		
		Element el2 = new Source("Test 1");
		Element el3 = new Source("Test 2");	
		s.addElement(el1);
		s.addElement(el2);
		s.addElement(el3);

		Element[] elements = s.getElements();

		assertArrayContainsAll(elements, all(el1, el2), "Missing elements");
	}

	@Test
	void testNameSource(){
		String name="Test";
		Element el = new Source(name);

		assertEquals(name, el.getName(), "Wrong name for element");
	}

	@Test
	void testNameTap(){
		String name="Test";
		Element el = new Tap(name);

		assertEquals(name, el.getName(), "Wrong name for element");
	}

	@Test
	void testNameSink(){
		String name="Test";
		Element el = new Sink(name);

		assertEquals(name, el.getName(), "Wrong name for element");
	}

	@Test
	void testConnections(){
		HSystem s = new HSystem();
		Source src = new Source("Src");		
		Tap tap = new Tap("Tap");		
		Sink sink = new Sink("Sink");

		s.addElement(src);
		s.addElement(tap);
		s.addElement(sink);

		src.connect(tap);
		tap.connect(sink);

		assertSame(tap, src.getOutput(), "Wrong output for element src");
		assertSame(sink, tap.getOutput(), "Wrong output for element tap");
	}

	@Test
	void testSinkConnect(){
		Tap tap = new Tap("Tap");		
		Sink sink = new Sink("Sink");

		Element none = sink.getOutput();

		sink.connect(tap);

		Element out = sink.getOutput();

		assertSame(none, out, "Connect on a sink should have no effect");

		tap.connect(sink);

		assertSame(sink, tap.getOutput(), "Connect to a sink should work");
	}

	@Test
	void testSplitName(){
		Split t = new Split("Split name");	

		assertNotNull(t.getName(), "Missing split name");
		assertEquals("Split name", t.getName(), "Wrong name for split element");
	}


	@Test
	void testSplit(){

		HSystem s = new HSystem();

		Element src = new Source("Src");		
		Split t = new Split("T");	
		Element sink1 = new Sink("Sink 1");		
		Element sink2 = new Sink("Sink 2");		

		s.addElement(src);
		s.addElement(t);
		s.addElement(sink1);
		s.addElement(sink2);

		src.connect(t);
		t.connect(sink1, 0);
		t.connect(sink2, 1);

		Element[] out = t.getOutputs();

		assertNotNull(out, "Missing output for the split");
		assertSame(sink1, out[0], "Wrong output 0 for Split");
		assertSame(sink2, out[1], "Wrong output 1 for Split");
	}

}
