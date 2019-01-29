package me.chrisochs.protectiontime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import me.chrisochs.protectiontime.configuration.EntryManager;
import me.chrisochs.protectiontime.configuration.TextEntry;
import me.chrisochs.protectiontime.listeners.AuthMeListener;
import me.chrisochs.protectiontime.listeners.EntityDamageByEntityListener;
import me.chrisochs.protectiontime.listeners.EntityDamageListener;
import me.chrisochs.protectiontime.listeners.PlayerJoinListener;
import me.chrisochs.protectiontime.listeners.PlayerRespawnListener;
import me.clip.placeholderapi.PlaceholderAPI;

public class ProtectionTime extends JavaPlugin implements Listener {
  public static ProtectionTime protectionTime;
  private ProtectionHandler protectionHandler;
  private EntryManager entryManager;
  public static boolean usesAuthMe;
  public static boolean usesPlaceholderAPI;
  private PluginLogger log;

  // INIT
  public void onEnable() {
    log = new PluginLogger(this);
    protectionTime = this;
    protectionHandler = new ProtectionHandler();
    initConfig();
    entryManager = new EntryManager(getConfig());
    this.startSheduler();
    checkForAuthMe();
    checkForPlaceholderAPI();
    registerListeners();
  }

  public void checkForAuthMe() {
    if (getServer().getPluginManager().isPluginEnabled("AuthMe")
        && getConfig().getBoolean("useAuthMe")) {
      usesAuthMe = true;
      log.info("Found AuthMe. Enabling AuthMe integration.");
      return;
    }
    usesAuthMe = false;
  }

  public void checkForPlaceholderAPI() {
    usesPlaceholderAPI = getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
    log.info("Found PlaceholderAPI. Enabling PlaceholderAPI integration.");
  }

  public void initConfig() {
    saveDefaultConfig();
    int configversion = 0;
    if (getConfig().getInt("configversion") != configversion) {
      log.warning(
          "\n========================================\nProtectionTime Config version does not fit. \nPlugin is being disabled! \nPlease remove the config and let the new Config generate.\n========================================");
      getServer().getPluginManager().disablePlugin(this);
    }
  }

  public void registerListeners() {
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new PlayerJoinListener(this), this);
    pm.registerEvents(new PlayerRespawnListener(this), this);
    pm.registerEvents(new EntityDamageListener(this), this);
    pm.registerEvents(new EntityDamageByEntityListener(this), this);
    if (!usesAuthMe)
      return;
    pm.registerEvents(new AuthMeListener(this), this);

  }

  public void startSheduler() {
    this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
      public void run() {
        int size = protectionHandler.getSize();
        if (size > 0) {
          handleClock(size);
        }
      }
    }, 0L, 20L);
  }

  public void handleClock(int size) {
    List<ProtectedPlayer> toRemove = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      ProtectedPlayer pp = protectionHandler.getProtectedPlayer(i);
      Player p = Bukkit.getPlayer(pp.getUUID());
      if (p == null || pp.getDifference() >= getConfig().getInt("protectiontime")
          || getConfig().getStringList("disabledWorlds").contains(p.getWorld().getName())) {
        String key = "cooldownended";
        sendMessageToPlayer(pp.getUUID(), key, 0);
        toRemove.add(pp);
      } else {
        String key = String.valueOf(getConfig().getInt("protectiontime") - pp.getDifference());

        sendMessageToPlayer(pp.getUUID(), key,
            getConfig().getInt("protectiontime") - pp.getDifference());
      }
    }
    protectionHandler.removeAll(toRemove);


  }

  public ProtectionHandler getProtectionHandler() {
    return protectionHandler;
  }

  public EntryManager getEntryManager() {
    return entryManager;
  }

  public void sendMessageToPlayer(UUID uuid, String messageKey, int time) {
    if (entryManager.hasEntry(messageKey)) {
      Player p = Bukkit.getServer().getPlayer(uuid);
      if (p == null)
        return;
      TextEntry entry = entryManager.getEntry(messageKey);
      String text = entry.getText();
      boolean useTitles = entry.useTitles();
      text = ChatColor.translateAlternateColorCodes('&', text);
      text = text.replaceAll("%name%", p.getName()).replaceAll("%displayname%", p.getDisplayName())
          .replaceAll("%timeleft%", String.valueOf(time))
          .replaceAll("%time%", getConfig().getString("protectiontime"));
      if (usesPlaceholderAPI) {
        text = PlaceholderAPI.setBracketPlaceholders(p, text);
      }
      if (!useTitles) {
        p.sendMessage(text);
        return;
      }
      String[] titles = text.split(";");
      String title = titles[0];
      String subtitle = titles.length > 1 ? titles[1] : "";
      p.sendTitle(title, subtitle, 0, 50, 30);
    }
  }


}
