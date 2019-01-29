package me.chrisochs.protectiontime.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import me.chrisochs.protectiontime.ProtectionTime;

public class EntityDamageByEntityListener implements Listener {
  private ProtectionTime plugin;

  public EntityDamageByEntityListener(ProtectionTime pl) {
    plugin = pl;
  }

  @EventHandler
  public void onPlayerHitEvent(EntityDamageByEntityEvent e) {
    if (e.isCancelled())
      return;
    // Damager and Damaged are Players
    if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
      Player damager = (Player) e.getDamager();

      if (plugin.getProtectionHandler().containsPlayer(damager.getUniqueId())) {
        // DAMAGER IS PROTECTED
        int difference =
            plugin.getProtectionHandler().getProtectedPlayer(damager.getUniqueId()).getDifference();
        int protectiontime = plugin.getConfig().getInt("protectiontime");
        String key = "damagebyprotected";
        plugin.sendMessageToPlayer(damager.getUniqueId(), key, protectiontime - difference);
        e.setCancelled(true);
        return;
      }
      Player damaged = (Player) e.getEntity();
      if (plugin.getProtectionHandler().containsPlayer(damaged.getUniqueId())) {
        // DAMAGED IS PROTECTED
        int difference =
            plugin.getProtectionHandler().getProtectedPlayer(damaged.getUniqueId()).getDifference();
        int protectiontime = plugin.getConfig().getInt("protectiontime");
        String key = "damageonprotected";
        plugin.sendMessageToPlayer(damager.getUniqueId(), key, protectiontime - difference);
        e.setCancelled(true);
        return;

      }
    } else if (e.getDamager() instanceof Player && !plugin.getConfig().getBoolean("canHarmMobs")) {
      Player damager = (Player) e.getDamager();
      if (!plugin.getProtectionHandler().containsPlayer(damager.getUniqueId()))
        return;
      // DAMAGER IS PLAYER AND CANNOT HARM MOBS
      String key = "damagebyprotected";
      int difference =
          plugin.getProtectionHandler().getProtectedPlayer(damager.getUniqueId()).getDifference();
      int protectiontime = plugin.getConfig().getInt("protectiontime");
      plugin.sendMessageToPlayer(damager.getUniqueId(), key, protectiontime - difference);

      e.setCancelled(true);
      return;


    }
  }

}
