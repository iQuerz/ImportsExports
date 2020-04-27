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

public class Export {
	int price;
	int amount;
	Inventory inv;
	ItemStack export;
	Inventory playerInv;
	int index;
	
	public Export(Material item, int price, int amount, int index) {
		this.price = price;
		this.amount = amount;
		int pg = index+1;
		inv = Bukkit.createInventory(null, 9, "Daily export deals,	 pg. "+ pg);
		inv = initializeInv(inv,item,price,amount);
		this.index = index;
	}
	
	public void openShop(Player p) {
		p.openInventory(inv);
	}
	
	public Inventory initializeInv(Inventory inv, Material item, int price, int amount) {
		inv.clear();
		ItemStack glassPane = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
		ItemMeta glassPaneMeta = glassPane.getItemMeta();
		glassPaneMeta.setDisplayName("Export 1");
		glassPane.setItemMeta(glassPaneMeta);
		inv.setItem(0,glassPane);
		glassPaneMeta.setDisplayName("Export All");
		glassPane.setItemMeta(glassPaneMeta);
		inv.setItem(1,glassPane);
		glassPaneMeta.setDisplayName("Next Export");
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
		export = new ItemStack(item);
		ItemMeta meta = export.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add("$"+price);
		lore.add("an export requested by a nearby nation.");
		meta.setLore(lore);
		export.setItemMeta(meta);
		inv.setItem(4, export);
		return inv;
	}
	
	int timesExported;
	
	public void onInventoryClick(InventoryClickEvent event, Inventory inventory, Economy economy, ImportsExports plugin) {
		if(!inv.equals(event.getClickedInventory())) {
			return;
		}
		event.setCancelled(true);
		//Bukkit.broadcastMessage("exports left:"+amount);
		if(event.getRawSlot()==2||event.getRawSlot()==3||event.getRawSlot()==4||event.getRawSlot()==5||event.getRawSlot()==6||event.getRawSlot()==7)
			return;
		Player p = Bukkit.getPlayer(event.getWhoClicked().getUniqueId());
		if(event.getRawSlot()==8) {
			p.closeInventory();
			plugin.getEManager().updateIndex();
			plugin.getEManager().openExports(p, plugin.getEManager().getIndex());
			return;
		}
		if(amount<1) {
			p.sendMessage("Sorry, this export is not needed anymore.");
			return;
		}
		int index = itemCheck(p.getInventory(),export.getType());
		if(index==-1) {
			p.sendMessage("Sorry, you don't have the requested item.");
			return;
		}
		if(event.getRawSlot()==0) {
			ItemStack itm = p.getInventory().getItem(index);
			int amt = itm.getAmount() - 1;
			itm.setAmount(amt);
			amount-=1;
			//set the item in the player's inventory at slot i to "itm" if the amount
		    //is > 0, and to null if it is <= 0
		    p.getInventory().setItem(index, amt > 0 ? itm : null);
		    p.updateInventory();
		    economy.depositPlayer(p, price);
		    p.sendMessage("You got $"+price+".");
		    return;
		}
		if(event.getRawSlot()==1) {
			playerInv = p.getInventory();
			//int timesExported = removeItems(export.getType(),amount,0);
			removeItems(export.getType(),amount,0);
			//Bukkit.broadcastMessage(""+timesExported);
			economy.depositPlayer(p, price*timesExported);
			p.sendMessage("You got $"+price*timesExported);
			amount -= timesExported;
			timesExported =  0;
			p.getInventory().setContents(playerInv.getContents());
			p.updateInventory();
			return;
		}
	}
	
	private int removeItems( Material mat, int amount, int i) {
		int index = playerInv.first(mat);
		if(index == -1) {
			return i;
		}
		ItemStack item = playerInv.getItem(index);
		if(item.getAmount()<amount) {
			int temp = item.getAmount();
			playerInv.setItem(index, null);
			timesExported += temp;
			removeItems(mat,amount-temp, i+temp);
		}
		else {//amount<inventory
			int temp = amount;
			item.setAmount(item.getAmount() - temp);
			playerInv.setItem(index, item);
			timesExported += temp;
			return i + temp;
		}
		return i;
	}
	
	private int itemCheck(Inventory inv, Material mat) {
		for(int i = 0;i<inv.getSize();i++) {
			if(inv.getItem(i)!= null && inv.getItem(i).getType().equals(mat))
				return i;
		}
		return -1;
	}
}
