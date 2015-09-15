package library.entities;

import java.util.List;

import library.interfaces.entities.EMemberState;
import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;

public class Member implements IMember
{
  
  private int iD;
  private String firstName;
  private String lastName;
  private String email;
  private String contactNumber;
  private EMemberState state;
  private List<ILoan> loan;
  private float fineAmount;

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
    return fineAmount;
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
    return loan;
  }



  @Override
  public void removeLoan (ILoan loan)
  {
    // TODO Auto-generated method stub

  }



  @Override
  public EMemberState getState ()
  {
    return state;
  }



  @Override
  public String getFirstName ()
  {
    return firstName;
  }



  @Override
  public String getLastName ()
  {
    return lastName;
  }



  @Override
  public String getContactPhone ()
  {
    return contactNumber;
  }



  @Override
  public String getEmailAddress ()
  {
    return email;
  }



  @Override
  public int getID ()
  {
    return iD;
  }

}
