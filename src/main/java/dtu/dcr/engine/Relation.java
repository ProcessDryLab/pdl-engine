package dtu.dcr.engine;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Relation {

	public enum TYPES {
		CONDITION, RESPONSE, INCLUDE, EXCLUDE, MILESTONE
	}

	@Getter
	private String sourceId;
	@Getter
	private String targetId;
	@Getter
	private TYPES relation;

	public Relation() {

	}

	public Relation(Activity source, TYPES relation, Activity target) {
		this.sourceId = source.getId();
		this.targetId = target.getId();
		this.relation = relation;
	}
}