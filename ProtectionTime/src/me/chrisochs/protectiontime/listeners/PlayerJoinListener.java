package me.chrisochs.protectiontime.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.chrisochs.protectiontime.Main;
import me.chrisochs.protectiontime.ProtectedPlayer;

public class PlayerJoinListener implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		if(p.hasPermission("protectiontime.use")){
			Main.protectionHandler.addProtectedPlayer(new ProtectedPlayer(p.getUniqueId(), Main.getCurrentDate()));
			Main.sendMessageToPlayer(p, Main.config.getString("lang.cooldownstart").replaceAll("%time%", Main.config.getString("protectiontime")));
		}

	}
}
