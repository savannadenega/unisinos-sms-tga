package model;

import java.util.List;
import java.util.ArrayList;

public class Transition implements NetworkNode {
	private String name;
	private int tokenCount;
	private List<Arc> inputs = new ArrayList<Arc>();
	private List<Arc> outputs = new ArrayList<Arc>();

	public Transition(String name) {
		this.name = name;
	}

	public boolean canFire() {
		int
			minTokenCount = 0,
			inputTokenCount = 0;

		for (Arc arc : this.getOutputs()){
			minTokenCount += arc.getWeight();
		}

		for (Arc arc : this.getInputs()){
			inputTokenCount += (arc.canFire()) ? arc.getWeight() : 0; 
		}

		return inputTokenCount >= minTokenCount;
	}

	/**
	 * TODO: Define if the remaining token count on a transition (if it exists) is based on max tokens sent or if goes straight to zero
	 */
	public void fire() {
		int maxTokenCountSent = 0;

		for(Arc arc : this.getInputs()){
			arc.fire();
		}

		for(Arc arc : this.getOutputs()){
			maxTokenCountSent = (arc.getWeight() > maxTokenCountSent) ? arc.getWeight() : maxTokenCountSent;
			arc.fire();
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

	public List<Arc> getInputs() {
		return this.inputs;
	}

	public List<Arc> getOutputs() {
		return this.outputs;
	}

	public void addInput(Arc arc){
		this.inputs.add(arc);
	}

	public void addOutput(Arc arc){
		this.outputs.add(arc);
	}

}