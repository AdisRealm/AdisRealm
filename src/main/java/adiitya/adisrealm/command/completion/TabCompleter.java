package adiitya.adisrealm.command.completion;

import java.util.*;

public final class TabCompleter {

	private final Map<Integer, List<TabCompletion>> completionMap;

	public TabCompleter() {
		completionMap = new HashMap<>();
	}

	public static List<String> empty() {
		return new TabCompleter().get(new ArrayList<>());
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
		return get(Arrays.asList(args));
	}

	public List<String> get(List<String> args) {

		List<String> results = new ArrayList<>();
		String search = args.isEmpty() ? args.get(args.size() - 1) : "";

		completionMap.getOrDefault(args.size(), new ArrayList<>())
				.stream()
				.filter(c -> c.passes(search))
				.forEach(c -> results.add(c.getResult()));

		return results;
	}
}
