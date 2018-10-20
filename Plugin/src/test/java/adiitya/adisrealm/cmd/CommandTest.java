package adiitya.adisrealm.cmd;

import adiitya.adisrealm.AdisRealm;
import adiitya.adisrealm.utils.DataManager;
import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static org.jooq.tools.reflect.Reflect.on;

public abstract class CommandTest<T extends CommandSender> {

	protected static ServerMock server;
	protected static AdisRealm plugin;
	protected static PluginCommand command;

	@BeforeAll
	public static void setup() {

		server = MockBukkit.mock();
		plugin = MockBukkit.load(AdisRealm.class);
		command = plugin.getCommand("nickname");

		DataManager.connect(":memory:");
	}

	@AfterAll
	public static void dispose() {

		on(server.getPluginManager())
				.field("temporaryFiles")
				.call("clear");

		MockBukkit.unload();
	}

	abstract T getSender();
	abstract String nextMessage();
}
