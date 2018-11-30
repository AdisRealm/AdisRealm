package adiitya.adisrealm.utils.name;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public final class PlayerName {

	@Getter
	private final String playerName;
	final Map<NameElement, String> elements = new EnumMap<>(NameElement.class);

	public PlayerName(String playerName) {
		this.playerName = playerName;
		setElement(NameElement.AFK_PREFIX, "§6[§lAFK§r§6]");
	}

	public String getName(NameElement... elements) {

		List<String> prefix = Arrays.stream(elements)
				.sorted(Comparator.comparingInt(e -> e.z))
				.map(this::getElement)
				.collect(Collectors.toList());

		return String.format("%s%s", String.join("", prefix), playerName);
	}

	public String getElement(NameElement element) {
		return elements.getOrDefault(element, "");
	}

	public void setElement(NameElement element, String value) {
		elements.put(element, value);
	}
}
