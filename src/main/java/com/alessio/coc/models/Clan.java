package com.alessio.coc.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Clan implements Serializable {

	private ArrayList<Member> members;

	public Clan(ArrayList<Member> members) {
		this.members = members;
	}

	public ArrayList<Member> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<Member> members) {
		this.members = members;
	}
}
