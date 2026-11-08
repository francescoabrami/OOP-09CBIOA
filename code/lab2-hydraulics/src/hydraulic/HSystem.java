package hydraulic;

import java.util.Arrays;

/**
 * Main class that acts as a container of the elements for
 * the simulation of a hydraulics system
 * 
 */
public class HSystem {

	public static final int MAX_ELEMENTS = 100;
	public static final int MAX_OUTPUTS = 2;

	Element[] element = new Element[MAX_ELEMENTS];
	int elmentsNumber = 0;

// R1
	/**
	 * Adds a new element to the system
	 * 
	 * @param elem the new element to be added to the system
	*/

	public void addElement(Element elem){

		if (elmentsNumber == 100) {

			return;

		}

		element[elmentsNumber] = elem;
		elmentsNumber++;

	}

	/**
	 * returns the number of elements currently present in the system
	 * 
	 * @return count of elements
	*/

	public int size() {
        
		return elmentsNumber;

    }

	/**
	 * returns the element added so far to the system
	 * 
	 * @return an array of elements whose length is equal to the number of added elements
	*/

	public Element[] getElements(){

		return Arrays.copyOf(element, elmentsNumber);

	}

// R2
	/**
	 * Starts the simulation of the system.
	 * The notifications about the simulations are sent
	 * to an observer object
	 * Before starting simulation, the parameters of the
	 * elements in the system must be defined
	 * 
	 * @param observer the observer receiving notifications
	 */
	public void simulate(SimulationObserver observer){
		
		Element root = element[0];

		simulateR(root, observer, 0.0);

	}

	public void simulateR(Element root, SimulationObserver observer, double portata) {

		if (root instanceof Source) {

			Element e = (Source)root;

			observer.notifyFlow("Source", e.getName(), SimulationObserver.NO_FLOW, e.getOutFlow());
			simulateR(root.getOutput(), observer, e.getOutFlow());

			return;

		}

		else if (root instanceof Sink) {

			Sink s = (Sink)root;

			s.setFlow(portata);
			observer.notifyFlow("Sink", s.getName(), s.getInFlow(), SimulationObserver.NO_FLOW);
			simulateR(root.getOutput(), observer, 0.0);

			return;
		}

		else if (root instanceof Tap) { 

			Tap t = (Tap)root;

			t.setFlow(portata);
			observer.notifyFlow("Tap", t.getName(), t.getInFlow(), t.getOutFlow());
			simulateR(root.getOutput(), observer, t.getOutFlow());

			return;

		}

		else if (root instanceof Multisplit) {

			Multisplit m = (Multisplit) root;

			m.setFlow(portata);

			double[] proportions = m.getProportions();
			Element[] outputs = m.getOutputs();

			double[] flows = new double[proportions.length];

			for (int i = 0; i < proportions.length; i++) {

				flows[i] = portata * proportions[i];

			}

			observer.notifyFlow("Multisplit", m.getName(), portata, flows);

			for (int i = 0; i < outputs.length; i++) {

				if (outputs[i] != null) {

					outputs[i].setFlow(flows[i]);

					simulateR(outputs[i], observer, flows[i]);

				}

			}

		}
		
		else if (root instanceof Split) {

			Split s = (Split)root;
			Element[] outputs = s.getOutputs();

			s.setFlow(portata);

			for(int i = 0; i < outputs.length; i++) {

				outputs[i].setFlow(portata / 2);

			}

			double[] flows = new double[outputs.length];

			for(int i = 0; i < outputs.length; i++) {

				flows[i]= outputs[i].getOutFlow();

			}

			observer.notifyFlow("Split", s.getName(), s.getInFlow(), flows);

			if (outputs != null) {

				for (int i = 0; i < outputs.length; i++) {

					if (outputs[i] != null) { 
					
						simulateR(outputs[i], observer, s.getOutFlow() / 2);

					}	

				}

			}

		} 
		
		else {

			return;

		}

		return;

	}


// R4
	/**
	 * Deletes a previously added element 
	 * with the given name from the system
	 */
	public boolean deleteElement(String name) {
		
		for (int i = 0; i < element.length; i++) {

			if (element[i] != null) {

				if (name.equals(element[i].getName())) {

					if (element[i] instanceof Source) { 

						System.out.println("It is not possible to delete a Source!\n\n");

						return false;

					}

					else if (element[i] instanceof Sink) {

						Element s = element[i];
						Element previousElement = s.getInput();

						if (previousElement instanceof Split || previousElement instanceof Multisplit) {

							Element[] outputs = previousElement.getOutputs();

							for (int j = 0; j < outputs.length; j++) {

								if(outputs[j] == s) {

									outputs[j] = null;
									element[i] = null;
									elmentsNumber--;

									return true;

								}

							}

						} 

						else {

							previousElement.setOutput(null);

							element[i] = null;
							elmentsNumber--;

							return true;
	
						}

					}

					else if (element[i] instanceof Tap) {

						Element t = element[i];

						Element output = t.getOutput();
						Element previousElement = t.getInput();

						previousElement.setOutput(output);

						if (output != null) {

							output.setInput(previousElement);

						}

						element[i] = null;
						elmentsNumber--;

						return true;
					}
					
					else if (element[i] instanceof Multisplit) {

						Element m = element[i];
						Element[] outs = m.getOutputs();
						int numSplit = outs.length;
	
						if (numSplit > 1){

							System.out.println("It is not possible to delete this Multisplit!\n\n");
							return false;

						}
	
						Element previousElement = m.getInput();
						previousElement.setOutput(outs[0]);
	
						Element uscita = outs[0];
						uscita.setInput(previousElement);
	
						return true;

					}

					else if (element[i] instanceof Split) {

						Element s = element[i];
						Element[] outputs = s.getOutputs();
						int splitNumber = s.countsOutputs(); 
	
						if (splitNumber > 1) {

							System.out.println("It is not possible to delete this Split!\n\n");
							return false;

						}
	
						Element output = outputs[0];
						Element previousElement = s.getInput();
	
						previousElement.setOutput(outputs[0]);
	
						if ( output != null) {

							output.setInput(previousElement);

						}
						
						element[i] = null;
						elmentsNumber--;
	
						return true;

					}

				}

			}

		}

		return false;

	}

// R5
	/**
	 * Starts the simulation of the system; if {@code enableMaxFlowCheck} is {@code true},
	 * checks also the elements maximum flows against the input flow.
	 * If {@code enableMaxFlowCheck} is {@code false}  a normal simulation as
	 * the method {@link #simulate(SimulationObserver)} is performed.
	 * Before performing a checked simulation, the max flows of the elements in the
	 * system must be defined.
	 */


	public void simulate(SimulationObserver observer, boolean enableMaxFlowCheck) {

		Element root = element[0];

		if (enableMaxFlowCheck) {

			simulateR(observer, enableMaxFlowCheck, root);

		} else { 

			simulate(observer);

		}

	}	

	public void simulateR(SimulationObserver observer, boolean enableMaxFlowCheck, Element root) {

		if (root == null) {
			return;
		}

		// SOURCE
		if (root instanceof Source) {

			Source e = (Source) root;

			observer.notifyFlow("Source", e.getName(),
					SimulationObserver.NO_FLOW, e.getOutFlow());

			simulateR(observer, enableMaxFlowCheck, e.getOutput());

		}

		// SINK
		else if (root instanceof Sink) {

			Sink e = (Sink) root;

			double inFlow = e.getInFlow();
			double maxFlow = e.getMaxFlow();

			observer.notifyFlow("Sink", e.getName(),
					inFlow, SimulationObserver.NO_FLOW);

			if (enableMaxFlowCheck && inFlow > maxFlow) {

				observer.notifyFlowError("Sink", e.getName(),
						inFlow, maxFlow);

			}

		}

		// TAP
		else if (root instanceof Tap) {

			Tap e = (Tap) root;

			double inFlow = e.getInFlow();
			double maxFlow = e.getMaxFlow();

			observer.notifyFlow("Tap", e.getName(),
					inFlow, e.getOutFlow());

			if (enableMaxFlowCheck && inFlow > maxFlow) {

				observer.notifyFlowError("Tap", e.getName(),
						inFlow, maxFlow);

			}

			simulateR(observer, enableMaxFlowCheck, e.getOutput());

		}

		// MULTISPLIT
		else if (root instanceof Multisplit) {

			Multisplit e = (Multisplit) root;

			double inFlow = e.getInFlow();
			double maxFlow = e.getMaxFlow();

			double[] proportions = e.getProportions();
			double[] flows = new double[proportions.length];

			for (int i = 0; i < proportions.length; i++) {

				flows[i] = inFlow * proportions[i];

			}

			observer.notifyFlow("Multisplit", e.getName(),
					inFlow, flows);

			if (enableMaxFlowCheck && inFlow > maxFlow) {

				observer.notifyFlowError("Multisplit", e.getName(),
						inFlow, maxFlow);

			}

			Element[] outs = e.getOutputs();

			for (int i = 0; i < outs.length; i++) {

				if (outs[i] != null) {

					outs[i].setFlow(flows[i]);

					simulateR(observer, enableMaxFlowCheck, outs[i]);

				}

			}

		}

		// SPLIT
		else if (root instanceof Split) {

			Split e = (Split) root;

			double inFlow = e.getInFlow();
			double maxFlow = e.getMaxFlow();

			Element[] outs = e.getOutputs();

			double[] flows = new double[outs.length];

			for (int i = 0; i < outs.length; i++) {

				flows[i] = inFlow / 2;

			}

			observer.notifyFlow("Split", e.getName(), inFlow, flows);

			if (enableMaxFlowCheck && inFlow > maxFlow) {

				observer.notifyFlowError("Split", e.getName(),
						inFlow, maxFlow);

			}

			for (int i = 0; i < outs.length; i++) {

				if (outs[i] != null) {

					outs[i].setFlow(flows[i]);

					simulateR(observer, enableMaxFlowCheck, outs[i]);

				}

			}

		}

	}

}
