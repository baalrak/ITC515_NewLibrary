package library.entities;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import library.entities.Member;
import library.interfaces.entities.EMemberState;
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
  List<ILoan> loan;
  private int    iD            = 101;
  private String fName         = "Jim";
  private String lName         = "Bob";
  private String email         = "jb@b.com";
  private String contactNumber = "02222222";
  
  @Rule
  public ExpectedException thrown= ExpectedException.none();

  @Before
  public void setUp () throws Exception
  {
    member = new Member(iD, fName, lName, email, contactNumber);
  }



  @After
  public void tearDown () throws Exception
  {
    member = null;
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
  }



  @Test
  public void testHasReachedLoanLimit ()
  {
    assertFalse (member.hasReachedLoanLimit());
  }



  @Test
  public void testHasFinesPayable ()
  {
    assertFalse (member.hasFinesPayable());
  }



  @Test
  public void testHasReachedFineLimit ()
  {
    assertFalse (member.hasReachedFineLimit());
  }


  
  @Test
  public void testGetFineAmount ()
  {
  assertEquals(0.0f, member.getFineAmount(), 0.001f);
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
    member.addLoan ((ILoan) loan);
    assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
    member.addLoan ((ILoan) loan);
    assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
    member.addLoan ((ILoan) loan);
    assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
    member.addLoan ((ILoan) loan);
    assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
    member.addLoan ((ILoan) loan);
    assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
    thrown.expect(RuntimeException.class);
    member.addLoan ((ILoan) loan);
    assertEquals(EMemberState.BORROWING_DISALLOWED, member.getState()); 
  }



  @Test
  public void testGetLoans ()
  {
    member.addLoan((ILoan)loan);
    assertEquals(member.getLoans().get(0), (ILoan)loan);
  }



  @Test
  public void testRemoveLoan ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testGetState ()
  {
    assertEquals(EMemberState.BORROWING_ALLOWED, member.getState());
  }



  @Test
  public void testGetFirstName ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testGetLastName ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testGetContactPhone ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testGetEmailAddress ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testGetID ()
  {
    fail ("Not yet implemented");
  }



}