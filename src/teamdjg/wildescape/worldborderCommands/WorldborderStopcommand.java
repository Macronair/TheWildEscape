package teamdjg.wildescape.worldborderCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.main.Main;

public class WorldborderStopcommand implements CommandExecutor {
	
	Main mainclass;
	
	public WorldborderStopcommand(Main plugin)
	{
		this.mainclass = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {		
		
		if(sender instanceof Player)
		{
			if(!sender.hasPermission(mainclass.borderstopPermission))
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
				sender.sendMessage(mainclass.pluginPrefix + ChatColor.DARK_RED + "ERROR:" + ChatColor.GOLD + "You can only stop a game if its running.");
				sender.sendMessage(mainclass.ChatLine());
			}
			else
			{
				System.out.println(mainclass.pluginPrefix + ChatColor.DARK_RED + "ERROR:" + ChatColor.GOLD + "You can only stop a game if its running.");
			}
			return true;
		}
		
		mainclass.getServer().getScheduler().cancelTasks(mainclass);
		mainclass._WorldborderMechanics.BorderReset();
		mainclass.GameRunning = false;
		mainclass.getServer().broadcastMessage(mainclass.pluginPrefix + ChatColor.GOLD + "The game has now been stopped.");

		
		return true;
	}

}
