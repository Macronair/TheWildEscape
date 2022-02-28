package teamdjg.wildescape.commandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.minecraft.world.inventory.ClickAction;
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
				clearChat(sender);
				int maximumPage = (int) Math.floor(commands.size() / commandsPerPage);
				
				
				if(args.length > 1)
				{
					int currentPageNumber = -1;
					
					try {					
						currentPageNumber = Integer.parseInt(args[1]);						
					} catch (Exception e) {
						sender.sendMessage(ChatColor.RED + "The help command expected a number");
					}
					sendBeginOfCommandList(currentPageNumber,maximumPage,sender);
					sendEndOfCommandList(currentPageNumber,maximumPage,sender);
				}
				else
				{	
					sendBeginOfCommandList(0,maximumPage,sender);
					sendEndOfCommandList(0,maximumPage,sender);				
				}				
				return true;
			}
			
			SubCommand currentCommand = this.getSubCommand(args[0]);
			
			
			if(currentCommand == null)
			{
				sender.sendMessage(ChatColor.RED + "Unkown command");
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
				sender.sendMessage(ChatColor.RED + "ERROR in command manager");
				e.printStackTrace();
			}			
		}
		
		
		return true;
	}
	

	private void clearChat(CommandSender sender)
	{
		for (int i = 0; i < 10; i++) {
			sender.sendMessage("");
		}
	}
	
	private void sendBeginOfCommandList(int currentPage, int maxPages, CommandSender sender)
	{
		sender.sendMessage(ChatColor.GOLD + "-------[ " + ChatColor.RED + currentPage + ChatColor.GOLD + "/" + ChatColor.RED + maxPages + ChatColor.GOLD + " ]-------");
	}
	
	private void sendEndOfCommandList(int currentPage, int maxPages, CommandSender sender)
	{
		if (currentPage == 0) {
			TextComponent a = new TextComponent("-----[ ");
			TextComponent b = new TextComponent(" ]-----");
			
			a.setColor(net.md_5.bungee.api.ChatColor.GOLD);
			b.setColor(net.md_5.bungee.api.ChatColor.GOLD);
			
			TextComponent bigRight = new TextComponent(">>>>>>>>");
			bigRight.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + mainCommand + " help " + (currentPage+1)));
			bigRight.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Next")));
			bigRight.setColor(net.md_5.bungee.api.ChatColor.RED);
			
			sender.spigot().sendMessage(a,bigRight,b);
		}
		else if (currentPage == maxPages)
		{
			TextComponent a = new TextComponent("-----[ ");
			TextComponent b = new TextComponent(" ]-----");
			
			a.setColor(net.md_5.bungee.api.ChatColor.GOLD);
			b.setColor(net.md_5.bungee.api.ChatColor.GOLD);
			
			TextComponent bigLeft = new TextComponent("<<<<<<<<");
			bigLeft.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + mainCommand + " help " + (currentPage-1)));
			bigLeft.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Prev")));
			bigLeft.setColor(net.md_5.bungee.api.ChatColor.RED);
			
			sender.spigot().sendMessage(a,bigLeft,b);		
		}
		else
		{
			TextComponent smallLeft = new TextComponent("<<<");
			TextComponent smallRight = new TextComponent(">>>");
			smallLeft.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + mainCommand + " help " + (currentPage-1)));
			smallLeft.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Prev")));
			smallLeft.setColor(net.md_5.bungee.api.ChatColor.RED);
			
			smallRight.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + mainCommand + " help " + (currentPage+1)));
			smallRight.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Next")));
			smallRight.setColor(net.md_5.bungee.api.ChatColor.RED);
			
			TextComponent a = new TextComponent("-----[ ");
			TextComponent b = new TextComponent(" ]-----");
			TextComponent c = new TextComponent(" | ");
			a.setColor(net.md_5.bungee.api.ChatColor.GOLD);
			b.setColor(net.md_5.bungee.api.ChatColor.GOLD);
			c.setColor(net.md_5.bungee.api.ChatColor.GOLD);
			
			sender.spigot().sendMessage(a, smallLeft, c, smallRight, b);	
			
		}
	}
	
	private SubCommand getSubCommand(String commandName)
	{
		Iterator<SubCommand> subCommands = this.commands.iterator();
		
		while(subCommands.hasNext())
		{
			SubCommand currentSubCommand = (SubCommand) subCommands.next();
			
			if(currentSubCommand.name().equalsIgnoreCase(commandName))
			{
				return currentSubCommand;
			}
			
			String[] aliases = currentSubCommand.aliases();
			
			for(int i = 0; i < aliases.length; i++)
			{
				String alias = aliases[i];
				if(commandName.equalsIgnoreCase(alias))
				{
					return currentSubCommand;
				}
			}
			
		}
		
		return null;
	}
	
	

}
