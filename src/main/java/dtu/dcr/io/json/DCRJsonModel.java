package dtu.dcr.io.json;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

public class DCRJsonModel {

	@Getter
	private String id;
	@Getter
	private String title;
	@Getter
	private String description = "";
	@Getter
	private String type = "DCRModel";
	@Getter
	private Set<Object> roles = new HashSet<Object>();
	@Getter
	private Set<DCRJsonEvent> events = new HashSet<DCRJsonEvent>();
	@Getter
	private Set<DCRJsonRule> rules = new HashSet<DCRJsonRule>();

	public DCRJsonEvent getEventFromId(String id) {
		for (DCRJsonEvent e : events) {
			if (e.getId().equals(id)) {
				return e;
			}
		}
		return null;
	}
}
