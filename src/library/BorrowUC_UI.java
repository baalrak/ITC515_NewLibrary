package library;

import java.awt.CardLayout;
import java.util.Map;
import java.util.HashMap;

import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.IBorrowUIListener;
import library.panels.ABorrowPanel;
import library.panels.RestrictedPanel;
import library.panels.CancelledPanel;
import library.panels.CompletedPanel;
import library.panels.SwipeCardPanel;
import library.panels.ConfirmLoanPanel;
import library.panels.ScanningPanel;

public class BorrowUC_UI extends ABorrowPanel {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private IBorrowUIListener listener;
	private EBorrowState state;
	private Map<EBorrowState,IBorrowUI> panels;

	
	public BorrowUC_UI(IBorrowUIListener listener) {
		this.listener = listener;
		this.panels = new HashMap<EBorrowState,IBorrowUI>();		
		this.setLayout(new CardLayout());

		addPanel(new SwipeCardPanel(listener),   EBorrowState.INITIALIZED);
		addPanel(new ScanningPanel(listener),    EBorrowState.SCANNING_BOOKS);
		addPanel(new RestrictedPanel(listener),  EBorrowState.BORROWING_RESTRICTED);
		addPanel(new ConfirmLoanPanel(listener), EBorrowState.CONFIRMING_LOANS);
		addPanel(new CancelledPanel(),           EBorrowState.CANCELLED);
		addPanel(new CompletedPanel(),           EBorrowState.COMPLETED);
	}
	
	private void addPanel(ABorrowPanel panel, EBorrowState state) {
        this.panels.put(state, panel);
        this.add(panel, state.toString());
 	}


	@Override
	public void setState(EBorrowState state) {
		CardLayout cl = (CardLayout) (this.getLayout());

		switch (state) {
		case INITIALIZED:
			cl.show(this, state.toString());
			break;
		case SCANNING_BOOKS:
			cl.show(this, state.toString());
			break;
		case CONFIRMING_LOANS:
			cl.show(this, state.toString());
			break;
		case BORROWING_RESTRICTED:
			cl.show(this, state.toString());
			break;
		case COMPLETED:
			cl.show(this, state.toString());
			break;
		case CANCELLED:
			cl.show(this, state.toString());
			break;
		default:
			throw new RuntimeException("Unknown state");
		}
		this.state = state;
	}


	@Override
	public void displayMemberDetails(int memberID, String memberName, String memberPhone) {
		IBorrowUI ui = panels.get(state);
		ui.displayMemberDetails( memberID,  memberName, memberPhone);		
	}


	@Override
	public void displayOverDueMessage() {
		IBorrowUI ui = panels.get(state);
		ui.displayOverDueMessage();		
	}


	@Override
	public void displayAtLoanLimitMessage() {
		IBorrowUI ui = panels.get(state);
		ui.displayAtLoanLimitMessage();		
	}


	@Override
	public void displayOutstandingFineMessage(float amountOwing) {
		IBorrowUI ui = panels.get(state);
		ui.displayOutstandingFineMessage(amountOwing);		
	}

	
	@Override
	public void displayExistingLoan(String loanDetails) {
		IBorrowUI ui = panels.get(state);
		ui.displayExistingLoan(loanDetails);		
	}

	
	@Override
	public void displayScannedBookDetails(String bookDetails) {
		IBorrowUI ui = panels.get(state);
		ui.displayScannedBookDetails(bookDetails);		
	}

	
	@Override
	public void displayPendingLoan(String loanDetails) {
		IBorrowUI ui = panels.get(state);
		ui.displayPendingLoan(loanDetails);		
	}

	
	@Override
	public void displayConfirmingLoan(String loanDetails) {
		IBorrowUI ui = panels.get(state);
		ui.displayConfirmingLoan(loanDetails);		
	}

	
	@Override
	public void displayErrorMessage(String errorMesg) {
		IBorrowUI ui = panels.get(state);
		ui.displayErrorMessage(errorMesg);		
	}

}
