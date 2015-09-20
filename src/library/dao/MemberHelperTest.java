package library.dao;

import static org.junit.Assert.*;
import library.entities.Member;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MemberHelperTest
{
  
  IMemberHelper memberHelper;
  IMember member;
  private int    iD            = 101;
  private String fName         = "Jim";
  private String lName         = "Bob";
  private String email         = "jb@b.com";
  private String contactNumber = "02222222";
  
  @Before
  public void setUp () throws Exception
  {
   memberHelper = new MemberHelper();
   member = new Member(iD, fName, lName, email, contactNumber);
  }



  @After
  public void tearDown () throws Exception
  {
    memberHelper = null;
  }
 
  @Test
  public void testMakeMember ()
  {
    IMember test = memberHelper.makeMember (fName, lName, contactNumber, email, 
        iD);
   assertEquals (test.toString (), member.toString ());
  }

}
