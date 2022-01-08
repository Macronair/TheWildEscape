package teamdjg.wildescape.carepackage.commands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.carepackage.CarePackageProfile;
import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;

public class GetLootList extends SubCommand{

	Main main;
	
	public GetLootList(Main main)
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
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "No items found in: " + ChatColor.DARK_RED + profileName);
				return;
			}
			
			if(main.getConfig().getConfigurationSection("carepackageprofiles." + profileName + ".itemslist").getKeys(false).size() == 0)
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "No items found in: " + ChatColor.DARK_RED + profileName);
				return;
			}
			
			Set<String> itemIndexes = main.getConfig().getConfigurationSection("carepackageprofiles." + profileName + ".itemslist").getKeys(false);
			
			sender.sendMessage(ChatColor.GOLD + "<----- " + ChatColor.DARK_RED + "Loot items" + ChatColor.GOLD + " ----->");
			for(String configName : itemIndexes)
			{
				
				String material = main.getConfig().getString("carepackageprofiles." + profileName + ".itemslist." + configName + ".material");
				String amount = main.getConfig().getString("carepackageprofiles." + profileName + ".itemslist." + configName + ".amount");
				String amountChance = main.getConfig().getString("carepackageprofiles." + profileName + ".itemslist." + configName + ".amountchance");
					
				if(material != null && amount != null && amountChance != null)
				{
					sender.sendMessage(ChatColor.GOLD + "index: " + ChatColor.DARK_RED + configName + ChatColor.GOLD + ", " + ChatColor.DARK_RED + material + ChatColor.GOLD + ", " + ChatColor.DARK_RED + amount + ChatColor.GOLD + " times, chance of: " + ChatColor.DARK_RED + amountChance);
				}

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
		return "GetLootList";
	}

	@Override
	public String info() {
		return "names all loot items in a profile";
	}

	@Override
	public String usage() {
		return "[profile name]";
	}

	@Override
	public String[] aliases() {
		return new String[]{"GLL"};
	}

}
