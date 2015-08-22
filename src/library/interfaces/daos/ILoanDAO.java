package library.interfaces.daos;

import java.util.Date;
import java.util.List;

import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public interface ILoanDAO {
		
	public void addLoan(IMember borrower, IBook book, Date borrowDate, Date dueDate);
	
	public ILoan getLoanByID(int id);
	
	public ILoan getLoanByBook(IBook book);
	
	public List<ILoan> listLoans();
	
	public List<ILoan> findLoansByBorrower(IMember borrower);

	public List<ILoan> findLoansByBookTitle(String title);
	
	public void updateOverDueStatus(Date currentDate);

	public List<ILoan> findOverDueLoans();

}

