package library.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
  private Date currentDate = new Date();

  
  public Member(int iD, String firstName, String lastName, String email, 
                String contactNumber)
  {
    if (sane(iD, firstName, lastName, email, contactNumber)) 
    {
      this.iD            = iD;
      this.firstName     = firstName;
      this.lastName      = lastName;
      this.email         = email;
      this.contactNumber = contactNumber;
      state = EMemberState.BORROWING_ALLOWED;
      loan = new ArrayList();
      fineAmount = 0.0f;
    }
  }
  
  
  
  private boolean sane(int iD, String firstName, String lastName, String email, 
      String contactNumber)
  {
    if (firstName == null || firstName.isEmpty())
    {
      throw new RuntimeException ("Member must have valid first name");
    }
    else if  (lastName == null || lastName.isEmpty())
    {
      throw new RuntimeException ("Member must have valid last name");
    }
    else if (email == null || email.isEmpty())
    {
      throw new RuntimeException ("Member must have valid email address");
    }
    else if (contactNumber == null || contactNumber.isEmpty())
    {
      throw new RuntimeException ("Member must have valid contact number");
    }
    else if (iD <= 0)
    {
      throw new RuntimeException ("Member must have valid ID") ;
    }
    else 
    {
      return true;
    }
  }
  
  
  
  @Override
  public boolean hasOverDueLoans ()
  {
    for (int i = 0; i < loan.size (); i++)
    {
      if (loan.iterator().hasNext())
      {
        if (loan.iterator().next().checkOverDue (currentDate))
        {          
        boolean isOverDue = loan.iterator().next().isOverDue();
          if (isOverDue)
          {
            return true;
          }
          else 
          {
            return false;
          }
        }
        else 
        {
          return false;
        }
      }
    }
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
