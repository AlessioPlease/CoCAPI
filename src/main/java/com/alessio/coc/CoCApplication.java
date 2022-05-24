package com.alessio.coc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class CoCApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoCApplication.class, args);

		ClashOfClansAPI coc = new ClashOfClansAPI();
		printMatrixList(coc.getClanMembersInfo());

		//System.out.println(File.readFromFile("ClanMembersInfo.txt"));
	}

	private static void printMatrixList(ArrayList<Member> infos) {

		for (Member row: infos) {
			row.toString();
		}
	}

}
