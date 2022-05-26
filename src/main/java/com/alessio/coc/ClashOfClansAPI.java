package com.alessio.coc;

import com.alessio.coc.models.Attack;
import com.alessio.coc.models.Member;
import com.alessio.coc.models.War;
import com.alessio.coc.models.WarMember;
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

	private ArrayList<Member> clanMembersInfo;
	private War warInfo;
	private Instant lastClanMembersUpdate = Instant.parse("2000-01-01T10:15:30.00Z");
	private Instant warInfoUpdate = Instant.parse("2000-01-01T10:15:30.00Z");


	public ClashOfClansAPI() {
//		updateClanMembersInfo();
//		updateWarInfo();
	}

	private String requestClanMembersInfo() {
		String url = "https://api.clashofclans.com/v1/clans/%23" + Constants.getClanTag() + "/members";
		String method = "GET";

		return getResponse(Objects.requireNonNull(buildRequest(url, method)));
	}

	public ArrayList<Member> extractClanMembersInfo(String response) {
		ArrayList<Member> membersInfo = new ArrayList<>();
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
			membersInfo.add(member);
		}
		return membersInfo;
	}

	private String requestOtherInfo(String dirtyTag) {
		String tag = dirtyTag.replace("#", "");
		String url = "https://api.clashofclans.com/v1/players/%23" + tag;
		String method = "GET";

		return getResponse(Objects.requireNonNull(buildRequest(url, method)));
	}

	private HashMap<String, String> extractOtherInfo(String response) {
		HashMap<String, String> memberInfo = new HashMap<>();
		JSONObject json = new JSONObject(response);

		memberInfo.put("townHallLevel", Integer.toString(json.getInt("townHallLevel")));
		memberInfo.put("attackWins", Integer.toString(json.getInt("attackWins")));
		memberInfo.put("warPreference", json.getString("warPreference"));
		memberInfo.put("warStars", Integer.toString(json.getInt("warStars")));

		return memberInfo;
	}

	private String requestWarInfo() {
		String url = "https://api.clashofclans.com/v1/clans/%23" + Constants.getClanTag() + "/currentwar";
		String method = "GET";

		return getResponse(Objects.requireNonNull(buildRequest(url, method)));
	}

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

	private ArrayList<Attack> extractAttacks(JSONArray rawAttacks, HashMap<String, Integer> opponentsPositions) {
		ArrayList<Attack> attacks = new ArrayList<>();

		for (int i = 0; i < rawAttacks.length(); i++) {
			JSONObject rawAttack = rawAttacks.getJSONObject(i);

			Attack attack = new Attack(
					rawAttack.getInt("order"),
					opponentsPositions.get(rawAttack.getString("defenderTag")),
					rawAttack.getInt("stars"),
					rawAttack.getDouble("destructionPercentage"));
			attacks.add(attack);
		}
		return attacks.size() > 1 ? orderAttacks(attacks) : attacks;
	}

	private ArrayList<Attack> orderAttacks(ArrayList<Attack> attacks) {
		attacks.sort(Comparator.comparing(Attack::getOrder));
		return attacks;
	}

	private HashMap<String, Integer> mapOpponents(JSONArray opponentMembers) {
		HashMap<String, Integer> opponentsInfo = new HashMap<>();

		for (int i = 0; i < opponentMembers.length(); i++) {
			JSONObject opponent = opponentMembers.getJSONObject(i);
			opponentsInfo.put(opponent.getString("tag"), opponent.getInt("mapPosition"));
		}
		return opponentsInfo;
	}

	private HttpURLConnection buildRequest(String urlString, String method) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection req = (HttpURLConnection) url.openConnection();
			req.setRequestMethod(method);
			req.setRequestProperty("Authorization", Constants.getAuthToken());
			return req;
		} catch (IOException e) {
			System.out.println("Something went wrong while building the request");
			e.printStackTrace();
			return null;
		}
	}

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

	private boolean updateIsOld(Instant date) {
		Duration timeElapsed = Duration.between(date, Instant.now());

		return timeElapsed.toMinutes() > Constants.getUpdateInterval();
	}

	public ArrayList<Member> getClanMembersInfo() {
		return this.clanMembersInfo;
	}

	public War getWarInfo() {
		return warInfo;
	}

	public void updateClanMembersInfo() {
		if (updateIsOld(lastClanMembersUpdate)) {
			this.clanMembersInfo = extractClanMembersInfo(requestClanMembersInfo());
			lastClanMembersUpdate = Instant.now();
		}
	}

	public void updateWarInfo() {
		if (updateIsOld(warInfoUpdate)) {
			this.warInfo = extractWarInfo(requestWarInfo());
			warInfoUpdate = Instant.now();
		}
	}
}
