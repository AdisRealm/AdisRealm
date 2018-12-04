package adiitya.adisrealm.utils.name;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public final class PlayerName {

	public static NameBuilder AFK_TAB = PlayerName.builder().addAFKTag().addFormatting();
	public static NameBuilder FORMATTED = PlayerName.builder().addFormatting();

	@Getter
	private final String name;

	private PlayerName(String name) {
		this.name = name;
	}

	public static NameBuilder builder() {
		return new NameBuilder();
	}

	public static final class NameBuilder {

		private List<NameElement> prefix = new ArrayList<>();

		private NameBuilder() {}

		public NameBuilder addAFKTag() {
			prefix.add(NameElement.AFK_PREFIX);
			return this;
		}

		public NameBuilder addFormatting() {
			prefix.add(NameElement.FORMATTING_PREFIX);
			return this;
		}

		public PlayerName build(String name) {

			String before = prefix.stream()
					.map(e -> e.get(name))
					.collect(Collectors.joining());

			return new PlayerName(String.format("%s%s", before, name));
		}
	}
}
