package adiitya.adisrealm.cmd;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

@UtilityClass
public class CommandUtils {

	public String getUsage(ICommand cmd) {
		return Bukkit.getPluginCommand(cmd.getName()).getUsage();
	}
}
