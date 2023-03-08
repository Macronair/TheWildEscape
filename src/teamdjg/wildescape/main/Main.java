package teamdjg.wildescape.main;


import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import teamdjg.wildescape.carepackage.CarePackageManager;
import teamdjg.wildescape.carepackage.CarePackageProfile;
import teamdjg.wildescape.carepackage.PlayerRank;
import teamdjg.wildescape.carepackage.commands.AddDroppingTime;
import teamdjg.wildescape.carepackage.commands.AddLootToCarepackage;
import teamdjg.wildescape.carepackage.commands.CarepackageProfileList;
import teamdjg.wildescape.carepackage.commands.CreateCarepackageProfile;
import teamdjg.wildescape.carepackage.commands.DroppingTimeList;
import teamdjg.wildescape.carepackage.commands.GetLootList;
import teamdjg.wildescape.carepackage.commands.PlayerRanks;
import teamdjg.wildescape.carepackage.commands.RemoveAllCarepackages;
import teamdjg.wildescape.carepackage.commands.RemoveAllLoot;
import teamdjg.wildescape.carepackage.commands.RemoveCarepackageProfile;
import teamdjg.wildescape.carepackage.commands.RemoveDroppingTime;
import teamdjg.wildescape.carepackage.commands.RemoveLootFromCarepackage;
import teamdjg.wildescape.carepackage.commands.SetChatHearingDistance;
import teamdjg.wildescape.carepackage.commands.SetDroppingAmount;
import teamdjg.wildescape.carepackage.commands.SetDroppingChance;
import teamdjg.wildescape.carepackage.commands.SetPlayerRank;
import teamdjg.wildescape.commandHandler.CommandManager;
import teamdjg.wildescape.teams.Teams;
import teamdjg.wildescape.teamsCommands.TeamsAddPlayer;
import teamdjg.wildescape.teamsCommands.TeamsRemovePlayer;
import teamdjg.wildescape.teamsCommands.TeamsShowCurrentTeam;
//
import teamdjg.wildescape.worldborder.WorldborderMechanics;
import teamdjg.wildescape.worldborderCommands.WorldBorderCenter;
import teamdjg.wildescape.worldborderCommands.WorldBorderPause;
import teamdjg.wildescape.worldborderCommands.WorldBorderResume;
import teamdjg.wildescape.worldborderCommands.WorldBorderSetup;
import teamdjg.wildescape.worldborderCommands.WorldBorderStart;
import teamdjg.wildescape.worldborderCommands.WorldBorderStop;

public class Main extends JavaPlugin implements Listener 
{
	public String pluginPrefix = ChatColor.DARK_GRAY +  "[" + ChatColor.BLUE + "TWE" + ChatColor.DARK_GRAY + "] " + ChatColor.RESET;
	
	//world border variables -------------------------
	public WorldborderMechanics _WorldborderMechanics;	
	public String WorldBorderWorldName;	
	public long WorldBorderSpeed;	
	public boolean MakeBorderSmallerOnMidNight = false;
	public Player ContactPlayerForWorldBorder;
	public boolean GameRunning = false;
	public Location WorldCenter;
	
	public int WorldBorderMax;
	public int WorldBorderMin;
	public int WorldBorderCurrent;
	public int WordBorderDistance;
	
	public boolean WorldborderSetupCheck = false;
	public boolean WorldborderCenterCheck = false;
	
	public String bordersetupPermission = "wildescape.command.bordersetup";
	public String bordercenterPermission = "wildescape.command.bordercenter";
	public String borderstartPermission = "wildescape.command.borderstart";
	public String borderstopPermission = "wildescape.command.borderstop";
	public String borderpauzePermission = "wildescape.command.borderpauze";
	public String borderresumePermission = "wildescape.command.borderresume";
	//------------------------------------------------
	
	//game variables ---------------------------------
	public int gameDifficulty = 1;
	public long gameStartTime = 0; //range from 0 - 18000
	
	public float chatHearingDistance = 0;
	public HashMap<UUID,PlayerRank> playerRanks;
	//------------------------------------------------
	
	
	//CarePackage variables
	public CarePackageManager carepackageManager;
	
	//Command manager
	public CommandManager commandManager;
	
	@Override
	public void onEnable() 
	{	
		//TODO load from configfile the worldborder values		
		
		// The event handler (comes in handy)
		new Eventhandler(this);
		
		// Initialize the command manager
		commandManager = new CommandManager(this, "TWE");
		
		// Commands | CarePackages
		commandManager.addCommand(new PlayerRanks());
		commandManager.addCommand(new SetPlayerRank(this));
		commandManager.addCommand(new SetChatHearingDistance(this));
		commandManager.addCommand(new CreateCarepackageProfile(this));
		commandManager.addCommand(new RemoveCarepackageProfile(this));
		commandManager.addCommand(new CarepackageProfileList(this));
		commandManager.addCommand(new AddLootToCarepackage(this));
		commandManager.addCommand(new RemoveLootFromCarepackage(this));
		commandManager.addCommand(new RemoveAllLoot(this));
		commandManager.addCommand(new GetLootList(this));
		commandManager.addCommand(new SetDroppingAmount(this));
		commandManager.addCommand(new SetDroppingChance(this));
		commandManager.addCommand(new AddDroppingTime(this));
		commandManager.addCommand(new RemoveDroppingTime(this));
		commandManager.addCommand(new DroppingTimeList(this));
		commandManager.addCommand(new RemoveAllCarepackages(this));
		
		// Commands | WorldBorder
		commandManager.addCommand(new WorldBorderCenter(this));
		commandManager.addCommand(new WorldBorderPause(this));
		commandManager.addCommand(new WorldBorderResume(this));
		commandManager.addCommand(new WorldBorderSetup(this));
		commandManager.addCommand(new WorldBorderStart(this));
		commandManager.addCommand(new WorldBorderStop(this));
		
		// Commands | Teams
		commandManager.addCommand(new TeamsAddPlayer(this));
		commandManager.addCommand(new TeamsRemovePlayer(this));
		commandManager.addCommand(new TeamsShowCurrentTeam(this));
		
		
		// Management | WorldBorder Mechanics
		_WorldborderMechanics = new WorldborderMechanics(this);
		
		// Management | PlayerRanks
		playerRanks = new HashMap<UUID, PlayerRank>();
		CarePackageManager.reloadPlayerRanks(this, playerRanks);
		
		// Management | CarePackages
		carepackageManager = new CarePackageManager(this);
		carepackageManager.loadProfilesFromConfig(this);
		carepackageManager.loadCarepackagesFromConfig();
		
		// Management | Distance Chat
		/*
		if(getConfig().contains("chathearingdistance")){
			chatHearingDistance = getConfig().getLong("chathearingdistance");
		}else{
			getConfig().set("chathearingdistance", chatHearingDistance);
			saveConfig();
		}
		*/
		
		// Management | Teams
		Teams.clearTeams();
		Teams.registerTeams();
		
		System.out.println(pluginPrefix + "PLUGIN ENABLED!");
		
	}
	
	@Override
	public void onDisable() 
	{	
		//TODO save the current worldborder values to the configfile
		
		Teams.clearTeams();
		
		getServer().getScheduler().cancelTasks(this);
		System.out.println(pluginPrefix + "PLUGIN DISABLED!");
	}
	
	//Time checker for midnight
	public void worldTime(World world, Main main)
		{
			HashMap<Long,String> droppingTimings = new HashMap<Long,String>();
			
			for(CarePackageProfile profile : main.carepackageManager.carePackageProfiles)
			{
				for(int timings : profile.droppingsTimings)
				{
					if(!profile.items.isEmpty() && !profile.droppingsTimings.isEmpty())
					{
						droppingTimings.put((long) timings, profile.name);
					}
					else
					{
						getServer().broadcastMessage(main.pluginPrefix + ChatColor.RED + "A profile does not have any loot or dropping timings specified");
					}
					
				}
			}
		
		    this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
		        public void run() {
		            long time = world.getTime();
		            
		            if(droppingTimings.containsKey(time) && GameRunning)
		            {
		            	CarePackageProfile profile = carepackageManager.getCarePackageProfileByName(droppingTimings.get(time));
		            	
		            	if(profile != null)
		            	{
		            		int amount = getAmountByChance(profile.droppingsAmount, profile.droppingsAmountChance);
		            		getServer().broadcastMessage(pluginPrefix + ChatColor.GREEN + "There are " + amount + " carepackages dropped");
		            		for (int i = 0; i < amount; i++) 
		            		{
		            			try
		            			{
		            				Location randomLocation = getRandomLocationInBorders(world);
									carepackageManager.saveCarepackages(randomLocation, carepackageManager.setBlockToACarePackageByProfile(profile, randomLocation));	
		            			}
		            			catch(Exception e)
		            			{
		            				e.printStackTrace();
		            			}						
							}
		            	}
		            }
		            
		            
		            //give all players regeneration
		            if (time == 18000 && MakeBorderSmallerOnMidNight && GameRunning) 
		            {
		            	for(Player player : Bukkit.getServer().getOnlinePlayers())
		        		{
		            		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 1));
		        		}
		            }
		            
		            //world border mechanisch
		            if(time == 17800 && MakeBorderSmallerOnMidNight && GameRunning)
		            {
		            	// print a warning to all players that border will be moving
		            	getServer().broadcastMessage(pluginPrefix + ChatColor.RED + "WARNING the border will move in 10 seconds !!");
		            }
		            
		            if (time == 18000 && MakeBorderSmallerOnMidNight && GameRunning) 
		            {
		            	// (print text and make border smaller) if enabled.
		            	getServer().broadcastMessage(pluginPrefix + ChatColor.DARK_RED + "The border is becomming smaller !!"); 
		            	_WorldborderMechanics.MakeBorderSmaller();
		            	for(Player player : Bukkit.getServer().getOnlinePlayers())
		        		{
		            		player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 1));
		        		}
		            }
		            
		        }
		    }, 1, 1);
		}

	public String ChatLine()
	{
		String st = ChatColor.DARK_GRAY + "<-------------------------------------------->";
		return st;
	}
	
	public Location getRandomLocationInBorders(World world)
	{
		Random random = new Random();
		
		int minX = WorldCenter.getBlockX() - (WorldBorderMax/2);
		int minZ = WorldCenter.getBlockZ() - (WorldBorderMax/2);
		
		
		int X = minX + Math.round(WorldBorderMax * random.nextFloat());
		int Z = minZ + Math.round(WorldBorderMax * random.nextFloat());
		int Y = world.getHighestBlockAt(new Location(world,X,0,Z)).getY() + 1;
		
		return new Location(world, X, Y, Z);
	}
	
	public int getAmountByChance(int amount, Double chance)
	{
		int amountAfterChance = 0;
		Random random = new Random();
		
		for (int i = 0; i < amount; i++) {
			if(random.nextFloat() < chance)
			{
				amountAfterChance++;
			}
		}
		
		return amountAfterChance;
	}
	
	public int getFitableAmountByAmount(int amount, int number)
	{
		int initialAmount = amount;
		int index = 0;
		
		while(initialAmount > 0)
		{
			initialAmount -= number;
			index++;
		}
		
		return index;
	}
}
