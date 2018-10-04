package adiitya.adisrealm;

import org.bukkit.plugin.java.JavaPlugin;

public final class AdisRealm extends JavaPlugin {

	private static AdisRealm instance;

	public AdisRealm() throws Exception {

		if (instance != null)
			throw new IllegalAccessException();

		AdisRealm.instance = this;
	}

	@Override
	public void onEnable() {


	}

	@Override
	public void onDisable() {


	}
}
