package library.entities;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import library.entities.Member;
import library.interfaces.daos.ILoanDAO;
import library.interfaces.entities.EMemberState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;


public class MemberTest
{
  IMember member;
  ILoan loan;
  IBook book;
  Date currentDate; 
  Date dueByDate;
  Date overDueDate;
  Calendar cal;
  ILoanDAO loanMap;
  private int    iD            = 1;
  private String fName         = "Jim";
  private String lName         = "Bob";
  private String email         = "jb@b.com";
  private String contactNumber = "02222222";
  private float fineAmount     = 0.0f;

  
  
  @Rule
  public ExpectedException thrown= ExpectedException.none();

  @Before
  public void setUp () throws Exception
  {
    cal = Calendar.getInstance ();
    member = new Member(iD, fName, lName, email, contactNumber);
    book = Mockito.mock(IBook.class);
    cal = Calendar.getInstance();
    currentDate = new Date();
    cal.setTime(currentDate);
    cal.add(Calendar.DATE, ILoan.LOAN_PERIOD);
    dueByDate = cal.getTime();
    cal.setTime(currentDate);
    cal.add(Calendar.DATE, ILoan.LOAN_PERIOD*2);
    overDueDate = cal.getTime();
    loan = Mockito.mock (ILoan.class);
  }



  @After
  public void tearDown () throws Exception
  {
    member = null;
    loan = null;
    cal = null;
    book = null;
  }



  @Test
  public void testMember ()
  {
    assertNotNull (member);
    assertTrue(member instanceof Member);
  }
  
  @Test
  public void testSane ()
  {
    // Testing no exception should be thrown
    member = new Member(iD, fName, lName, email, contactNumber);
    
    // Testing thrown exceptions
    thrown.expect(RuntimeException.class);
    member = new Member(0, fName, lName, email, contactNumber);
    thrown.expect(RuntimeException.class);
    member = new Member(iD, null, lName, email, contactNumber);
    thrown.expect(RuntimeException.class);
    member = new Member(iD, fName, null, email, contactNumber);
    thrown.expect(RuntimeException.class);
    member = new Member(iD, fName, lName, null, contactNumber);
    thrown.expect(RuntimeException.class);
    member = new Member(iD, fName, lName, email, null);
  }
  

  @Test
  public void testHasOverDueLoans ()
  {
	// Member does not have overdue loans
    Mockito.when(loan.isOverDue()).thenReturn (false);
	member.addLoan(loan);
    assertFalse (member.hasOverDueLoans());
    assertEquals (member.hasOverDueLoans (),loan.isOverDue()); 
    
    // Member does have overdue loans
    loanMap = Mockito.mock (ILoanDAO.class);
    Mockito.when(loanMap.createLoan (member, book)).thenReturn (loan);
    loanMap.commitLoan(loan);
    Mockito.when(loan.isOverDue()).thenReturn (true);
    member.addLoan (loan);
    assertTrue(member.hasOverDueLoans());
    assertEquals(member.hasOverDueLoans(), loan.isOverDue());
  }



  @Test
  public void testHasReachedLoanLimit ()
  {
    // Testing loan limit not reached
    assertFalse (member.hasReachedLoanLimit());
    
    // Testing loan limit reached
    member.addLoan (loan);
    member.addLoan (loan);
    member.addLoan (loan);
    member.addLoan (loan);
    member.addLoan (loan);
    thrown.expect(RuntimeException.class);
    member.addLoan (loan);
    assertTrue(member.hasReachedLoanLimit());
  }



  @Test
  public void testHasFinesPayable ()
  {
	// Testing member has no fines payable
    assertFalse (member.hasFinesPayable());
    
    // Testing adding a fine to member and now has fines payable
    member.addFine(11.0f);
    assertTrue (member.hasFinesPayable ());
  }



  @Test
  public void testHasReachedFineLimit ()
  {
	// Testing that member has not reached fine limit
    assertFalse (member.hasReachedFineLimit());
    
    // Testing adding a fine and member now reached fine limit.
    member.addFine(11.0f);
    assertTrue(member.hasReachedFineLimit());
  }


  
  @Test
  public void testGetFineAmount ()
  {
	// Test getting current fine amount from member  
	assertEquals(0.0f, member.getFineAmount(), 0.001f);
	
	// Test adding a new fine amount to member and getting the new amount
	member.addFine(11.0f);
	assertEquals(11.0f, member.getFineAmount(), 0.001f);
  }



  @Test
  public void testAddFine ()
  {
	// Testing adding a small fine resulting in borrowing still allowed
    member.addFine(5.0f);
    assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
    
    // Testing adding a larger fine resulting in borrowing disallowed
    member.addFine(10.0f);
    assertEquals(EMemberState.BORROWING_DISALLOWED, member.getState());
    
    // Testing adding a fine amount of $0.00 which throws an error.
    thrown.expect(RuntimeException.class);
    member.addFine (0.00f);
  }



  @Test
  public void testPayFine ()
  {
	// Testing adding a fine and then paying some of the fine to change state to 
    // borrowing allowed.
    member.addFine(10.0f);
    member.payFine(5.0f);
    assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
    
    // Testing adding a fine and then paying some of the fine but not enough to 
    // change state to borrowing allowed
    member.addFine(11.0f);
    member.payFine(1.0f);
    assertEquals(EMemberState.BORROWING_DISALLOWED, member.getState());
  }



  @Test
  public void testAddLoan ()
  {
	// Test adding a loan
    member.addLoan (loan);
    assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
    assertEquals(member.getLoans().get(0), loan);
    
    // Test adding a  second loan
    member.addLoan (loan);
    assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
    assertEquals(member.getLoans().get(1), loan);
    
    // Test adding a third loan
    member.addLoan (loan);
    assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
    assertEquals(member.getLoans().get(2), loan);
    
    // Test adding a fourth loan
    member.addLoan (loan);
    assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
    assertEquals(member.getLoans().get(3), loan);
    
    // Test adding a fifth loan
    member.addLoan (loan);
    assertEquals(EMemberState.BORROWING_DISALLOWED, member.getState());
    assertEquals(member.getLoans().get(4), loan);
    
    // Test adding a sixth loan, however maximum number of loans has been reached 
    thrown.expect(RuntimeException.class);
    member.addLoan (loan);
    assertEquals(EMemberState.BORROWING_DISALLOWED, member.getState());
    assertNotEquals(member.getLoans().get(5), loan);
  }



  @Test
  public void testGetLoans ()
  {
    member.addLoan(loan);
    assertEquals(member.getLoans().get(0), loan);
  }



  @Test
  public void testRemoveLoan ()
  {
	// Testing adding a loan and then removing then loan
    member.addLoan(loan);
    assertEquals(member.getLoans().get(0), loan);
    member.removeLoan(loan);
    assertNotEquals(member.getLoans(), loan);
  }



  @Test
  public void testGetState ()
  {
    assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
  }



  @Test
  public void testGetFirstName ()
  {
	assertEquals(member.getFirstName(), fName);
  }



  @Test
  public void testGetLastName ()
  {
	assertEquals(member.getLastName(), lName);
  }



  @Test
  public void testGetContactPhone ()
  {
	assertEquals(member.getContactPhone(), contactNumber);
  }



  @Test
  public void testGetEmailAddress ()
  {
	assertEquals(member.getEmailAddress(), email);
  }



  @Test
  public void testGetID ()
  {
	assertEquals(member.getID(), iD);
  }


  
  @Test
  public void testToString()
  {
	String expected = String.format("Id: %d\nName: %s %s\nContact Phone: %s"
	                                + "\nEmail: %s\nTotal Fines: %.2f", 
              						new Object[] {iD, fName, lName, 
	                                              contactNumber, email, 
	                                              fineAmount});
	assertEquals (expected, member.toString());
  }
  
 
  
  @Test
  public void testBorrowingAllowedOne()
  {
    // First test hasReachedLoanLimit is true
    member.addLoan (loan);
    member.addLoan (loan);
    member.addLoan (loan);
    member.addLoan (loan);
    member.addLoan (loan);
    thrown.expect(RuntimeException.class);
    member.addLoan (loan);
    assertTrue(member.hasReachedLoanLimit());
    assertEquals(EMemberState.BORROWING_DISALLOWED, member.getState());
  }
    
  
  
  @Test
  public void testBorrowingAllowedTwo()
  { 
    // Second Test hasReachedFineLimit is true
    member.addFine(11.0f);
    assertTrue(member.hasReachedFineLimit());
    assertEquals(EMemberState.BORROWING_DISALLOWED, member.getState());
  }
    
  
  
  @Test
  public void testBorrowingAllowedThree()
  {
    // Third test hasOverDueLoans is true
    member.addLoan(loan);
    Mockito.when(loan.isOverDue()).thenReturn (true);
    member.addLoan(loan);;
    assertTrue(member.hasOverDueLoans());
    assertEquals(member.hasOverDueLoans(), loan.isOverDue());
    assertEquals(EMemberState.BORROWING_DISALLOWED, member.getState());
  }
    
  
  
  @Test
  public void testBorrowingAllowedFour()
  {
    // Fourth test borrowing allowed is true
    assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());   
  }
  
  
    
  @Test
  public void testUpdateState()
  {
    // Testing state borrowing disallowed using hasReachedLoanLimit.
    member.addLoan (loan);
    member.addLoan (loan);
    member.addLoan (loan);
    member.addLoan (loan);
    member.addLoan (loan);
    thrown.expect(RuntimeException.class);
    member.addLoan (loan);
    assertTrue(member.hasReachedLoanLimit());
    assertEquals(EMemberState.BORROWING_DISALLOWED, member.getState());
  }
  
  
  
    public void testUpdateStateTwo()
    {   
    // Testing state borrowing allowed
    member.addLoan (loan);
    member.addLoan (loan);
    member.addLoan (loan);
    member.addLoan (loan);
    assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
  }
}