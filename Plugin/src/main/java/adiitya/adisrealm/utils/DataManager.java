package adiitya.adisrealm.utils;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import static adiitya.adisrealm.db.Tables.NICKNAMES;
import static adiitya.adisrealm.utils.ConnectionUtils.*;

public class DataManager {

	private static DSLContext ctx;
	private static Connection con;

	private static Logger log = Bukkit.getLogger();

	public DataManager() {

		if (ctx != null)
			throw new IllegalStateException();

		attemptReconnect();
	}

	public static List<String> getNicknames(UUID uuid) {

		if (!attemptReconnect())
			return Lists.newArrayList();

		return ctx.selectFrom(NICKNAMES)
				.where(NICKNAMES.UUID.eq(uuid.toString()))
				.fetch(NICKNAMES.NICKNAME);
	}

	public static int addNickname(UUID uuid, String nickname) {

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

	public static void removeNickname(UUID uuid, String nickname) {

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

	public static boolean isNickTaken(String nickname) {

		if (!attemptReconnect())
			return false;

		return ctx.select(NICKNAMES.NICKNAME)
				.from(NICKNAMES)
				.where(NICKNAMES.NICKNAME.equalIgnoreCase(nickname))
				.fetch()
				.isNotEmpty();
	}

	public static boolean hasNickname(UUID uuid, String nickname) {

		if (!attemptReconnect())
			return false;

		return ctx.select(NICKNAMES.NICKNAME)
				.from(NICKNAMES)
				.where(NICKNAMES.UUID.equalIgnoreCase(uuid.toString()))
				.and(NICKNAMES.NICKNAME.equalIgnoreCase(nickname))
				.fetch()
				.isNotEmpty();
	}

	public static Optional<UUID> getUserFromNickname(String nickname) {

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

	private static boolean attemptReconnect() {

		try {

			if (ctx != null && ctx.selectOne().from(NICKNAMES).fetch().isNotEmpty())
				return true;

			log.info("Reconnecting to database");
			con = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
			ctx = DSL.using(con, SQLDialect.MYSQL);

			return ctx.selectOne().from(NICKNAMES).fetch().isNotEmpty();
		} catch(Exception e) {

			log.log(Level.SEVERE, "Unable to reconnect to database", e);

			return false;
		}
	}

	public static void dispose() {

		try {
			con.close();
		} catch(Exception ignored) {}
	}
}
