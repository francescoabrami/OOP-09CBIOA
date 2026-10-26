package example;

import hydraulic.*;
import it.polito.oop.utils.Requirement;

import static it.polito.oop.utils.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestExampleR1R2 {
    
    @Test @Requirement(1)
    void testInitial() {
        HSystem s = new HSystem();

        assertEquals(0, s.size(), "There should be no element.");
        
        Element[] elements = s.getElements();
        assertNotNull(elements, "Apparently getElements() is not implemented yet");
        assertEquals(0, elements.length, "Initially no elements are present in the system");     
    }   

    @Test @Requirement(1)
    void testSimpleElements() {
        HSystem s = new HSystem();

        // the elements of the system are defined and added
        Element src = new Source("Src");
        s.addElement(src);
        Element r = new Tap("R");
        s.addElement(r);
        Element sink = new Sink("sink B");
        s.addElement(sink);

        assertEquals("Src", src.getName(), "Wrong source name.");
        assertEquals("sink B", sink.getName());

        Element[] elements = s.getElements();
        assertNotNull(elements, "Apparently getElements() is not implemented yet");
        assertEquals(3, elements.length, "Wrong number of elements");
        assertArrayContains(elements, src, "Missing source");
        assertArrayContains(elements, sink, "Missing source");
        
        // elements are then connected
        src.connect(r);
        r.connect(sink);
    
        assertSame(r, src.getOutput(), "Output of src should be r");
    }

    @Test @Requirement(1)
    void testSplit() {
        HSystem s = new HSystem();

        Element src = new Source("Src");
        s.addElement(src);
        Element r = new Tap("R");
        s.addElement(r);
        Element t = new Split("T");
        s.addElement(t);
        Element sinkA = new Sink("sink A");
        s.addElement(sinkA);
        Element sinkB = new Sink("sink B");
        s.addElement(sinkB);

        src.connect(r);
        r.connect(t);
        t.connect(sinkA,0);
        t.connect(sinkB,1);

        assertSame(r, src.getOutput(), "Output of src should be r.");
        Element[] outputs = t.getOutputs();
        assertArrayEquals(new Element[] {sinkA,sinkB}, outputs, "Outputs of t should be sink A and sink B");
    }

    @Test @Requirement(2)
    void testSimulation() {
        HSystem s = new HSystem();

        Source src = new Source("Src");
        s.addElement(src);
        Tap r = new Tap("R");
        s.addElement(r);
        Element t = new Split("T");
        s.addElement(t);
        Element sinkA = new Sink("sink A");
        s.addElement(sinkA);
        Element sinkB = new Sink("sink B");
        s.addElement(sinkB);
        src.connect(r);
        r.connect(t);
        t.connect(sinkA,0);
        t.connect(sinkB,1);

        // Simulation parameters are then defined
        src.setFlow(20);
        r.setOpen(true);
        
        // simulation starts
        PrintingObserver obs = new PrintingObserver();
        s.simulate(obs);

        assertEquals(5, obs.getCount(), "Wrong number of notifications.");
    }

}