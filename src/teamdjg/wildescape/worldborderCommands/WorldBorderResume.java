package teamdjg.wildescape.worldborderCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;

public class WorldBorderResume extends SubCommand{

	Main mainclass;
	
	public WorldBorderResume(Main plugin)
	{
		this.mainclass = plugin;
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if(sender instanceof Player)
		{
			if(!sender.hasPermission(mainclass.borderresumePermission))
			{
				sender.sendMessage(mainclass.pluginPrefix + ChatColor.DARK_RED + "ERROR: you don't have permission for this plugin.");
			}
		}
		
		if(mainclass.GameRunning == false)
		{
			if (sender instanceof Player) 
			{
				sender.sendMessage(mainclass.ChatLine());
				sender.sendMessage(mainclass.pluginPrefix + ChatColor.DARK_RED + "ERROR:" + ChatColor.GOLD + "You first need to start the game before you can resume it.");
				sender.sendMessage(mainclass.ChatLine());
			}
			else
			{
				System.out.println(mainclass.pluginPrefix + ChatColor.DARK_RED + "ERROR:" + ChatColor.GOLD + "You first need to start the game before you can resume it.");
			}
		}
		
		if(mainclass.MakeBorderSmallerOnMidNight == false)
		{
			mainclass.getServer().broadcastMessage(mainclass.pluginPrefix + ChatColor.GOLD + "Game resumed!");
			mainclass._WorldborderMechanics.BorderResumeMoving();
		}
		else
		{
			if (sender instanceof Player) 
			{
				sender.sendMessage(mainclass.ChatLine());
				sender.sendMessage(mainclass.pluginPrefix + ChatColor.DARK_RED + "ERROR:" + ChatColor.GOLD + "You can only resume the game when its pauzed.");
				sender.sendMessage(mainclass.ChatLine());
			}
			else
			{
				System.out.println(mainclass.pluginPrefix + ChatColor.DARK_RED + "ERROR:" + ChatColor.GOLD + "You can only resume the game when its pauzed.");
			}
		}
	}

	@Override
	public String name() {
		return "resume";
	}

	@Override
	public String info() {
		return "Resumes the game and players will be able to play further.";
	}

	@Override
	public String usage() {
		return "";
	}

	@Override
	public String[] aliases() {
		return new String[] {"r", "restart"};
	}

}
