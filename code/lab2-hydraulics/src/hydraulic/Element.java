package hydraulic;


/**
 * Represents the generic abstract element of an hydraulics system.
 * It is the base class for all elements.
 *
 * Any element can be connect to a downstream element
 * using the method {@link #connect(Element) connect()}.
 * 
 * The class is abstract since it is not intended to be instantiated,
 * though all methods are defined to make subclass implementation easier.
 */
public abstract class Element {

	public static final int MAX_OUTPUTS = 2;

	String name;
	double inFlow, outFlow;

	/**
	 * Constructor of an Element
	 * 
	 * @param name name of the element
	 */
	public Element(String name) {

		this.name = name;

	}
	
	
	public String getName() {

		return name;

	}
	
	public void connect(Element elem) {


	}
	
	public void connect(Element elem, int index){


	}

	public void setInput(Element input) {


	}

	public void setOutput(Element output) {


	}

	public Element getInput() {

		return null;

	}
	
	public Element getOutput(){

		return null;

	}

	public Element[] getOutputs(){

		return null;

	}	

	public void setFlow(double Flow) {

        this.outFlow = Flow;
		this.inFlow = Flow;

    }

	public double getInFlow() {

		return this.inFlow;

	}

	public double getOutFlow() {

		return this.outFlow;

	}

	
	public void setMaxFlow(double maxFlow) {


	}

	public int countsOutputs() {

		return 0;

	}

	@Override
	public String toString(){
		StringBuilder buffer = new StringBuilder("[%s]".formatted(getName()));
		int n = buffer.length();
		Element[] out = getOutputs();
		
		if( out!=null ){
			final String OUTPUT  =" +-> ";
			final String FILL    ="\n |   ";
			final String LASTFILL="\n     ";
			final String LASTOUT =out.length==1?" --> ":" \\-> ";
		
			for(int i=0; i<out.length; ++i) {
				if(i>0) buffer.append("\n");
				String outString = out[i] == null?" * ":out[i].toString();
				if(i==out.length-1) buffer.append(LASTOUT).append(outString.replace("\n",LASTFILL));
				else buffer.append(OUTPUT).append(outString.replace("\n",FILL));
			}
		}
		String res = buffer.toString().replace("\n", ("\n%"+n+"s").formatted(""));
		return res;
	}
}
