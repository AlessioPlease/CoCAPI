package com.alessio.coc;

import com.alessio.coc.models.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

//		###################################	REMEMBER TO UPDATE THE TOKEN	###################################
//		https://developer.clashofclans.com/#/account

public class ClashOfClansAPI {

	private final Constants constants;
	private Clan clanInfo;
	private War warInfo;
	private Instant lastClanInfoUpdate = Instant.parse("2000-01-01T10:15:30.00Z");
	private Instant warInfoUpdate = Instant.parse("2000-01-01T10:15:30.00Z");

	/**
	 * Initializes the class' {@code constants} object
	 * with the constants parameter passed as argument.
	 *
	 * @param constants Constants object.
	 */
	public ClashOfClansAPI(Constants constants) {
		this.constants = constants;
	}

	/**
	 * Builds and executes an HTTP GET request asking for general information
	 * about the clan and its members.
	 *
	 * @return A {@code String} object containing the response
	 *         of the request in JSON format.
	 */
	private String requestClanInfo() {
		String url = "https://api.clashofclans.com/v1/clans/%23" + constants.getClanTag() + "/members";
		String method = "GET";

		return getResponse(Objects.requireNonNull(buildRequest(url, method)));
	}

	/**
	 * Extracts general information about clan members from the {@code String} passed as argument.
	 * It also calls {@code extractOtherInfo(requestOtherInfo())} to get
	 * additional information about each clan member.
	 * It puts all the data received in a {@code Clan} object.
	 *
	 * @param response The {@code String} object containing the response
	 *                 of the request in JSON format.
	 *
	 * @return A {@code Clan} object containing information
	 *         about the clan members.
	 */
	private Clan extractClanInfo(String response) {
		ArrayList<Member> members = new ArrayList<>();
		JSONObject json = new JSONObject(response);
		JSONArray items = json.getJSONArray("items");

		for (int i = 0; i < items.length(); i++) {
			HashMap<String, String> otherInfo = extractOtherInfo(requestOtherInfo(items.getJSONObject(i).getString("tag")));

			Member member = new Member(
					items.getJSONObject(i).getString("name"),
					items.getJSONObject(i).getInt("donations"),
					items.getJSONObject(i).getInt("donationsReceived"),
					Integer.valueOf(otherInfo.get("attackWins")),
					Integer.valueOf(otherInfo.get("townHallLevel")),
					otherInfo.get("warPreference"),
					items.getJSONObject(i).getString("role"),
					items.getJSONObject(i).getInt("expLevel"),
					items.getJSONObject(i).getInt("trophies"),
					Integer.valueOf(otherInfo.get("warStars")),
					items.getJSONObject(i).getInt("clanRank"),
					items.getJSONObject(i).getString("tag")
			);
			members.add(member);
		}
		return new Clan(members);
	}

	/**
	 * Builds and executes an HTTP GET request asking for additional information
	 * about one clan member.
	 *
	 * @param dirtyTag The tag of the player to get information of.
	 *                 This tag still contains the hashtag.
	 *
	 * @return A {@code String} object containing the response
	 *         of the request in JSON format.
	 */
	private String requestOtherInfo(String dirtyTag) {
		String tag = dirtyTag.replace("#", "");
		String url = "https://api.clashofclans.com/v1/players/%23" + tag;
		String method = "GET";

		return getResponse(Objects.requireNonNull(buildRequest(url, method)));
	}

	/**
	 * Extracts additional information about a clan member from the response
	 * passed as argument and puts it in an {@code HashMap<String, String>} object.
	 *
	 * @param response The {@code String} object containing the response
	 *                 of the request in JSON format.
	 *
	 * @return An {@code HashMap<String, String>} object containing
	 *         the additional information about the clan member.
	 */
	private HashMap<String, String> extractOtherInfo(String response) {
		HashMap<String, String> memberInfo = new HashMap<>();
		JSONObject json = new JSONObject(response);

		memberInfo.put("townHallLevel", Integer.toString(json.getInt("townHallLevel")));
		memberInfo.put("attackWins", Integer.toString(json.getInt("attackWins")));
		memberInfo.put("warPreference", json.getString("warPreference"));
		memberInfo.put("warStars", Integer.toString(json.getInt("warStars")));

		return memberInfo;
	}

	/**
	 * Builds and executes an HTTP GET request asking for information
	 * about the ongoing war.
	 *
	 * @return A {@code String} object containing the response
	 *         of the request in JSON format.
	 */
	private String requestWarInfo() {
		String url = "https://api.clashofclans.com/v1/clans/%23" + constants.getClanTag() + "/currentwar";
		String method = "GET";

		return getResponse(Objects.requireNonNull(buildRequest(url, method)));
	}

	/**
	 * Extracts information about the ongoing war from the {@code String}
	 * passed as argument and puts it in a {@code War} object.
	 *
	 * @param response The {@code String} object containing the response
	 *                 of the request in JSON format.
	 *
	 * @return A {@code War} object containing information
	 *         about the ongoing war, that is:
	 *         - general information about the war
	 *         - performance of each member in war
	 */
	private War extractWarInfo(String response) {
		JSONObject json = new JSONObject(response);
		JSONObject ourClan = json.getJSONObject("clan");
		JSONArray ourClanMembers = ourClan.getJSONArray("members");
		JSONObject opponentClan = json.getJSONObject("opponent");
		JSONArray opponentClanMembers = opponentClan.getJSONArray("members");
		HashMap<String, Integer> mappedOpponents = mapOpponents(opponentClanMembers);

		ArrayList<WarMember> members = new ArrayList<>();

		for (int i = 0; i < ourClanMembers.length(); i++) {
			JSONObject rawMember = ourClanMembers.getJSONObject(i);

			JSONArray rawAttacks = null;
			if (rawMember.has("attacks")) {
				rawAttacks = rawMember.getJSONArray("attacks");
			}
			ArrayList<Attack> attacks = null;
			if (rawAttacks != null) {
				attacks = extractAttacks(rawAttacks, mappedOpponents);
			}

			WarMember member = new WarMember(
					ourClanMembers.getJSONObject(i).getString("name"),
					ourClanMembers.getJSONObject(i).getString("tag"),
					ourClanMembers.getJSONObject(i).getInt("mapPosition"),
					attacks);

			members.add(member);
		}

		return new War(
				json.getString("preparationStartTime"),
				json.getInt("teamSize"),
				ourClan.getInt("attacks"),
				ourClan.getInt("stars"),
				opponentClan.getInt("stars"),
				ourClan.getDouble("destructionPercentage"),
				members);
	}

	/**
	 * Extracts information about the attacks of a single player.
	 * The information is contained in the {@code JSONArray rawAttacks} parameter.
	 *
	 * @param rawAttacks The {@code JSONArray} object containing an array of
	 *                   attacks in JSON format.
	 * @param opponentsPositions The {@code HashMap<String, String>} object
	 *                           mapping opponents' tags with their position in war.
	 *
	 * @return An {@code ArrayList<Attack>} object containing information
	 *         about the attacks of a single war member.
	 */
	private ArrayList<Attack> extractAttacks(JSONArray rawAttacks, HashMap<String, Integer> opponentsPositions) {
		ArrayList<Attack> attacks = new ArrayList<>();

		for (int i = 0; i < rawAttacks.length(); i++) {
			JSONObject rawAttack = rawAttacks.getJSONObject(i);

			Attack attack = new Attack(
					rawAttack.getInt("order"),
					opponentsPositions.get(rawAttack.getString("defenderTag")),
					rawAttack.getInt("stars"),
					rawAttack.getInt("destructionPercentage"));
			attacks.add(attack);
		}
		return attacks.size() > 1 ? orderAttacks(attacks) : attacks;
	}

	/**
	 * Orders attacks of a member by the {@code Attack} object's parameter {@code getOrder}.
	 *
	 * @param attacks An {@code ArrayList<Attack>} object containing
	 *                a list of attacks.
	 *
	 * @return An {@code ArrayList<Attack>} object containing the ordered
	 *         list of attacks.
	 */
	private ArrayList<Attack> orderAttacks(ArrayList<Attack> attacks) {
		attacks.sort(Comparator.comparing(Attack::getOrder));
		return attacks;
	}

	/**
	 * Extracts information about opponents from the parameter and maps each
	 * opponent tag with their position in war.
	 *
	 * @param opponentMembers An {@code JSONArray} object containing
	 *                        an array of opponents in JSON format.
	 *
	 * @return An {@code HashMap<String, Integer>} object mapping
	 *         opponents' tags with their position in war.
	 */
	private HashMap<String, Integer> mapOpponents(JSONArray opponentMembers) {
		HashMap<String, Integer> opponentsInfo = new HashMap<>();

		for (int i = 0; i < opponentMembers.length(); i++) {
			JSONObject opponent = opponentMembers.getJSONObject(i);
			opponentsInfo.put(opponent.getString("tag"), opponent.getInt("mapPosition"));
		}
		return opponentsInfo;
	}

	/**
	 * Builds an HTTP request with the URL and method passed as parameters.
	 *
	 * @param urlString A {@code String} object containing
	 *                  the URL to use for the request.
	 * @param method A {@code String} object containing
	 *               the method to use for the response.
	 *
	 * @return An {@code HttpURLConnection} object with the
	 *         specified parameters.
	 */
	private HttpURLConnection buildRequest(String urlString, String method) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection req = (HttpURLConnection) url.openConnection();
			req.setRequestMethod(method);
			req.setRequestProperty("Authorization", constants.getAuthToken());
			return req;
		} catch (IOException e) {
			System.out.println("Something went wrong while building the request");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Executes the request received as parameter and reads its input stream.
	 * It then puts the content in a {@code String} object.
	 *
	 * @param request An {@code HttpURLConnection} object containing
	 *                the request.
	 *
	 * @return A {@code String} object containing the response in a JSON format.
	 */
	private String getResponse(HttpURLConnection request) {
		StringBuilder content = new StringBuilder();
		try {
			BufferedReader buffer = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String inputLine;

			while ((inputLine = buffer.readLine()) != null) {
				content.append(inputLine);
			}
			buffer.close();
		} catch (IOException e) {
			System.out.println("Something went wrong while reading the response");
			e.printStackTrace();
			return "";
		}
		return content.toString();
	}

	/**
	 * Updates the clan information after making sure that
	 * the last update is not too recent.
	 * It then resets the last update time.
	 */
	public void updateClanInfo() {
		if (updateIsOld(lastClanInfoUpdate)) {
			this.clanInfo = extractClanInfo(requestClanInfo());
			lastClanInfoUpdate = Instant.now();
		}
	}

	/**
	 * Updates the war information after making sure that
	 * the last update is not too recent.
	 * It then resets the last update time.
	 */
	public void updateWarInfo() {
		if (updateIsOld(warInfoUpdate)) {
			this.warInfo = extractWarInfo(requestWarInfo());
			warInfoUpdate = Instant.now();
		}
	}

	/**
	 * Compares the {@code Instant date} received as parameter with the current time
	 * and checks if it is older than {@code constants.getUpdateInterval()}.
	 *
	 * @param date The time of the last update.
	 *
	 * @return The result of the comparison in a {@code boolean} variable.
	 */
	private boolean updateIsOld(Instant date) {
		Duration timeElapsed = Duration.between(date, Instant.now());

		return timeElapsed.toMinutes() > constants.getUpdateInterval();
	}

	public Clan getClanInfo() {
		return this.clanInfo;
	}

	public War getWarInfo() {
		return warInfo;
	}
}
