package adiitya.adisrealm.cmd;

import adiitya.adisrealm.AdisRealm;
import adiitya.adisrealm.utils.DataManager;
import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.command.ConsoleCommandSenderMock;
import be.seeseemelk.mockbukkit.plugin.PluginManagerMock;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static org.joor.Reflect.on;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("/nick")
public final class NicknameTests {

	private static final UUID ADIITYA = UUID.fromString("06882ed8-a374-4198-899f-f604c989b377");

	private static ServerMock server;
	private static AdisRealm plugin;
	private static PluginCommand command;

	private static PluginManagerMock manager;

	@BeforeAll
	public static void setup() {

		server = MockBukkit.mock();
		plugin = MockBukkit.load(AdisRealm.class);
		command = plugin.getCommand("nickname");

		manager = server.getPluginManager();

		DataManager.connect(":memory:");
		setupDatabase();
	}

	private static void setupDatabase() {

		DataManager.addNickname(ADIITYA, "Adi");
		DataManager.addNickname(ADIITYA, "Diit");

		DataManager.addNickname(UUID.randomUUID(), "nickname1");
		DataManager.addNickname(UUID.randomUUID(), "nickname2");
		DataManager.addNickname(UUID.randomUUID(), "nickname3");
	}

	@Nested
	@DisplayName("/nick list")
	public class ListTests {

		@Nested
		public class GivenConsoleSender {

			private ConsoleCommandSenderMock sender;

			@BeforeEach
			public void before() {
				sender = (ConsoleCommandSenderMock) server.getConsoleSender();
			}

			@Test
			public void shouldExecuteWithExistingTarget() {

				command.execute(sender, "nick", new String[]{"list", "Adiitya"});

				assertAll(
						() -> assertEquals("Nicknames for Adiitya", ChatColor.stripColor(sender.nextMessage())),
						() -> assertEquals("> Adi", ChatColor.stripColor(sender.nextMessage())),
						() -> assertEquals("> Diit", ChatColor.stripColor(sender.nextMessage())),
						() -> assertNull(sender.nextMessage())
				);
			}

			@Test
			public void shouldFailToExecuteWithoutTarget() {

				command.execute(sender, "nick", new String[]{"list"});

				assertAll(
						() -> assertEquals("You must be a player to use that!", ChatColor.stripColor(sender.nextMessage())),
						() -> assertNull(sender.nextMessage())
				);
			}
		}
	}

	@AfterAll
	public static void dispose() {

		on(manager).field("temporaryFiles").call("clear");
		MockBukkit.unload();
	}
}
