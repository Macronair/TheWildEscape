package teamdjg.wildescape.worldborderCommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import teamdjg.wildescape.commandHandler.SubCommand;
import teamdjg.wildescape.main.Main;

public class WorldBorderCenter extends SubCommand{


	Main mainclass;
	
	public WorldBorderCenter (Main main)
	{
		this.mainclass = main;
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if(sender instanceof Player)
		{
			if(!sender.hasPermission(mainclass.bordercenterPermission))
			{
				sender.sendMessage(mainclass.pluginPrefix + ChatColor.DARK_RED + "ERROR: you don't have permission for this plugin.");
				return;
			}
		}
		
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			
			if(mainclass.WorldborderSetupCheck == false)
			{
				p.sendMessage(mainclass.ChatLine());
				p.sendMessage(mainclass.pluginPrefix + ChatColor.DARK_RED + "ERROR:" + ChatColor.GOLD + "You first need to use the  - /bordersetup - before you can move the center.");
				p.sendMessage(mainclass.ChatLine());
				return;
			}
			
			mainclass._WorldborderMechanics.BorderSetCenter(p.getLocation());
			p.sendMessage(mainclass.ChatLine());
			p.sendMessage(mainclass.pluginPrefix + ChatColor.GOLD + "Your border center is placed at:");
			p.sendMessage(mainclass.pluginPrefix + ChatColor.GOLD + "X:" + String.valueOf(p.getLocation().getBlockX()));
			p.sendMessage(mainclass.pluginPrefix + ChatColor.GOLD + "Z:" + String.valueOf(p.getLocation().getBlockZ()));
			p.sendMessage(mainclass.ChatLine());
			
			mainclass.WorldborderCenterCheck = true;
			mainclass.WorldCenter = p.getLocation();
		}
		else
		{
			System.out.print(mainclass.pluginPrefix + ChatColor.DARK_RED + "Only a player can perform this command.");
		}
		return;
	}

	@Override
	public String name() {
		return "centerborder";
	}

	@Override
	public String info() {
		return "Sets the center of the current configured world border.";
	}

	@Override
	public String usage() {
		return "";
	}

	@Override
	public String[] aliases() {
		return new String[] {"bc"};
	}

}
