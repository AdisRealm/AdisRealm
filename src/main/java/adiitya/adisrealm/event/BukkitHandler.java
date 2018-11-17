package adiitya.adisrealm.event;

import adiitya.adisrealm.discord.DiscordBot;
import adiitya.adisrealm.utils.AFKManager;
import adiitya.adisrealm.utils.Utils;
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

		if (broadcast && AFKManager.isAFK(player.getUniqueId()))
			Bukkit.broadcastMessage("§6§l[§c§l-§6§l]§f" + player.getDisplayName());

		AFKManager.exitAFK(player.getUniqueId());
	}
}
