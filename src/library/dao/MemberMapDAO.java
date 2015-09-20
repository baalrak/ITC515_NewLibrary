package library.dao;

import java.util.LinkedList;
import java.util.List;

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
      System.out.println((IMember)list.get(id));
      return (IMember)list.get(id);
    }
  }

  
  
  @Override
  public List<IMember> listMembers ()
  {
    // TODO Auto-generated method stub
    return null;
  }

  
  
  @Override
  public List<IMember> findMembersByLastName (String lastName)
  {
    // TODO Auto-generated method stub
    return null;
  }

  
  
  @Override
  public List<IMember> findMembersByEmailAddress (String emailAddress)
  {
    // TODO Auto-generated method stub
    return null;
  }

  
  
  @Override
  public List<IMember> findMembersByNames (String firstName, String lastName)
  {
    // TODO Auto-generated method stub
    return null;
  }
  
  
  
  private int getNextId()
  {
  return iD++;
  }
  

}
