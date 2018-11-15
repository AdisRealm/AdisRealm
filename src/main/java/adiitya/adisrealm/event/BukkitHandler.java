package adiitya.adisrealm.event;

import adiitya.adisrealm.discord.DiscordBot;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static org.bukkit.entity.EntityType.ENDERMAN;

public final class BukkitHandler implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {

		e.setFormat("§7%s§r: %s");
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		e.setJoinMessage(String.format("§a+§r%s", p.getDisplayName()));

		DiscordBot.onJoin();
		DiscordBot.updateInfoMessage(false);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {

		Player p = e.getPlayer();
		e.setQuitMessage(String.format("§c-§r%s", p.getDisplayName()));

		DiscordBot.onLeave();
		DiscordBot.updateInfoMessage(false);
	}

	@EventHandler
	public void onEndermanGrief(EntityChangeBlockEvent e) {

		EntityType type = e.getEntityType();

		if (type.equals(ENDERMAN))
			e.setCancelled(true);
	}
}
