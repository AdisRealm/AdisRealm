package adiitya.adisrealm.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatHandler implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {

		e.setFormat("§7%s§r: %s");
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		e.setJoinMessage(String.format("§a+§r%s", p.getDisplayName()));
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {

		Player p = e.getPlayer();
		e.setQuitMessage(String.format("§c-§r%s", p.getDisplayName()));
	}
}
