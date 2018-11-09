package adiitya.adisrealm.command.completion;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@UtilityClass
public class TabCompletions {

	public List<TabCompletion> players() {

		return Bukkit.getOnlinePlayers().stream()
				.map((Player p) -> new TabCompletion(p.getName(), test -> startsWith(test, p.getName())))
				.collect(Collectors.toList());
	}

	public boolean startsWith(String test, String string) {

		String pattern = String.format("^\\Q%s\\E(\\w)*", test);
		Matcher matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(string);

		return matcher.matches();
	}

	public TabCompletion never() {
		return new TabCompletion("", test -> false);
	}

	public TabCompletion always(String result) {
		return new TabCompletion(result, test -> true);
	}
}
