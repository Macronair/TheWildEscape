package teamdjg.wildescape.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import teamdjg.wildescape.main.Main;


public class CommandManager implements CommandExecutor{

	public ArrayList<SubCommand> commands = new ArrayList<>();
	
	private Main main;
	public String mainCommand;
	
	int commandsPerPage = 5;
	
	public CommandManager(Main main, String mainCommand)
	{
		this.main = main;
		this.mainCommand = mainCommand;
		this.main.getCommand(this.mainCommand).setExecutor(this);
	}
	
	public void addCommand(SubCommand command)
	{
		this.commands.add(command);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		
		if(cmd.getName().equalsIgnoreCase(mainCommand))
		{
			if(args.length == 0)
			{
				sender.sendMessage(ChatColor.GOLD + "Please add arguments to your command.\n Type <" + ChatColor.DARK_RED + "/" + mainCommand + " help" + ChatColor.GOLD + "> for the commands.");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h"))
			{
				if(args.length == 1)
				{					
					if(commands.size() > commandsPerPage)
					{
						sender.sendMessage(ChatColor.GOLD + "<--" + ChatColor.DARK_RED + "TWE commands" + ChatColor.GOLD + "--" + ChatColor.DARK_RED + "Page: 1/" + main.getFitableAmountByAmount(commands.size(), commandsPerPage) + ChatColor.GOLD + "-->");
						sender.sendMessage(ChatColor.GOLD + "/" + mainCommand + " help" + ChatColor.WHITE + ": gives all commands");
						sender.sendMessage(ChatColor.GOLD + "/" + mainCommand + " help " + "<" + mainCommand + " command / help index>" + ChatColor.WHITE + ": gives the usage");
						for(int i = 0; i < commandsPerPage; i++)
						{
							StringBuilder string = new StringBuilder();
							string.append(ChatColor.GOLD + "/");
							string.append(mainCommand + " " + commands.get(i).name() + ChatColor.WHITE + ": ");
							string.append(commands.get(i).info());
							sender.sendMessage(string.toString());
						}
						sender.sendMessage(ChatColor.GOLD + "<--- " + "type: /" + mainCommand + " help (page): for more help --->");
						
					}
					else
					{
						sender.sendMessage(ChatColor.GOLD + "<--------------" + ChatColor.DARK_RED + "TWE commands" + ChatColor.GOLD + "-------------->");
						sender.sendMessage(ChatColor.GOLD + "/" + mainCommand + " help" + ChatColor.WHITE + ": gives all commands");
						sender.sendMessage(ChatColor.GOLD + "/" + mainCommand + " help " + "<" + mainCommand + " command / help index>" + ChatColor.WHITE + ": gives the usage or other commands");
						for(SubCommand currentCommand : commands)
						{
							StringBuilder string = new StringBuilder();
							string.append(ChatColor.GOLD + "/");
							string.append(mainCommand + " " + currentCommand.name() + ChatColor.WHITE + ": ");
							string.append(currentCommand.info());
							sender.sendMessage(string.toString());
						}
						sender.sendMessage(ChatColor.GOLD + "<---------------------------------------->");
					}
				}
				else
				{
					SubCommand subcommand = getCommand(args[1]);
					
					if(subcommand == null)
					{
						int page = 0;
						
						try
						{
							page = Integer.parseInt(args[1]);
						}
						catch(Exception e)
						{
							sender.sendMessage(ChatColor.RED + "Invalid command");
							return true;
						}
						
						if(page > main.getFitableAmountByAmount(commands.size(), commandsPerPage))
						{
							sender.sendMessage(ChatColor.RED + "That page doesn't exist");
							return true;
						}
						
						sender.sendMessage(ChatColor.GOLD + "<--" + ChatColor.DARK_RED + "TWE commands" + ChatColor.GOLD + "--" + ChatColor.DARK_RED + "Page: " + page + "/" + main.getFitableAmountByAmount(commands.size(), commandsPerPage) + ChatColor.GOLD + "-->");						
						for(int i = 0 + (commandsPerPage * (page - 1)); i < commandsPerPage + (commandsPerPage * (page - 1)); i++)
						{
							if(i < commands.size())
							{
								StringBuilder string = new StringBuilder();
								string.append(ChatColor.GOLD + "/");
								string.append(mainCommand + " " + commands.get(i).name() + ChatColor.WHITE + ": ");
								string.append(commands.get(i).info());
								sender.sendMessage(string.toString());
							}
						}
						
						sender.sendMessage(ChatColor.GOLD + "<--- " + "type: /" + mainCommand + " help (page): for more help --->");
						return true;
					}
					
					sender.sendMessage(ChatColor.GOLD + "<-- " + ChatColor.DARK_RED + mainCommand + " usage of " + subcommand.name() + ChatColor.GOLD + " -->");
					sender.sendMessage(ChatColor.GOLD + "/" + mainCommand + " help " + subcommand.name() + ChatColor.WHITE + ": " + subcommand.usage());
					sender.sendMessage(ChatColor.GOLD + "<---------------------------------------->");
				}
				
				
				return true;
			}
			
			SubCommand currentCommand = this.getCommand(args[0]);
			
			
			if(currentCommand == null)
			{
				sender.sendMessage(ChatColor.RED + "Invalid command");
				return true;
			}
			
			
			ArrayList<String> argsList = new ArrayList<>();
			argsList.addAll(Arrays.asList(args));
			argsList.remove(0);
			
			
			try
			{
				currentCommand.onCommand(sender, args);
			}
			catch(Exception e)
			{
				sender.sendMessage(ChatColor.RED + "ERROR in command handler");
				e.printStackTrace();
			}
			
			
		}
		
		
		return true;
	}
	

	private SubCommand getCommand(String commandName)
	{
		Iterator<SubCommand> subCommands = this.commands.iterator();
		
		while(subCommands.hasNext())
		{
			SubCommand sc = (SubCommand) subCommands.next();
			
			if(sc.name().equalsIgnoreCase(commandName))
			{
				return sc;
			}
			
			String[] aliases = sc.aliases();
			
			for(int i = 0; i < aliases.length; i++)
			{
				String alias = aliases[i];
				if(commandName.equalsIgnoreCase(alias))
				{
					return sc;
				}
			}
			
		}
		
		return null;
	}
	
	

}
