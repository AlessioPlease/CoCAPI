package com.alessio.coc.models;

import java.io.Serializable;
import java.util.ArrayList;

public class WarMember implements Serializable {

	private String name;
	private String tag;
	private Integer warPosition;
	private ArrayList<Attack> attacks;

	public WarMember(String name, String tag, Integer warPosition, ArrayList<Attack> attacks) {
		this.name = name;
		this.tag = tag;
		this.warPosition = warPosition;
		this.attacks = attacks;
	}

	public String getName() {
		return name;
	}

	public String getTag() {
		return tag;
	}

	public Integer getWarPosition() {
		return warPosition;
	}

	public ArrayList<Attack> getAttacks() {
		return attacks;
	}

	@Override
	public String toString() {
		String attacksToString = attacks != null ? "\t" + attacks : "";

		return "\n" + name + "\t\t" +
				tag + "\t" +
				warPosition +
				attacksToString;
	}
}
