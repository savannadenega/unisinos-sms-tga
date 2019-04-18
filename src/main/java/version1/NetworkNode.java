package versao1;

public interface NetworkNode {
	
	public boolean canFire();
	
	public void addTokens(int tokenCount);

	public void removeTokens(int tokenCount);

	public int getTokenCount();

}
