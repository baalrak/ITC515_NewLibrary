package library;

public interface IBorrowUI {

	public void addListener(IBorrowUIListener listener);
	
	public void setState(EBorrowState state);
	
}
