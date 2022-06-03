package com.alessio.coc;

import com.alessio.coc.models.*;

import java.util.ArrayList;
import java.util.HashSet;

public class GUI {

	private final Constants constants;

	public GUI(Constants constants) {
		this.constants = constants;

		ClashOfClansAPI coc = new ClashOfClansAPI(constants);
		Clan clanInfo = File.readClanInfoFromFile();
		ArrayList<War> wars = File.readWarsInfoFromFile();

		clanInfo = fetchAndSaveClanMembersInfo(coc);
//		wars = fetchAndSaveWarInfo(coc);

		//printClanInfo(clanInfo);
	}



	private void incorporateNewWar(ArrayList<War> savedWars, War war) {
		boolean noWarMatched = true;

		for (int i = 0; i < savedWars.size(); i++) {
			if (savedWars.get(i).getPreparationStartTime().equals(war.getPreparationStartTime())) {
				savedWars.set(i, war);
				noWarMatched = false;
			}
		}
		if (noWarMatched) {
			savedWars.add(war);
		}
	}

	private Clan fetchAndSaveClanMembersInfo(ClashOfClansAPI coc) {
		coc.updateClanInfo();
		ArrayList<Member> membersWhoLeft = membersWhoLeft(coc.getClanInfo().getMembers());
		if (membersWhoLeft != null) {
			File.saveMembersWhoLeftToFile(membersWhoLeft);
		}
		//File.saveClanInfoToFile(coc.getClanInfo());
		return coc.getClanInfo();
	}

	private ArrayList<War> fetchAndSaveWarInfo(ClashOfClansAPI coc) {
		ArrayList<War> wars = File.readWarsInfoFromFile();
		coc.updateWarInfo();
		if (areThereNewMembers()) {
			 fetchAndSaveClanMembersInfo(coc);
		}
		incorporateNewWar(wars, coc.getWarInfo());
		File.saveWarsInfoToFile(wars);
		return wars;
	}

	private ArrayList<Member> membersWhoLeft(ArrayList<Member> newMembersInfo) {
		ArrayList<Member> membersWhoLeft = new ArrayList<>();
		HashSet<String> newMembersTags = new HashSet<>();
		HashSet<String> oldMembersTags = new HashSet<>();
		Clan clanInfo = File.readClanInfoFromFile();

		for (Member newMember: newMembersInfo) {
			newMembersTags.add(newMember.getTag());
		}
		for (Member oldMember: clanInfo.getMembers()) {
			oldMembersTags.add(oldMember.getTag());
		}

		if (oldMembersTags.equals(newMembersTags)) {
			return null;
		} else {
			oldMembersTags.removeAll(newMembersTags);		// oldMembersTags now only contains members who are not in the clan anymore
			for (Member oldMember: clanInfo.getMembers()) {
				if (oldMembersTags.contains(oldMember.getTag())) {
					System.out.println(oldMember);
					membersWhoLeft.add(oldMember);
				}
			}
			return membersWhoLeft;
		}
	}

	private Boolean areThereNewMembers() {
		return true;
	}

	private void printClanInfo(Clan clanInfo) {
		ArrayList<Member> membersInfo = clanInfo.getMembers();

		for (Member row: membersInfo) {
			System.out.printf(row.toString() + "\n");
		}
	}

	private void printWarsInfo(ArrayList<War> warsInfo) {

		for (War warInfo: warsInfo) {
			System.out.println(warInfo.toString());
			System.out.println("##### ATTACCHI #####");
			for (WarMember warMember: warInfo.getMembers()) {
				System.out.printf(warMember.toString() + " ");
				if (warMember.getAttacks() != null) {
					for (Attack attack: warMember.getAttacks()) {
						System.out.printf(attack.toString());
					}
				}
				System.out.println();
			}
		}
	}
}
