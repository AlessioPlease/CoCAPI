package com.alessio.coc.models;

import java.io.Serializable;

public class Attack implements Serializable {

	private Integer order;
	private Integer opponentPosition;
	private Integer attackStars;
	private Double destructionPercentage;

	public Attack(Integer order, Integer opponentPosition, Integer attackStars, Double destructionPercentage) {
		this.order = order;
		this.opponentPosition = opponentPosition;
		this.attackStars = attackStars;
		this.destructionPercentage = destructionPercentage;
	}

	public Integer getOrder() {
		return order;
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

	@Override
	public String toString() {
		return order + "\t" +
				opponentPosition + "\t" +
				attackStars + "\t" +
				destructionPercentage + "%";
	}
}
