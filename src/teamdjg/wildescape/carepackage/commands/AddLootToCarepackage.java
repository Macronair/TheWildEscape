package teamdjg.wildescape.carepackage.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.carepackage.CarePackageProfile;
import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;

public class AddLootToCarepackage extends SubCommand{

	Main main;
	
	public AddLootToCarepackage(Main main)
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
		
		if(args.length != 5)
		{
			sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Invalid arguments, you need atleast 4 arguments");
			return;
		}
		
		try
		{

			String profileName = args[1];
			String material = Material.valueOf(args[2]).toString();
			int amount = Integer.valueOf(args[3]);
			double amountChance = Double.valueOf(args[4]);
			
			CarePackageProfile profile = main.carepackageManager.getCarePackageProfileByName(profileName);

			if(profile == null)
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "A carepackage profile with that name does not exist");
				return;
			}
			
			if(amount > 64 || amount <= 0)
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Amount can't be above 64 or be 0");
				return;
			}
			
			if(amountChance > 1 || amountChance < 0)
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Amount chance can't be above 1 or below 0");
				return;
			}
			
			int itemIndex;
			
			if(main.getConfig().contains("carepackageprofiles." + profileName + ".itemslist.itemindex") == false)
			{			
				main.getConfig().set("carepackageprofiles." + profileName + ".itemslist.itemindex", 0);
			}
			
			itemIndex = main.getConfig().getInt("carepackageprofiles." + profileName + ".itemslist.itemindex");
			
			main.getConfig().set("carepackageprofiles." + profileName + ".itemslist." + itemIndex + ".material", material.toString());
			main.getConfig().set("carepackageprofiles." + profileName + ".itemslist." + itemIndex + ".amount", amount);
			main.getConfig().set("carepackageprofiles." + profileName + ".itemslist." + itemIndex + ".amountchance", amountChance);
			
			main.getConfig().set("carepackageprofiles." + profileName + ".itemslist.itemindex", itemIndex+1);
			
			main.saveConfig();
			main.carepackageManager.loadProfilesFromConfig(main);
			
			sender.sendMessage(main.pluginPrefix + ChatColor.GOLD + "New item added to profile: " + ChatColor.DARK_RED + profileName);
		}
		catch(Exception e)
		{
			sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Wrong format in arguments");
		}
	}

	@Override
	public String name() {
		return "AddLootToCarepackage";
	}

	@Override
	public String info() {
		return "adds a loot item to a carepackage profile";
	}

	@Override
	public String usage() {
		return "[profile name] [material] [amount: between 1-64] [amount chance: between 0-1]";
	}

	@Override
	public String[] aliases() {
		return new String[] {"ALTC"};
	}

}
