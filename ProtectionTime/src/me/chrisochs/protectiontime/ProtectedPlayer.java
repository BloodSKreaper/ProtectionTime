package me.chrisochs.protectiontime;

import java.util.Date;
import java.util.UUID;

public class ProtectedPlayer {
	private UUID uuid;
	private Date date;
	
	public ProtectedPlayer(UUID uuid, Date date) {
		this.uuid = uuid;
		this.date = date;
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public Date getDate() {
		return date;
	}

}
