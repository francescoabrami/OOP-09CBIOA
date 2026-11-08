package hydraulic;

/**
 * Represents a multisplit element, an extension of the Split that allows many outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */

public class Multisplit extends Split {

	private Element[] outputs;
	private double[] proportions = {0.0};

	public Multisplit(String name, int numOutput) {

		super(name);

		outputs = new Element[numOutput];
		proportions = new double[numOutput];

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

	// @Override
	// public void setInput(Element entrata) {

	// 	this.input = entrata;

	// }

	public void setFlow(double inFlow) {

		this.inFlow = inFlow;
		this.outFlow = inFlow;
		this.setOutFlow(inFlow);

	}

	public double getOutFlow() {

		return outFlow;

	}


	public void setOutFlow(double outFlow) {

		this.outFlow = outFlow;

		for (int i = 0; i < proportions.length; i++) {

			if (outputs[i] != null) {
				outputs[i].setFlow(proportions[i] * outFlow);
			}

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

		if (proportions.length != outputs.length) {
			return;
		}

		double sum = 0.0;

		for (double p : proportions) {
			if (p < 0.0) {
				return;
			}
			sum += p;
		}

		if (Math.abs(sum - 1.0) < 0.000001) {
			this.proportions = proportions.clone();
		}
	}

	public double[] getProportions() {

		return proportions;

	}
	
}
