package dtu.dcr.engine;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Relation {

	public enum TYPES {
		CONDITION, RESPONSE, INCLUDE, EXCLUDE, MILESTONE
	}

	@Getter
	private Activity source;
	@Getter
	private Activity target;
	@Getter
	private TYPES relation;

	public Relation(Activity source, TYPES relation, Activity target) {
		this.source = source;
		this.target = target;
		this.relation = relation;
	}
}