package library;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.Font;

public class CompletedPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public CompletedPanel(IBorrowUIListener listener) {
		setLayout(null);
		setBorder(new TitledBorder(null, "Completed", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setBounds(12, 23, 614, 451);
		
		JLabel lblCancelled = new JLabel("Completed");
		lblCancelled.setHorizontalAlignment(SwingConstants.CENTER);
		lblCancelled.setFont(new Font("Tahoma", Font.PLAIN, 42));
		lblCancelled.setBounds(74, 181, 436, 78);
		add(lblCancelled);		
	}

}
