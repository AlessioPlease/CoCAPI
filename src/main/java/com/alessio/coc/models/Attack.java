package com.alessio.coc.models;

import java.io.Serializable;

public class Attack implements Serializable {

	private Integer order;
	private Integer opponentPosition;
	private Integer attackStars;
	private Integer destructionPercentage;

	public Attack(Integer order, Integer opponentPosition, Integer attackStars, Integer destructionPercentage) {
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

	public Integer getDestructionPercentage() {
		return destructionPercentage;
	}

	@Override
	public String toString() {
		return String.format("[%2d°: %3d %2d☆ %4d%s]", order, opponentPosition, attackStars, destructionPercentage, "%%");
	}
}
