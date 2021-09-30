package dtu.dcr.engine;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Activity {

	@Getter
	private String name;
	@Getter
	private String role;

	public Activity(String name, String role) {
		this.name = name;
		this.role = role;
	}

	public Activity(String name) {
		this.name = name;
		this.role = null;
	}

	@Override
	public String toString() {
		return name + " [" + role + "]";
	}
}
