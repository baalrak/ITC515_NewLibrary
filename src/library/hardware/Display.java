package library.hardware;

import javax.swing.JFrame;
import javax.swing.JPanel;

import library.interfaces.hardware.IDisplay;

public class Display extends JFrame implements IDisplay {

	private static final long serialVersionUID = 1L;
	
	public Display() {
		setTitle("Display");
		setBounds(500, 50, 470, 680);
        //setBounds(500, 100, 750, 615);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}


	@Override
	public void setDisplay(JPanel panel) {
        getContentPane().add(panel, null);		
	}
	
}
