package teamdjg.wildescape.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import teamdjg.wildescape.carepackage.CarePackageProfile;
import teamdjg.wildescape.carepackage.PlayerRank;

public class Eventhandler implements Listener
{
	Main main;
	
	public Eventhandler(Main main)
	{
		main.getServer().getPluginManager().registerEvents(this, main);
		this.main = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{		
		if(main.getConfig().contains("PlayerRanks." + e.getPlayer().getUniqueId()))
		{
			main.playerRanks.put(e.getPlayer().getUniqueId(), PlayerRank.valueOf(main.getConfig().getString("PlayerRanks." + e.getPlayer().getUniqueId())));
		}
		else
		{
			main.playerRanks.put(e.getPlayer().getUniqueId(), PlayerRank.HUNTERS);
			main.getConfig().set("PlayerRanks." + e.getPlayer().getUniqueId(), PlayerRank.HUNTERS.toString());
			main.saveConfig();
		}
		
		if(main.GameRunning)
		{
			e.getPlayer().setGameMode(GameMode.SPECTATOR);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		if(main.playerRanks.containsKey(e.getPlayer().getUniqueId()))
		{
			main.playerRanks.remove(e.getPlayer().getUniqueId());
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e)
	{				
		
		if(main.GameRunning)
		{
			Player p = e.getPlayer();
			main.getServer().getScheduler().runTaskLater(main, new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					p.getInventory().clear();
					
					Location tpLoc = main.WorldCenter;
					int topBlock = tpLoc.getWorld().getHighestBlockYAt(main.WorldCenter);
					tpLoc.setY(topBlock+1);
					
					p.teleport(tpLoc);
					p.setGameMode(GameMode.SPECTATOR);
				}
			}, 1);
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
		Block placedBlock = e.getBlockPlaced();

		if(placedBlock.getType() == Material.CHEST)
		{
			int Y = placedBlock.getY();
			
			List<Block> seroundingBlock = new ArrayList<>();
			
			for (int X = -1; X < 2; X++) {
				for (int Z = -1; Z < 2; Z++) {
					
					if(X != 0 || Z != 0)
					{
						Location currentLocation = new Location(placedBlock.getWorld(),placedBlock.getX() + X, Y, placedBlock.getZ() + Z);
						Block currentBlock = currentLocation.getBlock();
						
						if(currentBlock.getType() == Material.CHEST)
						{
							seroundingBlock.add(currentBlock);
						}
					}
			
				}
			}
			
			for(Block block : seroundingBlock)
			{
				Chest chest = (Chest) block.getState();
				
				if(main.carepackageManager.getCarePackageProfileByName(chest.getCustomName()) != null)
				{
					e.setCancelled(true);
				}
			}
		}
		
		if(e.getBlock().getType() == Material.DIAMOND_BLOCK)
		{
			main.carepackageManager.loadCarepackagesFromConfig();
			return;
		}
		
		Location loc = e.getBlock().getLocation();
		main.carepackageManager.saveCarepackages(loc, main.carepackageManager.setBlockToACarePackageByProfileName("test", loc));
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e)
	{
		Block block = null;
		
		try
		{
			block = e.getClickedBlock();
		}
		catch(Exception exception)
		{
			
		}
		
		
		if(block != null)
		{
			if(block.getType() == Material.CHEST)
			{
				Chest carepackage = (Chest) e.getClickedBlock().getState();
				
				CarePackageProfile profile = null;
				profile = main.carepackageManager.getCarePackageProfileByName(carepackage.getCustomName()); 
				
				if(profile != null)
				{
					
					PlayerRank playersRank = main.playerRanks.get(e.getPlayer().getUniqueId());
					PlayerRank chestRank = profile.openingsRank;
					
					if(playersRank != chestRank)
					{
						e.getPlayer().sendTitle(ChatColor.RED + "Only the " + ChatColor.WHITE + chestRank.toString(), ChatColor.RED + " can open/break this chest.", 10, 30, 20);
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e)
	{
		String prefix = null;
		
		if(main.playerRanks.containsKey(e.getPlayer().getUniqueId()))
		{
			switch(main.playerRanks.get(e.getPlayer().getUniqueId()))
			{
			case ALFAWOLF:
				prefix = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "Alfawolf" + ChatColor.DARK_GRAY + "]" + ChatColor.RESET;
				break;
			case HUNTERS:
				prefix = ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Hunter" + ChatColor.DARK_GRAY + "]" + ChatColor.RESET;
				break;
			
			default:
				prefix = "[NoClass]"; 
				break;
			}
		}
		
		e.setFormat(prefix + " " + e.getPlayer().getDisplayName() + ": " + e.getMessage());
		
		for(Player recievingPlayer : e.getRecipients())
		{
			if(e.getPlayer().getLocation().distance(recievingPlayer.getLocation()) > main.chatHearingDistance && main.chatHearingDistance != 0)
			{
				e.getRecipients().remove(recievingPlayer);
				recievingPlayer.sendMessage(ChatColor.RED + "Someone said: " + ChatColor.WHITE + e.getMessage());
			}
		}
		
		
	}
}
