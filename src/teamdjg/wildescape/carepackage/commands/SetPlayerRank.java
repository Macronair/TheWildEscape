package teamdjg.wildescape.carepackage.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.carepackage.PlayerRank;
import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;

public class SetPlayerRank extends SubCommand {

	Main main;
	
	public SetPlayerRank(Main main)
	{
		this.main = main;
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		
		if(args.length != 3)
		{
			sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Invalid arguments, you need atleast 2 arguments");
			return;
		}
		
		try
		{

			String playerName = args[1];
			PlayerRank rank = PlayerRank.valueOf(args[2]);
			
			Player player = null;
			
			for(Player players : Bukkit.getOnlinePlayers())
			{
				if(players.getName().equals(playerName))
				{
					player = players;
				}
			}
			
			if(player == null)
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Player not found");
				return;
			}
			
			main.playerRanks.replace(player.getUniqueId(), rank);
			main.getConfig().set("PlayerRanks." + player.getUniqueId(), rank.toString());
			
			main.saveConfig();

			
			sender.sendMessage(main.pluginPrefix + ChatColor.DARK_RED + playerName + "'s " + ChatColor.GOLD + "rank is changed to " + ChatColor.DARK_RED + rank.toString());
		}
		catch(Exception e)
		{
			sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Wrong format in arguments");
		}
		
	}

	@Override
	public String name() {
		return "SetPlayerRank";
	}

	@Override
	public String info() {
		return "Chance the eank of a player";
	}

	@Override
	public String usage() {
		return "[player name] [rank]";
	}

	@Override
	public String[] aliases() {
		return new String[] {"SPR"};
	}

}
