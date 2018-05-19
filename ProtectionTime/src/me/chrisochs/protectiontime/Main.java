package me.chrisochs.protectiontime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.chrisochs.protectiontime.listeners.EntityDamageByEntityListener;
import me.chrisochs.protectiontime.listeners.EntityDamageListener;
import me.chrisochs.protectiontime.listeners.PlayerJoinListener;
import me.chrisochs.protectiontime.listeners.PlayerRespawnListener;



public class Main extends JavaPlugin implements Listener{
	public static ProtectionHandler protectionHandler = new ProtectionHandler();
	public static FileConfiguration config;
	public void onEnable(){
		saveDefaultConfig();
		reloadConfig();
		new ConfigUpdater(this);
		config = getConfig();
		
		
	    getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
	    getServer().getPluginManager().registerEvents(new PlayerRespawnListener(), this);
	    getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
	    getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
	    System.out.println("ProtectionTime by BloodSKreaper");
	    this.startSheduler();

	}
	
	public static Date getCurrentDate(){
		Date date = new Date();
		return date;
	}
	
	public int getDifferenceInSeconds(Date date){
		Date last = date;
		Date now = getCurrentDate();
		long diff = now.getTime() - last.getTime();
		long diffsec = diff / 1000;
		int diffwholesec = (int) diffsec;
		return diffwholesec;
		
	}
	
	public ProtectedPlayer checkschutzzeit(ProtectedPlayer pp){
		ProtectedPlayer result = null;
		int difference = this.getDifferenceInSeconds(pp.getDate());
		int zeit = this.getConfig().getInt("protectiontime");
		if(difference > zeit&& this.getConfig().getBoolean("lang.cooldownended.enabled")){
			result = protectionHandler.getProtectedPlayer(pp.getUUID());
			if(this.getServer().getPlayer(pp.getUUID()) != null){
				Player p = this.getServer().getPlayer(pp.getUUID());
				Main.sendMessageToPlayer(p, this.getConfig().getString("lang.cooldownended.message"));
			}	
		}else if (difference == zeit&& this.getConfig().getBoolean("lang.cooldownending.enabled")){
			if(this.getServer().getPlayer(pp.getUUID()) != null){
				Player p = this.getServer().getPlayer(pp.getUUID());
				Main.sendMessageToPlayer(p, cooldownEnding("0"));
			}	
			
		}else if (difference == zeit-1&& this.getConfig().getBoolean("lang.cooldownending.enabled")){
			if(this.getServer().getPlayer(pp.getUUID()) != null){
				Player p = this.getServer().getPlayer(pp.getUUID());
				Main.sendMessageToPlayer(p, cooldownEnding("1"));
			}	
			
		}else if (difference == zeit-2&& this.getConfig().getBoolean("lang.cooldownending.enabled")){
			if(this.getServer().getPlayer(pp.getUUID()) != null){
				Player p = this.getServer().getPlayer(pp.getUUID());
				Main.sendMessageToPlayer(p, cooldownEnding("2"));
			}	
		}else if (difference == zeit-3&& this.getConfig().getBoolean("lang.cooldownending.enabled")){
			if(this.getServer().getPlayer(pp.getUUID()) != null){
				Player p = this.getServer().getPlayer(pp.getUUID());
				Main.sendMessageToPlayer(p, cooldownEnding("3"));
			}	
		}else if (difference == zeit-4&& this.getConfig().getBoolean("lang.cooldownending.enabled")){
			if(this.getServer().getPlayer(pp.getUUID()) != null){
				Player p = this.getServer().getPlayer(pp.getUUID());
				Main.sendMessageToPlayer(p, cooldownEnding("4"));
				
			}
			}else if (difference == zeit-5 && this.getConfig().getBoolean("lang.cooldownending.enabled")){
				if(this.getServer().getPlayer(pp.getUUID()) != null){
					Player p = this.getServer().getPlayer(pp.getUUID());
					Main.sendMessageToPlayer(p, cooldownEnding("5"));
				}
			}else if (difference == zeit-10 && this.getConfig().getBoolean("lang.cooldown10sec.enabled")){
				if(this.getServer().getPlayer(pp.getUUID()) != null){
					Player p = this.getServer().getPlayer(pp.getUUID());
					Main.sendMessageToPlayer(p, this.getConfig().getString("lang.cooldown10sec.message").replaceAll("%time%", "10"));
				}	
			}
		return result;
	}

	
	@SuppressWarnings("deprecation")
	public void startSheduler(){
	this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
		public void run() {
			if(protectionHandler.getSize()>0) {
				List<ProtectedPlayer> toRemove = new ArrayList<ProtectedPlayer>();
				for(int i = 0; i<protectionHandler.getSize();i++){
					ProtectedPlayer pp = checkschutzzeit(protectionHandler.getProtectedPlayer(i));
					if(pp!=null) {
						toRemove.add(pp);
					}
				}
				if(toRemove.size()>0) {
					for(ProtectedPlayer pp : toRemove) {
						protectionHandler.removeProtectedPlayer(pp);
					}
				}
			}
		}
		}, 0L, 20L);
	
	
	}
	
	public static void sendMessageToPlayer(Player p, String message) {
		message = ChatColor.translateAlternateColorCodes('&',message);
		if(config.getBoolean("useTitles")==true) {
			if(message.contains(";")) {
				String[] splitted = message.split(";");
			String title = splitted[0];
			String subtitle = splitted[1];
			p.sendTitle(title, subtitle,0,50,30);
			}
		}else {
			p.sendMessage(message);
		}
	}
	
	private String cooldownEnding(String time) {
		return this.getConfig().getString("lang.cooldownending.message").replaceAll("%time%", time);
		
	}

	

}
