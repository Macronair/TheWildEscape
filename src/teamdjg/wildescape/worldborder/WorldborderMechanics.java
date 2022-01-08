package teamdjg.wildescape.worldborder;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import teamdjg.wildescape.main.Main;

public class WorldborderMechanics 
{
	Main mainclass;
	
	public WorldborderMechanics(Main plugin)
	{
		mainclass = plugin;
	}
	
	public boolean CheckVariablesBeforeMoving()
	{
		if(mainclass.WordBorderDistance <= 0 || mainclass.WorldBorderCurrent < 0 || mainclass.WorldBorderMin < 0 || mainclass.WorldBorderMax < mainclass.WorldBorderMin || mainclass.WorldBorderSpeed == 0 || mainclass.WorldBorderWorldName == null || mainclass.WorldBorderWorldName == "")
		{
			if(mainclass.ContactPlayerForWorldBorder != null && mainclass.ContactPlayerForWorldBorder.isOnline())
			{
				mainclass.ContactPlayerForWorldBorder.sendMessage(mainclass.ChatLine());
				mainclass.ContactPlayerForWorldBorder.sendMessage(mainclass.pluginPrefix + ChatColor.DARK_RED + " Not all the information is filled in correctly.");
				mainclass.ContactPlayerForWorldBorder.sendMessage(mainclass.pluginPrefix + ChatColor.WHITE + " Note: the min border can't be below 0.");
				mainclass.ContactPlayerForWorldBorder.sendMessage(mainclass.pluginPrefix + ChatColor.WHITE + " And the max border can't be equal or below the min border.");
				mainclass.ContactPlayerForWorldBorder.sendMessage(mainclass.ChatLine());
			}
			else
			{
				System.out.println(mainclass.pluginPrefix + ChatColor.DARK_RED + " Not all the information is filled in correctly.");
				System.out.println(mainclass.pluginPrefix + ChatColor.WHITE + " Note: the min border can't be below 0.");
				System.out.println(mainclass.pluginPrefix + ChatColor.WHITE + " And the max border can't be equal or below the min border.");
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void MakeBorderSmaller()
	{
		if(CheckVariablesBeforeMoving())
		{
			return;
		}
		
		int amountToMove;
		
		if((mainclass.WorldBorderCurrent - mainclass.WordBorderDistance) >= mainclass.WorldBorderMin )
		{
			amountToMove = mainclass.WorldBorderCurrent - mainclass.WordBorderDistance;
		}
		else
		{
			amountToMove = mainclass.WorldBorderMin;
			BorderStopMoving();
			mainclass.getServer().broadcastMessage(mainclass.pluginPrefix + ChatColor.GREEN + "The border will not move anymore");
		}		
		
		mainclass.getServer().getWorld(mainclass.WorldBorderWorldName).getWorldBorder().setSize(amountToMove, mainclass.WorldBorderSpeed);
		
		mainclass.WorldBorderCurrent = amountToMove;
		
	}
	
	public void MakeBorderBigger()
	{
		if(CheckVariablesBeforeMoving())
		{
			return;
		}
		
		int amountToMove;
		
		if((mainclass.WorldBorderCurrent + mainclass.WordBorderDistance) <= mainclass.WorldBorderMax)
		{
			amountToMove = mainclass.WorldBorderCurrent + mainclass.WordBorderDistance;
		}
		else
		{
			amountToMove = mainclass.WorldBorderMax;
		}
		
		mainclass.getServer().getWorld(mainclass.WorldBorderWorldName).getWorldBorder().setSize(amountToMove, 1);
		
		mainclass.WorldBorderCurrent = amountToMove;
	}

	public void BorderReset()
	{	
		mainclass.getServer().getWorld(mainclass.WorldBorderWorldName).getWorldBorder().setSize(mainclass.WorldBorderMax, 30);
		mainclass.WorldBorderCurrent = mainclass.WorldBorderMax;
		BorderStopMoving();
	}

	public void BorderStopMoving()
	{
		mainclass.MakeBorderSmallerOnMidNight = false;
	}

	public void BorderResumeMoving()
	{
		mainclass.MakeBorderSmallerOnMidNight = true;
	}
	
	public void BorderSetCenter(Location centerlocation)
	{
		mainclass.getServer().getWorld(mainclass.WorldBorderWorldName).getWorldBorder().setCenter(centerlocation.getBlockX(), centerlocation.getBlockZ());
	}

	public void SetBorderStart()
	{
		if(CheckVariablesBeforeMoving())
		{
			return;
		}
		
		mainclass.getServer().getWorld(mainclass.WorldBorderWorldName).getWorldBorder().setSize(mainclass.WorldBorderMax,0);
		mainclass.WorldBorderCurrent = mainclass.WorldBorderMax;
	}
}