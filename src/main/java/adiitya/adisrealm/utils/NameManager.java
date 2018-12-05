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

	public void setTabPrefix(UUID uuid, String prefix) {

		if (prefix.isEmpty())
			tabPrefixes.remove(uuid);
		else
			tabPrefixes.put(uuid, prefix);
	}

	public String getTabName(UUID uuid) {

		OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);

		if (p.getName() == null)
			return "";

		return tabPrefixes.getOrDefault(uuid, "") + getColoredName(p.getName());
	}

	public static void setColor(String name, ChatColor color) {

		Team team = getTeam(color);
		team.addEntry(name);
	}

	public static ChatColor getColor(String name) {

		Team team = scoreboard.getEntryTeam(name);

		return team == null ? ChatColor.WHITE : team.getColor();
	}

	public static String getColoredName(String name) {
		return getColor(name).toString() + name + "§r";
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
