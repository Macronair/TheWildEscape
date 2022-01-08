package teamdjg.wildescape.carepackage.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.carepackage.CarePackageProfile;
import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;

public class RemoveCarepackageProfile extends SubCommand{

	Main main;
	
	public RemoveCarepackageProfile(Main main)
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

			String name = args[1];	
			CarePackageProfile profile = main.carepackageManager.getCarePackageProfileByName(name);
			
			if(profile == null)
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "A carepackage profile with that name does not exist");
				return;
			}

			main.getConfig().set("carepackageprofiles." + name, null);
			main.saveConfig();
			main.carepackageManager.loadProfilesFromConfig(main);
			
			sender.sendMessage(main.pluginPrefix + ChatColor.GOLD + "Profile removed");
		}
		catch(Exception e)
		{
			sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Wrong format in arguments");
		}
	}

	@Override
	public String name() {
		return "RemoveCarepackageProfile";
	}

	@Override
	public String info() {
		return "removes a existing profile";
	}

	@Override
	public String usage() {
		return "[profile name]";
	}

	@Override
	public String[] aliases() {
		return new String[] {"RCP"};
	}

}
