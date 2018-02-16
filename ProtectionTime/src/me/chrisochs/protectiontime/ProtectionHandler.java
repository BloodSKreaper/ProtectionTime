package me.chrisochs.protectiontime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProtectionHandler {
	private List<ProtectedPlayer> protectedplayers = new ArrayList<ProtectedPlayer>();
	
	public void addProtectedPlayer(ProtectedPlayer pp) {
		if(containsPlayer(pp.getUUID())) {
			removePlayer(pp.getUUID());
		}
		protectedplayers.add(pp);
	}
	public boolean containsPlayer(UUID uuid) {
		boolean result = false;
		for(ProtectedPlayer pp:protectedplayers) {
			if(pp.getUUID().equals(uuid)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public void removePlayer(UUID uuid) {
		for(int i =0 ;i<protectedplayers.size();i++) {
			if(protectedplayers.get(i).getUUID().equals(uuid)) {
				protectedplayers.remove(i);
				break;
			}
		}
	}
	
	public void removeProtectedPlayer(ProtectedPlayer pp) {
		if(protectedplayers.contains(pp)) {
			protectedplayers.remove(pp);
		}
	}
	
	public ProtectedPlayer getProtectedPlayer(UUID uuid) {
		ProtectedPlayer result = null;
		for(ProtectedPlayer pp:protectedplayers) {
			if(pp.getUUID().equals(uuid))result = pp;
			break;
		}
		return result;
	}
	
	public ProtectedPlayer getProtectedPlayer(int i) {
		return protectedplayers.get(i);
	}
	
	public int getSize() {
		return protectedplayers.size();
	}

}
