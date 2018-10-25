package adiitya.adisrealm.utils;

import adiitya.adisrealm.utils.DataManager;
import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public final class DataManagerTests {

	private static final UUID ADIITYA = UUID.fromString("06882ed8-a374-4198-899f-f604c989b377");
	private static final UUID ASTRONYRA = UUID.fromString("9b2e9555-8968-42d4-a702-8de65744e752");

	private static ServerMock server;

	@BeforeAll
	public static void beforeAll() {
		System.setProperty("org.jooq.no-logo", "true");
		server = MockBukkit.mock();
	}

	@BeforeEach
	public void before() {
		DataManager.connect(":memory:", server.getLogger());
	}

	@Nested
	public class AddTests {

		@BeforeEach
		public void before() {
			DataManager.connect(":memory:", server.getLogger());
		}

		@Test
		public void shouldPassWithValidNickname() {

			String nickname = "Aditya";
			int status = DataManager.addNickname(ADIITYA, nickname);

			assertEquals(0, status);
		}

		@Test
		public void shouldFailWithIllegalSize() {

			String nickname = "0123456789abcdefg"; // 17 chars
			int status = DataManager.addNickname(ADIITYA, nickname);

			assertEquals(5, status);
		}

		@Test
		public void shouldFailWithDuplicateNickname() {

			String nickname = "Aditya";
			DataManager.addNickname(ADIITYA, nickname);

			int status = DataManager.addNickname(ADIITYA, nickname);

			assertEquals(2, status);
		}

		@Test
		public void shouldFailWithTakenNickname() {

			String nickname = "nickname";
			DataManager.addNickname(ASTRONYRA, nickname);

			int status = DataManager.addNickname(ADIITYA, nickname);

			assertEquals(1, status);
		}

		@AfterEach
		public void after() throws SQLException {
			DataManager.dispose();
		}
	}

	@Test
	public void shouldListNicknames() {

		DataManager.addNickname(ADIITYA, "Adi");
		DataManager.addNickname(ADIITYA, "Aditya");
		DataManager.addNickname(ASTRONYRA, "Astro");

		List<String> nicknames = DataManager.getNicknames(ADIITYA);

		assertEquals(Arrays.asList("Adi", "Aditya"), nicknames);
	}

	@Nested
	public class UUIDTests {

		@BeforeEach
		public void before() {
			DataManager.connect(":memory:", server.getLogger());
		}

		@AfterEach
		public void after() throws SQLException {
			DataManager.dispose();
		}

		@Test
		public void shouldPassWithNickname() {

			DataManager.addNickname(ADIITYA, "Adi");
			Optional<UUID> uuid = DataManager.getUUIDFromNickname("adi");

			assertTrue(uuid.isPresent());
		}
	}

	@Nested
	public class RemoveTests {

		@BeforeEach
		public void before() {
			DataManager.connect(":memory:", server.getLogger());
		}

		@AfterEach
		public void after() throws SQLException {
			DataManager.dispose();
		}

		@Test
		public void shouldPassWithoutNickname() {

			DataManager.removeNickname(ADIITYA, "Adi");
			assertFalse(DataManager.hasNickname(ADIITYA, "Adi"));
		}

		@Test
		public void shouldPassWithDifferentCase() {

			DataManager.addNickname(ADIITYA, "Adi");
			assertTrue(DataManager.isNickTaken("adi"));

			DataManager.removeNickname(ADIITYA, "aDI");
			assertFalse(DataManager.isNickTaken("adI"));
		}

		@Test
		public void shouldPassWithPurge() {

			DataManager.addNickname(ASTRONYRA, "Astro");
			DataManager.addNickname(ADIITYA, "Adi");

			DataManager.purgeNickname("astro");

			assertFalse(DataManager.isNickTaken("astro"));
			assertTrue(DataManager.isNickTaken("adi"));
		}
	}

	@AfterEach
	public void after() throws SQLException {
		DataManager.dispose();
	}
}
