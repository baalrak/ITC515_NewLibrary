package library.unit.tests;

import static org.junit.Assert.*;
import library.dao.MemberHelper;
import library.entities.Member;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class MemberHelperUnitTest
{
IMemberHelper memberHelper;
IMember member;
private int    iD            = 101;
private String fName         = "Jim";
private String lName         = "Bob";
private String email         = "jb@b.com";
private String contactNumber = "02222222";
private float fineAmount     = 0.0f;

@Before
public void setUp () throws Exception
{
 memberHelper = new MemberHelper();
 member       = Mockito.mock (IMember.class);
}



@After
public void tearDown () throws Exception
{
  memberHelper = null;
  member       = null;
}

@Test
// Testing that make member method of MemberHelper
public void testMakeMember ()
{
  IMember test = memberHelper.makeMember (fName, lName, contactNumber, email, 
                                          iD);
  Mockito.when (member.toString())
                .thenReturn (String.format("Id: %d\nName: %s %s\nContact Phone: "
                                           + "%s\nEmail: %s\nTotal Fines: %.2f", 
                                           new Object[] {iD, fName, lName, 
                                                         contactNumber, email, 
                                                         fineAmount}));
  
  assertEquals (test.toString (), member.toString ());
  assertTrue (memberHelper instanceof IMemberHelper);
}
}

