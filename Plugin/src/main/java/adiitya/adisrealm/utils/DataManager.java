package adiitya.adisrealm.utils;

import adiitya.adisrealm.db.DefaultSchema;
import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import static adiitya.adisrealm.db.Tables.NICKNAMES;

@UtilityClass
public class DataManager {

	private DSLContext ctx;
	private Connection con;

	private String databasePath;

	private Logger log;

	public void connect(String path, Logger log) {

		DataManager.log = log;
		databasePath = path;

		try {
			con = DriverManager.getConnection("jdbc:sqlite:" + path);
			ctx = DSL.using(con, SQLDialect.SQLITE);
			log.info("Connected to database");
		} catch (Exception e) {
			log.severe("Unable to connect to database");
		}

		for (Table<?> table : DefaultSchema.DEFAULT_SCHEMA.getTables())
			ctx.createTableIfNotExists(table)
				.columns(table.fields())
				.execute();
	}

	public List<String> getNicknames(UUID uuid) {

		if (!attemptReconnect())
			return Lists.newArrayList();

		return ctx.selectFrom(NICKNAMES)
				.where(NICKNAMES.UUID.eq(uuid.toString()))
				.fetch(NICKNAMES.NICKNAME);
	}

	public int addNickname(UUID uuid, String nickname) {

		if (!attemptReconnect())
			return -1; // error

		if (nickname.length() < 3 || nickname.length() > 16)
			return 5; // bad_length

		if (isNickTaken(nickname))
			if (hasNickname(uuid, nickname))
				return 2; // duplicate
			else
				return 1; // taken

		ctx.insertInto(NICKNAMES)
				.columns(NICKNAMES.UUID, NICKNAMES.NICKNAME)
				.values(uuid.toString(), nickname)
				.execute();

		return hasNickname(uuid, nickname) ? 0 : -1; // success : error
	}

	public void removeNickname(UUID uuid, String nickname) {

		if (!attemptReconnect())
			return;

		ctx.deleteFrom(NICKNAMES)
				.where(NICKNAMES.NICKNAME.equalIgnoreCase(nickname), NICKNAMES.UUID.eq(uuid.toString()))
				.execute();
	}

	public static void purgeNickname(String nickname) {

		if (!attemptReconnect())
			return;

		ctx.deleteFrom(NICKNAMES)
				.where(NICKNAMES.NICKNAME.equalIgnoreCase(nickname))
				.execute();
	}

	public boolean isNickTaken(String nickname) {

		if (!attemptReconnect())
			return false;

		return ctx.select(NICKNAMES.NICKNAME)
				.from(NICKNAMES)
				.where(NICKNAMES.NICKNAME.equalIgnoreCase(nickname))
				.fetch()
				.isNotEmpty();
	}

	public boolean hasNickname(UUID uuid, String nickname) {

		if (!attemptReconnect())
			return false;

		return ctx.select(NICKNAMES.NICKNAME)
				.from(NICKNAMES)
				.where(NICKNAMES.UUID.equalIgnoreCase(uuid.toString()))
				.and(NICKNAMES.NICKNAME.equalIgnoreCase(nickname))
				.fetch()
				.isNotEmpty();
	}

	public Optional<UUID> getUUIDFromNickname(String nickname) {

		if (!attemptReconnect())
			return Optional.empty();

		if (ctx.select(NICKNAMES.UUID).from(NICKNAMES).where(NICKNAMES.NICKNAME.equalIgnoreCase(nickname)).fetch().isEmpty())
			return Optional.empty();

		String uuid = ctx.select(NICKNAMES.UUID)
				.from(NICKNAMES)
				.where(NICKNAMES.NICKNAME.equalIgnoreCase(nickname))
				.fetchOne()
				.component1();

		if (!uuid.equals(""))
			return Optional.of(UUID.fromString(uuid));

		return Optional.empty();
	}

	private boolean attemptReconnect() {

		try {

			if (ctx != null && ctx.selectOne().from("sqlite_master").fetch().isNotEmpty())
				return true;

			log.info("Reconnecting to database");
			connect(databasePath, log);

			return ctx.selectOne().from("sqlite_master").fetch().isNotEmpty();
		} catch(Exception e) {
			return false;
		}
	}

	public void dispose() throws SQLException {
		con.close();
	}
}
