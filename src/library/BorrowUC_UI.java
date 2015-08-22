package library;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class BorrowUC_UI extends JPanel implements IBorrowUI {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private IBorrowUIListener listener;

	public BorrowUC_UI(IBorrowUIListener listener) {
		this.listener = listener;
		this.setLayout(new CardLayout());
        this.add(new InitializedPanel(listener), EBorrowState.INITIALIZED.toString());
        this.add(new ScanReadyPanel(listener), EBorrowState.SCANREADY.toString());
        this.add(new ScanCompletePanel(listener), EBorrowState.SCANCOMPLETE.toString());
        this.add(new CancelledPanel(listener), EBorrowState.CANCELLED.toString());
        this.add(new CompletedPanel(listener), EBorrowState.COMPLETED.toString());
	}


	@Override
	public void setState(EBorrowState state) {
		CardLayout cl = (CardLayout) (this.getLayout());

		switch (state) {
		case INITIALIZED:
			cl.show(this, state.toString());
			break;
		case SCANREADY:
			cl.show(this, state.toString());
			break;
		case SCANCOMPLETE:
			cl.show(this, state.toString());
			break;
		case COMPLETED:
			cl.show(this, state.toString());
			break;
		case CANCELLED:
			cl.show(this, state.toString());
			break;
		default:
			throw new RuntimeException("Unknown state");
		}
	}


	@Override
	public void addListener(IBorrowUIListener listener) {
		this.listener = listener;
		
	}


}
