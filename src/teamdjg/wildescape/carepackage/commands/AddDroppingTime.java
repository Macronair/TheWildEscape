package teamdjg.wildescape.carepackage.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.carepackage.CarePackageProfile;
import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;

public class AddDroppingTime extends SubCommand {

	Main main;
	
	public AddDroppingTime(Main main)
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
		
		if(args.length != 3)
		{
			sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Invalid arguments, you need atleast 2 arguments");
			return;
		}
		
		try
		{

			String profileName = args[1];
			int time = Integer.valueOf(args[2]);
			
			CarePackageProfile profile = main.carepackageManager.getCarePackageProfileByName(profileName);

			if(profile == null)
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "A carepackage profile with that name does not exist");
				return;
			}
			
			if(time < 0 || time > 24000)
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Amount can't be 0 or above 24000");
				return;
			}

			ArrayList<Integer> droppingTimings = new ArrayList<>();
			
			for(CarePackageProfile currentProfile : main.carepackageManager.carePackageProfiles)
			{
				for(int timeInprofile : currentProfile.droppingsTimings)
				{
					droppingTimings.add(timeInprofile);
				}
			}
			
			if(droppingTimings.contains(time))
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "There is already a profile with that dropping time");
				return;
			}
			
			List<Integer> timings = new ArrayList<>();
			
			if(main.getConfig().contains("carepackageprofiles." + profileName + ".droppingstimings"))
			{
				List<Integer> loadedTimings = main.getConfig().getIntegerList("carepackageprofiles." + profileName + ".droppingstimings");
				for(int i : loadedTimings)
				{
					timings.add(i);
				}
			}
			
			timings.add(time);
			
			main.getConfig().set("carepackageprofiles." + profileName + ".droppingstimings", timings);
			main.saveConfig();
			main.carepackageManager.loadProfilesFromConfig(main);
			
			sender.sendMessage(main.pluginPrefix + ChatColor.GOLD + "Droppings time " + ChatColor.DARK_RED + time + ChatColor.GOLD + " added in profile: " + ChatColor.DARK_RED + profileName);
		}
		catch(Exception e)
		{
			sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Wrong format in arguments");
		}
	}

	@Override
	public String name() {
		return "AddDroppingTime";
	}

	@Override
	public String info() {
		return "adds a dropping time to a profile";
	}

	@Override
	public String usage() {
		return "[profile name] [dropping time: (0-24000)]";
	}

	@Override
	public String[] aliases() {
		return new String[] {"ADT"};
	}

}
