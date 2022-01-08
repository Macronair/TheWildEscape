package teamdjg.wildescape.worldborderCommands;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import teamdjg.wildescape.main.Main;

public class WorldborderStartercommand implements CommandExecutor {
	
	Main mainclass;
	
	public WorldborderStartercommand(Main plugin)
	{
		this.mainclass = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {		
		
		if(sender instanceof Player)
		{
			if(!sender.hasPermission(mainclass.borderstartPermission))
			{
				sender.sendMessage(mainclass.pluginPrefix + ChatColor.DARK_RED + "ERROR: you don't have permission for this plugin.");
				return true;
			}
		}
		
		if(mainclass.WorldborderCenterCheck == false)
		{
			if (sender instanceof Player) 
			{
				sender.sendMessage(mainclass.ChatLine());
				sender.sendMessage(mainclass.pluginPrefix + ChatColor.DARK_RED + "ERROR:" + ChatColor.GOLD + "You first need to use the  - /bordercenter - before you can start the game");
				sender.sendMessage(mainclass.ChatLine());
			}
			else
			{
				System.out.println(mainclass.pluginPrefix + ChatColor.DARK_RED + "ERROR:" + ChatColor.GOLD + "You first need to use the  - /bordercenter - before you can start the game");
			}
			return true;
		}
		
		World gameWorld = mainclass.getServer().getWorld(mainclass.WorldBorderWorldName);
		
		//start game :
		
		mainclass.GameRunning = true;
		
		//change difficulty
		gameWorld.setDifficulty(Difficulty.getByValue(mainclass.gameDifficulty));
		
		//set game time
		gameWorld.setTime(mainclass.gameStartTime);
		
		//start world moving
		mainclass.getServer().getScheduler().cancelTasks(mainclass);
		mainclass.worldTime(gameWorld, mainclass);
		mainclass.MakeBorderSmallerOnMidNight = true;
		
		//start message
		mainclass.getServer().broadcastMessage(mainclass.pluginPrefix + ChatColor.GOLD + "The game has begon. The last team alive will win.");
		
		boolean tpCommandPlayer = true;
		
		if(args.length > 0)
		{
			if(args[0].equals("false"))
			{
				tpCommandPlayer = false;
			}
		}
		
		for(Player player : mainclass.getServer().getOnlinePlayers())
		{			
			//remove current potion effects
			if(player.getActivePotionEffects().isEmpty() == false)
			{
				Collection<PotionEffect> effects = new ArrayList<>();
				effects.addAll(player.getActivePotionEffects());
				
				for(PotionEffect effect : effects)
				{
					player.removePotionEffect(effect.getType());
				}
				
				effects.clear();
			}
			//-------
			
			//Spread players
			if(tpCommandPlayer == false || !(sender instanceof Player))
			{				
				if(!(player.equals(sender)))
				{
					setupPlayer(player, mainclass.getRandomLocationInBorders(gameWorld));
				}	
			}
			else
			{
				setupPlayer(player, mainclass.getRandomLocationInBorders(gameWorld));
			}
			//------
			
		}
		return true;
		
	}
	
	public void setupPlayer(Player player, Location location)
	{
		player.teleport(location);
	
		player.getInventory().clear();
		player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF,5));
		player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1200, 50));
		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 19200, 4));
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1200, 4));
		
		player.setHealth(20);
		player.setFoodLevel(20);
		player.setGameMode(GameMode.SURVIVAL);
	}

}
