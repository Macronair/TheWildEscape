package teamdjg.wildescape.carepackage.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.carepackage.CarePackageProfile;
import teamdjg.wildescape.carepackage.PlayerRank;
import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;

public class CreateCarepackageProfile extends SubCommand {

	Main main;
	
	public CreateCarepackageProfile(Main main)
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

			String name = args[1];
			String openingsrank = args[2];
			
			CarePackageProfile profile = main.carepackageManager.getCarePackageProfileByName(name);
			
			if(profile != null)
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "A carepackage profile with that name already exist");
				return;
			}
			
			PlayerRank rank = PlayerRank.valueOf(openingsrank);
			
			main.getConfig().set("carepackageprofiles." + name + ".openingrank", rank.toString());
			main.saveConfig();
			main.carepackageManager.loadProfilesFromConfig(main);
			
			sender.sendMessage(main.pluginPrefix + ChatColor.GOLD + "New profile made named: " + ChatColor.DARK_RED + name + ChatColor.GOLD + " with openings rank: " + ChatColor.DARK_RED + rank.toString());
		}
		catch(Exception e)
		{
			sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Wrong format in arguments");
		}
		
		
	}

	@Override
	public String name() {
		return "CreateCarepackageProfile";
	}

	@Override
	public String info() {
		return "creates a new profile for dropping carepackages";
	}

	@Override
	public String usage() {
		return "[name] [openings rank]";
	}

	@Override
	public String[] aliases() {
		return new String[] {"CCP"};
	}

}
