package adiitya.adisrealm.utils;

import adiitya.adisrealm.utils.name.NameElement;
import adiitya.adisrealm.utils.name.NameManager;
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
	private static final List<Team> colorTeams = new ArrayList<>();

	public void createTeams() {

		colorTeams.clear();

		Arrays.stream(ChatColor.values())
				.filter(ChatColor::isColor)
				.forEach(NameColorManager::createTeam);
	}

	private void createTeam(ChatColor color) {

		if (color.isFormat())
			return;

		String name = color.name().toLowerCase();

		try {

			Team team = Optional.of(scoreboard.getTeam(name)).orElse(scoreboard.registerNewTeam(name));
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

			NameManager.setElement(uuid, NameElement.FORMATTING_PREFIX, color.toString());
		} catch(IllegalArgumentException e) {
			return false;
		}

		return true;
	}

	public boolean hasColor(Player player) {

		//System.out.println(player.getName());
		//colorTeams.forEach(t -> System.out.printf("%s: %s%n", t.getDisplayName(), t.getEntries()));

		return scoreboard.getEntryTeam(player.getName()) != null;
		//.stream().anyMatch(t -> t.hasEntry(player.getName()));
	}

	public ChatColor getColor(Player p) {

		return !hasColor(p) ? ChatColor.WHITE : scoreboard.getEntryTeam(p.getName()).getColor();/*colorTeams.stream()
				.filter(t -> t.hasEntry(p.getName()))
				.findAny()
				.get()
				.getColor();*/
	}

	public boolean isColor(Player p, ChatColor color) {
		return getColor(p).equals(color);
	}
}
