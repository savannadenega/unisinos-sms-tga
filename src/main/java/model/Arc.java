package model;

public class Arc {
	private int weight;
	private NetworkNode origin, destination;

	public Arc() {

	}

	public Arc(int weight, NetworkNode origin, NetworkNode destination) {		
		this. weight = weight;
		this.origin = origin;
		this.destination = destination;
	}

	public Arc(NetworkNode origin, NetworkNode destination) {
		this(1, origin, destination);
	}

	/** 
	 * TODO: Define the logic so an arc knows wether it can or can't fire
	*/
	public boolean canFire() {
		return (this.getOrigin().getTokenCount() >= this.getWeight()) && this.getDestination().canFire();
	}

	/**
	 * TODO: Implement this so the arc basically moves the tokens (weight) from it's origin to it's destination
	 */
	public void fire() {
		
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getWeight() {
		return this.weight;
	}

	public void setOrigin(NetworkNode origin) {
		this.origin = origin;
	}

	public NetworkNode getOrigin() {
		return this.origin;
	}

	public void setDestination(NetworkNode destination) {
		this.destination = destination;
	}

	public NetworkNode getDestination() {
		return this.destination;
	}
}