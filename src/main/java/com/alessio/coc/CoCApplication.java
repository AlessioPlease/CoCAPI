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

		ClashOfClansAPI coc = new ClashOfClansAPI();
//		coc.updateClanMembersInfo();
//		File.saveClanMembersInfoToFile(coc.getClanMembersInfo());
//		printMatrixList(File.readClanMembersInfoFromFile());
		ArrayList<War> wars = File.readWarInfoFromFile();
		if (wars != null) {
			System.out.println(File.readWarInfoFromFile().toString());
		} else {
			System.out.println("File was empty?");
		}


//		printMatrixList(coc.getClanMembersInfo());
//		System.out.println(coc.getWarInfo().toString());
	}



	private static void printMatrixList(ArrayList<Member> infos) {
		for (Member row: infos) {
			System.out.printf(row.toString() + "\n");
		}
	}

}
