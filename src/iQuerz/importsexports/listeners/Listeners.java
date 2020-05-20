package iQuerz.importsexports.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import iQuerz.importsexports.main.ImportsExports;

public class Listeners implements Listener{

	ImportsExports plugin;
	int x1, x2, y1,y2,z1,z2;
	
	public Listeners(ImportsExports plugin) {
		this.plugin = plugin;
		x1 = plugin.getConfig().getInt("exports-location.x");
		y1 = plugin.getConfig().getInt("exports-location.y");
		z1 = plugin.getConfig().getInt("exports-location.z");
		x2 = plugin.getConfig().getInt("imports-location.x");
		y2 = plugin.getConfig().getInt("imports-location.y");
		z2 = plugin.getConfig().getInt("imports-location.z");
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getClickedBlock()==null)
			return;
		if(event.getClickedBlock().getLocation().equals(new Location(event.getClickedBlock().getLocation().getWorld(),x1,y1,z1,0,0))) {
			if(event.getPlayer().getDisplayName()=="Giryee388") {
				event.getPlayer().sendMessage("Sorry, you are not allowed to access exports right now.");
				return;
			}
			plugin.getEManager().resetIndex();
			plugin.getEManager().openExports(event.getPlayer(),0);
		}
		else {
			if(event.getClickedBlock().getLocation().equals(new Location(event.getClickedBlock().getLocation().getWorld(),x2,y2,z2,0,0))) {
				plugin.getEManager().resetIndex1();
				plugin.getEManager().openImports(event.getPlayer(), 0);
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		plugin.getEManager().inventoryClick(event, event.getInventory(), plugin.getEconomy(), plugin);
		plugin.getEManager().inventoryClick1(event, event.getInventory(), plugin.getEconomy(), plugin);
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		plugin.getEManager().closeExport(event);
		plugin.getEManager().closeImport(event);
	}
}
