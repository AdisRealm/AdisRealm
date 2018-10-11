package adiitya.adisrealm;

import com.google.common.collect.Lists;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static adiitya.adisrealm.db.Tables.NICKNAMES;

public class DatabaseManager {

	private static DSLContext ctx;

	public DatabaseManager(Connection con, SQLDialect dialect) {

		if (ctx != null)
			throw new IllegalStateException();

		ctx = DSL.using(con, dialect);
	}

	public static List<String> getNicknames(UUID uuid) {

		if (ctx == null)
			return Lists.newArrayList();

		return ctx.selectFrom(NICKNAMES)
				.where(NICKNAMES.UUID.eq(uuid.toString()))
				.fetch(NICKNAMES.NICKNAME);
	}

	public static void addNickname(UUID uuid, String nickname) {

		if (ctx == null || nickname.length() < 3 || nickname.length() > 16)
			return;

		ctx.insertInto(NICKNAMES)
				.columns(NICKNAMES.UUID, NICKNAMES.NICKNAME)
				.values(uuid.toString(), nickname)
				.execute();
	}

	public static void removeNickname(UUID uuid, String nickname) {

		if (ctx == null)
			return;

		ctx.deleteFrom(NICKNAMES)
				.where(NICKNAMES.NICKNAME.equalIgnoreCase(nickname), NICKNAMES.UUID.eq(uuid.toString()))
				.execute();
	}

	public static void purgeNickname(String nickname) {

		if (ctx == null)
			return;

		ctx.deleteFrom(NICKNAMES)
				.where(NICKNAMES.NICKNAME.equalIgnoreCase(nickname))
				.execute();
	}

	public static Optional<UUID> getUserFromNickname(String nickname) {

		if (ctx == null)
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
}
