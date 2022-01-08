package teamdjg.wildescape.teamCommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.main.Main;

public class TeamCreate implements CommandExecutor{

	Main mainclass;
	
	public TeamCreate(Main plugin)
	{
		this.mainclass = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			player.sendMessage("Works!");
			player.sendMessage(mainclass.pluginPrefix);
		}
		else
		{
			sender.sendMessage("Works in the console!");
		}
		
		return true;
		
	}
	
}
