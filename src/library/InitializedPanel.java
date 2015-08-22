package library;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InitializedPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public InitializedPanel(IBorrowUIListener listener) {
		setLayout(null);
		setBorder(new TitledBorder(null, "Prompt for Card Swipe", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setBounds(12, 23, 614, 451);
		
		JLabel lblSwipeCard = new JLabel("Swipe Card Please");
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
		btnCancel.setBounds(248, 318, 127, 35);
		add(btnCancel);
	}

}
