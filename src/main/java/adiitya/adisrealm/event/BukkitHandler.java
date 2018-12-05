package adiitya.adisrealm.event;

import adiitya.adisrealm.discord.DiscordBot;
import adiitya.adisrealm.utils.AFKManager;
import adiitya.adisrealm.utils.NameManager;
import adiitya.adisrealm.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.*;

import static org.bukkit.entity.EntityType.ENDERMAN;

public final class BukkitHandler implements Listener {

	private static final String JOIN_PREFIX = "§a+§r";
	private static final String LEAVE_PREFIX = "§c-§r";

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {

		Player p = e.getPlayer();
		String prefix = NameManager.getColor(p.getName()).toString();
		e.setFormat(prefix + "%s§r: %s");
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		String name = NameManager.getColoredName(p.getName());
		e.setJoinMessage(JOIN_PREFIX + name);

		DiscordBot.onJoin();
		DiscordBot.updateInfoMessage(false);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {

		Player p = e.getPlayer();
		String name = NameManager.getColoredName(p.getName());
		e.setQuitMessage(LEAVE_PREFIX + name);

		DiscordBot.onLeave();
		DiscordBot.updateInfoMessage(false);

		AFKManager.exitAFK(p);
	}

	@EventHandler
	public void onEndermanGrief(EntityChangeBlockEvent e) {

		EntityType type = e.getEntityType();

		if (type.equals(ENDERMAN))
			e.setCancelled(true);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {

		if (Utils.isMoved(e.getTo(), e.getFrom(), 0))
			AFKManager.exitAFK(e.getPlayer());
	}
}
