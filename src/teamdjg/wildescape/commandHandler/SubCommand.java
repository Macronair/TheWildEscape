package teamdjg.wildescape.commandHandler;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {

	public SubCommand()
	{
		
	}
	
	public abstract void onCommand(CommandSender sender, String[] args);
	
	public abstract String name();
	
	public abstract String info();
	
	public abstract String usage();
	
	public abstract String[] aliases();
}
