package library;

import java.util.ArrayList;
import java.util.Iterator;
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
	private Iterator iterator;
	
	private List<IBook> bookList;
	private List<ILoan> loanList;
	private IMember borrower;
	private JPanel previous;


	public BorrowUC_CTL(ICardReader reader, IScanner scanner, 
			IPrinter printer, IDisplay display,
			IBookDAO bookDAO, ILoanDAO loanDAO, IMemberDAO memberDAO ) {

		this.display = display;
		this.ui = new BorrowUC_UI(this);
		this.reader = reader;
		reader.addListener (this);
		this.scanner = scanner;
		scanner.addListener (this);
		this.memberDAO = memberDAO;
		this.loanDAO = loanDAO;
		this.bookDAO = bookDAO;
		state = EBorrowState.CREATED;
	}
	
	public void initialise() {
		//previous = display.getDisplay();
		//display.setDisplay((JPanel) ui, "Borrow UI");
		setState(EBorrowState.INITIALIZED);
	}
	
	public void close() {
		//display.setDisplay(previous, "Main Menu");
	}

	@Override
	public void cardSwiped(int memberID) {
	    if(!state.equals(EBorrowState.INITIALIZED))
	    {
	      throw new RuntimeException("There was an error with the card swiped");
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
	    setState(EBorrowState.SCANNING_BOOKS);
	    boolean hasFines = borrower.hasFinesPayable();
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
      this.state = state;
      ui.setState(state);
      switch(state)
      {
      case INITIALIZED:
          reader.setEnabled(true);
          scanner.setEnabled(false);
          break;

      case SCANNING_BOOKS:
          reader.setEnabled(false);
          scanner.setEnabled(true);
          bookList = new ArrayList<IBook>();
          loanList = new ArrayList<ILoan>();
          scanCount = borrower.getLoans().size();
          ui.displayScannedBookDetails("");
          ui.displayPendingLoan("");
          break;

      case CONFIRMING_LOANS:
          reader.setEnabled(false);
          scanner.setEnabled(false);
          //ui.displayConfirmingLoan(buildLoanListDisplay(loanList));
          break;

      case COMPLETED:
          reader.setEnabled(false);
          scanner.setEnabled(false);
          ILoan loan;
          //for(iterator = loanList.iterator(); iterator.hasNext(); 
          //    loanDAO.commitLoan(loan))
          {
          //    loan = (ILoan) iterator.next();
          }

          //printer.print(buildLoanListDisplay(loanList));
          close();
          break;

      case CANCELLED:
          reader.setEnabled(false);
          scanner.setEnabled(false);
          close();
          break;

      case BORROWING_RESTRICTED:
          reader.setEnabled(false);
          scanner.setEnabled(false);
          ui.displayErrorMessage("Member ID: " + borrower.getID () + " Name: " 
                                 + borrower.getFirstName() + " " 
                                 + borrower.getLastName () + "is restricted "
                                 + "from borrowing");
          break;

      default:
          throw new RuntimeException("Invalid State");
      }
  }

	@Override
	public void cancelled() {
		setState(EBorrowState.CANCELLED);
	}
	
	@Override
	public void scansCompleted() {
		setState(EBorrowState.CONFIRMING_LOANS);
	}

	@Override
	public void loansConfirmed() {
		setState(EBorrowState.COMPLETED);
	}

	@Override
	public void loansRejected() {
	    setState(EBorrowState.SCANNING_BOOKS);
	}

	private String buildLoanListDisplay(List<ILoan> loans) {
		StringBuilder bld = new StringBuilder();
		for (ILoan ln : loans) {
			if (bld.length() > 0) bld.append("\n\n");
			bld.append(ln.toString());
		}
		return bld.toString();		
	}
	
	public EBorrowState getState()
	{
	  return state;
	}

}
