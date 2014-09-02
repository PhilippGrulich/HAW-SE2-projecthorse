package com.haw.projecthorse.score.json;

public class ScoreImpl implements Score {
	private int ID = -1, ep = 0;
	private Horse horse = new HorseImpl();

	public ScoreImpl() {
	}
	
	public ScoreImpl(int ID) {
		this.ID = ID;
	}
	
	@Override
	public int getID() {
		return ID;
	}

	@Override
	public int getEP() {
		return ep;
	}

	@Override
	public String getHorseName() {
		return horse.getName();
	}

	@Override
	public void addEP(int toAdd) {
		ep += toAdd;
	}

}
