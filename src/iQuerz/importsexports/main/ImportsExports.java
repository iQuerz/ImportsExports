package iQuerz.importsexports.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import iQuerz.importsexports.listeners.Listeners;
import iQuerz.importsexports.managers.ExportsManager;
import net.milkbowl.vault.economy.Economy;

public class ImportsExports extends JavaPlugin{
	Economy economy;
	ExportsManager eManager;
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		final RegisteredServiceProvider<Economy> rsp = (RegisteredServiceProvider<Economy>)this.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp != null) {
            economy = rsp.getProvider();
        }
		getServer().getPluginManager().registerEvents(new Listeners(this), this);
		eManager = new ExportsManager(this);
		Bukkit.getServer().getLogger().info("Enabled " + getDescription().getName() + " v" + getDescription().getVersion() +".");
	}
	
	@Override
	public void onDisable() {
		Bukkit.getServer().getLogger().info("Disabled " + getDescription().getName() + " v" + getDescription().getVersion() +".");
	}
	public Economy getEconomy() {
		return this.economy;
	}
	public ExportsManager getEManager() {
		return eManager;
	}
}
