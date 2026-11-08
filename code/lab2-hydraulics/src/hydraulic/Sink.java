package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends Element {

	private Element input = null;

	/**
	 * Constructor of the Sink;
	 * 
	 * @param name name of the sink element;
	 */
	public Sink(String name) {

		super(name);

	}

	@Override
	public void connect(Element elem) {

	
				
	}

	@Override
	public void setInput(Element input) {

		this.input = input;

	}

	@Override
	public Element getInput() {

		return this.input;

	}

}
