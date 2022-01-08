package teamdjg.wildescape.carepackage.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import teamdjg.wildescape.carepackage.PlayerRank;
import teamdjg.wildescape.commandHandler.SubCommand;

public class PlayerRanks extends SubCommand {

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		
		PlayerRank[] ranks = PlayerRank.values();
		
		sender.sendMessage(ChatColor.GOLD + "<------ " + ChatColor.DARK_RED + "Ranks" + ChatColor.GOLD + " ------>");
		for(PlayerRank rank : ranks)
		{
			sender.sendMessage(ChatColor.DARK_RED + rank.toString());
		}
		sender.sendMessage(ChatColor.GOLD + "<------------------>");
	}

	@Override
	public String name() {
		return "PlayerRanks";
	}

	@Override
	public String info() {
		return "displays all player ranks";
	}

	@Override
	public String usage() {
		return "";
	}

	@Override
	public String[] aliases() {
		return new String[] {"PR"};
	}

}
