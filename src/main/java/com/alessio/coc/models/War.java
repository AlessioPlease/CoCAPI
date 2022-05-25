package com.alessio.coc.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class War implements Serializable {

	private Integer numberOfParticipants;
	private Date startPreparation;
	private Date startBattle;
	private Date endBattle;
	private Integer usedAttacks;
	private Integer ourStars;
	private Integer opponentStars;
	private Integer destructionPercentage;
	private ArrayList<WarMember> members;

	public War(Integer numberOfParticipants, Date startPreparation, Date startBattle, Date endBattle, Integer usedAttacks, Integer ourStars, Integer opponentStars, Integer destructionPercentage, ArrayList<WarMember> members) {
		this.numberOfParticipants = numberOfParticipants;
		this.startPreparation = startPreparation;
		this.startBattle = startBattle;
		this.endBattle = endBattle;
		this.usedAttacks = usedAttacks;
		this.ourStars = ourStars;
		this.opponentStars = opponentStars;
		this.destructionPercentage = destructionPercentage;
		this.members = members;
	}

	public Integer getNumberOfParticipants() {
		return numberOfParticipants;
	}

	public void setNumberOfParticipants(Integer numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
	}

	public Date getStartPreparation() {
		return startPreparation;
	}

	public void setStartPreparation(Date startPreparation) {
		this.startPreparation = startPreparation;
	}

	public Date getStartBattle() {
		return startBattle;
	}

	public void setStartBattle(Date startBattle) {
		this.startBattle = startBattle;
	}

	public Date getEndBattle() {
		return endBattle;
	}

	public void setEndBattle(Date endBattle) {
		this.endBattle = endBattle;
	}

	public Integer getUsedAttacks() {
		return usedAttacks;
	}

	public void setUsedAttacks(Integer usedAttacks) {
		this.usedAttacks = usedAttacks;
	}

	public Integer getOurStars() {
		return ourStars;
	}

	public void setOurStars(Integer ourStars) {
		this.ourStars = ourStars;
	}

	public Integer getOpponentStars() {
		return opponentStars;
	}

	public void setOpponentStars(Integer opponentStars) {
		this.opponentStars = opponentStars;
	}

	public Integer getDestructionPercentage() {
		return destructionPercentage;
	}

	public void setDestructionPercentage(Integer destructionPercentage) {
		this.destructionPercentage = destructionPercentage;
	}

	public ArrayList<WarMember> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<WarMember> members) {
		this.members = members;
	}
}
