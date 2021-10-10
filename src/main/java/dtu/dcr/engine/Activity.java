package dtu.dcr.engine;

import java.util.UUID;

import lombok.Getter;

public class Activity {

	@Getter
	private String id;
	@Getter
	private String name;

	public Activity() {

	}

	public Activity(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public Activity(String name) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Activity) {
			return getId().equals(((Activity) obj).getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}
}
