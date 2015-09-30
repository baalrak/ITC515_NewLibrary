package library.integration.tests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;

import library.BorrowUC_CTL;
import library.BorrowUC_UI;
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
import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.IBorrowUIListener;
import library.interfaces.daos.IBookDAO;
import library.interfaces.daos.IBookHelper;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.daos.ILoanHelper;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.EBookState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.hardware.ICardReader;
import library.interfaces.hardware.IDisplay;
import library.interfaces.hardware.IPrinter;
import library.interfaces.hardware.IScanner;
import library.panels.borrow.ABorrowPanel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class BorrowUC_CTLIntegrationTest
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
  ABorrowPanel ui;
  IBorrowUIListener listener;
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

  
  
  @Rule
  public ExpectedException thrown= ExpectedException.none();
  
  
  
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
    book = bookDAO.addBook ("Banjo Paterson", "Monster", "003333");
    loanHelper = new LoanHelper();
    loanDAO = new LoanMapDAO(loanHelper); 
    scanner = Mockito.mock (IScanner.class);
    cardReader = Mockito.mock (ICardReader.class);
    ui = Mockito.mock (ABorrowPanel.class);
    display = Mockito.mock (IDisplay.class);
    borrowCtl = new BorrowUC_CTL(cardReader, scanner, printer, display, bookDAO,
                                 loanDAO, memberDAO, ui);
    
  }



  @After
  public void tearDown () throws Exception
  {
    cal = null;
    currentDate = null;
    dueByDate = null;
    overDueDate = null;
    memHelper = null;
    memberDAO = null;
    bookHelper = null;
    bookDAO = null;
    book = null;
    loanHelper = null;
    loanDAO = null;
    scanner = null;
    cardReader = null;
    ui = null;
    display = null;
    borrowCtl = null;
  }

  
  
  @Test
  public void testBorrowUseCaseControllerUI()
  {
    borrowCtl = new BorrowUC_CTL(cardReader, scanner, printer, display, bookDAO,
                                 loanDAO, memberDAO, ui);
    assertTrue(borrowCtl instanceof BorrowUC_CTL);
    assertTrue(borrowCtl.getState ().equals (EBorrowState.CREATED));
    assertNotNull(borrowCtl);
  }

  
  
  @Test
  public void testInitialise()
  {
    //Test that then state does in fact transition into Initialized
    borrowCtl.initialise ();
    assertTrue(EBorrowState.INITIALIZED.equals(borrowCtl.getState()));
  }

  
  
  @Test
  public void testcardSwiped ()
  {
    // Test swiped card is valid
    Mockito.when(display.getDisplay()).thenReturn (null);
    borrowCtl.initialise();
    assertTrue (borrowCtl.getState ().equals (EBorrowState.INITIALIZED));
    borrowCtl.cardSwiped (1);
    assertTrue(borrowCtl.getState ().equals (EBorrowState.SCANNING_BOOKS));
  }
  
  
  
  @Test
  public void testCardSwipedError()
  {
     //Test swiped card is not valid
     Mockito.when(display.getDisplay()).thenReturn (null);
     borrowCtl.initialise();
     assertTrue (borrowCtl.getState ().equals (EBorrowState.INITIALIZED));
     thrown.expect(RuntimeException.class);
     borrowCtl.cardSwiped (2);
     assertFalse(borrowCtl.getState().equals (EBorrowState.SCANNING_BOOKS));
  }
  
  
  
  @Test
  public void testCardSwipedBorrowingRestricted()
  {
     //Test swiped card is valid but member is restricted
    Mockito.when (member.hasReachedFineLimit ()).thenReturn (true);
    Mockito.when(display.getDisplay()).thenReturn (null);
    borrowCtl.initialise();
    assertTrue (borrowCtl.getState ().equals (EBorrowState.INITIALIZED));
    borrowCtl.cardSwiped (1);
    assertTrue(borrowCtl.getState ().equals (EBorrowState.BORROWING_RESTRICTED));
  }
  
   
  
  @Test
  public void testBookScanned()
  {
    //Test that booked scanned method.
    Mockito.when(display.getDisplay()).thenReturn (null);
    borrowCtl.initialise();
    assertTrue (borrowCtl.getState ().equals (EBorrowState.INITIALIZED));
    borrowCtl.cardSwiped (1);
    assertTrue(borrowCtl.getState ().equals (EBorrowState.SCANNING_BOOKS));
    borrowCtl.bookScanned (1);
    assertTrue(book.getState ().equals (EBookState.AVAILABLE));
    assertTrue(borrowCtl.getState ().equals(EBorrowState.CONFIRMING_LOANS));
  }

  
  
  @Test
  public void testBookScannedErrorBookNotFound()
  {
    //Test that booked scanned method.
    Mockito.when(display.getDisplay()).thenReturn (null);
    borrowCtl.initialise();
    assertTrue (borrowCtl.getState ().equals (EBorrowState.INITIALIZED));
    borrowCtl.cardSwiped (1);
    assertTrue(borrowCtl.getState ().equals (EBorrowState.SCANNING_BOOKS));
    borrowCtl.bookScanned (0);
    assertFalse(book.getState ().equals (EBookState.AVAILABLE));
    assertTrue(borrowCtl.getState ().equals (EBorrowState.SCANNING_BOOKS));
  }
  
  
  
  @Test
  public void testSetStateCompleted()
  {
    //Test that then state does in fact transition into Completed.
    thrown.expect (NullPointerException.class);
    borrowCtl.loansConfirmed ();
    assertTrue(EBorrowState.COMPLETED.equals (borrowCtl.getState ()));
  }
  
  
  
  @Test
  public void testSetStateBorrowingRestricted()
  {
    //Test that then state does in fact transition into Borrowing restricted
    book = new Book("Michael", "Hi There", "003", 1);
    loan = new Loan(book, memberDAO.getMemberByID (1), currentDate, dueByDate);
    System.out.println(memberDAO.getMemberByID (1));
    System.out.println(memberDAO.getMemberByID (1).getLoans ());
    memberDAO.getMemberByID (1).addLoan (loan);
    memberDAO.getMemberByID (1).addLoan (loan);
    memberDAO.getMemberByID (1).addLoan (loan);
    memberDAO.getMemberByID (1).addLoan (loan);
    memberDAO.getMemberByID (1).addLoan (loan);
    thrown.expect (RuntimeException.class);
    memberDAO.getMemberByID (1).addLoan (loan);
    assertTrue(EBorrowState.BORROWING_RESTRICTED.equals (borrowCtl.getState()));
  }
  
  
  
  @Test
  public void testSetStateCancelled()
  {
    //Test that then state does in fact transition into Cancelled
    thrown.expect (NullPointerException.class);
    borrowCtl.cancelled ();
    assertTrue(EBorrowState.CANCELLED.equals(borrowCtl.getState()));
  }
  
  
  
  /*@Test
  public void testBookScanned()
  {
    //Test that the method book scanned works
    borrowCtl.setState (EBorrowState.INITIALIZED);
    borrowCtl.cardSwiped (1);
    int barcode = 1;
    borrowCtl.bookScanned (barcode);
    assertTrue(EBorrowState.CONFIRMING_LOANS.equals (borrowCtl.getState()));
  }*/
  
  
  
  @Test
  public void testBookScannedErrorNotInStateScanningBooks()
  {
    // Test that the method book scanned fails because borrowCtl is not in the
    // state SCANNING_BOOKS
    thrown.expect (RuntimeException.class);
    int barcode = 1;
    borrowCtl.bookScanned (barcode);
    assertFalse(EBorrowState.SCANNING_BOOKS.equals (borrowCtl.getState()));
  }
}
