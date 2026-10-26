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
	
	/**
	 * getter method for the name of the element
	 * 
	 * @return the name of the element
	 */
	public String getName() {
		return null;
	}
	
	/**
	 * Connects this element to a given element.
	 * The given element will be connected downstream of this element
	 * 
	 * In case of element with multiple outputs this method operates on the first one,
	 * it is equivalent to calling {@code connect(elem,0)}. 
	 * 
	 * @param elem the element that will be placed downstream
	 */
	public void connect(Element elem) {
		// does nothing by default
	}
	
	/**
	 * Connects a specific output of this element to a given element.
	 * The given element will be connected downstream of this element
	 * 
	 * @param elem the element that will be placed downstream
	 * @param index the output index that will be used for the connection
	 */
	public void connect(Element elem, int index){
		// does nothing by default
	}
	
	/**
	 * Retrieves the single element connected downstream of this element
	 * 
	 * @return downstream element
	 */
	public Element getOutput(){
		return null;
	}

	/**
	 * Retrieves the elements connected downstream of this element
	 * 
	 * @return downstream element
	 */
	public Element[] getOutputs(){
		return null;
	}
	
	/**
	 * Defines the maximum input flow acceptable for this element
	 * 
	 * @param maxFlow maximum allowed input flow
	 */
	public void setMaxFlow(double maxFlow) {
		// does nothing by default
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
