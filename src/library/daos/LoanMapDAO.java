package library.daos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class LoanMapDAO implements ILoanDAO {

	private int nextID;
	private Map<Integer, ILoan> loanMap;
	private ILoanHelper helper;
	

	public LoanMapDAO(ILoanHelper helper) {
		if (helper == null ) {
			throw new IllegalArgumentException(
				String.format("LoanMapDAO : constructor : helper cannot be null."));
		}
		nextID = 1;
		this.helper = helper; 
		loanMap = new HashMap<Integer, ILoan>();
	}

	public LoanMapDAO(ILoanHelper helper, Map<Integer,ILoan> loanMap) {
		this(helper);
		if (loanMap == null ) {
			throw new IllegalArgumentException(
				String.format("LoanMapDAO : constructor : loanMap cannot be null."));
		}
		this.loanMap = loanMap;
	}


	@Override
	public ILoan getLoanByID(int id) {
		if (loanMap.containsKey(Integer.valueOf(id))) {
			return loanMap.get(Integer.valueOf(id));
		}
		return null;
	}

	@Override
	public ILoan getLoanByBook(IBook book) {
		if (book == null ) {
			throw new IllegalArgumentException(
				String.format("LoanMapDAO : getLoanByBook : book cannot be null."));
		}
		for (ILoan loan : loanMap.values()) {
			IBook tempBook = loan.getBook();
			if (book.equals(tempBook)) {
				return loan;
			}
		}
		return null;
	}

	@Override
	public List<ILoan> listLoans() {
		List<ILoan> list = new ArrayList<ILoan>(loanMap.values());
		return Collections.unmodifiableList(list);
	}

	@Override
	public List<ILoan> findLoansByBorrower(IMember borrower) {
		if (borrower == null ) {
			throw new IllegalArgumentException(
				String.format("LoanMapDAO : findLoansByBorrower : borrower cannot be null."));
		}
		List<ILoan> list = new ArrayList<ILoan>();
		for (ILoan loan : loanMap.values()) {
			if (borrower.equals(loan.getBorrower())) {
				list.add(loan);
			}
		}
		return Collections.unmodifiableList(list);
	}

	@Override
	public List<ILoan> findLoansByBookTitle(String title) {
		if (title == null || title.isEmpty()) {
			throw new IllegalArgumentException(
				String.format("LoanMapDAO : findLoansByBookTitle : title cannot be null or blank."));
		}
		List<ILoan> list = new ArrayList<ILoan>();
		for (ILoan loan : loanMap.values()) {
			String tempTitle = loan.getBook().getTitle();
			if (title.equals(tempTitle)) {
				list.add(loan);
			}
		}
		return Collections.unmodifiableList(list);
	}

	@Override
	public void updateOverDueStatus(Date currentDate) {
		for (ILoan loan : loanMap.values()) {
			loan.checkOverDue(currentDate);
		}
	}

	@Override
	public List<ILoan> findOverDueLoans() {
		List<ILoan> list = new ArrayList<ILoan>();
		for (ILoan loan : loanMap.values()) {
			if (loan.isOverDue()) {
				list.add(loan);
			}
		}
		return Collections.unmodifiableList(list);
	}

	private int getNextId() {
		return nextID++;
	}

	@Override
	public void addLoan(IMember borrower, IBook book, Date borrowDate, Date dueDate) {
		int id = getNextId();
		ILoan loan = helper.makeLoan(book, borrower, borrowDate, dueDate, id);
		borrower.addLoan(loan);
		book.borrow(loan);
		loan.commit();
		loanMap.put(id, loan);		
	}

}
