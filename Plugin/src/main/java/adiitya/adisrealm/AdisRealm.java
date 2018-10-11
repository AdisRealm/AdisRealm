package adiitya.adisrealm;

import adiitya.adisrealm.cmd.ICommand;
import adiitya.adisrealm.cmd.NicknameCommand;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jooq.SQLDialect;

import java.sql.DriverManager;
import java.sql.SQLException;

public final class AdisRealm extends JavaPlugin {

	private static AdisRealm instance;

	public AdisRealm() throws Exception {

		if (instance != null)
			throw new IllegalAccessException();

		AdisRealm.instance = this;
	}

	@Override
	public void onEnable() {

		//disable jooq logo in console to remove spam
		System.setProperty("org.jooq.no-logo", "true");

		try {
			System.out.println("Connecting to the database");
			new DatabaseManager(DriverManager.getConnection("jdbc:mysql://ldn.sql.cubedhost.com/gs10044", "gs10044", "HTezYy6L7m9j"), SQLDialect.MYSQL);
		} catch(SQLException e) {
			System.out.println("Unable to connect to database");
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


	}
}
