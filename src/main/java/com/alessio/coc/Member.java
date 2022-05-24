package com.alessio.coc;

public class Member {

	private String name;
	private Integer donated;
	private Integer received;
	private Integer attacksWon;
	private Integer th;
	private Integer warPreference;
	private String role;
	private Integer exp;
	private Integer trophies;
	private Integer warStars;
	private Integer clanRank;
	private String tag;

	public Member(String name, Integer donated, Integer received, Integer attacksWon, Integer th, Integer warPreference, String role, Integer exp, Integer trophies, Integer warStars, Integer clanRank, String tag) {
		this.name = name;
		this.donated = donated;
		this.received = received;
		this.attacksWon = attacksWon;
		this.th = th;
		this.warPreference = warPreference;
		this.role = role;
		this.exp = exp;
		this.trophies = trophies;
		this.warStars = warStars;
		this.clanRank = clanRank;
		this.tag = tag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDonated() {
		return donated;
	}

	public void setDonated(Integer donated) {
		this.donated = donated;
	}

	public Integer getReceived() {
		return received;
	}

	public void setReceived(Integer received) {
		this.received = received;
	}

	public Integer getAttacksWon() {
		return attacksWon;
	}

	public void setAttacksWon(Integer attacksWon) {
		this.attacksWon = attacksWon;
	}

	public Integer getTh() {
		return th;
	}

	public void setTh(Integer th) {
		this.th = th;
	}

	public Integer getWarPreference() {
		return warPreference;
	}

	public void setWarPreference(Integer warPreference) {
		this.warPreference = warPreference;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getExp() {
		return exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public Integer getTrophies() {
		return trophies;
	}

	public void setTrophies(Integer trophies) {
		this.trophies = trophies;
	}

	public Integer getWarStars() {
		return warStars;
	}

	public void setWarStars(Integer warStars) {
		this.warStars = warStars;
	}

	public Integer getClanRank() {
		return clanRank;
	}

	public void setClanRank(Integer clanRank) {
		this.clanRank = clanRank;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return name + " " + donated + " " + received + " " + attacksWon + " " + th + " " + warPreference + " " + role + " " + exp + " " + trophies + " " + warStars + " " + clanRank + " " + tag;
	}
}
