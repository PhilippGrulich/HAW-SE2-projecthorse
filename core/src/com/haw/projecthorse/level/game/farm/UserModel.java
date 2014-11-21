package com.haw.projecthorse.level.game.farm;

public class UserModel {
	public String id = String.valueOf(1);
	public String name;
	public String x = "0";
	public String y = "0";
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());

		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserModel other = (UserModel) obj;
		return id.equals(other.id);		
	}
}
