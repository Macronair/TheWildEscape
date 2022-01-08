package teamdjg.wildescape.carepackage.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.carepackage.CarePackageProfile;
import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;

public class RemoveLootFromCarepackage extends SubCommand{

	Main main;
	
	public RemoveLootFromCarepackage(Main main)
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
			int itemIndex = Integer.parseInt(args[2]);
			
			CarePackageProfile profile = main.carepackageManager.getCarePackageProfileByName(profileName);

			if(profile == null)
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "A carepackage profile with that name does not exist");
				return;
			}

			if(main.getConfig().contains("carepackageprofiles." + profileName + ".itemslist") == false)
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "No items found in: " + ChatColor.DARK_RED + profileName);
				return;
			}
			
			if(main.getConfig().contains("carepackageprofiles." + profileName + ".itemslist." + itemIndex) == false)
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "That index does not exist");
				return;
			}
			
			main.getConfig().set("carepackageprofiles." + profileName + ".itemslist." + itemIndex, null);
			
			main.saveConfig();
			main.carepackageManager.loadProfilesFromConfig(main);
			
			sender.sendMessage(main.pluginPrefix + ChatColor.GOLD + "Item removed from profile: " + ChatColor.DARK_RED + profileName);
		}
		catch(Exception e)
		{
			sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Wrong format in arguments");
		}
	}

	@Override
	public String name() {
		return "RemoveLootFromCarepackage";
	}

	@Override
	public String info() {
		return "removes a item from the carepackage";
	}

	@Override
	public String usage() {
		return "[profile name] [item index]";
	}

	@Override
	public String[] aliases() {
		return new String[] {"RLFC"};
	}

}
