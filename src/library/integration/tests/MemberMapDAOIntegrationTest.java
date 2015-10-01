package library.integration.tests;

import static org.junit.Assert.*;

import library.dao.MemberHelper;
import library.dao.MemberMapDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.IMember;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MemberMapDAOIntegrationTest
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
    map = null;
  }
  
  
  
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  
  
  @Test
  public void testMemberMapDAO ()
  {
    // Test creation of a MemberMapDAO object
    map = new MemberMapDAO(memberHelper);
    assertNotNull(map);
    assertTrue(map instanceof MemberMapDAO);
  }
  
  
  
  @Test
  public void testMemberMapDAONullValue ()
  {
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
    // Test getting member by ID - first member
    map.addMember ("Bob", "Janson", "55544411", "bj@bj.com");
    map.getMemberByID (1);
    assertNotNull(map.getMemberByID(1));
    System.out.println("GetMemberByID(1st Member): \n" 
        + map.getMemberByID(1) + "\n");
    
    // Test getting member by ID - second member
    map.addMember ("Ren", "Pampers", "223322335", "rp@wow.com");
    map.getMemberByID (2);
    assertNotNull(map.getMemberByID(2));
    System.out.println("GetMemberByID(2nd Member): \n" 
                       + map.getMemberByID(2) + "\n");
  }
  
  
  
  @Test
  public void testGetMemberByIDError()
  {
    // Test getting member that does not exist
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
    System.out.println("ListMembers: \n" + map.listMembers() + "\n");
  }



  @Test
  public void testFindMembersByLastName ()
  {
	// Test finding members that have been added to MemberMapDAO by last name
    map.addMember ("Bob", "Janson", "55544411", "bj@bj.com");
    map.addMember ("Ren", "Pampers", "223322335", "rp@wow.com");
    assertNotNull(map.findMembersByLastName("Janson"));
    System.out.println("MemberByLastName: \n" 
        + map.findMembersByLastName ("Janson")+"\n");
  }



  @Test
  public void testFindMembersByEmailAddress ()
  {
	// Test finding members that have been added to MemberMapDAO by email
    map.addMember ("Bob", "Janson", "55544411", "bj@bj.com");
    map.addMember ("Ren", "Pampers", "223322335", "rp@wow.com");
    assertNotNull(map.findMembersByEmailAddress("rp@wow.com"));
    System.out.println("MemberByEmail: \n" 
                       + map.findMembersByEmailAddress ("rp@wow.com")+"\n");
  }



  @Test
  public void testFindMembersByNames ()
  {
	// Test finding members that have been added to MemberMapDAO by first and last name
    map.addMember ("Bob", "Janson", "55544411", "bj@bj.com");
    map.addMember ("Ren", "Pampers", "223322335", "rp@wow.com");
    assertNotNull(map.findMembersByNames("Ren", "Pampers"));
    System.out.println("MemberByNames: \n" 
                       + map.findMembersByNames ("Ren", "Pampers") + "\n");
  }

}
