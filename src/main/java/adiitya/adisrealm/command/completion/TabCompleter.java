package adiitya.adisrealm.command.completion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TabCompleter {

	private Map<Integer, List<TabCompletion>> completionMap;

	public TabCompleter() {
		completionMap = new HashMap<>();
	}

	public TabCompleter add(int pos, TabCompletion completion) {

		List<TabCompletion> completions = completionMap.getOrDefault(pos, new ArrayList<>());
		completions.add(completion);

		completionMap.put(pos, completions);

		return this;
	}

	public TabCompleter add(int pos, List<TabCompletion> completions) {

		completions.forEach(c -> add(pos, c));

		return this;
	}

	public List<String> get(String[] args) {

		List<String> results = new ArrayList<>();
		String search = args.length > 0 ? args[args.length - 1] : "";

		completionMap.getOrDefault(args.length, new ArrayList<>())
				.stream()
				.filter(c -> c.passes(search))
				.forEach(c -> results.add(c.getResult()));

		return results;
	}
}
