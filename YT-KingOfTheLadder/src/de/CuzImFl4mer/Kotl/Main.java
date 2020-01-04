package de.CuzImFl4mer.Kotl;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	ArrayList<String> kotl = new ArrayList<>();
	String prefix = "§aKingOfTheLadder §7» ";
	ItemStack goldhelmet = new ItemStack(Material.GOLD_HELMET);
	
	@Override
	public void onEnable() {
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);
	}
	
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		
		
		if((cs instanceof Player)) {
			cs.sendMessage("Du bist kein Spieler!");
		}
		Player p = (Player)cs;
		
		if(cmd.getName().equalsIgnoreCase("kingoftheladder")) {
			if (args.length == 0) {
				
				if(this.kotl.size() == 1) {
					cs.sendMessage(this.prefix + "§7Der momentane §eKingOfTheLadder §7ist: §a" + this.kotl.toString());
					return true;
				}
				if(this.kotl.size() == 0) {
					cs.sendMessage(this.prefix + "§7Momentan hat niemand den §aKingOfTheLadder §7Titel!");
					return true;
				}
			} else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("reset")) {
					if((cs.hasPermission("youtube.admin"))) {
						this.kotl.clear();
						cs.sendMessage(this.prefix + "§7KingOfTheLadder wurde §aerfolgreich §cneugestartet§7!");
						p.getInventory().setArmorContents(null);
						return true;
					}
					cs.sendMessage(this.prefix + "§cDazu hast du keine Rechte!");
				} else if(args[0].equalsIgnoreCase("help")) {
					
					if((cs.hasPermission("youtube.admin"))) {
						cs.sendMessage(this.prefix + "§7BefehlsListe:");
						cs.sendMessage(this.prefix + "§e/KingOfTheLadder");
						cs.sendMessage(this.prefix + "§e/KingOfTheLadder <help>");
						cs.sendMessage(this.prefix + "§e/KingOfTheLadder <reset>");
					} else {
						cs.sendMessage(this.prefix + "§7BefehlsListe:");
						cs.sendMessage(this.prefix + "§e/KingOfTheLadder");
				} 

				}
			}
		}
		return false;	
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if ((p.getWorld().getName().equalsIgnoreCase("world")) && 
				(p.getLocation().getBlock().getType() == Material.GOLD_PLATE) && 
				(p.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() == Material.GOLD_BLOCK) && 
				(!this.kotl.contains(p.getName()))) {
				if (!this.kotl.contains(p.getName())) {
					
					this.kotl.clear();
					this.kotl.add(p.getName());
					p.sendMessage(this.prefix + "§7Du bist der §eKingOfTheLadder§7!");
					p.playSound(p.getLocation(), Sound.ANVIL_LAND, 2.0F, 2.0F);
					p.getInventory();
					p.getInventory().setHelmet(null);
					p.getInventory().setHelmet(this.goldhelmet);
					ItemMeta meta = this.goldhelmet.getItemMeta();
					meta.setDisplayName("§aKingOfTheLadder");
					this.goldhelmet.setItemMeta(meta);
					p.updateInventory();
				}
		}
		if ((!this.kotl.contains(p.getName())) &&
				(p.getInventory().getHelmet().getType() == Material.GOLD_HELMET)) {
			
			p.getInventory();
			p.getInventory().setHelmet(goldhelmet);
		}
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (this.kotl.contains(p.getName())) {
			this.kotl.remove(p.getName());
			p.getInventory();
			p.getInventory().setHelmet(null);
		}
	}
}