package library;

import javax.swing.JPanel;

public class BorrowUC_CTL implements ICardReaderListener, 
									 IScannerListener, 
									 IBorrowUIListener,
									 IBorrowCTL {
	
	private ICardReader reader;
	private IScanner scanner; 
	private IPrinter printer; 
	private IDisplay display;
	//private String state;
	private int scanCount = 0;
	private int MAX_COUNT = 3;
	private IBorrowUI ui;
	private EBorrowState state; 

	public BorrowUC_CTL(ICardReader reader, IScanner scanner, 
			IPrinter printer, IDisplay display) {

		ui = new BorrowUC_UI(this);
		this.reader = reader;
		reader.addListener(this);
		this.scanner = scanner;
		scanner.addListener(this);
		this.printer = printer;
		this.display = display;
		this.display.setDisplay((JPanel) ui);
		setState(EBorrowState.INITIALIZED);
	}

	@Override
	public void receiveCardData(int cardData) {
		System.out.println("receiveCardData: got " + cardData);
		if (!state.equals(EBorrowState.INITIALIZED)) {
			throw new RuntimeException("BorrowCTL.receiveCardData: illegal state : " + state.toString());
		}
		setState(EBorrowState.SCANREADY);
		
	}
	@Override
	public void receiveScan(int barcode) {
		scanCount++;
		System.out.println("receiveScan: got " + barcode + " Scan count = " + scanCount);
		if (scanCount >= MAX_COUNT) {
			setState(EBorrowState.SCANCOMPLETE);
		}
	}

	private void setState(EBorrowState state) {
		System.out.println("Setting state: " + state);
		
		switch (state) {
		case INITIALIZED:
			reader.setEnabled(true);
			scanner.setEnabled(false);
			break;
		case SCANREADY:
			reader.setEnabled(false);
			scanner.setEnabled(true);
			break;
		case SCANCOMPLETE:
			reader.setEnabled(false);
			scanner.setEnabled(false);
			break;
		case COMPLETED:
			reader.setEnabled(false);
			scanner.setEnabled(false);
			break;
		case CANCELLED:
			reader.setEnabled(false);
			scanner.setEnabled(false);
			break;
		default:
			throw new RuntimeException("Unknown state");
		}
		this.state = state;
		ui.setState(state);
		//this.state = state;
	}

	@Override
	public void cancelled() {
		setState(EBorrowState.CANCELLED);		
	}
	
	@Override
	public void scansCompleted() {
		setState(EBorrowState.SCANCOMPLETE);
		
	}

	@Override
	public void loansConfirmed() {
		printer.print("Loans Completed");
		setState(EBorrowState.COMPLETED);				
	}
}
