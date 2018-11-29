package adiitya.adisrealm.event;

import adiitya.adisrealm.discord.DiscordBot;
import adiitya.adisrealm.utils.AFKManager;
import adiitya.adisrealm.utils.Utils;
import adiitya.adisrealm.utils.name.NameElement;
import adiitya.adisrealm.utils.name.NameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static adiitya.adisrealm.utils.name.NameElement.FORMATTING_PREFIX;
import static org.bukkit.entity.EntityType.ENDERMAN;

public final class BukkitHandler implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {

		Player p = e.getPlayer();
		String prefix = NameManager.getElement(p.getUniqueId(), FORMATTING_PREFIX);
		e.setFormat(prefix + "%s§r: %s");
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		String name = NameManager.getName(p.getUniqueId(), FORMATTING_PREFIX);
		e.setJoinMessage(String.format("§a+§r%s", name));

		DiscordBot.onJoin();
		DiscordBot.updateInfoMessage(false);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {

		Player p = e.getPlayer();
		String name = NameManager.getName(p.getUniqueId(), FORMATTING_PREFIX);
		e.setQuitMessage(String.format("§c-§r%s", name));

		DiscordBot.onLeave();
		DiscordBot.updateInfoMessage(false);

		exitAFK(e.getPlayer(), false);
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
			exitAFK(e.getPlayer(), true);
	}

	private void exitAFK(Player player, boolean broadcast) {

		if (broadcast && AFKManager.isAFK(player.getUniqueId())) {

			String name = NameManager.getName(player.getUniqueId(), FORMATTING_PREFIX);
			String message = "§6§l[§c§l-§6§l]§f" + name;
			Bukkit.getConsoleSender().sendMessage(message);

			for (Player p : Bukkit.getOnlinePlayers())
				p.sendMessage(message);
		}

		AFKManager.exitAFK(player.getUniqueId());
	}
}
