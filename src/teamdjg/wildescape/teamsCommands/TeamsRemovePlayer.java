package teamdjg.wildescape.teamsCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;
import teamdjg.wildescape.teams.Teams;

public class TeamsRemovePlayer extends SubCommand {

	Main main;

	public TeamsRemovePlayer(Main plugin)
	{
		this.main = plugin;
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if(args.length == 2)
		{
			String playerName = args [1];
			Player target = Bukkit.getServer().getPlayerExact(playerName);
			
			if(target == null)
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Player not found.");
			}
			else
			{
				Teams.removeFromTeam(target);
				sender.sendMessage(main.pluginPrefix + ChatColor.GREEN + "Player " + target.getName() + " removed from their team.");
			}
		}
		else
		{
			sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Invaild arguments.");
		}
	}

	@Override
	public String name() {
		return "teamremove";
	}

	@Override
	public String info() {
		return "Removes a player from their joined team.";
	}

	@Override
	public String usage() {
		return "[player]";
	}

	@Override
	public String[] aliases() {
		return new String[] {""};
	}

}
