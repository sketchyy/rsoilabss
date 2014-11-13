package com.sketchyy.rsoi1.oauth;

public class Character {
	private String name;
	private String realm;
	private String thumbnail;
	private String guild;
	private Long level;

	public Character() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getGuild() {
		if (guild == null) {
			return "no guild";
		} else {
			return guild;
		}
	}

	public void setGuild(String guild) {
		this.guild = guild;
	}

	public String getLevel() {
		return level.toString();
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = "http://eu.battle.net/static-render/eu/" + thumbnail;
	}
}
