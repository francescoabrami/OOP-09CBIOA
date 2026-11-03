package hydraulic;

/**
 * Represents a multisplit element, an extension of the Split that allows many outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */

public class Multisplit extends Split {

	private Element[] outputs;
	private Element input;
	private double inFlow, outFlow;
	private double[] proportions = {0.0};

	public Multisplit(String name, int numOutput) {

		super(name);
		
		outputs = new Element[numOutput];

	}

	@Override
    public void connect(Element elem, int index) {

        if (index < 0 || index >= outputs.length) {

            return;

        }
		
        outputs[index] = elem;
		elem.setInput(this);

    }

	public Element[] getOutputs() {

		return outputs;

	}

	public double getInFlow() {

		return inFlow;

	}

	@Override
	public void setInput(Element entrata) {

		this.input = entrata;

	}

	public void setFlow(double inFlow) {

		this.inFlow = inFlow;
		this.outFlow = inFlow;
		this.setOutFlow(inFlow);

	}

	public double getOutFlow() {

		return outFlow;

	}

	public void setOutFlow(double outFlow) {
		
		for(int i = 0; i < proportions.length; i++){

			outputs[i].setFlow(proportions[i]);

		}
	}
	
	/**
	 * Define the proportion of the output flows w.r.t. the input flow.
	 * 
	 * The sum of the proportions should be 1.0 and 
	 * the number of proportions should be equals to the number of outputs.
	 * Otherwise a check would detect an error.
	 * 
	 * @param proportions the proportions of flow for each output
	 */
	public void setProportions(double... proportions) {

		double checkSum = 0.0;

		for (int i = 0; i < proportions.length; i++) {

			checkSum =+ proportions[i];

		}

		if (checkSum == 1.0) {

			this.proportions = proportions;

			for (int i = 0; i < proportions.length; i++) {

				if(outputs[i] != null){

					Element out = outputs[i];
					out.setFlow(proportions[i] * outFlow);

				}
				
			}

		}

		else {

			System.out.println("The sum of the proportions is different than 1.0!\n\n");

		}

	}
	
}
