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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import teamdjg.wildescape.carepackage.CarePackageProfile;
import teamdjg.wildescape.carepackage.PlayerRank;
import teamdjg.wildescape.teams.Teams;
import teamdjg.wildescape.teams.TeamsType;

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
		
		// Check if player is already in a team.
		if(main.getConfig().contains("Team." + e.getPlayer().getUniqueId()))
		{
			main.playerTeams.put(e.getPlayer().getUniqueId(), TeamsType.valueOf(main.getConfig().getString("Team." + e.getPlayer().getUniqueId())));
		}
		else
		{
			main.playerTeams.put(e.getPlayer().getUniqueId(), TeamsType.SPECTATOR);
			main.getConfig().set("Team." + e.getPlayer().getUniqueId(), TeamsType.SPECTATOR);
			main.saveConfig();
		}
		
		Teams.reloadTeams(main, main.playerTeams);
		e.getPlayer().setScoreboard(Teams.twe);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e)
	{
		if(main.playerRanks.containsKey(e.getPlayer().getUniqueId()))
		{
			main.playerRanks.remove(e.getPlayer().getUniqueId());
		}
		
		if(main.playerTeams.containsKey(e.getPlayer().getUniqueId()))
		{
			main.playerTeams.remove(e.getPlayer().getUniqueId());
		}
		
		Player player = e.getPlayer();
		player.setScoreboard(Teams.twe);
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
		StringBuilder chatPrefix = new StringBuilder();
		
		//if(main.playerRanks.containsKey(e.getPlayer().getUniqueId()))
		//{
		//	switch(main.playerRanks.get(e.getPlayer().getUniqueId()))
		//	{
		//	case ALFAWOLF:
		//		prefix = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "Alfawolf" + ChatColor.DARK_GRAY + "]" + ChatColor.RESET;
		//		break;
		//	case HUNTERS:
		//		prefix = ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Hunter" + ChatColor.DARK_GRAY + "]" + ChatColor.RESET;
		//		break;
		//	
		//	default:
		//		prefix = "[NoClass]"; 
		//		break;
		//	}
		//}
		if (Teams.getTeamType(e.getPlayer()) == null) {
			prefix = ChatColor.GRAY + "[" + ChatColor.WHITE + "Speler" + ChatColor.GRAY + "]";
		}
		else
		{
			switch(Teams.getTeamType(e.getPlayer())) {
			case GUARD:
				prefix = Teams.GuardPrefix + ChatColor.WHITE;
				break;
			case BLUE:
				prefix = Teams.BluePrefix;
				break;
			case GREEN:
				prefix = Teams.GreenPrefix;
				break;
			case YELLOW:
				prefix = Teams.YellowPrefix;
				break;
			case RED:
				prefix = Teams.RedPrefix;
				break;
			case PINK:
				prefix = Teams.PinkPrefix;
				break;
			case PURPLE:
				prefix = Teams.PurplePrefix;
				break;
			case SPECTATOR:
				prefix = Teams.SpectatorPrefix;
			}
		}
		chatPrefix.append(prefix);
		chatPrefix.append(" ");
		
		e.setFormat(chatPrefix.toString() + e.getPlayer().getDisplayName() + ": " + e.getMessage());
		
		for(Player recievingPlayer : e.getRecipients())
		{
			if(e.getPlayer().getLocation().distance(recievingPlayer.getLocation()) > main.chatHearingDistance && main.chatHearingDistance != 0)
			{
				e.getRecipients().remove(recievingPlayer);
				recievingPlayer.sendMessage(ChatColor.RED + "Someone said: " + ChatColor.WHITE + e.getMessage());
			}
		}
	}
	
	@EventHandler
	public void OnDeath(PlayerDeathEvent e) {
		Player killer = e.getEntity().getKiller();
		Player loser = e.getEntity().getPlayer();
		
		StringBuilder prKiller = new StringBuilder();
		StringBuilder prLoser = new StringBuilder();
		StringBuilder deathMessage = new StringBuilder();
		
		switch(Teams.getTeamType(killer)) {
		case GUARD:
			prKiller.append(Teams.GuardPrefix);
			break;
		case BLUE:
			prKiller.append(Teams.BluePrefix);
			break;
		case GREEN:
			prKiller.append(Teams.GreenPrefix);
			break;
		case YELLOW:
			prKiller.append(Teams.YellowPrefix);
			break;
		case RED:
			prKiller.append(Teams.RedPrefix);
			break;
		case PINK:
			prKiller.append(Teams.PinkPrefix);
			break;
		case PURPLE:
			prKiller.append(Teams.PurplePrefix);
			break;
		default:
			prKiller.append("[Speler] ");
			break;	
		}
		prKiller.append(" ");
		prKiller.append(killer.getDisplayName());
		
		switch(Teams.getTeamType(loser)) {
		case GUARD:
			prLoser.append(Teams.GuardPrefix);
			break;
		case BLUE:
			prLoser.append(Teams.BluePrefix);
			break;
		case GREEN:
			prLoser.append(Teams.GreenPrefix);
			break;
		case YELLOW:
			prLoser.append(Teams.YellowPrefix);
			break;
		case RED:
			prLoser.append(Teams.RedPrefix);
			break;
		case PINK:
			prLoser.append(Teams.PinkPrefix);
			break;
		case PURPLE:
			prLoser.append(Teams.PurplePrefix);
			break;
		default:
			prLoser.append("[Speler]");
			break;	
		}
		prLoser.append(" ");
		prLoser.append(loser.getDisplayName());
		
		deathMessage.append(prLoser);
		deathMessage.append(" is vermoord door ");
		deathMessage.append(prKiller);
		
		e.setDeathMessage(deathMessage.toString());
	}
}
