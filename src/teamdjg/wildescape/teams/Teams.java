package teamdjg.wildescape.teams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import teamdjg.wildescape.main.Main;

public class Teams {

	public static ScoreboardManager manager = Bukkit.getScoreboardManager();
	public static Scoreboard twe = manager.getNewScoreboard();
	
	public static Team sGuard = twe.registerNewTeam("AlphaGuard");
	public static Team sBlue = twe.registerNewTeam("Blue");
	public static Team sGreen = twe.registerNewTeam("Green");
	public static Team sYellow = twe.registerNewTeam("Yellow");
	public static Team sRed = twe.registerNewTeam("Red");
	public static Team sPink = twe.registerNewTeam("Pink");
	public static Team sPurple = twe.registerNewTeam("Purple");
	public static Team sSpectator = twe.registerNewTeam("Spectator");
	
	public static String GuardPrefix = ChatColor.BLACK + "[" + ChatColor.DARK_RED + "Alpha Guard" + ChatColor.BLACK + "]" + ChatColor.GRAY;
	public static String BluePrefix = ChatColor.GRAY + "[" + ChatColor.BLUE + "Blue" + ChatColor.GRAY + "]";
	public static String GreenPrefix = ChatColor.GRAY + "[" + ChatColor.GREEN + "Green" + ChatColor.GRAY + "]";
	public static String YellowPrefix = ChatColor.GRAY + "[" + ChatColor.YELLOW + "Yellow" + ChatColor.GRAY + "]";
	public static String RedPrefix = ChatColor.GRAY + "[" + ChatColor.RED + "Red" + ChatColor.GRAY + "]";
	public static String PinkPrefix = ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "Pink" + ChatColor.GRAY + "]";
	public static String PurplePrefix = ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + "Purple" + ChatColor.GRAY + "]";
	public static String SpectatorPrefix = ChatColor.GRAY + "[" + ChatColor.WHITE + "Spectator" + ChatColor.GRAY + "]";
	
	private static List<String> Guards = new ArrayList<String>();
	private static List<String> Spectators = new ArrayList<String>();
	private static List<String> teamBlue = new ArrayList<String>();
	private static List<String> teamGreen = new ArrayList<String>();
	private static List<String> teamYellow = new ArrayList<String>();
	private static List<String> teamRed = new ArrayList<String>();
	private static List<String> teamPink = new ArrayList<String>();
	private static List<String> teamPurple = new ArrayList<String>();
	
	public List<String> getGuards() {return Guards;}
	public List<String> getTeamBlue() {return teamBlue;}
	public List<String> getTeamGreen() {return teamGreen;}
	public List<String> getTeamYellow() {return teamYellow;}
	public List<String> getTeamRed() {return teamRed;}
	public List<String> getTeamPink() {return teamPink;}
	public List<String> getTeamPurple() {return teamPurple;}
	public List<String> getSpectators() {return Spectators;}
	
	public static void addToTeam(TeamsType type, Player player) {
		removeFromTeam(player);
			switch (type) {
			case GUARD:
				Guards.add(player.getName());
				sGuard.addEntry(player.getName());
				
				player.sendMessage(
						ChatColor.GRAY + "You are currently in team " + GuardPrefix);
				break;
			case BLUE:
				teamBlue.add(player.getName());
				sBlue.addEntry(player.getName());
				
				player.sendMessage(
						ChatColor.GRAY + "You are currently in team " + BluePrefix);
				break;
			case GREEN:
				teamGreen.add(player.getName());
				sGreen.addEntry(player.getName());
				
				player.sendMessage(
						ChatColor.GRAY + "You are currently in team " + GreenPrefix);
				break;
			case YELLOW:
				teamYellow.add(player.getName());
				sYellow.addEntry(player.getName());
				
				player.sendMessage(
						ChatColor.GRAY + "You are currently in team " + YellowPrefix);
				break;
			case RED:
				teamRed.add(player.getName());
				sRed.addEntry(player.getName());
				
				player.sendMessage(
						ChatColor.GRAY + "You are currently in team " + RedPrefix);
				break;
			case PINK:
				teamPink.add(player.getName());
				sPink.addEntry(player.getName());
				
				player.sendMessage(
						ChatColor.GRAY + "You are currently in team " + PinkPrefix);
				break;
			case PURPLE:
				teamPurple.add(player.getName());
				sPurple.addEntry(player.getName());
				
				player.sendMessage(
						ChatColor.GRAY + "You are currently in team " + PurplePrefix);
				break;
			case SPECTATOR:
				Spectators.add(player.getName());
				sSpectator.addEntry(player.getName());
				
				player.sendMessage(
						ChatColor.GRAY + "You are currently in team " + SpectatorPrefix);
				break;
			}
			
		player.setScoreboard(twe);
	}
	
	public static void removeFromTeam(Player player) {
		Guards.remove(player.getName());
		teamBlue.remove(player.getName());
		teamGreen.remove(player.getName());
		teamYellow.remove(player.getName());
		teamRed.remove(player.getName());
		teamPink.remove(player.getName());
		teamPurple.remove(player.getName());
		Spectators.remove(player.getName());
		
		sGuard.removeEntry(player.getName());
		sBlue.removeEntry(player.getName());
		sGreen.removeEntry(player.getName());
		sYellow.removeEntry(player.getName());
		sRed.removeEntry(player.getName());
		sPink.removeEntry(player.getName());
		sPurple.removeEntry(player.getName());
		sSpectator.removeEntry(player.getName());
	}
	
	public static void reloadTeams(Main main, HashMap<UUID,TeamsType> playerTeamVar) {
		clearTeams();
		if(playerTeamVar.isEmpty() == false)
		{
			playerTeamVar.clear();
		}
		
		for(Player p : Bukkit.getOnlinePlayers())
		{
			if(main.getConfig().contains("Team." + p.getUniqueId()))
			{
				playerTeamVar.put(p.getPlayer().getUniqueId(), TeamsType.valueOf(main.getConfig().getString("Team." + p.getUniqueId())));
			}
			else
			{
				playerTeamVar.put(p.getUniqueId(), TeamsType.SPECTATOR);
				main.getConfig().set("Team." + p.getUniqueId(), TeamsType.SPECTATOR.toString());
				main.saveConfig();
			}
			
			TeamsType team = TeamsType.valueOf(main.getConfig().getString("Team." + p.getUniqueId()));
			
			switch (team) {
			case GUARD:
				addToTeam(TeamsType.GUARD, p);
				break;
			case BLUE:
				addToTeam(TeamsType.BLUE, p);
				break;
			case GREEN:
				addToTeam(TeamsType.GREEN, p);
				break;
			case YELLOW:
				addToTeam(TeamsType.YELLOW, p);
				break;
			case RED:
				addToTeam(TeamsType.RED, p);
				break;
			case PINK:
				addToTeam(TeamsType.PINK, p);
				break;
			case PURPLE:
				addToTeam(TeamsType.PURPLE, p);
				break;
			case SPECTATOR:
				addToTeam(TeamsType.SPECTATOR, p);
				break;
			}
		}
	}
	
	public static void registerTeams() {
		sGuard.setPrefix(ChatColor.BLACK + "[" + 
				ChatColor.DARK_RED + "ALPHA" +
				ChatColor.BLACK + "] ");
		sGuard.setAllowFriendlyFire(false);
		sGuard.setColor(ChatColor.DARK_RED);
		
		sBlue.setPrefix(ChatColor.GRAY + "[" + 
					ChatColor.BLUE + "BL" +
					ChatColor.GRAY + "] ");
		sBlue.setAllowFriendlyFire(false);
		sBlue.setColor(ChatColor.BLUE);
		
		sGreen.setPrefix(ChatColor.GRAY + "[" + 
				ChatColor.GREEN + "GR" +
				ChatColor.GRAY + "] ");
		sGreen.setAllowFriendlyFire(false);
		sGreen.setColor(ChatColor.GREEN);
	
		sYellow.setPrefix(ChatColor.GRAY + "[" + 
				ChatColor.YELLOW + "YE" +
				ChatColor.GRAY + "] ");
		sYellow.setAllowFriendlyFire(false);
		sYellow.setColor(ChatColor.YELLOW);
		
		sRed.setPrefix(ChatColor.GRAY + "[" + 
				ChatColor.RED + "RE" +
				ChatColor.GRAY + "] ");
		sRed.setAllowFriendlyFire(false);
		sRed.setColor(ChatColor.RED);
		
		sPink.setPrefix(ChatColor.GRAY + "[" + 
				ChatColor.LIGHT_PURPLE + "PI" +
				ChatColor.GRAY + "] ");
		sPink.setAllowFriendlyFire(false);
		sPink.setColor(ChatColor.LIGHT_PURPLE);
		
		sPurple.setPrefix(ChatColor.GRAY + "[" + 
				ChatColor.DARK_PURPLE + "PU" +
				ChatColor.GRAY + "] ");
		sPurple.setAllowFriendlyFire(false);
		sPurple.setColor(ChatColor.DARK_PURPLE);
		
		sSpectator.setPrefix(ChatColor.GRAY + "[" + 
				ChatColor.WHITE + "X" +
				ChatColor.GRAY + "] ");
		sSpectator.setAllowFriendlyFire(false);
		sSpectator.setColor(ChatColor.GRAY);
	}
	
	public static boolean isInTeam(Player player) {
		return Guards.contains(player.getName())
				|| teamBlue.contains(player.getName())
				|| teamGreen.contains(player.getName())
				|| teamYellow.contains(player.getName())
				|| teamRed.contains(player.getName())
				|| teamPink.contains(player.getName())
				|| teamPurple.contains(player.getName())
				|| Spectators.contains(player.getName());
	}
	
	public static void clearTeams() {
		Guards.clear();
		teamBlue.clear();
		teamGreen.clear();
		teamYellow.clear();
		teamRed.clear();
		teamPink.clear();
		teamPurple.clear();
		Spectators.clear();
	}
	
	public static List<String> getAllPlayersInTeams() {
		List<String> combinedTeams = new ArrayList<String>();
		combinedTeams.addAll(Guards);
		combinedTeams.addAll(teamBlue);
		combinedTeams.addAll(teamGreen);
		combinedTeams.addAll(teamYellow);
		combinedTeams.addAll(teamRed);
		combinedTeams.addAll(teamPink);
		combinedTeams.addAll(teamPurple);
		combinedTeams.addAll(Spectators);
		return combinedTeams;
		}
	
	public static TeamsType getTeamType(Player player) {
		if (!isInTeam(player)) {
			return null;
		}
		else if (Guards.contains(player.getName())) {
			return TeamsType.GUARD;
		}
		else if (teamBlue.contains(player.getName())) {
			return TeamsType.BLUE;
		}
		else if (teamGreen.contains(player.getName())) {
			return TeamsType.GREEN;
		}
		else if (teamYellow.contains(player.getName())) {
			return TeamsType.YELLOW;
		}
		else if (teamRed.contains(player.getName())) {
			return TeamsType.RED;
		}
		else if (teamPink.contains(player.getName())) {
			return TeamsType.PINK;
		}
		else if (teamPurple.contains(player.getName())) {
			return TeamsType.PURPLE;
		}
		else if (Spectators.contains(player.getName())) {
			return TeamsType.SPECTATOR;
		}
		else {
			return null;
		}
	}
}
