package teamdjg.wildescape.carepackage.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.carepackage.CarePackageProfile;
import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;

public class DroppingTimeList extends SubCommand {

	Main main;
	
	public DroppingTimeList(Main main)
	{
		this.main = main;
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		
		if( !(sender instanceof Player))
		{
			sender.sendMessage(main.pluginPrefix + "Only a player can perform this command");
			return;
		}
		
		if(args.length != 2)
		{
			sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Invalid arguments, you need atleast 1 arguments");
			return;
		}
		
		try
		{
			String profileName = args[1];
		
			CarePackageProfile profile = main.carepackageManager.getCarePackageProfileByName(profileName);
			
			if(profile == null)
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "A carepackage profile with that name does not exist");
				return;
			}
		
			if(profile.droppingsTimings.isEmpty())
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "There are no dropping timings in that profile");
				return;
			}
			
			sender.sendMessage(ChatColor.GOLD + "<------ " + ChatColor.DARK_RED + profileName + ChatColor.GOLD + " ------>");
			
			for(int timeInprofile : profile.droppingsTimings)
			{
				sender.sendMessage(ChatColor.GOLD + "Time: " + ChatColor.DARK_RED + timeInprofile);
			}
		
			sender.sendMessage(ChatColor.GOLD + "<-------------------->");
		}
		catch(Exception e)
		{
			sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Wrong format in arguments");
		}
	}

	@Override
	public String name() {
		return "DroppingTimeList";
	}

	@Override
	public String info() {
		return "Shows all the dropping timings";
	}

	@Override
	public String usage() {
		return "[profile name]";
	}

	@Override
	public String[] aliases() {
		return new String[] {"DTL"};
	}

}
