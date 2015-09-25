package library.entities;

import java.util.ArrayList;
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
  private List<ILoan> totalLoans;
  private float fineAmount;
  private final int LOAN_LIMIT = 5;
  private final float FINE_LIMIT = 10.0f;

  
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
      totalLoans = new ArrayList();
      fineAmount = 0.0f;
      return;
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
  public boolean hasOverDueLoans()
  {
   for (int i = 0; i < totalLoans.size() -1; i++)
   {
	 if (totalLoans.iterator().hasNext())
      if (totalLoans.get(i).isOverDue())
       {
        return true;
       }
   }
    return false;
  }



  @Override
  public boolean hasReachedLoanLimit ()
  {
    if (totalLoans.size() >= LOAN_LIMIT)
      {
      return true;
      }
    return false;
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
    boolean limit = (fineAmount >= FINE_LIMIT);
    return limit;  
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
    updateState();
    }
  }



  @Override
  public void payFine (float payment)
  {
    if (payment <= 0.0f || payment > fineAmount)
    {
      throw new RuntimeException ("Payment amount must be greater than $0.00 and"
                                  + " less than" + FINE_LIMIT);
    }
    else
    {
    fineAmount -= payment;
    updateState();
    return;
    }
  }



  @Override
  public void addLoan (ILoan loan)
  {
    if (!borrowingAllowed())
    { 
      throw new RuntimeException("Member " + firstName + " " + lastName + " "
                                 + "cannot borrow any more books at this "
                                 + "time!");
    }
    else{
      totalLoans.add (loan);
      updateState();
    }
    }



  @Override
  public List<ILoan> getLoans ()
  {
    return totalLoans;
  }



  @Override
  public void removeLoan (ILoan loan)
  {
    if (totalLoans.contains (loan))
    {
      totalLoans.remove (loan);
      updateState();
    }
    else 
    {
      throw new RuntimeException("Loan is not in the listed loans for Member: " 
    		  					 + firstName + " " + lastName);
    }

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
  
  
  
  public String toString()
  {
    return String.format("Id: %d\nName: %s %s\nContact Phone: %s\nEmail: %s"
                         + "\nTotal Fines: %.2f", 
                         new Object[] {iD, firstName, lastName, contactNumber, 
                                       email, fineAmount});
  }
  
  
  
  private boolean borrowingAllowed()
  {
    if (hasReachedLoanLimit() || hasOverDueLoans() || hasReachedFineLimit())
    {
        return false;
    }
    else
    {
    	return true;
    }
  }
  
  
  private void updateState()
  {
    if (borrowingAllowed())
    {
      state = EMemberState.BORROWING_ALLOWED;
      return;
    }
    else 
    {
      state = EMemberState.BORROWING_DISALLOWED;
      return;
    }
  }

}
