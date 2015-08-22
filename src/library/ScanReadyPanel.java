package library;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScanReadyPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ScanReadyPanel(IBorrowUIListener listener) {
		setLayout(null);
		setBorder(new TitledBorder(null, "Scanning", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setBounds(12, 23, 614, 451);
		
		JLabel lblScanBook = new JLabel("Scan Book Please");
		lblScanBook.setHorizontalAlignment(SwingConstants.CENTER);
		lblScanBook.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblScanBook.setBounds(74, 181, 436, 78);
		add(lblScanBook);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				listener.cancelled();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancel.setBounds(350, 318, 127, 35);
		add(btnCancel);
		
		JButton button = new JButton("Completed");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listener.scansCompleted();
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 14));
		button.setBounds(141, 318, 127, 35);
		add(button);
	}

}
