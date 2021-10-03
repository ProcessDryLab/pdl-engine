package dtu.dcr.engine;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Activity {

	@Getter
	private String id;
	@Getter
	private String name;
	@Getter
	private String role;

	public Activity(String name, String role) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.role = role;
	}

	public Activity(String name) {
		this(name, null);
	}

	@Override
	public String toString() {
		return name + " [" + role + "]";
	}
}
