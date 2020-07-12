package iQuerz.importsexports.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import iQuerz.importsexports.main.ImportsExports;
import net.milkbowl.vault.economy.Economy;

public class ExportsManager {
	List<Export> exports;
	List<Import> imports;
	int activeIndex, activeIndex1;
	boolean available, available1;
	
	public ExportsManager(ImportsExports plugin) {
		//initialize inventories
		available = true;
		available1 = true;
		exports = new ArrayList<Export>();
		imports = new ArrayList<Import>();
		
		Set<String> list = plugin.getConfig().getConfigurationSection("exports").getKeys(false);
		List<String> subPaths = new ArrayList<String>(list);
		for(int i = 0;i<subPaths.size();i++) {
			list = plugin.getConfig().getConfigurationSection("exports."+subPaths.get(i)).getKeys(false);
			List<String> subPath = new ArrayList<String>(list);
			Random r = new Random();
			int index = r.nextInt(subPath.size()-1);
			String s = "exports."+subPaths.get(i)+"."+subPath.get(index);
			String[] data = plugin.getConfig().getString(s).split(", ");
			
			exports.add(new Export(getMaterialByName(s),Integer.parseInt(data[0]),999999,i));
			imports.add(new Import(getMaterialByName(s),Integer.parseInt(data[0]),999999,i));
		}
		this.activeIndex = 0;
		this.activeIndex1 = 0;
	}
	
	public void resetIndex() {
		activeIndex = 0;
	}
	public void resetIndex1() {
		activeIndex1 = 0;
	}
	
	public int getIndex() {
		return this.activeIndex;
	}
	public int getIndex1() {
		return this.activeIndex1;
	}
	
	public void updateIndex() {
		activeIndex++;
		if(activeIndex == 5)
			activeIndex = 0;
	}
	public void updateIndex1() {
		activeIndex1++;
		if(activeIndex1 == 5)
			activeIndex1 = 0;
	}
	
	public void openExports(Player p) {
		if(available) {
			activeIndex = 0;
			exports.get(activeIndex).openShop(p);
			available = false;
		}
	}
	
	public void openImports(Player p) {
		if(available1) {
			activeIndex1 = 0;
			imports.get(activeIndex1).openShop(p);
			available1 = false;
		}
	}
	
	public void closeExport(InventoryCloseEvent event) {
		if(exports.get(activeIndex).checkInv(event.getInventory()))
			available = true;
	}
	
	public void closeImport(InventoryCloseEvent event) {
		if(imports.get(activeIndex1).checkInv(event.getInventory()))
			available1 = true;
	}
	
	public void inventoryClick(InventoryClickEvent event, Inventory inventory,Economy economy, ImportsExports plugin) {
		exports.get(activeIndex).onInventoryClick(event, inventory, economy, plugin);
	}
	
	public void inventoryClick1(InventoryClickEvent event, Inventory inventory, Economy economy, ImportsExports plugin) {
		imports.get(activeIndex1).onInventoryClick(event, inventory, economy, plugin);
	}
	
	private Material getMaterialByName(String name) {
		switch (name) {
			case "exports.ores.diamond":{
				return Material.DIAMOND;
			}
			case "exports.ores.iron":{
				return Material.IRON_INGOT;
			}
			case "exports.ores.gold":{
				return Material.GOLD_INGOT;
			}
			case "exports.ores.emerald":{
				return Material.EMERALD;
			}
			case "exports.ores.lapis-lazuli":{
				return Material.LAPIS_LAZULI;
			}
			case "exports.ores.redstone":{
				return Material.REDSTONE;
			}
			case "exports.ores.coal":{
				return Material.COAL;
			}
			case "exports.blocks.stone":{
				return Material.STONE;
			}
			case "exports.blocks.granite":{
				return Material.GRANITE;
			}
			case "exports.blocks.diorite":{
				return Material.DIORITE;
			}
			case "exports.blocks.andesite":{
				return Material.ANDESITE;
			}
			case "exports.blocks.nether-bricks":{
				return Material.NETHER_BRICK;
			}
			case "exports.blocks.obsidian":{
				return Material.OBSIDIAN;
			}
			case "exports.blocks.sand":{
				return Material.SAND;
			}
			case "exports.blocks.gravel":{
				return Material.GRAVEL;
			}
			case "exports.blocks.red-sand":{
				return Material.RED_SAND;
			}
			case "exports.food.bread":{
				return Material.BREAD;
			}
			case "exports.food.cookie":{
				return Material.COOKIE;
			}
			case "exports.food.pumpkin-pie":{
				return Material.PUMPKIN_PIE;
			}
			case "exports.food.cake":{
				return Material.CAKE;
			}
			case "exports.food.cooked-porkchop":{
				return Material.COOKED_PORKCHOP;
			}
			case "exports.food.steak":{
				return Material.COOKED_BEEF;
			}
			case "exports.food.cooked-mutton":{
				return Material.COOKED_MUTTON;
			}
			case "exports.food.cooked-chicken":{
				return Material.COOKED_CHICKEN;
			}
			case "exports.food.cooked-rabbit":{
				return Material.COOKED_RABBIT;
			}
			case "exports.food.golden-carrots":{
				return Material.GOLDEN_CARROT;
			}
			case "exports.crops.wheat":{
				return Material.WHEAT;
			}
			case "exports.crops.carrot":{
				return Material.CARROT;
			}
			case "exports.crops.potato":{
				return Material.POTATO;
			}
			case "exports.crops.nether-wart":{
				return Material.NETHER_WART;
			}
			case "exports.crops.sugar-cane":{
				return Material.SUGAR_CANE;
			}
			case "exports.crops.cactus":{
				return Material.CACTUS;
			}
			case "exports.crops.bamboo":{
				return Material.BAMBOO;
			}
			case "exports.crops.kelp":{
				return Material.KELP;
			}
			case "exports.mob-drops.gunpowder":{
				return Material.GUNPOWDER;
			}
			case "exports.mob-drops.rotten-flesh":{
				return Material.ROTTEN_FLESH;
			}
			case "exports.mob-drops.bone":{
				return Material.BONE;
			}
			case "exports.mob-drops.string":{
				return Material.STRING;
			}
			case "exports.mob-drops.spider-eye":{
				return Material.SPIDER_EYE;
			}
			case "exports.mob-drops.blaze-rod":{
				return Material.BLAZE_ROD;
			}
			case "exports.mob-drops.wither-skeleton-skull":{
				return Material.WITHER_SKELETON_SKULL;
			}
			case "exports.mob-drops.slime-ball":{
				return Material.SLIME_BALL;
			}
		}
		return Material.ACACIA_LOG;
	}
}
