package library;


public class RunBorrowUC {

	private static CardReader reader;
	private static Scanner scanner;
	private static Printer printer;
	private static Display display;
	//private static BorrowUC_CTL control;

	public static void createAndShowGUI() {
		reader = new CardReader();
		scanner = new Scanner();
		printer = new Printer();
		display = new Display();
		new BorrowUC_CTL(reader, scanner, printer, display);
		
		reader.setVisible(true);
		scanner.setVisible(true);
		printer.setVisible(true);
		display.setVisible(true);
	}

	public static void main(String[] args) {
        // start the GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
        
		

	}

}
