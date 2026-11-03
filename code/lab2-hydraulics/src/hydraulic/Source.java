package hydraulic;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * Lo status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends Element {

	private Element output = null;

	/**
	 * constructor
	 * @param name name of the source element Source
	 */
	public Source(String name) {

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
	public void setOutput(Element elem){

		this.output = elem;

	}

	public void setFlow(double flow){

		this.outFlow = flow;

		if (output != null) {

			output.setFlow(flow);

		}
		
	}

}
