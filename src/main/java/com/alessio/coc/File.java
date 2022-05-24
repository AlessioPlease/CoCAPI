package com.alessio.coc;

import java.io.*;

public class File {

	private File() {}

	public static boolean saveToFile(String response, ResponseType responseType) {

		String fileName = "";
		switch (responseType) {
			case ClanMembersInfo:
				fileName = "ClanMembersInfo.txt";
				break;

			default:
				break;
		}

		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(response);
			oos.close();

			return true;
		} catch (IOException e) {
			System.out.println("Something went wrong while trying to save data to file");
			e.printStackTrace();
			return false;
		}
	}

	public static String readFromFile(String fileName) {

		try {
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);

			String data = (String) ois.readObject();
			ois.close();
			return data;
		} catch (IOException e) {
			System.out.println("Something went wrong while trying to read data from file (IOException)");
			e.printStackTrace();
			return "";
		} catch (ClassNotFoundException e) {
			System.out.println("Something went wrong while trying to read data from file (ClassNotFoundException)");
			e.printStackTrace();
			return "";
		}

	}
}
