package me.chrisochs.protectiontime;

import java.util.HashMap;

import org.bukkit.plugin.Plugin;

public class ConfigUpdater {
	private HashMap<String, Object> defaultSettings = new HashMap<String, Object>();
	private Plugin plugin;
	
	public ConfigUpdater(Plugin pl) {
		plugin = pl;
		loadDefaults();
		updateConfigIfNotUpToDate();
		plugin.saveConfig();
	}
	
	public void loadDefaults() {
		defaultSettings.put("protectiontime", "15");
		defaultSettings.put("useTitles", true);
		defaultSettings.put("canHarmMobs", true);
		//Lang Settings
		loadLangSettings("lang.cooldownstart.message", "&2Protection time started!;&6%time% seconds!");
		loadLangSettings("lang.cooldown10sec.message", "&2Time is up soon!;&6%time% seconds left.");
		loadLangSettings("lang.cooldownending.message", "&2Time runs up!;&6%time% seconds left!");
		loadLangSettings("lang.cooldownended.message", "&4Time is up!;&cYou are now vulnerable!");
		loadLangSettings("lang.damageonprotected.message", "&4Attack failed!;&6%player% is protected!");
		loadLangSettings("lang.damagebyprotected.message", "&4Attack failed!;&6You &aare protected!");
		
		
	}
	private void loadLangSettings(String key, String value) {
		defaultSettings.put(key, value);
		defaultSettings.put(key.replace("message", "enabled"), true);		
	}
	
	public void updateConfigIfNotUpToDate() {
		for(String key:defaultSettings.keySet()) {
			if(!plugin.getConfig().contains(key)) {
				plugin.getConfig().set(key, defaultSettings.get(key));
			}
		}
	}

}
