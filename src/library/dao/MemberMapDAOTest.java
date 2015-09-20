package library.dao;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Map;

import library.entities.Member;
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
    map = new MemberMapDAO(memberHelper);
    assertNotNull(map);
    thrown.expect(RuntimeException.class);
    map2 = new MemberMapDAO(null);
  }



  @Test
  public void testAddMember ()
  {
    map.addMember ("Bob", "Janson", "55544411", "bj@bj.com");
  }



  @Test
  public void testGetMemberByID ()
  {
    map.addMember ("Bob", "Janson", "55544411", "bj@bj.com");
    map.getMemberByID (1);
    assertNotNull(map.getMemberByID(1));
    map.addMember ("Ren", "Pampers", "223322335", "rp@wow.com");
    map.getMemberByID (2);
    assertNotNull(map.getMemberByID(2));
    thrown.expect (RuntimeException.class);
    map.getMemberByID (3);
  }



  @Test
  public void testListMembers ()
  {
    map.addMember ("Bob", "Janson", "55544411", "bj@bj.com");
    map.addMember ("Ren", "Pampers", "223322335", "rp@wow.com");
    map.listMembers ();
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
