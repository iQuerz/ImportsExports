package iQuerz.importsexports.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import iQuerz.importsexports.main.ImportsExports;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class Import {

	double price;
	int amount;
	Inventory inv;
	ItemStack import1;
	Inventory playerInv;
	int index;
	
	public Import(Material item,int price, int amount, int index) {
		this.price = price*1.1;
		this.price = (int)this.price;
		this.amount = amount;
		inv = Bukkit.createInventory(null, 9, "Daily import deals");
		inv = initializeInv(inv,item,this.price,amount);
		this.index = index;
	}
	
	public Inventory initializeInv(Inventory inv, Material item, double price, int amount) {
		inv.clear();
		ItemStack glassPane = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
		ItemMeta glassPaneMeta = glassPane.getItemMeta();
		glassPaneMeta.setDisplayName("Import 1");
		glassPane.setItemMeta(glassPaneMeta);
		inv.setItem(0,glassPane);
		glassPaneMeta.setDisplayName("Import 64");
		glassPane.setItemMeta(glassPaneMeta);
		inv.setItem(1,glassPane);
		glassPaneMeta.setDisplayName("Next Import");
		glassPane.setItemMeta(glassPaneMeta);
		inv.setItem(8,glassPane);
		
		glassPane = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
		glassPaneMeta.setDisplayName(" ");
		glassPane.setItemMeta(glassPaneMeta);
		inv.setItem(2,glassPane);
		inv.setItem(3,glassPane);
		inv.setItem(5,glassPane);
		inv.setItem(6,glassPane);
		inv.setItem(7,glassPane);
		import1 = new ItemStack(item);
		ItemMeta meta = import1.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add("$"+price);
		lore.add("an import requested by a nearby nation.");
		meta.setLore(lore);
		import1.setItemMeta(meta);
		inv.setItem(4, import1);
		return inv;
	}
	
	public void openShop(Player p) {
		p.openInventory(inv);
	}
	
	public boolean checkInv(Inventory inventory) {
		if(inventory.equals(this.inv)){
			return true;
		}
		return false;
	}
	
	public void onInventoryClick(InventoryClickEvent event, Inventory inventory, Economy economy, ImportsExports plugin) {
		if(!inv.equals(event.getClickedInventory())) {
			return;
		}
		event.setCancelled(true);
		
		if(event.getRawSlot()==2||event.getRawSlot()==3||event.getRawSlot()==4||event.getRawSlot()==5||event.getRawSlot()==6||event.getRawSlot()==7)
			return;
		Player p = Bukkit.getPlayer(event.getWhoClicked().getUniqueId());
		if(event.getRawSlot()==8) {
			p.closeInventory();
			plugin.getEManager().updateIndex1();
			plugin.getEManager().openImports(p, plugin.getEManager().getIndex1());
			return;
		}
		if(amount<1) {
			p.sendMessage("Sorry, this import is not needed anymore.");
			return;
		}
		if(event.getRawSlot()==0) {
			if(economy.getBalance(p) < price) {
				p.sendMessage("Insufficient money");
				return;
			}
			EconomyResponse response = economy.withdrawPlayer(p, price);
			if(response.transactionSuccess()) {
				ItemStack import2 = new ItemStack(import1.getType());
				p.getInventory().addItem(import2);
				p.updateInventory();
				p.sendMessage("§6You Bought 1 of our Imports for $"+price+"!");
			}
		    return;
		}
		if(event.getRawSlot()==1) {
			if(economy.getBalance(p) < price*64) {
				p.sendMessage("Insufficient money");
				return;
			}
			playerInv = p.getInventory();
			EconomyResponse response = economy.withdrawPlayer(p, price*64);
			if(response.transactionSuccess()) {
				ItemStack itm = new ItemStack(import1.getType());
				itm.setAmount(64);
				p.getInventory().addItem(itm);
				p.updateInventory();
				p.sendMessage("§6You Bought 1 stack of our Imports for $"+price*64+"!");
			}
			return;
		}
	}
}
