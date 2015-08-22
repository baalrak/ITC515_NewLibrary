package library;

import java.awt.CardLayout;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.swing.JPanel;

import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.IBorrowUIListener;
import library.interfaces.entities.ILoan;
import library.panels.BorrowingRestrictedPanel;
import library.panels.CancelledPanel;
import library.panels.CompletedPanel;
import library.panels.SwipeCardPanel;
import library.panels.ConfirmLoanPanel;
import library.panels.ScanReadyPanel;

public class BorrowUC_UI extends JPanel implements IBorrowUI {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private IBorrowUIListener listener;
	private EBorrowState state;
	private Map<EBorrowState,IBorrowUI> panels;

	public BorrowUC_UI(IBorrowUIListener listener) {
		this.listener = listener;
		this.panels = new HashMap<EBorrowState,IBorrowUI>();		
		this.setLayout(new CardLayout());

		SwipeCardPanel swipePanel = new SwipeCardPanel(listener);
        this.panels.put(EBorrowState.INITIALIZED, swipePanel);
        this.add(swipePanel, EBorrowState.INITIALIZED.toString());
        
        ScanReadyPanel scanPanel = new ScanReadyPanel(listener);
        this.panels.put(EBorrowState.SCANNING_BOOKS, scanPanel);
        this.add(scanPanel, EBorrowState.SCANNING_BOOKS.toString());
        
        BorrowingRestrictedPanel restrictedPanel = new BorrowingRestrictedPanel(listener);
        this.panels.put(EBorrowState.BORROWING_RESTRICTED, restrictedPanel);
        this.add(restrictedPanel, EBorrowState.BORROWING_RESTRICTED.toString());
        
        ConfirmLoanPanel confirmPanel = new ConfirmLoanPanel(listener);
        this.panels.put(EBorrowState.CONFIRMING_LOANS, confirmPanel);
        this.add(confirmPanel, EBorrowState.CONFIRMING_LOANS.toString());
        
        CancelledPanel cancelPanel = new CancelledPanel(listener);
        this.panels.put(EBorrowState.CANCELLED, cancelPanel);
        this.add(cancelPanel, EBorrowState.CANCELLED.toString());
        
        CompletedPanel completedPanel = new CompletedPanel(listener);
        this.panels.put(EBorrowState.COMPLETED, completedPanel);
        this.add(completedPanel, EBorrowState.COMPLETED.toString());
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
	public void addListener(IBorrowUIListener listener) {
		this.listener = listener;		
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
	public void listPendingLoans(List<ILoan> loans) {
		IBorrowUI ui = panels.get(state);
		ui.listPendingLoans(loans);		
	}


	@Override
	public void displayExistingLoan(String loanDetails) {
		IBorrowUI ui = panels.get(state);
		ui.displayExistingLoan(loanDetails);		
	}


}
