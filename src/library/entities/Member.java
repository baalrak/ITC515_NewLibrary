package library.entities;

import java.util.List;

import library.interfaces.entities.EMemberState;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class Member implements IMember
{

  @Override
  public boolean hasOverDueLoans ()
  {
    // TODO Auto-generated method stub
    return false;
  }



  @Override
  public boolean hasReachedLoanLimit ()
  {
    // TODO Auto-generated method stub
    return false;
  }



  @Override
  public boolean hasFinesPayable ()
  {
    // TODO Auto-generated method stub
    return false;
  }



  @Override
  public boolean hasReachedFineLimit ()
  {
    // TODO Auto-generated method stub
    return false;
  }



  @Override
  public float getFineAmount ()
  {
    // TODO Auto-generated method stub
    return 0;
  }



  @Override
  public void addFine (float fine)
  {
    // TODO Auto-generated method stub

  }



  @Override
  public void payFine (float payment)
  {
    // TODO Auto-generated method stub

  }



  @Override
  public void addLoan (ILoan loan)
  {
    // TODO Auto-generated method stub

  }



  @Override
  public List<ILoan> getLoans ()
  {
    // TODO Auto-generated method stub
    return null;
  }



  @Override
  public void removeLoan (ILoan loan)
  {
    // TODO Auto-generated method stub

  }



  @Override
  public EMemberState getState ()
  {
    // TODO Auto-generated method stub
    return null;
  }



  @Override
  public String getFirstName ()
  {
    // TODO Auto-generated method stub
    return null;
  }



  @Override
  public String getLastName ()
  {
    // TODO Auto-generated method stub
    return null;
  }



  @Override
  public String getContactPhone ()
  {
    // TODO Auto-generated method stub
    return null;
  }



  @Override
  public String getEmailAddress ()
  {
    // TODO Auto-generated method stub
    return null;
  }



  @Override
  public int getID ()
  {
    // TODO Auto-generated method stub
    return 0;
  }

}
