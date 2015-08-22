package library;

public interface ICardReader {
	
	public void addListener(ICardReaderListener listener);

	public void setEnabled(boolean enabled);

}
