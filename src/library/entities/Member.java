package library.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import library.interfaces.entities.ILoan;
import library.interfaces.entities.IMember;
import library.interfaces.entities.EMemberState;

public class Member implements IMember {

	private final String firstName;
	private final String lastName;
	private final String contactPhone;
	private final String emailAddress;
	private final int id;
	
	private EMemberState state;
	private List<ILoan> loanList;
	private float totalFines;
	
	public Member(String firstName, String lastName, String contactPhone,
			String email, int memberID) {
		if ( !sane(firstName, lastName, contactPhone, email, memberID)) {
			throw new IllegalArgumentException("Member: constructor : bad parameters");
		}
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactPhone = contactPhone;
		this.emailAddress = email;
		this.id = memberID;
		this.loanList = new ArrayList<ILoan>();
		this.totalFines = 0.0f;
		this.state = EMemberState.BORROWING_ALLOWED;
	}

	
	private boolean sane(String firstName, String lastName, String contactPhone,
			String emailAddress, int memberID) {
		return  ( firstName != null    && !firstName.isEmpty()    &&
				  lastName != null     && !lastName.isEmpty()     &&
				  contactPhone != null && !contactPhone.isEmpty() &&
				  emailAddress != null && !emailAddress.isEmpty() &&
				  memberID > 0 
				);
	}


	@Override
	public boolean hasOverDueLoans() {
		for (ILoan loan : loanList) {
			if (loan.isOverDue()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasReachedLoanLimit() {
		boolean b = loanList.size() >= IMember.LOAN_LIMIT;
		return b;
	}

	@Override
	public boolean hasFinesPayable() {
		boolean b = totalFines > 0.0f;
		return b;
	}

	@Override
	public boolean hasReachedFineLimit() {
		boolean b = totalFines >= IMember.FINE_LIMIT;
		return b;
	}

	@Override
	public float getFineAmount() {
		return totalFines;
	}

	@Override
	public void addFine(float fine) {
		if (fine < 0) {
			throw new RuntimeException(String.format("Member: addFine : fine cannot be negative"));
		}
		totalFines += fine;
		updateState();
	}

	@Override
	public void payFine(float payment) {
		if (payment < 0 || payment > totalFines) {
			throw new RuntimeException(String.format("Member: addFine : payment cannot be negative or greater than totalFines"));
		}
		totalFines -= payment;
		updateState();
	}

	@Override
	public void addLoan(ILoan loan) {
		if (!borrowingAllowed()) {
			throw new RuntimeException(String.format("Member: addLoan : illegal operation in state: %s", state));
		}
		loanList.add(loan);
		updateState();
	}

	@Override
	public List<ILoan> getLoans() {
		return Collections.unmodifiableList(loanList);
	}

	@Override
	public void removeLoan(ILoan loan) {
		if (loan == null || !loanList.contains(loan)) {
			throw new RuntimeException(String.format("Member: removeLoan : loan is null or not found in loanList"));
		}
		loanList.remove(loan);
		updateState();
	}

	
	@Override
	public EMemberState getState() {
		return state;
	}

	
	@Override
	public String getFirstName() {
		return firstName;
	}

	
	@Override
	public String getLastName() {
		return lastName;
	}

	
	@Override
	public String getContactPhone() {
		return contactPhone;
	}

	
	@Override
	public String getEmailAddress() {
		return emailAddress;
	}

	
	@Override
	public int getID() {
		return id;
	}

	
	@Override
	public String toString() {
		return String.format(
				"Id: %d\nName: %s %s\nContact Phone: %s\nEmail: %s\nOutstanding Charges: %0.2f", id,
				firstName, lastName, contactPhone, emailAddress, totalFines);
	}

	private Boolean borrowingAllowed() {
		boolean b = !hasOverDueLoans() &&
				!hasReachedFineLimit() &&
				!hasReachedLoanLimit();
		return b;
	}

	private void updateState() {
		if (borrowingAllowed()) {
			state = EMemberState.BORROWING_ALLOWED;
		}
		else {
			state = EMemberState.BORROWING_DISALLOWED;
		}
	}


}
