package teamdjg.wildescape.carepackage.commands;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;

public class CarepackageProfileList extends SubCommand{

	Main main;
	
	public CarepackageProfileList(Main main)
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
		
		if(main.getConfig().getConfigurationSection("carepackageprofiles").getKeys(false).size() == 0)
		{
			sender.sendMessage(main.pluginPrefix + ChatColor.GOLD + "There are no profiles to list");
		}
		else
		{
			Set<String> profileNames = main.getConfig().getConfigurationSection("carepackageprofiles").getKeys(false);
			
			sender.sendMessage(ChatColor.GOLD + "<----- " + ChatColor.DARK_RED + "Profiles" + ChatColor.GOLD + " ----->");
			for(String profileName : profileNames)
			{
				sender.sendMessage(ChatColor.DARK_RED + profileName);
			}
			sender.sendMessage(ChatColor.GOLD + "<------------------>");
		}
		
	}

	@Override
	public String name() {
		return "CarepackageProfileList";
	}

	@Override
	public String info() {
		return "returns all carepackage profiles";
	}

	@Override
	public String usage() {
		return "";
	}

	@Override
	public String[] aliases() {
		return new String[] {"CPL"};
	}

}
