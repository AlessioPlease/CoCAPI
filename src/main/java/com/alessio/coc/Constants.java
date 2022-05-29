package com.alessio.coc;

public final class Constants {

	private final String AUTHTOKEN;
	private final String CLANTAG;

	Constants() {
		AUTHTOKEN = "Bearer " + File.readDataFromFile("Token.txt");
		CLANTAG = File.readDataFromFile("ClanTag.txt");
	}

	public String getAuthToken() {
		return AUTHTOKEN;
	}

	public String getClanTag() {
		return CLANTAG;
	}

	public int getUpdateInterval() {
		return 2;		// MINUTES
	}
}