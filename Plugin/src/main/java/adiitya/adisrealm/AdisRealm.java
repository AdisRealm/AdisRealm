package adiitya.adisrealm;

import adiitya.adisrealm.cmd.ICommand;
import adiitya.adisrealm.cmd.NicknameCommand;
import adiitya.adisrealm.utils.DataManager;
import lombok.Getter;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class AdisRealm extends JavaPlugin {

	@Getter private static AdisRealm instance;

	public AdisRealm() throws IllegalAccessException {

		if (instance != null)
			throw new IllegalAccessException();

		AdisRealm.instance = this;
	}

	@Override
	public void onEnable() {

		//disable jooq logo in console to remove spam
		System.setProperty("org.jooq.no-logo", "true");

		try {
			System.out.println("Loading config");
			File config = new File(getDataFolder(), "config.yml");
			System.out.println(config.getAbsolutePath());
			getConfig().load(config);

			System.out.println("Connecting to the database");
			new DataManager();
		} catch (InvalidConfigurationException e) {
			System.out.println("Invalid config file");
		} catch (FileNotFoundException e) {
			System.out.println("config.yml not found");
		} catch (IOException e) {
			System.out.println("Unable to read config file");
		}

		addCommand(new NicknameCommand());
	}

	private void addCommand(ICommand cmd) {

		PluginCommand command = getCommand(cmd.getName());

		command.setExecutor(cmd);
		command.setTabCompleter(cmd);
	}

	@Override
	public void onDisable() {

		DataManager.dispose();
	}
}
