package library.panels;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import library.interfaces.IBorrowUIListener;
import library.interfaces.entities.ILoan;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import java.awt.Color;

import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;

public class SwipeCardPanel extends JPanel implements IBorrowUI {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public SwipeCardPanel(IBorrowUIListener listener) {
		setLayout(null);
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Borrow Book - Initialized", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setBounds(12, 23, 460, 640);
		//setBounds(12, 23, 614, 451);
		
		JLabel lblSwipeCard = new JLabel("Swipe Card Please");
		lblSwipeCard.setHorizontalAlignment(SwingConstants.CENTER);
		lblSwipeCard.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblSwipeCard.setBounds(12, 181, 436, 78);
		add(lblSwipeCard);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				listener.cancelled();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancel.setBounds(170, 320, 127, 35);
		add(btnCancel);
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
