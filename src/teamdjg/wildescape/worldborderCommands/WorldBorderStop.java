package teamdjg.wildescape.worldborderCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;

public class WorldBorderStop extends SubCommand{

	Main mainclass;
	
	public WorldBorderStop(Main plugin)
	{
		this.mainclass = plugin;
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if(sender instanceof Player)
		{
			if(!sender.hasPermission(mainclass.borderstopPermission))
			{
				sender.sendMessage(mainclass.pluginPrefix + ChatColor.DARK_RED + "ERROR: you don't have permission for this plugin.");
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
		}
		
		mainclass.getServer().getScheduler().cancelTasks(mainclass);
		mainclass._WorldborderMechanics.BorderReset();
		mainclass.GameRunning = false;
		mainclass.getServer().broadcastMessage(mainclass.pluginPrefix + ChatColor.GOLD + "The game has now been stopped.");
	}

	@Override
	public String name() {
		return "stop";
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return "Stops the game completely.";
	}

	@Override
	public String usage() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String[] aliases() {
		// TODO Auto-generated method stub
		return new String[] {""};
	}

}
