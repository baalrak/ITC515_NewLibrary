package library;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import library.dao.BookHelper;
import library.dao.BookMapDAO;
import library.dao.LoanHelper;
import library.dao.LoanMapDAO;
import library.dao.MemberHelper;
import library.dao.MemberMapDAO;
import library.entities.Loan;
import library.entities.Member;
import library.entities.Book;
import library.hardware.CardReader;
import library.hardware.Scanner;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BorrowUC_CTLTest
{
  IMember member;
  IMemberDAO memberDAO;
  IMemberHelper memHelper;
  IBook book;
  IBookDAO bookDAO;
  IBookHelper bookHelper;
  ILoan loan;
  ILoanDAO loanDAO;
  ILoanHelper loanHelper;
  ICardReader cardReader;
  IScanner scanner;
  IPrinter printer;
  IDisplay display;
  BorrowUC_CTL borrowCtl;
  private String fName         = "Jim";
  private String lName         = "Bob";
  private String email         = "jb@b.com";
  private String contactNumber = "02222222";
  Date currentDate; 
  Date dueByDate;
  Date overDueDate;
  Calendar cal;

  @Before
  public void setUp () throws Exception
  {
    cal = Calendar.getInstance();
    currentDate = new Date();
    cal.setTime(currentDate);
    cal.add(Calendar.DATE, ILoan.LOAN_PERIOD);
    dueByDate = cal.getTime();
    cal.setTime(currentDate);
    cal.add(Calendar.DATE, ILoan.LOAN_PERIOD*2);
    overDueDate = cal.getTime();
    memHelper = new MemberHelper();
    memberDAO = new MemberMapDAO(memHelper);
    memberDAO.addMember (fName, lName, contactNumber, email);
    bookHelper = new BookHelper();
    bookHelper.makeBook ("Banjo", "Paterson", "003333", 1);
    bookDAO = new BookMapDAO(bookHelper);
    loanHelper = new LoanHelper();
    loanDAO = new LoanMapDAO(loanHelper); 
    scanner = new Scanner();
    cardReader = new CardReader();
    borrowCtl = new BorrowUC_CTL(cardReader, scanner, printer, display, bookDAO,
                                 loanDAO, memberDAO);
  }



  @After
  public void tearDown () throws Exception
  {
  }



  @Test
  public void testcardSwiped ()
  {
    borrowCtl.initialise ();
    borrowCtl.cardSwiped (1);
  }

}
