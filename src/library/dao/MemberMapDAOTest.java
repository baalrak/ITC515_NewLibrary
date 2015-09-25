package library.dao;

import static org.junit.Assert.*;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MemberMapDAOTest
{
  IMember member;
  IMemberHelper memberHelper;
  IMemberDAO map;
  IMemberDAO map2;
  private int    iD            = 101;
  private String fName         = "Jim";
  private String lName         = "Bob";
  private String email         = "jb@b.com";
  private String contactNumber = "02222222";
  
  @Before
  public void setUp () throws Exception
  {
    memberHelper = new MemberHelper();
    map = new MemberMapDAO(memberHelper);
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
    // Test creation of a MemberMapDAO object
    map = new MemberMapDAO(memberHelper);
    assertNotNull(map);
    
    // Test the runtime error if null is passed when creating a MemberMapDAO object
    thrown.expect(RuntimeException.class);
    map2 = new MemberMapDAO(null);
  }



  @Test
  public void testAddMember ()
  {
    // Test adding a member.
    map.addMember ("Bob", "Janson", "55544411", "bj@bj.com");
  }



  @Test
  public void testGetMemberByID ()
  {
    // Test One getting member by ID first member
    map.addMember ("Bob", "Janson", "55544411", "bj@bj.com");
    map.getMemberByID (1);
    assertNotNull(map.getMemberByID(1));
    
    // Test Two getting member by ID second member
    map.addMember ("Ren", "Pampers", "223322335", "rp@wow.com");
    map.getMemberByID (2);
    assertNotNull(map.getMemberByID(2));
    
    // Test Three getting member that does not exist
    thrown.expect (RuntimeException.class);
    map.getMemberByID (3);
  }



  @Test
  public void testListMembers ()
  {
    // Test listing members that have been added to MemberMapDAO
    map.addMember ("Bob", "Janson", "55544411", "bj@bj.com");
    map.addMember ("Ren", "Pampers", "223322335", "rp@wow.com");
    assertNotNull(map.listMembers ());
  }



  @Test
  public void testFindMembersByLastName ()
  {
    map.addMember ("Bob", "Janson", "55544411", "bj@bj.com");
    map.addMember ("Ren", "Pampers", "223322335", "rp@wow.com");
    assertNotNull(map.findMembersByLastName("Pampers"));
  }



  @Test
  public void testFindMembersByEmailAddress ()
  {
    map.addMember ("Bob", "Janson", "55544411", "bj@bj.com");
    map.addMember ("Ren", "Pampers", "223322335", "rp@wow.com");
    assertNotNull(map.findMembersByEmailAddress("rp@wow.com"));
  }



  @Test
  public void testFindMembersByNames ()
  {
    map.addMember ("Bob", "Janson", "55544411", "bj@bj.com");
    map.addMember ("Ren", "Pampers", "223322335", "rp@wow.com");
    assertNotNull(map.findMembersByNames("Ren", "Pampers"));
  }

}
