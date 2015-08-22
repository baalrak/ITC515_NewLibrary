package library.panels;

import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.Font;

public class CancelledPanel extends ABorrowPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public CancelledPanel() {
		setLayout(null);
		setBorder(new TitledBorder(null, "Cancelled", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setBounds(12, 23, 460, 640);
		
		JLabel lblCancelled = new JLabel("Cancelled");
		lblCancelled.setHorizontalAlignment(SwingConstants.CENTER);
		lblCancelled.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblCancelled.setBounds(12, 187, 436, 78);
		add(lblCancelled);		
	}


}
