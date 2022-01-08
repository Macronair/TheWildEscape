package teamdjg.wildescape.carepackage.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.carepackage.CarePackageProfile;
import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;

public class SetDroppingChance extends SubCommand{

	Main main;
	
	public SetDroppingChance(Main main)
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
			Double amount = Double.valueOf(args[2]);
			
			CarePackageProfile profile = main.carepackageManager.getCarePackageProfileByName(profileName);

			if(profile == null)
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "A carepackage profile with that name does not exist");
				return;
			}
			
			if(amount < 0 || amount > 1)
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Amount can't below 0 or above 1");
				return;
			}

			main.getConfig().set("carepackageprofiles." + profileName + ".droppingsamountchance", amount);
			main.saveConfig();
			main.carepackageManager.loadProfilesFromConfig(main);
			
			sender.sendMessage(main.pluginPrefix + ChatColor.GOLD + "Droppings chance updated in profile:" + ChatColor.DARK_RED + profileName);
		}
		catch(Exception e)
		{
			sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Wrong format in arguments");
		}
	}

	@Override
	public String name() {
		return "SetDroppingChance";
	}

	@Override
	public String info() {
		return "sets the dropping chance for a profile";
	}

	@Override
	public String usage() {
		return "[profile name] [amount: (0-1)]";
	}

	@Override
	public String[] aliases() {
		return new String[] {"SDC"};
	}

}
