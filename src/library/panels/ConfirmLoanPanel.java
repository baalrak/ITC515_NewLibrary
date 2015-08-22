package library.panels;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import library.entities.Loan;
import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.IBorrowUIListener;
import library.interfaces.entities.ILoan;
import java.awt.Font;

public class ConfirmLoanPanel extends JPanel implements IBorrowUI {

	private static final long serialVersionUID = 1L;
	private JTextArea loanListTA;

	/**
	 * Create the panel.
	 */
	public ConfirmLoanPanel(IBorrowUIListener listener) {
		setLayout(null);
		setBorder(new TitledBorder(null, "Confirm Loans", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setBounds(12, 23, 460, 640);
		//setBounds(12, 23, 614, 451);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Current Loan List", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 24, 415, 200);
		this.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 20, 395, 170);
		panel.add(scrollPane);
		
		loanListTA = new JTextArea();
		loanListTA.setEditable(false);
		scrollPane.setViewportView(loanListTA);

		JButton btnReject = new JButton("Reject");
		btnReject.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnReject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				listener.loansRejected();
			}
		});
		btnReject.setBounds(63, 237, 115, 35);
		this.add(btnReject);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listener.loansConfirmed();
			}
		});
		btnConfirm.setBounds(275, 237, 115, 35);
		this.add(btnConfirm);
		
		JButton button = new JButton("Cancel");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				listener.cancelled();
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 14));
		button.setBounds(171, 285, 115, 35);
		add(button);
/*
		JLabel lblConfirmLoans = new JLabel("Confirm Loans Please");
		lblConfirmLoans.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfirmLoans.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblConfirmLoans.setBounds(74, 181, 436, 78);
		add(lblConfirmLoans);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				listener.cancelled();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancel.setBounds(350, 320, 127, 35);
		add(btnCancel);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listener.loansConfirmed();
			}
		});
		btnConfirm.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnConfirm.setBounds(127, 320, 127, 35);
		add(btnConfirm);
*/
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
		StringBuilder bld = new StringBuilder();
		for (ILoan loan : loans) {
			bld.append(((Loan) loan).toString() + "\n\n");
		}
		loanListTA.setText(bld.toString());
		loanListTA.setCaretPosition(0);
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