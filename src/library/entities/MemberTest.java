package library.entities;

import static org.junit.Assert.*;
import library.entities.Member;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MemberTest
{
  
  IMember member;
  private int    iD            = 101;
  private String fName         = "Jim";
  private String lName         = "Bob";
  private String email         = "jb@b.com";
  private String contactNumber = "02222222";

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
    assertNotNull(member);
  }
  
  @Test
  public void testSane ()
  {
    ;
  }
  

  @Test
  public void testHasOverDueLoans ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testHasReachedLoanLimit ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testHasFinesPayable ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testHasReachedFineLimit ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testGetFineAmount ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testAddFine ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testPayFine ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testAddLoan ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testGetLoans ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testRemoveLoan ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testGetState ()
  {
    fail ("Not yet implemented");
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



  @Test
  public void testObject ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testGetClass ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testHashCode ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testEquals ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testClone ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testToString ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testNotify ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testNotifyAll ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testWaitLong ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testWaitLongInt ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testWait ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testFinalize ()
  {
    fail ("Not yet implemented");
  }

}
