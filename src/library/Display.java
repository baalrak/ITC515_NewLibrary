package library;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display extends JFrame implements IDisplay {

	private static final long serialVersionUID = 1L;
	
	public Display() {
		setTitle("Display");
        setBounds(500, 100, 750, 615);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}


	@Override
	public void setDisplay(JPanel panel) {
        getContentPane().add(panel, null);		
	}
	
}
