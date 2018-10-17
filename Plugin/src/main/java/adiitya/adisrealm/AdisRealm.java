package adiitya.adisrealm;

import adiitya.adisrealm.cmd.ICommand;
import adiitya.adisrealm.cmd.NicknameCommand;
import adiitya.adisrealm.event.ChatHandler;
import adiitya.adisrealm.utils.DataManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class AdisRealm extends JavaPlugin {

	@Getter private static AdisRealm instance;

	private Logger log;

	public AdisRealm() throws IllegalAccessException {

		if (instance != null)
			throw new IllegalAccessException();

		AdisRealm.instance = this;
	}

	@Override
	public void onEnable() {

		//disable jooq logo in console to remove spam
		System.setProperty("org.jooq.no-logo", "true");

		log = Bukkit.getLogger();

		try {

			log.info("Loading config");
			File config = new File(getDataFolder(), "config.yml");
			getConfig().load(config);

			getServer().getPluginManager().registerEvents(new ChatHandler(), this);

			log.info("Connecting to the database");
			new DataManager();
		} catch (InvalidConfigurationException e) {
			log.log(Level.SEVERE, "Invalid config file", e);
		} catch (FileNotFoundException e) {
			log.log(Level.SEVERE, "config.yml not found", e);
		} catch (IOException e) {
			log.log(Level.SEVERE, "Unable to read config file", e);
		}

		addCommand(new NicknameCommand());
	}

	private void addCommand(ICommand cmd) {

		PluginCommand command = getCommand(cmd.getName());

		log.fine("Registering command " + cmd.getName());

		command.setExecutor(cmd);
		command.setTabCompleter(cmd);
	}

	@Override
	public void onDisable() {

		DataManager.dispose();
	}
}
