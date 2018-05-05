package me.chrisochs.protectiontime.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.chrisochs.protectiontime.Main;

public class EntityDamageByEntityListener implements Listener{
	@EventHandler
	public void onPlayerHitEvent(EntityDamageByEntityEvent e){
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player){
			Player p = (Player) e.getDamager();
		
		if(p.hasPermission("protectiontime.use") && Main.protectionHandler.containsPlayer(p.getUniqueId())){
			e.setCancelled(true);
			Main.sendMessageToPlayer(p, Main.config.getString("lang.damagebyprotected"));
		}
		}
		if(e.getDamager() instanceof Player && Main.config.getBoolean("canHarmMobs") == false) {
			Player p = (Player) e.getDamager();
			Main.sendMessageToPlayer(p, Main.config.getString("lang.damagebyprotected"));
			e.setCancelled(true);
		}
		
	}

}
