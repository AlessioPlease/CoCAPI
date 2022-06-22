package com.alessio.coc;

import com.alessio.coc.models.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Controller {

	private final ClashOfClansAPI api;

	/**
	 * Initializes the class' {@code ClashOfClansAPI} object
	 * with the {@code api} parameter passed as argument.
	 *
	 * @param api The {@code ClashOfClansAPI} object.
	 */
	public Controller(ClashOfClansAPI api) {
		this.api = api;
	}

	/**
	 * Updates information about clan members, checks if there are members
	 * who are not in the clan anymore from the last update
	 * and saves the past wars' performance of these members in a file.
	 * It then saves the information about clan members in another file.
	 *
	 * @return A {@code Clan} object containing information about the clan members.
	 */
	public Clan fetchAndSaveClanMembersInfo() {
		ClashOfClansAPI coc = this.api;
		coc.updateClanInfo();
		ArrayList<Member> membersWhoLeft = membersWhoLeft(coc.getClanInfo().getMembers());
		if (membersWhoLeft != null) {
			ArrayList<War> performanceOfMembersWhoLeft = getWarPerformanceOfTheseMembers(membersWhoLeft);
			File.addMembersWhoLeftToFile(performanceOfMembersWhoLeft);
		}
		File.saveClanInfoToFile(coc.getClanInfo());
		return coc.getClanInfo();
	}

	/**
	 * Updates information about the ongoing war.
	 * Checks if there are new members in the current war that are not present in che clan
	 * members information. If so it proceeds to {@code fetchAndSaveClanMembersInfo()}.
	 * It finally adds the current war to the wars already saved in the file.
	 *
	 * @return An {@code ArrayList<War>} object containing information about
	 *         the ongoing war.
	 */
	public ArrayList<War> fetchAndSaveWarInfo() {
		ClashOfClansAPI coc = this.api;
		ArrayList<War> wars = File.readWarsInfoFromFile();
		coc.updateWarInfo();
		if (areThereNewMembers(coc.getWarInfo().getMembers())) {
			fetchAndSaveClanMembersInfo();
		}
		incorporateNewWar(wars, coc.getWarInfo());
		File.saveWarsInfoToFile(wars);
		return wars;
	}

	/**
	 * Checks whether the single war is already present in the list of wars.
	 * If it is present, the single war data is overwritten, otherwise it is added
	 * at the end of the {@code savedWars} list.
	 *
	 * @param savedWars The {@code ArrayList<War>} object containing a list of war.
	 * @param war The single {@code War} object to search for in the {@code ArrayList<War>}
	 */
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

	/**
	 * Looks for the war performances, in all wars contained in the file,
	 * for each member passed as argument.
	 *
	 * @param membersWhoLeft The {@code ArrayList<Member>} object containing
	 *                       the list of members to return information of.
	 *
	 * @return An {@code ArrayList<War>} object containing war performance
	 *         information about all the members contained in the parameter.
	 */
	private ArrayList<War> getWarPerformanceOfTheseMembers(ArrayList<Member> membersWhoLeft) {
		ArrayList<War> performanceInfo = new ArrayList<>();
		ArrayList<War> wars = File.readWarsInfoFromFile();

		for (Member member: membersWhoLeft) {
			for (War war: wars) {
				for (WarMember warMember: war.getMembers()) {
					if (member.getTag().equals(warMember.getTag())) {
						performanceInfo.add(new War(
								war.getPreparationStartTime(),
								0,
								0,
								0,
								0,
								0d,
								new ArrayList<>(List.of(new WarMember(warMember.getName(), warMember.getTag(), warMember.getWarPosition(), warMember.getAttacks())))));
					}
				}
			}
		}
		if (performanceInfo.size() != 0) {
			return performanceInfo;
		} else {
			return null;
		}
	}

	/**
	 * Compares the clan members information saved in the file with the list
	 * of members passed as argument to check if there are members that
	 * are not in the clan anymore.
	 *
	 * @param newMembersInfo The {@code ArrayList<Member>} object containing
	 *                       the list of members to compare with the file.
	 *
	 * @return An {@code ArrayList<Member>} object containing the list of members
	 *         that are not in the clan anymore.
	 */
	private ArrayList<Member> membersWhoLeft(ArrayList<Member> newMembersInfo) {
		ArrayList<Member> membersWhoLeft = new ArrayList<>();
		HashSet<String> newMembersTags = new HashSet<>();
		HashSet<String> oldMembersTags = new HashSet<>();
		ArrayList<Member> oldMembersInfo = File.readClanInfoFromFile().getMembers();

		// Fill HashSets
		for (Member newMember: newMembersInfo) {
			newMembersTags.add(newMember.getTag());
		}
		for (Member oldMember: oldMembersInfo) {
			oldMembersTags.add(oldMember.getTag());
		}

		if (oldMembersTags.equals(newMembersTags)) {
			return null;
		} else {
			oldMembersTags.removeAll(newMembersTags);		// oldMembersTags now only contains members who are not in the clan anymore
			System.out.println("These members left or were kicked out:");
			for (Member oldMember: oldMembersInfo) {
				if (oldMembersTags.contains(oldMember.getTag())) {
					System.out.println(oldMember.getName());
					membersWhoLeft.add(oldMember);
				}
			}
			return membersWhoLeft;
		}
	}

	/**
	 * Compares the clan members information saved in the file with the list
	 * of members passed as argument to check if there are new members in the clan.
	 *
	 * @param newMembersInfo The {@code ArrayList<Member>} object containing
	 *                       the list of members to compare with the file.
	 *
	 * @return A {@code boolean} variable returning {@code true} if the
	 *         {@code ArrayList<WarMember>} object passed as argument
	 *         contains new members compared to the list of members in
	 *         the clan members information file.
	 */
	private boolean areThereNewMembers(ArrayList<WarMember> newMembersInfo) {
		ArrayList<Member> oldMembersInfo = File.readClanInfoFromFile().getMembers();
		HashSet<String> newMembersTags = new HashSet<>();
		HashSet<String> oldMembersTags = new HashSet<>();

		// Fill HashSets
		for (WarMember newMember: newMembersInfo) {
			newMembersTags.add(newMember.getTag());
		}
		for (Member oldMember: oldMembersInfo) {
			oldMembersTags.add(oldMember.getTag());
		}

		return !newMembersTags.equals(oldMembersTags);
	}

	/**
	 * Prints the {@code Clan} object passed as argument
	 * in an easily readable format.
	 *
	 * @param clanInfo The {@code Clan} object containing
	 *                 information about the clan members.
	 */
	public void printClanInfo(Clan clanInfo) {
		ArrayList<Member> membersInfo = clanInfo.getMembers();

		for (Member row: membersInfo) {
			System.out.printf(row.toString() + "\n");
		}
	}

	/**
	 * Prints the {@code ArrayList<War>} object passed as argument
	 * in an easily readable format.
	 *
	 * @param warsInfo The {@code ArrayList<Member>} object containing
	 *                 information about all saved wars.
	 */
	public void printWarsInfo(ArrayList<War> warsInfo) {

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
