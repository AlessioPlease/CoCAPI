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
}
