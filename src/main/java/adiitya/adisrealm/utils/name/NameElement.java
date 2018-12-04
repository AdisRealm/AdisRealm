package adiitya.adisrealm.utils.name;

import adiitya.adisrealm.NameColorManager;

import java.util.function.Function;

public enum NameElement {

	AFK_PREFIX(name -> "\u00A76\u00A7l[AFK]\u00A7r"),
	FORMATTING_PREFIX(name -> NameColorManager.getColor(name).toString());

	private final Function<String, String> operation;

	NameElement(Function<String, String> operation) {
		this.operation = operation;
	}

	public String get(String name) {
		return operation.apply(name);
	}
}
