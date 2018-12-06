package adiitya.adisrealm.utils;

import adiitya.adisrealm.AdisRealm;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This class handles all name related function.
 */
@UtilityClass
public class NameManager {

	private static final Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
	private static final Map<UUID, String> tabPrefixes = new HashMap<>();

	static {
		Bukkit.getScheduler()
				.scheduleSyncRepeatingTask(AdisRealm.getInstance(), NameManager::updateTabNames, 0L, 0L);
	}

	private void updateTabNames() {
		Bukkit.getOnlinePlayers()
				.forEach(p -> p.setPlayerListName(getTabName(p.getUniqueId())));
	}

	/**
	 * Sets the tab prefix of the user. Tab names are updated every tick.
	 *
	 * @param uuid The player
	 * @param prefix The prefix
	 */
	public void setTabPrefix(UUID uuid, String prefix) {

		if (prefix.isEmpty())
			tabPrefixes.remove(uuid);
		else
			tabPrefixes.put(uuid, prefix);
	}

	/**
	 * Gets the users tab name. This includes the tab prefix and colored name.
	 *
	 * @param uuid the player
	 *
	 * @return The formatted tab name
	 */
	public String getTabName(UUID uuid) {

		OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);

		if (p.getName() == null)
			return "";

		return tabPrefixes.getOrDefault(uuid, "") + getColoredName(p.getName());
	}

	/**
	 * Sets the name color of the player
	 *
	 * @param name The name of the player
	 * @param color The color
	 */
	public void setColor(String name, ChatColor color) {

		Team team = getTeam(color);
		team.addEntry(name);
	}

	/**
	 * Gets the color of the player
	 *
	 * @param name The name of the player
	 *
	 * @return The color else white if the user has no color
	 */
	public ChatColor getColor(String name) {

		Team team = scoreboard.getEntryTeam(name);

		return team == null ? ChatColor.WHITE : team.getColor();
	}

	/**
	 * Gets the colored name of a player
	 *
	 * @param name The name of the player
	 *
	 * @return The colored name of the player
	 */
	public String getColoredName(String name) {
		return getColor(name).toString() + name + "§r";
	}

	/**
	 * Checks whether the player has a specific name color
	 *
	 * @param name The name of the player
	 * @param color The color to check
	 *
	 * @return Whether the player has the color
	 */
	public boolean isColor(String name, ChatColor color) {
		return getColor(name) == color;
	}

	private Team getTeam(ChatColor color) {

		Team team = scoreboard.getTeam(color.name());

		return team == null ? createTeam(color) : team;
	}

	private Team createTeam(ChatColor color) {

		Team team = scoreboard.getTeam(color.name());

		if (team != null)
			return team;

		team = scoreboard.registerNewTeam(color.name());
		team.setColor(color);

		return team;
	}
}
