package teamdjg.wildescape.carepackage.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.carepackage.CarePackageProfile;
import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;

public class RemoveAllLoot extends SubCommand{

	Main main;
	
	public RemoveAllLoot(Main main)
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

			if(main.getConfig().contains("carepackageprofiles." + profileName + ".itemslist") == false)
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "There are no loot items to remove");
				return;
			}
			
			main.getConfig().set("carepackageprofiles." + profileName + ".itemslist", null);
			
			main.saveConfig();
			main.carepackageManager.loadProfilesFromConfig(main);
			
			sender.sendMessage(main.pluginPrefix + ChatColor.GOLD + "All items remove from profile: " + ChatColor.DARK_RED + profileName);
		}
		catch(Exception e)
		{
			sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Wrong format in arguments");
		}
		
	}

	@Override
	public String name() {
		return "RemoveAllLoot";
	}

	@Override
	public String info() {
		return "removes all loot from a carepackage profile";
	}

	@Override
	public String usage() {
		return "[profile name]";
	}

	@Override
	public String[] aliases() {
		return new String[] {"RAL"};
	}

}
