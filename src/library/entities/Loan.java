package library.entities;

import java.text.DateFormat;
import java.util.Date;

import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.entities.ELoanState;

public class Loan implements ILoan {

	private int id;
	private final IMember borrower;
	private final IBook book;
	private Date borrowDate;
	private Date dueDate;
	private ELoanState state;
	
	public Loan(IBook book, IMember borrower, Date borrowDate, Date returnDate) {
		if (!sane(book, borrower, borrowDate, returnDate)) {
			throw new IllegalArgumentException("Loan: constructor : bad parameters");
		}
		this.book = book;
		this.borrower = borrower;
		this.borrowDate = borrowDate;
		this.dueDate = returnDate;	
		this.state = ELoanState.PENDING;
	}
	
	private boolean sane(IBook book, IMember borrower, Date borrowDate, Date returnDate) {
		return  ( book != null && 
				  borrower != null && 
				  borrowDate != null && 
				  returnDate != null && 
				  borrowDate.compareTo(returnDate) <= 0);
	}

	@Override
	public void commit(int loanId) {
		if (!(state == ELoanState.PENDING)) {
			throw new RuntimeException(
					String.format("Loan : commit : incorrect state transition  : %s -> %s\n", 
							state, ELoanState.CURRENT));
		}
		if (loanId <= 0) {
			throw new RuntimeException(
					String.format("Loan : commit : id must be a positive integer  : %d\n", 
							loanId));
		}
		this.id = loanId;
		state = ELoanState.CURRENT;
		book.borrow(this);
		borrower.addLoan(this);
	}

	@Override
	public void complete() {
		if (!(state == ELoanState.CURRENT || state == ELoanState.OVERDUE)) {
			throw new RuntimeException(
					String.format("Loan : complete : incorrect state transition  : %s -> %s\n",
							state, ELoanState.COMPLETE));
		}
		state = ELoanState.COMPLETE;		
	}

	@Override
	public boolean isOverDue() {
		return (state == ELoanState.OVERDUE);
	}

	@Override
	public boolean checkOverDue(Date currentDate) {
		if (!(state == ELoanState.CURRENT || state == ELoanState.OVERDUE )) {
			throw new RuntimeException(
					String.format("Loan : checkOverDue : incorrect state transition  : %s -> %s\n",
							state, ELoanState.OVERDUE));
		}
		if (currentDate.compareTo(dueDate) > 0) {
			state = ELoanState.OVERDUE;
		}
		return isOverDue();
	}

	@Override
	public IMember getBorrower() {
		return borrower;
	}

	@Override
	public IBook getBook() {
		return book;
	}

	@Override
	public int getID() {
		return id;
	}
	
	public ELoanState getState() {
		return state;
	}

	@Override
	public String toString() {
		return (String.format("Loan ID:  %d\nAuthor:   %s\nTitle:    %s\nBorrower: %s %s\nBorrowed: %s\nDue Date: %s", 
				id, book.getAuthor(), book.getTitle(), borrower.getFirstName(), borrower.getLastName(),
				DateFormat.getDateInstance().format(borrowDate),
				DateFormat.getDateInstance().format(dueDate)));
	}


}
