package library.dao;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Map;

import library.entities.Member;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.daos.IMemberHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MemberMapDAOTest
{
  IMemberHelper memberHelper;
  IMemberDAO map;
  IMemberDAO map2;
  MemberMapDAO test;
  private int    iD            = 101;
  private String fName         = "Jim";
  private String lName         = "Bob";
  private String email         = "jb@b.com";
  private String contactNumber = "02222222";
  
  @Before
  public void setUp () throws Exception
  {
    memberHelper = new MemberHelper();
    memberHelper.makeMember (fName, lName, contactNumber, email, iD);
    
  }



  @After
  public void tearDown () throws Exception
  {
    memberHelper = null;
  }
  
  
  
  @Rule
  public ExpectedException thrown= ExpectedException.none();

  
  
  @Test
  public void testMemberMapDAO ()
  {
    map = new MemberMapDAO(memberHelper);
    assertNotNull(map);
    thrown.expect(RuntimeException.class);
    map2 = new MemberMapDAO(null);
  }



  @Test
  public void testAddMember ()
  {
    map = new MemberMapDAO(memberHelper);
    map.addMember ("Bob", "Janson", "55544411", "bj@bj.com");
  }



  @Test
  public void testGetMemberByID ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testListMembers ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testFindMembersByLastName ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testFindMembersByEmailAddress ()
  {
    fail ("Not yet implemented");
  }



  @Test
  public void testFindMembersByNames ()
  {
    fail ("Not yet implemented");
  }

}
