package teamdjg.wildescape.carepackage.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;

public class SetChatHearingDistance extends SubCommand {

	Main main;
	
	public SetChatHearingDistance(Main main)
	{
		this.main = main;
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		
		int distance;
		if(args.length == 1)
		{
			sendMessage(sender,ChatColor.RED + "You need at least 1 argument");
		}
		else
		{
			try
			{
				distance = Integer.parseInt(args[1]);
			}
			catch(Exception e)
			{
				sendMessage(sender,ChatColor.RED + "Wrong format. you need to fill in a number");
				return;
			}
			
			main.chatHearingDistance = distance;
			main.getConfig().set("chathearingdistance", distance);
			main.saveConfig();
			sendMessage(sender,ChatColor.GOLD + "Chat hearing distance set to: " + ChatColor.DARK_RED + distance);
		}
		
	}

	private void sendMessage(CommandSender sender, String message)
	{
		if(sender instanceof Player)
		{
			sender.sendMessage(main.pluginPrefix + message);
		}
		else
		{
			System.out.println(main.pluginPrefix + message);
		}
	}
	
	@Override
	public String name() {
		return "ChatDistance";
	}

	@Override
	public String info() {
		return "sets the chat hearing distance";
	}

	@Override
	public String[] aliases() {
		return new String[] {"cd"};
	}

	@Override
	public String usage() {
		return "[number: hearing distance. type 0 for infinite]";
	}

}
