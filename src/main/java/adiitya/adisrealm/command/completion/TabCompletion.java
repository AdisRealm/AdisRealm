package adiitya.adisrealm.command.completion;

import lombok.Getter;

import java.util.function.Predicate;

public final class TabCompletion {

	@Getter private final String result;
	private final Predicate<String> condition;

	public TabCompletion(String result, Predicate<String> condition) {
		this.result = result;
		this.condition = condition;
	}

	public boolean passes(String test) {
		return condition.test(test);
	}
}
