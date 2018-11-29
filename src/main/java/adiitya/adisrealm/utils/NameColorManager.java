package adiitya.adisrealm.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

@UtilityClass
public final class NameColorManager {

	private static final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
	private static final Set<Team> colorTeams = new HashSet<>();

	private void createTeam(ChatColor color) {

		if (color.isFormat())
			return;

		String name = color.name().toLowerCase();

		try {
			Team team = scoreboard.registerNewTeam(name);
			team.setColor(color);

			colorTeams.add(team);
		} catch(IllegalArgumentException ignored) {}
	}

	public boolean setColor(UUID uuid, ChatColor color) {

		if (color.isFormat())
			return false;

		createTeam(color);

		try {
			Team team = scoreboard.getTeam(color.name().toLowerCase());
			team.addEntry(Bukkit.getPlayer(uuid).getName());
		} catch(IllegalArgumentException e) {
			return false;
		}

		return true;
	}

	public boolean hasColor(Player player) {
		return colorTeams.stream()
				.anyMatch(t -> t.hasEntry(player.getName()));
	}

	public ChatColor getColor(Player p) {

		return !hasColor(p) ? ChatColor.WHITE
				: colorTeams.stream()
				.filter(t -> t.hasEntry(p.getName()))
				.findFirst()
				.get()
				.getColor();
	}

	public boolean isColor(Player p, ChatColor color) {
		return getColor(p).equals(color);
	}
}
