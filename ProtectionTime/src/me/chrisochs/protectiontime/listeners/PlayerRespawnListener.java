package me.chrisochs.protectiontime.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.chrisochs.protectiontime.Main;
import me.chrisochs.protectiontime.ProtectedPlayer;

public class PlayerRespawnListener implements Listener{
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e){
		Player p = e.getPlayer();
		if(p.hasPermission("protectiontime.use")){
			Main.protectionHandler.addProtectedPlayer(new ProtectedPlayer(p.getUniqueId(), Main.getCurrentDate()));
			if(Main.config.getBoolean("lang.cooldownstart.enabled"))
			Main.sendMessageToPlayer(p, Main.config.getString("lang.cooldownstart.message").replaceAll("%time%", Main.config.getString("protectiontime")));
		}


	}

}
