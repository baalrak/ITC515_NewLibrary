package library;

import javax.swing.JPanel;

import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.IBorrowUIListener;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
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
	private int MAX_COUNT = 3;
	private IBorrowUI ui;
	private EBorrowState state; 
	private IBookDAO bookDAO;
	private IMemberDAO memberDAO;
	private ILoanDAO loanDAO;

	public BorrowUC_CTL(ICardReader reader, IScanner scanner, 
			IPrinter printer, IDisplay display,
			IBookDAO bookDAO, ILoanDAO loanDAO, IMemberDAO memberDAO ) {

		this.bookDAO = bookDAO;
		this.memberDAO= memberDAO;
		this.loanDAO = loanDAO;
		
		this.ui = new BorrowUC_UI(this);
		this.reader = reader;
		reader.addListener(this);
		this.scanner = scanner;
		scanner.addListener(this);
		this.printer = printer;
		this.display = display;
		this.display.setDisplay((JPanel) ui);
		
		setState(EBorrowState.INITIALIZED);
	}

	@Override
	public void receiveCardData(int memberID) {
		System.out.println("receiveCardData: got " + memberID);
		if (!state.equals(EBorrowState.INITIALIZED)) {
			throw new RuntimeException("BorrowCTL.receiveCardData: illegal state : " + state.toString());
		}
		IMember member = memberDAO.getMemberByID(memberID);
		if (member == null) {
			throw new RuntimeException(String.format("BorrowCTL : cardScanned : memberID not found"));
		}
		boolean overdue = member.hasOverDueLoans();
		boolean atLoanLimit = member.hasReachedLoanLimit();
		boolean overFineLimit = member.hasReachedFineLimit();
		boolean borrowing_restricted = (overdue || atLoanLimit || overFineLimit);
		
		if (borrowing_restricted) {
			setState(EBorrowState.BORROWING_RESTRICTED);
			if (overdue) {
				ui.displayOverDueMessage();
			}
			if (atLoanLimit) {
				ui.displayAtLoanLimitMessage();
			}
			if (overFineLimit) {
				float amountOwing = member.getFineAmount();
				ui.displayOutstandingFineMessage(amountOwing);
			}
		}
		else {
			setState(EBorrowState.SCANNING_BOOKS);
			//initialize scanCount with number of existing loans
			//so that member doesn't borrow more than they should
			scanCount = member.getLoans().size();
		}

		//display member details
		int mID = member.getID();
		String mName = member.getFirstName() + " " + member.getLastName();
		String mContact = member.getContactPhone();
		ui.displayMemberDetails(mID, mName, mContact);	
		
		//display existing loans
		for (ILoan ln : member.getLoans()) {
			ui.displayExistingLoan(ln.toString());
		}
		
	}
	
	
	@Override
	public void receiveScan(int barcode) {
		scanCount++;
		System.out.println("receiveScan: got " + barcode + " Scan count = " + scanCount);
		if (scanCount >= MAX_COUNT) {
			setState(EBorrowState.CONFIRMING_LOANS);
		}
	}

	private void setState(EBorrowState state) {
		System.out.println("Setting state: " + state);
		
		switch (state) {
		case INITIALIZED:
			reader.setEnabled(true);
			scanner.setEnabled(false);
			break;
		case SCANNING_BOOKS:
			reader.setEnabled(false);
			scanner.setEnabled(true);
			break;
		case CONFIRMING_LOANS:
			reader.setEnabled(false);
			scanner.setEnabled(false);
			break;
		case COMPLETED:
			reader.setEnabled(false);
			scanner.setEnabled(false);
			break;
		case CANCELLED:
			reader.setEnabled(false);
			scanner.setEnabled(false);
			break;
		case BORROWING_RESTRICTED:
			reader.setEnabled(false);
			scanner.setEnabled(false);
			break;
		default:
			throw new RuntimeException("Unknown state");
		}
		this.state = state;
		ui.setState(state);
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
		printer.print("Loans Completed");
		setState(EBorrowState.COMPLETED);				
	}

	@Override
	public void loansRejected() {
		System.out.println("Loans Rejected");
		setState(EBorrowState.SCANNING_BOOKS);		
	}
	
}
