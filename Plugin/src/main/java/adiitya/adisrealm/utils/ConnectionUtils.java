package adiitya.adisrealm.utils;

import adiitya.adisrealm.AdisRealm;
import lombok.experimental.UtilityClass;
import org.bukkit.configuration.file.FileConfiguration;

@UtilityClass
class ConnectionUtils {

	private FileConfiguration config = AdisRealm.getInstance().getConfig();

	String getUrl() {
		return config.getString("dbUrl", "");
	}

	String getUsername() {
		return config.getString("dbUsername", "");
	}

	String getPassword() {
		return config.getString("dbPassword", "");
	}
}
