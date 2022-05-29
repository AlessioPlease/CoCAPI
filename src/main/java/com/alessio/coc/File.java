package com.alessio.coc;

import com.alessio.coc.models.Member;
import com.alessio.coc.models.War;

import org.apache.commons.io.IOUtils;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class File {

	private File() {}

	public static void saveClanMembersInfoToFile(ArrayList<Member> data) {
		String fileName = "ClanMembersInfo.txt";
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(data);
			oos.close();
		} catch (IOException e) {
			System.out.println("Something went wrong while trying to save clan data to file");
			e.printStackTrace();
		}
	}

	public static void saveWarsInfoToFile(ArrayList<War> data) {
		String fileName = "WarInfo.txt";
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(data);
			oos.close();
		} catch (IOException e) {
			System.out.println("Something went wrong while trying to save war data to file");
			e.printStackTrace();
		}
	}

	public static ArrayList<Member> readClanMembersInfoFromFile() {
		String fileName = "ClanMembersInfo.txt";
		if (fileDoesNotExist(fileName)) {
			throw new NullPointerException("No file was found for " + fileName + ". It must be created first!");
		}
		try {
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);

			ArrayList<Member> data = (ArrayList<Member>) ois.readObject();
			ois.close();
			return data;
		} catch (IOException e) {
			System.out.println("Something went wrong while trying to read clan data from file (IOException)");
			e.printStackTrace();
			return new ArrayList<>();
		} catch (ClassNotFoundException e) {
			System.out.println("Something went wrong while trying to read clan data from file (ClassNotFoundException)");
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public static ArrayList<War> readWarsInfoFromFile() {
		String fileName = "WarInfo.txt";
		if (fileDoesNotExist(fileName)) {
			throw new NullPointerException("No file was found for " + fileName + ". It must be created first!");
		}
		try {
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);

			ArrayList<War> data = (ArrayList<War>) ois.readObject();
			ois.close();
			return data;
		} catch (IOException e) {
			System.out.println("Something went wrong while trying to read war data from file (IOException)");
			e.printStackTrace();
			return new ArrayList<>();
		} catch (ClassNotFoundException e) {
			System.out.println("Something went wrong while trying to read war data from file (ClassNotFoundException)");
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public static String readDataFromFile(String fileName) {

		try(FileInputStream inputStream = new FileInputStream(fileName)) {
			return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
		} catch (Exception e) {
			System.out.println("Something went wrong while trying to read data from file");
			e.printStackTrace();
			return "";
		}
	}

	private static boolean fileDoesNotExist(String fileName) {
		java.io.File file = new java.io.File(fileName);
		return !(file.exists() && !file.isDirectory());
	}
}
