package library.entities;

import java.util.ArrayList;
import java.util.Collections;
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
  private final int MAX_LOANS = 5;
  private final float MAX_FINES = 10.0f;

  
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
    if (loan.size() > MAX_LOANS)
      {
      return true;
      }
    else
    {
      return false;
    }
  }



  @Override
  public boolean hasFinesPayable ()
  {
    if (fineAmount > 0)
    {
      return true;
    }
    else
    {
      return false;
    }
  }



  @Override
  public boolean hasReachedFineLimit ()
  {
    if (hasFinesPayable() && fineAmount > MAX_FINES)
    {
      return true;
    }
    else 
    {
      return false;  
    }
  }



  @Override
  public float getFineAmount ()
  {
    return fineAmount;
  }



  @Override
  public void addFine (float fine)
  {
    if (fine <= 0.0f)
    {
      throw new RuntimeException ("Fine amount must not be less than $0.00");
    }
    else
    {
    fineAmount += fine;
    if (fineAmount > MAX_FINES)
    {
      state = EMemberState.BORROWING_DISALLOWED;
    }
    else
    {
      state = EMemberState.BORROWING_ALLOWED;
    }
    }
  }



  @Override
  public void payFine (float payment)
  {
    if (payment <= 0.0f || payment > fineAmount)
    {
      throw new RuntimeException ("Payment amount must be greater than $0.00 and"
                                  + " less than" + MAX_FINES);
    }
    else
    {
    fineAmount -= payment;
    if (fineAmount <= MAX_FINES)
    {
      state = EMemberState.BORROWING_ALLOWED;
    }
    else
    {
      state = EMemberState.BORROWING_DISALLOWED;
    }
    }
  }



  @Override
  public void addLoan (ILoan loan)
  {
    if (state == EMemberState.BORROWING_ALLOWED)
    { 
      if (this.loan.size() < MAX_LOANS)
      {
        this.loan.add(loan);
      }
      else
      {
        state = EMemberState.BORROWING_DISALLOWED;
        throw new RuntimeException("Member " + firstName + "" + lastName + " has"
                                   + " the maximum amount of loans!");
      }
    }
    else
    {
    throw new RuntimeException("Member " + firstName + "" + lastName + " "
                               + "cannot borrow any more books at this "
                               + "time!");
    }
    

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
