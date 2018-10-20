package adiitya.adisrealm.cmd;

import adiitya.adisrealm.utils.DataManager;
import be.seeseemelk.mockbukkit.command.ConsoleCommandSenderMock;
import org.bukkit.ChatColor;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("/nick")
public final class NicknameTests {

	private static final UUID ADIITYA = UUID.fromString("06882ed8-a374-4198-899f-f604c989b377");

	@Nested
	@DisplayName("/nick add")
	public class AddTests{

		@Nested
		public class GivenConsoleSender extends CommandTest<ConsoleCommandSenderMock> {

			@Test
			public void shouldFailWithoutNickname() {

				command.execute(getSender(), "nick", new String[] {"add"});

				assertAll(
						() -> assertEquals("USAGE: /nickname add <nickname>", nextMessage()),
						() -> assertNull(nextMessage())
				);
			}

			@Test
			public void shouldFailWithSingleNickname() {

				command.execute(getSender(), "nick", new String[] {"add", "Aditya"});

				assertAll(
						() -> assertEquals("You must be a player to use that!", nextMessage()),
						() -> assertNull(nextMessage())
				);
			}

			@Override
			public String nextMessage() {
				String next = getSender().nextMessage();

				return next != null ? ChatColor.stripColor(next): null;
			}

			@Override
			public ConsoleCommandSenderMock getSender() {
				return (ConsoleCommandSenderMock) server.getConsoleSender();
			}
		}
	}

	@Nested
	@DisplayName("/nick list")
	public class ListTests {

		@Nested
		public class GivenConsoleSender extends CommandTest<ConsoleCommandSenderMock> {

			@BeforeEach
			public void setupDatabase() {

				DataManager.addNickname(ADIITYA, "Adi");
				DataManager.addNickname(ADIITYA, "Diit");
			}

			@Test
			public void shouldPassWithExistingTarget() {

				command.execute(getSender(), "nick", new String[]{"list", "Adiitya"});

				assertAll(
						"existing target",
						() -> assertEquals("Nicknames for Adiitya", nextMessage()),
						() -> assertEquals("> Adi", nextMessage()),
						() -> assertEquals("> Diit", nextMessage()),
						() -> assertNull(nextMessage())
				);
			}

			@Test
			public void shouldFailWithNoTarget() {

				command.execute(getSender(), "nick", new String[]{"list"});

				assertAll(
						"no target",
						() -> assertEquals("You must be a player to use that!", nextMessage()),
						() -> assertNull(nextMessage())
				);
			}

			@Override
			public String nextMessage() {

				String next = getSender().nextMessage();

				return next != null ? ChatColor.stripColor(next): null;
			}

			@Override
			public ConsoleCommandSenderMock getSender() {
				return (ConsoleCommandSenderMock) server.getConsoleSender();
			}
		}
	}
}
