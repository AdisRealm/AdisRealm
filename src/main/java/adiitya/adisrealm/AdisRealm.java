package adiitya.adisrealm;

import adiitya.adisrealm.cmd.*;
import adiitya.adisrealm.discord.DiscordBot;
import adiitya.adisrealm.event.*;
import adiitya.adisrealm.utils.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import adiitya.adisrealm.discord.DiscordBot.MissingTokenException;
import sx.blah.discord.modules.Configuration;

public final class AdisRealm extends JavaPlugin {

	private Logger log;

	@Override
	public void onEnable() {

		long start = System.nanoTime();

		//disable jooq logo in console to remove spam
		System.setProperty("org.jooq.no-logo", "true");

		Configuration.AUTOMATICALLY_ENABLE_MODULES = false;
		Configuration.LOAD_EXTERNAL_MODULES = false;

		log = Bukkit.getLogger();

		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new BukkitHandler(), this);

		try {
			DataManager.connect(getDatabasePath(), Bukkit.getLogger());
		} catch (IOException e) {
			e.printStackTrace();
		}

		addCommand(new NicknameCommand());
		addCommand(new MessageCommand());
		addCommand(new ReplyCommand());

		try {
			DiscordBot.connect();
		} catch(MissingTokenException e) {
			log.info(e.getMessage());
		}

		log.info(() -> String.format("Enabled Adi's Realm (Took %.2fms)", (System.nanoTime() - start) / 1000000D));
	}

	private void addCommand(Command cmd) {

		PluginCommand command = getCommand(cmd.getName());

		log.fine(() -> String.format("Registering command %s", cmd.getName()));

		command.setExecutor(cmd);
		command.setTabCompleter(cmd);
	}

	private String getDatabasePath() throws IOException {

		File databaseFile = new File(getDataFolder(), "db.sqlite");

		if (!databaseFile.exists())
			databaseFile.createNewFile();

		return databaseFile.getAbsolutePath();
	}

	public static AdisRealm getInstance() {
		return getPlugin(AdisRealm.class);
	}

	@Override
	public void onDisable() {

		log.info("Disabling Adi's Realm");

		DiscordBot.disconnect();

		try {
			DataManager.dispose();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
