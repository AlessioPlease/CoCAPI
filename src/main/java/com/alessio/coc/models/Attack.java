package com.alessio.coc.models;

public class Attack {

	private Integer opponentPosition;
	private Integer attackStars;
	private Double destructionPercentage;

	public Attack(Integer opponentPosition, Integer attackStars, Double destructionPercentage) {
		this.opponentPosition = opponentPosition;
		this.attackStars = attackStars;
		this.destructionPercentage = destructionPercentage;
	}

	public Integer getOpponentPosition() {
		return opponentPosition;
	}

	public Integer getAttackStars() {
		return attackStars;
	}

	public Double getDestructionPercentage() {
		return destructionPercentage;
	}
}
