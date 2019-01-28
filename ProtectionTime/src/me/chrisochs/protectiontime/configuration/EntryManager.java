package me.chrisochs.protectiontime.configuration;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class EntryManager {
  private Map<String, TextEntry> entries = new HashMap<>();

  public EntryManager(FileConfiguration config) {
    ConfigurationSection lang = config.getConfigurationSection("lang");
    for (String key : lang.getKeys(false)) {
      String text = lang.getString(key + ".text");
      boolean useTitles = lang.getBoolean(key + ".useTitles");
      TextEntry entry = new TextEntry(text, useTitles);
      entries.put(key, entry);
    }

  }

  public TextEntry getEntry(String key) {
    return entries.get(key);
  }

  public boolean hasEntry(String key) {
    return entries.containsKey(key);
  }

}
