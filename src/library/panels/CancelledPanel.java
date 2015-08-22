package library.panels;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.IBorrowUIListener;
import library.interfaces.entities.ILoan;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;

public class CancelledPanel extends JPanel implements IBorrowUI {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public CancelledPanel(IBorrowUIListener listener) {
		setLayout(null);
		setBorder(new TitledBorder(null, "Cancelled", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setBounds(12, 23, 460, 640);
		//setBounds(12, 23, 614, 451);
		
		JLabel lblCancelled = new JLabel("Cancelled");
		lblCancelled.setHorizontalAlignment(SwingConstants.CENTER);
		lblCancelled.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblCancelled.setBounds(12, 187, 436, 78);
		add(lblCancelled);		
	}

	@Override
	public void addListener(IBorrowUIListener listener) {
		throw new RuntimeException("Illegal operation in current state");		
	}

	@Override
	public void setState(EBorrowState state) {
		throw new RuntimeException("Illegal operation in current state");		
	}

	@Override
	public void listPendingLoans(List<ILoan> loans) {
		throw new RuntimeException("Illegal operation in current state");		
	}

	@Override
	public void displayMemberDetails(int memberID, String memberName, String memberPhone) {
		throw new RuntimeException("Illegal operation in current state");		
	}

	@Override
	public void displayExistingLoan(String loanDetails) {
		throw new RuntimeException("Illegal operation in current state");		
	}

	@Override
	public void displayOverDueMessage() {
		throw new RuntimeException("Illegal operation in current state");		
	}

	@Override
	public void displayAtLoanLimitMessage() {
		throw new RuntimeException("Illegal operation in current state");		
	}

	@Override
	public void displayOutstandingFineMessage(float amountOwing) {
		throw new RuntimeException("Illegal operation in current state");		
	}


}
