package adiitya.adisrealm.event;

import adiitya.adisrealm.AdisRealm;
import adiitya.adisrealm.discord.DiscordBot;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;

public final class DiscordHandler {

	@EventSubscriber
	public void onReady(GuildCreateEvent e) {

		FileConfiguration config = AdisRealm.getInstance().getConfig();

		if (!config.contains("guildId"))
			return;

		if (e.getGuild().getLongID() != config.getLong("guildId"))
			return;

		DiscordBot.updateInfoMessage(false);
	}
}
