package library;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScanCompletePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ScanCompletePanel(IBorrowUIListener listener) {
		setLayout(null);
		setBorder(new TitledBorder(null, "Scan Complete", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setBounds(12, 23, 614, 451);
		
		JLabel lblSwipeCard = new JLabel("Confirm Loans Please");
		lblSwipeCard.setHorizontalAlignment(SwingConstants.CENTER);
		lblSwipeCard.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblSwipeCard.setBounds(74, 181, 436, 78);
		add(lblSwipeCard);
		
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
	}

}
