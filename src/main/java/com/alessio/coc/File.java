package com.alessio.coc;

import com.alessio.coc.models.DataType;
import com.alessio.coc.models.Member;

import java.io.*;
import java.util.ArrayList;

public class File {

	private File() {}

	public static boolean saveToFile(ArrayList<Member> data, DataType dataType) {

		String fileName = "";
		switch (dataType) {
			case ClanMembersInfo:
				fileName = "ClanMembersInfo.txt";
				break;

			default:
				break;
		}

		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(data);
			oos.close();

			return true;
		} catch (IOException e) {
			System.out.println("Something went wrong while trying to save data to file");
			e.printStackTrace();
			return false;
		}
	}

	public static ArrayList<Member> readFromFile(String fileName) {

		try {
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);

			ArrayList<Member> data = (ArrayList<Member>) ois.readObject();
			ois.close();
			return data;
		} catch (IOException e) {
			System.out.println("Something went wrong while trying to read data from file (IOException)");
			e.printStackTrace();
			return new ArrayList<>();
		} catch (ClassNotFoundException e) {
			System.out.println("Something went wrong while trying to read data from file (ClassNotFoundException)");
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}
