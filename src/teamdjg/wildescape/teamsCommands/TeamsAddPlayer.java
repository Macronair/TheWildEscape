package teamdjg.wildescape.teamsCommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;
import teamdjg.wildescape.teams.Teams;
import teamdjg.wildescape.teams.TeamsType;

public class TeamsAddPlayer extends SubCommand {

	Main main;

	public TeamsAddPlayer(Main plugin)
	{
		this.main = plugin;
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if(args.length == 3)
		{
			String playerName = args [1];
			String playerTeam = args [2];
			Player target = Bukkit.getServer().getPlayerExact(playerName);
			
			if(target == null)
			{
				sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Player not found.");
			}
			else
			{
				switch (playerTeam) {
				case "guards":
					Teams.addToTeam(TeamsType.GUARD, target);
					sender.sendMessage(main.pluginPrefix + ChatColor.GREEN +
						"Player " + target.getName() + " succesfully added to the Guards.");
					break;
				case "blue":
					Teams.addToTeam(TeamsType.BLUE, target);
					sender.sendMessage(main.pluginPrefix + ChatColor.GREEN +
						"Player " + target.getName() + " succesfully added to team BLUE.");
					break;
				case "green":
					Teams.addToTeam(TeamsType.GREEN, target);
					sender.sendMessage(main.pluginPrefix + ChatColor.GREEN +
						"Player " + target.getName() + " succesfully added to team GREEN.");
				break;
				case "red":
					Teams.addToTeam(TeamsType.RED, target);
					sender.sendMessage(main.pluginPrefix + ChatColor.GREEN +
						"Player " + target.getName() + " succesfully added to team RED.");
					break;
				case "yellow":
					Teams.addToTeam(TeamsType.YELLOW, target);
					sender.sendMessage(main.pluginPrefix + ChatColor.GREEN +
						"Player " + target.getName() + " succesfully added to team YELLOW.");
					break;
				case "pink":
					Teams.addToTeam(TeamsType.PINK, target);
					sender.sendMessage(main.pluginPrefix + ChatColor.GREEN +
						"Player " + target.getName() + " succesfully added to team PINK.");
					break;
				case "purple":
					Teams.addToTeam(TeamsType.PURPLE, target);
					sender.sendMessage(main.pluginPrefix + ChatColor.GREEN +
						"Player " + target.getName() + " succesfully added to team PURPLE.");
					break;
				}
			}
		}
		else if (args.length == 3)
		{
			sender.sendMessage(main.pluginPrefix + ChatColor.GRAY + "Available teams:");
			sender.sendMessage(main.pluginPrefix + 
					ChatColor.DARK_RED + "Guard" + ChatColor.GRAY + ", " +
					ChatColor.BLUE + "Blue" + ChatColor.GRAY + ", " +
					ChatColor.GREEN + "Green" + ChatColor.GRAY + ", " +
					ChatColor.RED + "Red" + ChatColor.GRAY + ", " +
					ChatColor.YELLOW + "Yellow" + ChatColor.GRAY + ", " +
					ChatColor.LIGHT_PURPLE + "Pink" + ChatColor.GRAY + ", " +
					ChatColor.DARK_PURPLE + "Purple");
		}
		else
		{
			sender.sendMessage(main.pluginPrefix + ChatColor.RED + "Invaild arguments.");
		}
		
		
	}

	@Override
	public String name() {
		return "teamadd";
	}

	@Override
	public String info() {
		return "Add a player to a certain team.";
	}

	@Override
	public String usage() {
		return "[player] [team]";
	}

	@Override
	public String[] aliases() {
		return new String[] {""};
	}

}
