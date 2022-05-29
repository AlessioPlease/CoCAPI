package com.alessio.coc;

import com.alessio.coc.models.Clan;
import com.alessio.coc.models.Member;
import com.alessio.coc.models.War;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;

@SpringBootApplication
public class CoCApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoCApplication.class, args);

		ClashOfClansAPI coc = new ClashOfClansAPI(new Constants());
		Clan clanInfo = File.readClanInfoFromFile();
		ArrayList<War> wars = File.readWarsInfoFromFile();

		clanInfo = fetchAndSaveClanMembersInfo(coc);
		wars = fetchAndSaveWarInfo(coc);

		System.out.println(wars);
	}

	private static void incorporateNewWar(ArrayList<War> savedWars, War war) {
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

	private static Clan fetchAndSaveClanMembersInfo(ClashOfClansAPI coc) {
		coc.updateClanInfo();
		File.saveClanInfoToFile(coc.getClanInfo());
		return coc.getClanInfo();
	}

	private static ArrayList<War> fetchAndSaveWarInfo(ClashOfClansAPI coc) {
		ArrayList<War> wars = File.readWarsInfoFromFile();
		coc.updateWarInfo();
		incorporateNewWar(wars, coc.getWarInfo());
		File.saveWarsInfoToFile(wars);
		return wars;
	}

	private static void printClanInfo(Clan clanInfo) {
		ArrayList<Member> membersInfo = clanInfo.getMembers();

		for (Member row: membersInfo) {
			System.out.printf(row.toString() + "\n");
		}
	}

	private static void printWarsInfo(ArrayList<War> warsInfo) {

		for (War row: warsInfo) {

		}
	}
}
