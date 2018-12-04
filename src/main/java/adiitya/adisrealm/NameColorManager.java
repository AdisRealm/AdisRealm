package adiitya.adisrealm;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public final class  NameColorManager {

	private static final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

	public static void setColor(String name, ChatColor color) {

		Team team = getTeam(color);
		team.addEntry(name);
	}

	public static ChatColor getColor(String name) {

		Team team = scoreboard.getEntryTeam(name);

		return team == null ? ChatColor.WHITE : team.getColor();
	}

	public static String getColoredName(String name) {
		return String.format("%s%s", getColor(name).toString(), name);
	}

	public static boolean isColor(String name, ChatColor color) {
		return getColor(name) == color;
	}

	private static Team getTeam(ChatColor color) {

		Team team = scoreboard.getTeam(color.name());

		return team == null ? createTeam(color) : team;
	}

	private static Team createTeam(ChatColor color) {

		Team team = scoreboard.getTeam(color.name());

		if (team != null)
			return team;

		team = scoreboard.registerNewTeam(color.name());
		team.setColor(color);

		return team;
	}
}
