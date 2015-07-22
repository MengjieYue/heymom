package com.heymom.backend.entity;

public enum EntityStatus {
	DELETEED(-1), NORMAL(0);
	private int value;

	private EntityStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
