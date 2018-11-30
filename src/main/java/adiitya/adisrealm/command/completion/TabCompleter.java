package adiitya.adisrealm.command.completion;

import java.util.*;
import java.util.function.BiPredicate;

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

	public TabCompleter add(int pos, List<String> completions, BiPredicate<String, String> condition) {

		completions.stream()
				.map(s -> new TabCompletion(s, test -> condition.test(test, s)))
				.forEach(c -> add(pos, c));

		return this;
	}

	public List<String> get(String[] args) {
		return get(Arrays.asList(args));
	}

	public List<String> get(List<String> args) {

		if (args.isEmpty())
			return new ArrayList<>();

		List<String> results = new ArrayList<>();
		String search = args.get(args.size() - 1);

		completionMap.getOrDefault(args.size(), new ArrayList<>())
				.stream()
				.filter(c -> c.passes(search))
				.forEach(c -> results.add(c.getResult()));

		return results;
	}
}
