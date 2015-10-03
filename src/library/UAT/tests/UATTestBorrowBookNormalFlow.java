package library.UAT.tests;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import library.BorrowUC_CTL;
import library.dao.BookHelper;
import library.dao.BookMapDAO;
import library.dao.LoanHelper;
import library.dao.LoanMapDAO;
import library.dao.MemberHelper;
import library.dao.MemberMapDAO;
import library.interfaces.EBorrowState;
import library.interfaces.IBorrowUI;
import library.interfaces.IBorrowUIListener;
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
import library.panels.borrow.ABorrowPanel;
import library.panels.borrow.ConfirmLoanPanel;
import library.panels.borrow.ScanningPanel;
import library.panels.borrow.SwipeCardPanel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class UATTestBorrowBookNormalFlow
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
  ABorrowPanel ui, confirmLoanPanel, scanningPanel, swipeCardPanel;
  IBorrowUI uIMock;
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
    printer = Mockito.mock(IPrinter.class);
    cardReader = Mockito.mock (ICardReader.class);
    ui = Mockito.mock (ABorrowPanel.class);
    display = Mockito.mock (IDisplay.class);
    borrowCtl = new BorrowUC_CTL(cardReader, scanner, printer, display, bookDAO,
                                 loanDAO, memberDAO, ui);
    confirmLoanPanel = new ConfirmLoanPanel(listener);
    swipeCardPanel = new SwipeCardPanel(listener);
    scanningPanel = new ScanningPanel(listener);
    
    IBook[] book = new IBook[16];
    IMember member[] = new IMember[6];
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
    book[15] = bookDAO.addBook("author6", "title16", "callNo16");
    member[0] = memberDAO.addMember("fName0", "lName0", "0001", "email0");
    member[1] = memberDAO.addMember("fName1", "lName1", "0002", "email1");
    member[2] = memberDAO.addMember("fName2", "lName2", "0003", "email2");
    member[3] = memberDAO.addMember("fName3", "lName3", "0004", "email3");
    member[4] = memberDAO.addMember("fName4", "lName4", "0005", "email4");
    member[5] = memberDAO.addMember("fName5", "lName5", "0006", "email5");
    Calendar cal = Calendar.getInstance();
    Date now = cal.getTime();
            
    //create a member with overdue loans        
    for (int i=0; i<2; i++) {
        ILoan loan = loanDAO.createLoan(member[1], book[i]);
        loanDAO.commitLoan(loan);
    }
    cal.setTime(now);
    cal.add(Calendar.DATE, ILoan.LOAN_PERIOD + 1);
    Date checkDate = cal.getTime();     
    loanDAO.updateOverDueStatus(checkDate);
    
    //create a member with maxed out unpaid fines
    member[2].addFine(10.0f);
    
    //create a member with maxed out loans
    for (int i=2; i<7; i++) {
        ILoan loan = loanDAO.createLoan(member[3], book[i]);
        loanDAO.commitLoan(loan);
    }
    
    //a member with a fine, but not over the limit
    member[4].addFine(5.0f);
    
    //a member with a couple of loans but not over the limit
    for (int i=7; i<9; i++) {
        ILoan loan = loanDAO.createLoan(member[5], book[i]);
        loanDAO.commitLoan(loan);
    }
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
  public void testBorrowBookNormalFlow() {
    
    //TEST STEP 1
    borrowCtl.initialise();
    
    //TEST STEP 1 - EXPECTED RESULTS
    Mockito.verify(display).setDisplay(ui, "Borrow UI");
    assertTrue(swipeCardPanel.isEnabled ());
    assertNotNull(memberDAO);
    Mockito.verify (cardReader).setEnabled (true);
    Mockito.verify (scanner).setEnabled(false);
    assertEquals(EBorrowState.INITIALIZED, borrowCtl.getState());
      
    //TEST STEP 2
    borrowCtl.cardSwiped(1);
    
    //TEST STEP 2 - EXPECTED RESULTS
    assertTrue(scanningPanel.isEnabled ());
    Mockito.verify (cardReader).setEnabled (false);
    Mockito.verify (scanner).setEnabled(true);
    Mockito.verify(ui).displayMemberDetails(1, "Jim Bob", "02222222");
    Mockito.verify (ui).displayExistingLoan ("");
    // Verify that the user does not have any loans
    assertTrue(memberDAO.getMemberByID (1).getLoans ().isEmpty ());
    // Verify that the user does not have any fines
    assertTrue(memberDAO.getMemberByID (1).getFineAmount () == 0.0f);
    Mockito.verify(ui).setState(EBorrowState.SCANNING_BOOKS);
    assertEquals(EBorrowState.SCANNING_BOOKS, borrowCtl.getState()); 
    
    //TEST STEP 3
    borrowCtl.bookScanned(13);
    
    //TEST STEP 3 - EXPECTED RESULTS
    assertTrue(scanningPanel.isEnabled ());
    Mockito.verify (cardReader).setEnabled(false);
    Mockito.verify (scanner).setEnabled (true);
    assertTrue(borrowCtl.getScanCount() == 1);
    Mockito.verify(ui).displayScannedBookDetails(bookDAO.getBookByID(13).toString());
    assertFalse(loanDAO.listLoans ().isEmpty ());
    Mockito.verify (ui).displayPendingLoan ("");
    assertEquals(EBorrowState.SCANNING_BOOKS, borrowCtl.getState()); 
    
    //TEST STEP 4
    borrowCtl.scansCompleted ();
    
    //TEST STEP 4 - EXPECTED RESULTS
    assertTrue(confirmLoanPanel.isEnabled ());
    Mockito.verify (ui).displayPendingLoan ("");
    Mockito.verify (cardReader, Mockito.times (2)).setEnabled(false);
    Mockito.verify (scanner, Mockito.times (2)).setEnabled (false);
    assertTrue(EBorrowState.CONFIRMING_LOANS.equals (borrowCtl.getState ()));
    
    //TEST STEP 5
    borrowCtl.loansConfirmed ();
    
    //TEST STEP 5 - EXPECTED RESULTS
    Mockito.verify(display).setDisplay (null, "Main Menu");
    Mockito.verify(ui).displayPendingLoan ("");
    Mockito.verify(printer).print (loanDAO.getLoanByID (10).toString ());
    Mockito.verify (cardReader, Mockito.times(3)).setEnabled (false);
    Mockito.verify(scanner, Mockito.times(3)).setEnabled(false);
    assertTrue(EBorrowState.COMPLETED.equals (borrowCtl.getState ()));
  }}
