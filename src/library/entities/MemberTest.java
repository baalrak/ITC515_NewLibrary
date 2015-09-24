package library.entities;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import library.entities.Member;
import library.interfaces.entities.EMemberState;
import library.interfaces.entities.IBook;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MemberTest
{
  
  IMember member;
  ILoan loan;
  IBook book;
  Date currentDate; 
  Date overDueDate;
  static Calendar cal;
  private int    iD            = 101;
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
    book = new Book("JIM", "JAMES", "JJ", 003);
  }



  @After
  public void tearDown () throws Exception
  {
    member = null;
    loan = null;
  }



  @Test
  public void testMember ()
  {
    assertNotNull (member);
  }
  
  @Test
  public void testSane ()
  {
    // no exception should be thrown
    member = new Member(iD, fName, lName, email, contactNumber);
    // Beginning of thrown exception tests
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
    assertFalse (member.hasOverDueLoans());
    assertEquals (member.hasOverDueLoans (),loan.isOverDue());
    
    // Loan class needs to be implemented to test assertTrue.
  }



  @Test
  public void testHasReachedLoanLimit ()
  {
    cal = Calendar.getInstance();
    currentDate = new Date();
    cal.setTime(currentDate);
    cal.add(Calendar.DATE, ILoan.LOAN_PERIOD);
    overDueDate = cal.getTime();
    //cal.add(Calendar.DATE, ILoan.LOAN_PERIOD);
    //overdueDate = cal.getTime();
    //loan limit not reached
    assertFalse (member.hasReachedLoanLimit());
    //loan limit reached
    loan = new Loan(book, member, currentDate, overDueDate);
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
    assertFalse (member.hasFinesPayable());
    member.addFine(11.0f);
    assertTrue (member.hasFinesPayable ());
  }



  @Test
  public void testHasReachedFineLimit ()
  {
    assertFalse (member.hasReachedFineLimit());
    member.addFine(11.0f);
    assertTrue(member.hasReachedFineLimit());
  }


  
  @Test
  public void testGetFineAmount ()
  {
  assertEquals(0.0f, member.getFineAmount(), 0.001f);
  member.addFine(11.0f);
  assertEquals(11.0f, member.getFineAmount(), 0.001f);
  }



  @Test
  public void testAddFine ()
  {
    member.addFine(5.0f);
    assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
    member.addFine(10.0f);
    assertEquals(EMemberState.BORROWING_DISALLOWED, member.getState());
    thrown.expect(RuntimeException.class);
    member.addFine (0.00f);
  }



  @Test
  public void testPayFine ()
  {
    member.addFine(10.0f);
    member.payFine(5.0f);
    assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
    member.addFine(11.0f);
    member.payFine(1.0f);
    assertEquals(EMemberState.BORROWING_DISALLOWED, member.getState());
    thrown.expect(RuntimeException.class);
    member.addFine (0.00f);
  }



  @Test
  public void testAddLoan ()
  {
    cal = Calendar.getInstance();
    currentDate = new Date();
    cal.setTime(currentDate);
    cal.add(Calendar.DATE, ILoan.LOAN_PERIOD);
    overDueDate = cal.getTime();
	  loan = (ILoan) new Loan(null, member, null, null);
      member.addLoan (loan);
      assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
      assertEquals(member.getLoans().get(0), loan);
      member.addLoan (loan);
      assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
      assertEquals(member.getLoans().get(1), loan);
      member.addLoan (loan);
      assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
      assertEquals(member.getLoans().get(2), loan);
      member.addLoan (loan);
      assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
      assertEquals(member.getLoans().get(3), loan);
      member.addLoan (loan);
      assertEquals(EMemberState.BORROWING_DISALLOWED, member.getState());
      assertEquals(member.getLoans().get(4), loan);
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
    cal = Calendar.getInstance();
    currentDate = new Date();
    cal.setTime(currentDate);
    cal.add(Calendar.DATE, ILoan.LOAN_PERIOD);
    overDueDate = cal.getTime();
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
	System.out.println(member.toString ());
  }
  
  
  @Test
  public void testBorrowingAllowed()
  {
    cal = Calendar.getInstance();
    currentDate = new Date();
    cal.setTime(currentDate);
    cal.add(Calendar.DATE, ILoan.LOAN_PERIOD);
    overDueDate = cal.getTime();
    // first test hasReachedLoanLimit is true
    loan = (ILoan) new Loan(null, member, null, null);
    member.addLoan (loan);
    member.addLoan (loan);
    member.addLoan (loan);
    member.addLoan (loan);
    member.addLoan (loan);
    thrown.expect(RuntimeException.class);
    member.addLoan (loan);
    assertTrue(member.hasReachedLoanLimit());
    assertEquals(EMemberState.BORROWING_DISALLOWED, member.getState());
    
    
    // Second Test hasReachedFineLimit
    member.addFine(11.0f);
    assertTrue(member.hasReachedFineLimit());
    assertEquals(EMemberState.BORROWING_DISALLOWED, member.getState());
    
    
    // Third test hasOverDueLoans (Unable to test without Loan class implemented)
    
    // Fourth test borrowing allowed
    assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());   
  }
  
  
  @Test
  public void testUpdateState()
  {
    cal = Calendar.getInstance();
    currentDate = new Date();
    cal.setTime(currentDate);
    cal.add(Calendar.DATE, ILoan.LOAN_PERIOD);
    overDueDate = cal.getTime();
    // testing state borrowing disallowed.
    loan = (ILoan) new Loan(null, member, null, null);
    member.addLoan (loan);
    member.addLoan (loan);
    member.addLoan (loan);
    member.addLoan (loan);
    member.addLoan (loan);
    thrown.expect(RuntimeException.class);
    member.addLoan (loan);
    assertTrue(member.hasReachedLoanLimit());
    assertEquals(EMemberState.BORROWING_DISALLOWED, member.getState());
    
    // testing state borrowing allowed
    loan = (ILoan) new Loan(null, member, null, null);
    member.addLoan (loan);
    member.addLoan (loan);
    member.addLoan (loan);
    member.addLoan (loan);
    assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
  }
}