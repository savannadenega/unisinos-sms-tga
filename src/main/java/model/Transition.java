package model;

import java.util.List;

public class Transition implements NetworkNode {
	private int tokenCount;
	private List<Arc> inputs, outputs;

	public Transition() {

	}

	public Transition(List<Arc> inputs, List<Arc> outputs) {
		this.inputs = inputs;
		this.outputs = outputs;
	}

	public boolean canFire() {
		int
			minTokenCount = 0,
			inputTokenCount = 0;

		for (int index = 0; index < this.getOutputs().size(); index++)
			minTokenCount += this.getOutputs().get(index).getWeight();

		for (int index = 0; index < this.getInputs().size(); index++)
			if (this.getInputs().get(index).canFire())
				inputTokenCount += this.getInputs().get(index).getWeight();

		return inputTokenCount >= minTokenCount;
	}

	/**
	 * TODO: Define if the remaining token count on a transition (if it exists) is based on max tokens sent or if goes straight to zero
	 */
	public void fire() {
		int maxTokenCountSent = 0;

		for (int index = 0; index < this.getOutputs().size(); index++) {
			if (this.getOutputs().get(index).getWeight() > maxTokenCountSent)
				maxTokenCountSent = this.getOutputs().get(index).getWeight();
			
			this.getOutputs().get(index).fire();
		}

		this.removeTokens(maxTokenCountSent);
	}

	public void addTokens(int tokenCount) {
		this.tokenCount += tokenCount;
	}

	public void removeTokens(int tokenCount) {
		this.tokenCount -= tokenCount;
	}

	public void setTokenCount(int tokenCount) {
		this.tokenCount = tokenCount;
	}

	public int getTokenCount() {
		return this.tokenCount;
	}

	public void setInputs(List<Arc> inputs) {
		this.inputs = inputs;
	}

	public List<Arc> getInputs() {
		return this.inputs;
	}

	public void setOutputs(List<Arc> outputs) {
		this.outputs = outputs;
	}

	public List<Arc> getOutputs() {
		return this.outputs;
	}

}