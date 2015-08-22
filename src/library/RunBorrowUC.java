package library;

import library.hardware.CardReader;
import library.hardware.Display;
import library.hardware.Printer;
import library.hardware.Scanner;

import java.util.Calendar;
import java.util.Date;

import library.daos.BookHelper;
import library.daos.BookMapDAO;
import library.daos.LoanHelper;
import library.daos.LoanMapDAO;
import library.daos.MemberHelper;
import library.daos.MemberMapDAO;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class RunBorrowUC {

	private static CardReader reader;
	private static Scanner scanner;
	private static Printer printer;
	private static Display display;
	private static IBookDAO bookDAO;
	private static ILoanDAO loanDAO;
	private static IMemberDAO memberDAO;
	//private static BorrowUC_CTL control;

	public static void showGUI() {		
		reader.setVisible(true);
		scanner.setVisible(true);
		printer.setVisible(true);
		display.setVisible(true);
	}

	public static void main(String[] args) {
		
		bookDAO = new BookMapDAO(new BookHelper());
		loanDAO = new LoanMapDAO(new LoanHelper());
		memberDAO = new MemberMapDAO(new MemberHelper());

		reader = new CardReader();
		scanner = new Scanner();
		printer = new Printer();
		display = new Display();
		new BorrowUC_CTL(reader, scanner, printer, display, 
						 bookDAO, loanDAO, memberDAO);
		
		IBook[] book = new IBook[15];
		IMember[] member = new IMember[5];
		
		book[0]  = bookDAO.addBook("author1", "title1", "callNo1");
		book[1]  = bookDAO.addBook("author1", "title2", "callNo2");
		book[2]  = bookDAO.addBook("author1", "title3", "callNo3");
		book[3]  = bookDAO.addBook("author1", "title4", "callNo4");
		book[4]  = bookDAO.addBook("author2", "title5", "callNo5");
		book[5]  = bookDAO.addBook("author2", "title6", "callNo6");
		book[6]  = bookDAO.addBook("author2", "title7", "callNo7");
		book[7]  = bookDAO.addBook("author2", "title8", "callNo8");
		book[8]  = bookDAO.addBook("author3", "title9", "callNo9");
		book[9]  = bookDAO.addBook("author3", "title10", "callNo10");
		book[10] = bookDAO.addBook("author4", "title11", "callNo11");
		book[11] = bookDAO.addBook("author4", "title12", "callNo12");
		book[12] = bookDAO.addBook("author5", "title13", "callNo13");
		book[13] = bookDAO.addBook("author5", "title14", "callNo14");
		book[14] = bookDAO.addBook("author5", "title15", "callNo15");
		
		member[0] = memberDAO.addMember("fName1", "lName1", "0001", "email1");
		member[1] = memberDAO.addMember("fName2", "lName2", "0002", "email2");
		member[2] = memberDAO.addMember("fName3", "lName3", "0003", "email3");
		member[3] = memberDAO.addMember("fName4", "lName4", "0004", "email4");
		member[4] = memberDAO.addMember("fName5", "lName5", "0005", "email5");
		
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		Date borrowDate = now;
		
		
		//create a member with overdue loans
		cal.setTime(now);
		cal.add(Calendar.DATE, ILoan.LOAN_PERIOD + 1);
		Date checkDate = cal.getTime();		
		
		for (int i=0; i<2; i++) {
			ILoan loan = loanDAO.createLoan(member[1], book[i]);
			loanDAO.commitLoan(loan);
		}
		loanDAO.updateOverDueStatus(checkDate);
		
		//create a member with maxed out unpaid fines
		member[2].addFine(10.0f);
		
		//create a member with maxed out loans
		for (int i=2; i<7; i++) {
			ILoan loan = loanDAO.createLoan(member[3], book[i]);
			loanDAO.commitLoan(loan);
		}
		//member[3].addFine(5.0f);

        // start the GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showGUI();
            }
        });
	}

}
