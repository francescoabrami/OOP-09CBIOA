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

	public void setOpen(boolean open) {

		this.isOpen = open;

		this.outFlow = open ? this.inFlow : 0.0;

		if (output != null) {
			output.setFlow(this.outFlow);
		}
	}

	public boolean isOpen() {

		return this.isOpen;

	}

	@Override
	public void setFlow(double flow) {

		this.inFlow = flow;
		this.outFlow = isOpen ? flow : 0.0;

		if (output != null) {
			output.setFlow(this.outFlow);
		}
	}

}
