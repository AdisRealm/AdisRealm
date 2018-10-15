package adiitya.adisrealm;

import adiitya.adisrealm.utils.DataManager;
import org.jooq.SQLDialect;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockFileDatabase;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class DatabaseManagerTest {

	final UUID uuid = UUID.fromString("06882ed8-a374-4198-899f-f604c989b377");

	@BeforeClass
	public static void setup() throws IOException {

		Connection con = new MockConnection(new MockFileDatabase(DatabaseManagerTest.class.getResourceAsStream("/mockdb.db")));
		new DataManager(con, SQLDialect.SQLITE);
	}

	@Test
	public void getNicknames() {

		List<String> nicknames = DataManager.getNicknames(uuid);
		assertEquals(Arrays.asList("Adi", "Diit"), nicknames);
	}

	@Test
	public void removeNickname() {

		DataManager.removeNickname(uuid, "Diit");

	}
}