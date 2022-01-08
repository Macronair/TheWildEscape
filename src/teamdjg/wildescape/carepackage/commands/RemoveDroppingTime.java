package teamdjg.wildescape.carepackage.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.carepackage.CarePackageProfile;
import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;

public class RemoveDroppingTime extends SubCommand {

	Main main;
	
	public RemoveDroppingTime(Main main)
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
			int time = Integer.valueOf(args[1]);

			if(time < 0 || time > 24000)
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Amount can't be 0 or above 24000");
				return;
			}

			ArrayList<Integer> droppingTimings = new ArrayList<>();
			
			for(CarePackageProfile profile : main.carepackageManager.carePackageProfiles)
			{
				for(int timeInprofile : profile.droppingsTimings)
				{
					droppingTimings.add(timeInprofile);
				}
			}
			
			if(!droppingTimings.contains(time))
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "That dropping time does not exist");
				return;
			}
			
			for(CarePackageProfile profile :  main.carepackageManager.carePackageProfiles)
			{
				if(profile.droppingsTimings.contains(time))
				{
					List<Integer> timings = main.getConfig().getIntegerList("carepackageprofiles." + profile.name + ".droppingstimings");
					Object timeOject = time;
					timings.remove(timeOject);
					main.getConfig().set("carepackageprofiles." + profile.name + ".droppingstimings", timings);
					sender.sendMessage(main.pluginPrefix + ChatColor.GOLD + "Droppings time " + ChatColor.DARK_RED + time + ChatColor.GOLD + " removed in profile: " + ChatColor.DARK_RED + profile.name);
				}
			}

			main.saveConfig();
			main.carepackageManager.loadProfilesFromConfig(main);
			
			
		}
		catch(Exception e)
		{
			sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Wrong format in arguments");
		}
	}

	@Override
	public String name() {
		return "RemoveDroppingTime";
	}

	@Override
	public String info() {
		return "removes a dropping time from a profile";
	}

	@Override
	public String usage() {
		return "[dropping time: (0-24000)]";
	}

	@Override
	public String[] aliases() {
		return new String[] {"RDT"};
	}

}
