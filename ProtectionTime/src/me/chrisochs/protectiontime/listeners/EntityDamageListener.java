package me.chrisochs.protectiontime.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import me.craftersforever.bloodskreaper.Main;

public class EntityDamageListener implements Listener{
	@EventHandler
	public void onDamageEvent(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			if(p.hasPermission("protectiontime.use") && Main.protectionHandler.containsPlayer(p.getUniqueId())){
				e.setCancelled(true);
				if(e instanceof EntityDamageByEntityEvent){
					EntityDamageByEntityEvent dmgevent = (EntityDamageByEntityEvent) e;
					if(dmgevent.getDamager() instanceof Player){
						Player dmger = (Player) dmgevent.getDamager();
						Main.sendMessageToPlayer(dmger, Main.config.getString("lang.damageonprotected").replaceAll("%player%", p.getName()));
					}
				}
			}
		}
	}

}