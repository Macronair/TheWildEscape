package teamdjg.wildescape.teamsCommands;

import org.bukkit.command.CommandSender;

import teamdjg.wildescape.commandHandler.SubCommand;

public class TeamsToggle extends SubCommand{

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		
	}

	@Override
	public String name() {
		return "toggleteams";
	}

	@Override
	public String info() {
		return "Enable/Disable teams in the game.";
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
