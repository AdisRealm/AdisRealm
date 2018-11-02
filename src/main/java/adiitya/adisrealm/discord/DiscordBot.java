package adiitya.adisrealm.discord;

import adiitya.adisrealm.AdisRealm;
import adiitya.adisrealm.event.DiscordHandler;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;

@UtilityClass
public final class DiscordBot {

	private static IDiscordClient client;
	private static IGuild guild;
	private static IMessage infoMessage;

	private static int onlinePlayers = 0;

	public static void connect() throws MissingTokenException {

		if (client != null && client.isReady())
			return;

		FileConfiguration config = AdisRealm.getInstance().getConfig();

		if (!config.contains("token"))
			throw new MissingTokenException("token not found in config.yml");

		String token = config.getString("token");

		client = new ClientBuilder()
				.withToken(token)
				.registerListener(new DiscordHandler())
				.login();

		updateInfoMessage(false);
	}

	public void sendMessage(IChannel channel, DiscordMessage message) {
		channel.sendMessage(message.getEmbed());
	}

	public void sendMessage(IChannel channel, String message) {
		channel.sendMessage(message);
	}

	public void updateInfoMessage(boolean dispose) {

		if (!initMessage())
			return;

		infoMessage.edit(buildInfoMessage(dispose));
	}

	private boolean initMessage() {

		if (!client.isReady())
			return false;

		FileConfiguration config = AdisRealm.getInstance().getConfig();

		if (!config.contains("infoId"))
			return false;

		if (!config.contains("guildId"))
			return false;

		IGuild g = client.getGuildByID(config.getLong("guildId"));
		IMessage message;

		if (g != null) {
			guild = g;
			message = g.getMessageByID(config.getLong("infoId"));
		} else {
			return false;
		}

		if (message != null)
			infoMessage = message;
		else
			return false;

		return true;
	}

	private static EmbedObject buildInfoMessage(boolean dispose) {

		EmbedBuilder b = new EmbedBuilder();

		b.withAuthorIcon(guild.getIconURL());
		b.withAuthorName(guild.getName());

		b.appendField("Bot status", dispose ? ":x:" : ":white_check_mark:", false);

		b.appendField("IP", "mc.adisrealm.tk", true);
		b.appendField("Seed", "" + Bukkit.getWorlds().get(0).getSeed(), true);

		b.appendField("Players online", getPlayers(), false);

		b.withFooterText("Made by " + client.fetchUser(154403301159469056L).getName());

		return b.build();
	}

	private String getPlayers() {

		int max = Bukkit.getMaxPlayers();

		return String.format("%s/%s", onlinePlayers, max);
	}

	public void onJoin() {
		onlinePlayers += 1;
	}

	public void onLeave() {
		onlinePlayers -= 1;
	}

	public static void disconnect() {

		if (client == null || !client.isReady())
			return;

		updateInfoMessage(true);

		client.logout();
	}

	public static final class MissingTokenException extends Exception {

		public MissingTokenException(String message) {
			super(message);
		}
	}
}
