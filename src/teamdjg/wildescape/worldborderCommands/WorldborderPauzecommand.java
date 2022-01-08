package teamdjg.wildescape.worldborderCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.main.Main;

public class WorldborderPauzecommand implements CommandExecutor{

	Main mainclass;
	
	public WorldborderPauzecommand(Main plugin)
	{
		this.mainclass = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player)
		{
			if(!sender.hasPermission(mainclass.borderpauzePermission))
			{
				sender.sendMessage(mainclass.pluginPrefix + ChatColor.DARK_RED + "ERROR: you don't have permission for this plugin.");
				return true;
			}
		}
		
		if(mainclass.GameRunning == false)
		{
			if (sender instanceof Player) 
			{
				sender.sendMessage(mainclass.ChatLine());
				sender.sendMessage(mainclass.pluginPrefix + ChatColor.DARK_RED + "ERROR:" + ChatColor.GOLD + "You first need to start the game before you can pauze it.");
				sender.sendMessage(mainclass.ChatLine());
			}
			else
			{
				System.out.println(mainclass.pluginPrefix + ChatColor.DARK_RED + "ERROR:" + ChatColor.GOLD + "You first need to start the game before you can pauze it.");
			}
			
			return true;
		}
		
		if(mainclass.MakeBorderSmallerOnMidNight == true)
		{
			mainclass.getServer().broadcastMessage(mainclass.pluginPrefix + ChatColor.GOLD + "The game is now pauzed.");
			mainclass._WorldborderMechanics.BorderStopMoving();
		}
		else
		{
			if (sender instanceof Player) 
			{
				sender.sendMessage(mainclass.ChatLine());
				sender.sendMessage(mainclass.pluginPrefix + ChatColor.DARK_RED + "ERROR:" + ChatColor.GOLD + "You can only pauze the game when its not already pauzed.");
				sender.sendMessage(mainclass.ChatLine());
			}
			else
			{
				System.out.println(mainclass.pluginPrefix + ChatColor.DARK_RED + "ERROR:" + ChatColor.GOLD + "You can only pauze the game when its not already pauzed.");
			}
		}
		return true;	
	}
}
