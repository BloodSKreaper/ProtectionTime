package me.chrisochs.protectiontime.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import me.chrisochs.protectiontime.ProtectionTime;

public class EntityDamageListener implements Listener {
  private ProtectionTime plugin;

  public EntityDamageListener(ProtectionTime pl) {
    plugin = pl;
  }

  @EventHandler
  public void onDamageEvent(EntityDamageEvent e) {
    if (e.getEntity() instanceof Player) {
      Player p = (Player) e.getEntity();
      if (plugin.getProtectionHandler().containsPlayer(p.getUniqueId())) {
        e.setCancelled(true);
      }
    }
  }

}
