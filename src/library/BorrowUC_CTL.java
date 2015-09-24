package library;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.IBorrowUIListener;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.ICardReaderListener;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;
import library.interfaces.hardware.IScannerListener;

public class BorrowUC_CTL implements ICardReaderListener, 
									 IScannerListener, 
									 IBorrowUIListener {
	
	private ICardReader reader;
	private IScanner scanner; 
	private IPrinter printer; 
	private IDisplay display;
	//private String state;
	private int scanCount = 0;
	private IBorrowUI ui;
	private EBorrowState state; 
	private IBookDAO bookDAO;
	private IMemberDAO memberDAO;
	private ILoanDAO loanDAO;
	
	private List<IBook> bookList;
	private List<ILoan> loanList;
	private IMember borrower;
	
	private JPanel previous;


	public BorrowUC_CTL(ICardReader reader, IScanner scanner, 
			IPrinter printer, IDisplay display,
			IBookDAO bookDAO, ILoanDAO loanDAO, IMemberDAO memberDAO ) {

		this.display = display;
		this.ui = new BorrowUC_UI(this);
		state = EBorrowState.CREATED;
	}
	
	public void initialise() {
		previous = display.getDisplay();
		display.setDisplay((JPanel) ui, "Borrow UI");		
	}
	
	public void close() {
		display.setDisplay(previous, "Main Menu");
	}

	@Override
	public void cardSwiped(int memberID) {
	  System.out.println("cardSwiped: " + memberID);
	    if(!state.equals(EBorrowState.INITIALIZED))
	    {
	      throw new RuntimeException("There was an error with the card swiped"
	                                   + " in it's cur state: " + state);
	    }
	    borrower = memberDAO.getMemberByID(memberID);
	    if(borrower == null)
	    {
	       ui.displayErrorMessage("Member " + memberID + "cannot be found");
	       return;
	    }
	    boolean isOverdue = borrower.hasOverDueLoans();
	    boolean isAtLoanLimit = borrower.hasReachedLoanLimit();
	    boolean isAtFineLimit = borrower.hasReachedFineLimit();
	    if (isOverdue || isAtLoanLimit || isAtFineLimit)
	    {
	      setState(EBorrowState.BORROWING_RESTRICTED);
	    }
	    boolean hasFines = borrower.hasFinesPayable();
	    setState(EBorrowState.SCANNING_BOOKS);
	    int borrowerID = borrower.getID();
	    String borrowerName = (borrower.getFirstName() + " " + borrower.getLastName());
	    String borrowerContact = borrower.getContactPhone();
	    ui.displayMemberDetails(borrowerID, borrowerName, borrowerContact);
	    if(hasFines)
	    {
	      ui.displayOutstandingFineMessage(borrower.getFineAmount ());
	    }
	    if(isOverdue)
	    {
	      ui.displayOverDueMessage();
	    }
	    if(isAtLoanLimit)
	    {
	      ui.displayAtLoanLimitMessage();
	    }
	    if(isAtFineLimit)
	    {
	      System.out.println("State: " + state);
	      ui.displayOverFineLimitMessage(borrower.getFineAmount() - 
	                                     borrower.FINE_LIMIT);
	    }
	    String loanString = buildLoanListDisplay(borrower.getLoans());
	    ui.displayExistingLoan(loanString);
	    }
	    
	    
	
	
	@Override
	public void bookScanned(int barcode) {
		throw new RuntimeException("Not implemented yet");
	}

	
	private void setState(EBorrowState state) {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public void cancelled() {
		close();
	}
	
	@Override
	public void scansCompleted() {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public void loansConfirmed() {
		throw new RuntimeException("Not implemented yet");
	}

	@Override
	public void loansRejected() {
		throw new RuntimeException("Not implemented yet");
	}

	private String buildLoanListDisplay(List<ILoan> loans) {
		StringBuilder bld = new StringBuilder();
		for (ILoan ln : loans) {
			if (bld.length() > 0) bld.append("\n\n");
			bld.append(ln.toString());
		}
		return bld.toString();		
	}

}
