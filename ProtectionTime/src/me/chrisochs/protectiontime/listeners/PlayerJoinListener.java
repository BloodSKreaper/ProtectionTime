package me.chrisochs.protectiontime.listeners;

import java.util.Date;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import me.chrisochs.protectiontime.ProtectedPlayer;
import me.chrisochs.protectiontime.ProtectionTime;

public class PlayerJoinListener implements Listener {
  private ProtectionTime plugin;

  public PlayerJoinListener(ProtectionTime pl) {
    plugin = pl;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    if (p.hasPermission("protectiontime.use")) {
      if (plugin.getConfig().getStringList("disabledWorlds").contains(p.getWorld().getName()))
        return;
      plugin.getProtectionHandler()
          .addProtectedPlayer(new ProtectedPlayer(p.getUniqueId(), new Date()));
      int difference =
          plugin.getProtectionHandler().getProtectedPlayer(p.getUniqueId()).getDifference();
      int protectiontime = plugin.getConfig().getInt("protectiontime");
      String key = "cooldownstart";
      if (plugin.getEntryManager().hasEntry(key)) {
        plugin.sendMessageToPlayer(p.getUniqueId(), key, protectiontime - difference);
      }
    }
  }
}
