package dtu.dcr.io.json;

import dtu.dcr.engine.Activity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class DCRJsonEvent {

	@Getter
	private String id;
	@Getter
	private String label;

	public DCRJsonEvent(Activity a) {
		this.id = a.getId();
		this.label = a.getName();
	}

	public Activity getActivity() {
		return new Activity(getLabel());
	}
}
