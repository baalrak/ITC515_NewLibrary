package library.unit.tests;

import static org.junit.Assert.*;


import library.dao.MemberMapDAO;
import library.interfaces.daos.IMemberDAO;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.IMember;

import org.mockito.Mockito;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MemberMapDAOUnitTest
{
  
  IMember member;
  IMemberHelper memberHelper;
  IMemberDAO map;
  IMemberDAO map2;
  private int    iD            = 1;
  private String fName         = "Jim";
  private String lName         = "Bob";
  private String email         = "jb@b.com";
  private String contactNumber = "02222222";
  
  
  @Before
  public void setUp () throws Exception
  {
    memberHelper = Mockito.mock (IMemberHelper.class);
    map = Mockito.mock(IMemberDAO.class);
    map = new MemberMapDAO(memberHelper);
    member = Mockito.mock (IMember.class);
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
    Mockito.when (memberHelper.makeMember (Mockito.eq(fName), Mockito.eq(lName), 
                                           Mockito.eq(contactNumber), 
                                           Mockito.eq(email), Mockito.eq (iD)))
                                           .thenReturn (member);
    IMember actualMember = map.addMember (fName, lName, contactNumber, email);
    Mockito.verify(memberHelper).makeMember (Mockito.eq(fName), Mockito.eq(lName), 
                                             Mockito.eq(contactNumber), 
                                             Mockito.eq(email), Mockito.eq (iD));
    assertEquals(actualMember, member);
  }



  @Test
  public void testGetMemberByID ()
  {
    // Test getting member by ID
    IMember member = Mockito.mock(IMember.class);
    Mockito.when(memberHelper.makeMember(Mockito.anyString(), Mockito.anyString(), 
                                         Mockito.anyString(), Mockito.anyString(), 
                                         Mockito.anyInt())).thenReturn(member);
    map.addMember(fName,lName,contactNumber,email);
    IMember actualMember = map.getMemberByID(0);
    assertEquals(member,actualMember);
    assertNotNull(map.getMemberByID(0));
  }
  
  
  
  @Test
  public void testGetMemberByIDMemberDoesNotExist()
  {
    // Test getting member that does not exist
    thrown.expect (RuntimeException.class);
    map.getMemberByID (3);
  }



  @Test
  public void testListMembers ()
  {
    // Test that a list of members is returned
    IMember member = Mockito.mock(IMember.class);
    Mockito.when(memberHelper.makeMember(Mockito.anyString(), Mockito.anyString(), 
                                         Mockito.anyString(), Mockito.anyString(), 
                                         Mockito.anyInt())).thenReturn(member);
    map.addMember(fName,lName,contactNumber,email);
    assertNotNull(map.listMembers());
  }



  @Test
  public void testFindMembersByLastName ()
  {
    // Test that a member is returned when searching by last name
    IMember member = Mockito.mock(IMember.class);
    Mockito.when (member.getLastName ()).thenReturn (lName);
    Mockito.when (member.getFirstName ()).thenReturn (fName);
    Mockito.when (member.getContactPhone ()).thenReturn (contactNumber);
    Mockito.when (member.getEmailAddress ()).thenReturn (email);
    Mockito.when (member.getID ()).thenReturn (iD);
    Mockito.when (memberHelper.makeMember(Mockito.eq(fName), Mockito.eq(lName), 
                                          Mockito.eq(contactNumber), 
                                          Mockito.eq(email), Mockito.eq (iD)))
                                          .thenReturn (member);
    map = new MemberMapDAO(memberHelper);
    map.addMember(fName,lName,contactNumber,email);
    Mockito.verify(memberHelper).makeMember (Mockito.eq(fName), Mockito.eq(lName), 
                                             Mockito.eq(contactNumber), 
                                             Mockito.eq(email), Mockito.eq (iD));
    assertNotNull(map.findMembersByLastName(lName));
  }



  @Test
  public void testFindMembersByEmailAddress ()
  {
    // Test that a member is returned when searching by email address
    IMember member = Mockito.mock(IMember.class);
    Mockito.when (member.getLastName ()).thenReturn (lName);
    Mockito.when (member.getFirstName ()).thenReturn (fName);
    Mockito.when (member.getContactPhone ()).thenReturn (contactNumber);
    Mockito.when (member.getEmailAddress ()).thenReturn (email);
    Mockito.when (member.getID ()).thenReturn (iD);
    Mockito.when (memberHelper.makeMember(Mockito.eq(fName), Mockito.eq(lName), 
                                          Mockito.eq(contactNumber), 
                                          Mockito.eq(email), Mockito.eq (iD)))
                                          .thenReturn (member);
    map = new MemberMapDAO(memberHelper);
    map.addMember(fName,lName,contactNumber,email);
    Mockito.verify(memberHelper).makeMember (Mockito.eq(fName), Mockito.eq(lName), 
                                             Mockito.eq(contactNumber), 
                                             Mockito.eq(email), Mockito.eq (iD));
    assertNotNull(map.findMembersByEmailAddress(email));
  }



  @Test
  public void testFindMembersByNames ()
  {
    // Test that a member is returned when searching by both first and last names
    IMember member = Mockito.mock(IMember.class);
    Mockito.when (member.getLastName ()).thenReturn (lName);
    Mockito.when (member.getFirstName ()).thenReturn (fName);
    Mockito.when (member.getContactPhone ()).thenReturn (contactNumber);
    Mockito.when (member.getEmailAddress ()).thenReturn (email);
    Mockito.when (member.getID ()).thenReturn (iD);
    Mockito.when (memberHelper.makeMember(Mockito.eq(fName), Mockito.eq(lName), 
                                          Mockito.eq(contactNumber), 
                                          Mockito.eq(email), Mockito.eq (iD)))
                                          .thenReturn (member);
    map = new MemberMapDAO(memberHelper);
    map.addMember(fName,lName,contactNumber,email);
    Mockito.verify(memberHelper).makeMember (Mockito.eq(fName), Mockito.eq(lName), 
                                             Mockito.eq(contactNumber), 
                                             Mockito.eq(email), Mockito.eq (iD));
    assertNotNull(map.findMembersByNames(lName, fName));
  }
}

