package adiitya.adisrealm;

import adiitya.adisrealm.cmd.ICommand;
import adiitya.adisrealm.cmd.NicknameCommand;
import adiitya.adisrealm.event.ChatHandler;
import adiitya.adisrealm.utils.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class AdisRealm extends JavaPlugin {

	private Logger log;

	public AdisRealm() {
		super();
	}

	protected AdisRealm(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
		super(loader, description, dataFolder, file);
	}

	@Override
	public void onEnable() {

		long start = System.nanoTime();

		//disable jooq logo in console to remove spam
		System.setProperty("org.jooq.no-logo", "true");

		log = Bukkit.getLogger();

		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new ChatHandler(), this);

		try {
			log.info("Connecting to the database");
			DataManager.connect(getDatabasePath(), Bukkit.getLogger());
		} catch (IOException e) {}

		addCommand(new NicknameCommand());

		log.info(String.format("Enabled Adi's Realm (Took %fms)", (System.nanoTime() - start) / 1000D));
	}

	private void addCommand(ICommand cmd) {

		PluginCommand command = getCommand(cmd.getName());

		log.log(Level.FINE, "Registering command {0}", cmd.getName());

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

		try {
			log.info("Disabling Adi's Realm");
			DataManager.dispose();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
