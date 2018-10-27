package adiitya.adisrealm;

import lombok.experimental.UtilityClass;
import org.bukkit.configuration.file.FileConfiguration;
import sx.blah.discord.Discord4J;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.modules.Configuration;

@UtilityClass
public final class DiscordBot {

	private static IDiscordClient client;
	private static boolean isInitialized = false;

	public static void init() {

		if (isInitialized)
			return;

		Discord4J.disableAudio();
		Configuration.AUTOMATICALLY_ENABLE_MODULES = false;
		Configuration.LOAD_EXTERNAL_MODULES = false;

		isInitialized = true;
	}

	public static void connect() throws MissingTokenException {

		if (client != null && client.isReady())
			return;

		FileConfiguration config = AdisRealm.getInstance().getConfig();

		if (!config.contains("token"))
			throw new MissingTokenException("token not found in config.yml");

		String token = config.getString("token");

		client = new ClientBuilder()
				.withToken(token)
				.login();
	}

	public static void disconnect() {

		if (client == null || !client.isLoggedIn())
			return;

		client.logout();
		client = null;
	}

	public static final class MissingTokenException extends Exception {

		public MissingTokenException(String message) {
			super(message);
		}
	}
}
