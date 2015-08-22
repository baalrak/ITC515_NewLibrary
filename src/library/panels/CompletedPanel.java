package library.panels;

import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.Font;

public class CompletedPanel extends ABorrowPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public CompletedPanel() {
		setLayout(null);
		setBorder(new TitledBorder(null, "Completed", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setBounds(12, 23, 460, 640);
		//setBounds(12, 23, 614, 451);
		
		JLabel lblCancelled = new JLabel("Borrowing Completed");
		lblCancelled.setHorizontalAlignment(SwingConstants.CENTER);
		lblCancelled.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblCancelled.setBounds(12, 181, 436, 78);
		add(lblCancelled);		
	}


}
