package library.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import library.interfaces.daos.IMemberDAO;
import library.interfaces.daos.IMemberHelper;
import library.interfaces.entities.IMember;

public class MemberMapDAO implements IMemberDAO
{
  
  private IMemberHelper memberHelper;
  private int           iD;
  LinkedList            list;
  
  
  
  public MemberMapDAO(IMemberHelper helper)
  {
    if (helper == null)
    {
      throw new RuntimeException("helper cannot be null!");
    }
    else
    {
      memberHelper = helper;
      iD = 1;
      list = new LinkedList();
      list.add (0, null);
    }
  }

  
  
  @Override
  public IMember addMember (String firstName, String lastName,
      String contactPhone, String emailAddress)
  {
    this.iD = getNextId();
    IMember newMember = memberHelper.makeMember (firstName, lastName,contactPhone,
                                                 emailAddress, this.iD);
    list.add (newMember.getID(), newMember);
    return newMember;
  }

  
  
  @Override
  public IMember getMemberByID (int id)
  {
    if((IMember)list.get(id) == null)
    {
      throw new RuntimeException("That Member Does not exist!");
    } 
    else
    {
      return (IMember)list.get(id);
    }
  }

  
  
  @Override
  public List<IMember> listMembers ()
  {
      return list;
  }

  
  
  @Override
  public List<IMember> findMembersByLastName (String lastName)
  {
    List<IMember> membersByLastName = new LinkedList();
    if(lastName == null || lastName.isEmpty())
    {
      throw new RuntimeException("This member does not exist!");
    }
    for(int i = 1; i < list.size(); i++)
    {
      String m = list.get(i).toString ();
        if(m.contains(lastName))
        {
          membersByLastName.add ((IMember) list.get (i));
        }
  }
    System.out.println (membersByLastName);
    return membersByLastName;
  }

  
  
  @Override
  public List<IMember> findMembersByEmailAddress (String emailAddress)
  {
    List<IMember> membersByEmailAddress = new LinkedList();
    if(emailAddress == null || emailAddress.isEmpty())
    {
      throw new RuntimeException("This member does not exist!");
    }
    for(int i = 1; i < list.size(); i++)
    {
      String m = list.get(i).toString ();
        if(m.contains(emailAddress))
        {
          membersByEmailAddress.add ((IMember) list.get (i));
        }
  }
    System.out.println (membersByEmailAddress);
    return membersByEmailAddress;
  }

  
  
  @Override
  public List<IMember> findMembersByNames (String firstName, String lastName)
 {
    List<IMember> membersByName = new LinkedList();
    if(lastName == null || lastName.isEmpty() || firstName == null || 
       firstName.isEmpty ())
      
    {
      throw new RuntimeException("This member does not exist!");
    }
    for(int i = 1; i < list.size(); i++)
    {
      String m = list.get(i).toString ();
        if(m.contains(lastName) && m.contains (firstName))
        {
          membersByName.add ((IMember) list.get (i));
        }
  }
    System.out.println (membersByName);
    return membersByName;
  }
  
  
  private int getNextId()
  {
    return list.size ();
  }
  

}
