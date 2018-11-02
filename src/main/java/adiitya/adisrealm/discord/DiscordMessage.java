package adiitya.adisrealm.discord;

import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.util.EmbedBuilder;

public final class DiscordMessage {

	private EmbedBuilder builder = new EmbedBuilder();

	public EmbedObject getEmbed() {
		return builder.build();
	}
}
