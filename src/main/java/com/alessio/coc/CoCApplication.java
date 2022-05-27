package com.alessio.coc;

import com.alessio.coc.models.Member;
import com.alessio.coc.models.War;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class CoCApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoCApplication.class, args);

		ClashOfClansAPI coc = new ClashOfClansAPI(Constants.getClanTag());
		ArrayList<Member> clanMembersInfo = File.readClanMembersInfoFromFile();
		ArrayList<War> wars = File.readWarInfoFromFile();

		fetchAndSaveWarInfo(coc);

		System.out.println(wars);
	}



	private static void incorporateNewWar(ArrayList<War> savedWars, War war) {
		boolean noWarMatched = true;

		for (int i = 0; i < savedWars.size(); i++) {
			System.out.println("one");
			if (savedWars.get(i).getPreparationStartTime().equals(war.getPreparationStartTime())) {
				System.out.println("it hit the spot! so now i'll replace the war");
				savedWars.set(i, war);
				noWarMatched = false;
			}
		}
		if (noWarMatched) {
			savedWars.add(war);
		}
	}

	private static ArrayList<Member> fetchAndSaveClanMembersInfo(ClashOfClansAPI coc) {
		coc.updateClanMembersInfo();
		File.saveClanMembersInfoToFile(coc.getClanMembersInfo());
		return coc.getClanMembersInfo();
	}

	private static War fetchAndSaveWarInfo(ClashOfClansAPI coc) {
		ArrayList<War> wars = File.readWarInfoFromFile();
		coc.updateWarInfo();
		incorporateNewWar(wars, coc.getWarInfo());
		File.saveWarInfoToFile(wars);
		return coc.getWarInfo();
	}

	private static void printMatrixList(ArrayList<Member> infos) {
		for (Member row: infos) {
			System.out.printf(row.toString() + "\n");
		}
	}

}
