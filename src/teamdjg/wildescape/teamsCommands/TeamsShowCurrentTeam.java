package teamdjg.wildescape.teamsCommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;
import teamdjg.wildescape.teams.Teams;

public class TeamsShowCurrentTeam extends SubCommand {

	Main main;

	public TeamsShowCurrentTeam(Main plugin)
	{
		this.main = plugin;
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		sender.sendMessage(main.pluginPrefix + " Your current team: " + Teams.getTeamType((Player)sender));
	}

	@Override
	public String name() {
		return "teamshow";
	}

	@Override
	public String info() {
		return "Shows the current joined team.";
	}

	@Override
	public String usage() {
		return "";
	}

	@Override
	public String[] aliases() {
		return new String[] {""};
	}

}
