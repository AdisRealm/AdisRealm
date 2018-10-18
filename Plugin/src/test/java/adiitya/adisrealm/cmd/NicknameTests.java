package adiitya.adisrealm.cmd;

import adiitya.adisrealm.AdisRealm;
import be.seeseemelk.mockbukkit.*;
import be.seeseemelk.mockbukkit.command.ConsoleCommandSenderMock;
import org.bukkit.command.PluginCommand;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("/nick")
public final class NicknameTests {

	private static ServerMock server;
	private static AdisRealm plugin;
	private static PluginCommand command;

	@BeforeAll
	public static void setup() {

		server = MockBukkit.mock();
		plugin = MockBukkit.load(AdisRealm.class);
		command = plugin.getCommand("nickname");
	}

	@Nested
	@DisplayName("/nick list")
	public class ListTests {

		@Nested
		public class GivenNoTarget {

			@Test
			public void shouldFailToExecuteWithConsoleSender() {

				ConsoleCommandSenderMock sender = (ConsoleCommandSenderMock) server.getConsoleSender();
				command.execute(sender, "nick", new String[] {"list"});

				assertEquals("§cYou must be a player to use that!", sender.nextMessage());
			}
		}
	}

	@AfterAll
	public static void dispose() {

		MockBukkit.unload();
	}
}
