package library.daos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import library.interfaces.daos.IMemberDAO;
import library.interfaces.entities.IMember;
import library.interfaces.daos.IMemberHelper;

public class MemberMapDAO implements IMemberDAO {

	private IMemberHelper helper;
	private Map<Integer, IMember> memberMap;
	private int nextID;
	
	public MemberMapDAO(IMemberHelper helper) {
		if (helper == null ) {
			throw new IllegalArgumentException(
				String.format("MemberMapDAO : constructor : helper cannot be null."));
		}
		this.helper = helper;
		this.memberMap = new HashMap<Integer, IMember>();
		this.nextID = 1;
	}

	public MemberMapDAO(IMemberHelper helper, Map<Integer,IMember> memberMap) {
		this(helper);
		if (memberMap == null ) {
			throw new IllegalArgumentException(
				String.format("MemberMapDAO : constructor : memberMap cannot be null."));
		}
		this.memberMap = memberMap;
	}

	
	@Override
	public IMember addMember(String firstName, String lastName,
			String contactPhone, String emailAddress) {
		int id = getNextId();
		IMember mem = helper.makeMember(firstName, lastName, contactPhone, emailAddress, id);
		memberMap.put(Integer.valueOf(id), mem);
		return mem;
	}

	@Override
	public IMember getMemberByID(int id) {
		if (memberMap.keySet().contains(Integer.valueOf(id))) {
			return memberMap.get(Integer.valueOf(id));
		}
		return null;
	}

	@Override
	public List<IMember> listMembers() {
		List<IMember> list = new ArrayList<IMember>(memberMap.values());
		return Collections.unmodifiableList(list);
	}

	@Override
	public List<IMember> findMembersByLastName(String lastName) {
		if ( lastName == null || lastName.isEmpty()) {
			throw new IllegalArgumentException(
				String.format("MemberMapDAO : findMembersByLastName : lastName cannot be null or blank"));
		}
		List<IMember> list = new ArrayList<IMember>();
		for (IMember m : memberMap.values()) {
			if (lastName.equals(m.getLastName())) {
				list.add(m);
			}
		}
		return Collections.unmodifiableList(list);
	}

	@Override
	public List<IMember> findMembersByEmailAddress(String emailAddress) {
		if ( emailAddress == null || emailAddress.isEmpty()) {
			throw new IllegalArgumentException(
				String.format("MemberMapDAO : findMembersByEmailAddress : emailAddress cannot be null or blank"));
		}
		List<IMember> list = new ArrayList<IMember>();
		for (IMember m : memberMap.values()) {
			if (emailAddress.equals(m.getEmailAddress())) {
				list.add(m);
			}
		}
		return Collections.unmodifiableList(list);
	}

	@Override
	public List<IMember> findMembersByNames(String firstName, String lastName) {
		if ( firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
			throw new IllegalArgumentException(
				String.format("MemberMapDAO : findMembersByNames : firstName and lastName cannot be null or blank"));
		}
		List<IMember> list = new ArrayList<IMember>();
		for (IMember m : memberMap.values()) {
			if (firstName.equals(m.getFirstName()) && lastName.equals(m.getLastName())) {
				list.add(m);
			}
		}
		return Collections.unmodifiableList(list);
	}

	private int getNextId() {
		return nextID++;
	}


}
