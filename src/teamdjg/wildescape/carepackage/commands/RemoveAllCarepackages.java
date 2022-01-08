package teamdjg.wildescape.carepackage.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;

public class RemoveAllCarepackages extends SubCommand{

	Main main;
	
	public RemoveAllCarepackages(Main main)
	{
		this.main = main;
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		
		main.carepackageManager.removeAllCarepackages();
		sender.sendMessage(main.pluginPrefix + ChatColor.GOLD + "All carepackages removed");
	}

	@Override
	public String name() {
		return "RemoveAllCarepackages";
	}

	@Override
	public String info() {
		return "removes all the carepackages";
	}

	@Override
	public String usage() {
		return "";
	}

	@Override
	public String[] aliases() {
		return new String[] {"RAC"};
	}

}
