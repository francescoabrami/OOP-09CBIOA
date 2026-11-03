package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends Element {

	private Element[] outputs = new Element[MAX_OUTPUTS];
	private Element input;

	/**
	 * Constructor
	 * @param name name of the split element
	 */
	public Split(String name) {

		super(name);

	}

	@Override 
	public void connect(Element elem, int index){

		outputs[index] = elem;
		elem.setInput(this);

	}

	@Override

	public Element[] getOutputs(){

		return outputs;

	}

	@Override
	public Element getInput() {

		return input;

	}

	@Override
	public void setInput(Element input) {

		this.input = input;
		
	}


	@Override 
	public int countsOutputs() {

		int currentsOutputs = 0;

		for (int i = 0; i < MAX_OUTPUTS; i++) {

			if (outputs[i] != null) {

				currentsOutputs++ ;

			}

		}

		return currentsOutputs;


	}
	
}
