package hydraulic;

/**
 * Represents a tap that can interrupt the flow.
 * 
 * The status of the tap is defined by the method
 * {@link #setOpen(boolean) setOpen()}.
 */

public class Tap extends Element {

	private Element output = null;
	private Element input = null;
	private boolean isOpen;

	/**
	 * Constructor
	 * @param name name of the tap element
	 */
	public Tap(String name) {

		super(name);

	}

	@Override
	public void connect(Element elem){

		this.output = elem;
		elem.setInput(this);
		
	}

	@Override
	public Element getOutput(){

		return this.output;

	}

	@Override
	public Element getInput(){

		return this.input;

	}

	@Override
	public void setOutput(Element elem){

		this.output = elem;

	}

	@Override 
	public void setInput(Element input) {

    	this.input = input;

	}

	/**
	 * Set whether the tap is open or not. The status is used during the simulation.
	 *
	 * @param open opening status of the tap
	 */
	public void setOpen(boolean open){
		
		this.isOpen = open;

		if (open) {

			this.outFlow = inFlow;

			if (output != null) {

				output.setFlow(outFlow);

			}

		} 

	}

	public boolean isOpen() {

		return this.isOpen;

	}

	@Override
	public void setFlow(double Flow) {

		if(isOpen){

			this.outFlow = Flow;
			this.inFlow = Flow;

		} else {

			this.inFlow = Flow;
			this.outFlow = 0.0;

		}
        
    }

}
