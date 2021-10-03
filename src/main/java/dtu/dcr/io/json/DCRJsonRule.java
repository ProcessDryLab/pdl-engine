package dtu.dcr.io.json;

import dtu.dcr.engine.Relation;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class DCRJsonRule {

	@Getter
	private String type;
	@Getter
	private String source;
	@Getter
	private String target;
	@Getter
	private String description = "";

	public DCRJsonRule(Relation r) {
		this.type = r.getRelation().name().toLowerCase();
		this.source = r.getSource().getId();
		this.target = r.getTarget().getId();
	}
}
