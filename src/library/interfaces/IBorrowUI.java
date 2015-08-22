package library.interfaces;

import java.util.List;
import library.interfaces.entities.ILoan;

public interface IBorrowUI {

	public void addListener(IBorrowUIListener listener);
	
	public void setState(EBorrowState state);
	
	public void displayMemberDetails(int memberID, String memberName, String memberPhone);

	public void displayExistingLoan(String loanDetails);

	public void displayOverDueMessage();
	
	public void displayAtLoanLimitMessage();
	
	public void displayOutstandingFineMessage(float amountOwing);

	public void listPendingLoans(List<ILoan> loans);
	
}
