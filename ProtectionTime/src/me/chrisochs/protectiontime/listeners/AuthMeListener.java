package me.chrisochs.protectiontime.listeners;

import java.util.Date;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import fr.xephi.authme.events.LoginEvent;
import me.chrisochs.protectiontime.ProtectedPlayer;
import me.chrisochs.protectiontime.ProtectionTime;

public class AuthMeListener implements Listener {
  private ProtectionTime plugin;

  public AuthMeListener(ProtectionTime pl) {
    plugin = pl;
  }

  @EventHandler
  public void onLogin(LoginEvent event) {
    Player p = event.getPlayer();
    if (p.hasPermission("protectiontime.use")) {
      if (plugin.getConfig().getStringList("disabledWorlds").contains(p.getWorld().getName()))
        return;
      plugin.getProtectionHandler()
          .addProtectedPlayer(new ProtectedPlayer(p.getUniqueId(), new Date()));
      int difference =
          plugin.getProtectionHandler().getProtectedPlayer(p.getUniqueId()).getDifference();
      int protectiontime = plugin.getConfig().getInt("protectiontime");
      String key = "cooldownstart";
      plugin.sendMessageToPlayer(p.getUniqueId(), key, protectiontime - difference);

    }

  }
}
