package library.dao;

import java.util.List;

import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.IMember;

public class MemberMapDAO implements IMemberDAO
{

  @Override
  public IMember addMember (String firstName, String lastName,
      String ContactPhone, String emailAddress)
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public IMember getMemberByID (int id)
  {
    // TODO Auto-generated method stub
    return null;
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

}
