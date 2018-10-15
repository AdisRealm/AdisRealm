package adiitya.adisrealm.utils;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

public final class MinecraftUtils {

	private static CloseableHttpClient client = HttpClients.createMinimal();

	private static final Function<String, String> USERNAMES_ENDPOINT = uuid -> String.format("https://api.mojang.com/user/profiles/%s/names", uuid);

	public static boolean userExists(UUID uuid) {
		return !getUsernames(uuid).isEmpty();
	}

	public static List<String> getUsernames(UUID uuid) {

		List<String> usernames = Lists.newArrayList();
		HttpGet request = new HttpGet(USERNAMES_ENDPOINT.apply(uuid.toString().replace("-", "")));

		try (CloseableHttpResponse response = client.execute(request)) {

			HttpEntity body = response.getEntity();
			int code = response.getStatusLine().getStatusCode();

			if (code == 200)
				usernames.addAll(extractUsernamesFromHistory(body.getContent()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return usernames;
	}

	private static List<String> extractUsernamesFromHistory(InputStream input) {

		List<String> usernames = Lists.newArrayList();
		JsonArray history = new JsonParser().parse(new InputStreamReader(input)).getAsJsonArray();

		history.forEach(e -> usernames.add(extractHistoryEntry(e)));

		return usernames;
	}

	private static String extractHistoryEntry(JsonElement e) {
		return e.getAsJsonObject()
				.get("name")
				.getAsString();
	}

	private MinecraftUtils() {}
}
