package dtu.dcr.engine;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

public class Simulation {

	@Getter
	private String id;
	@Getter
	private Process process;
	private HashSet<String> executedActivities = new HashSet<String>();
	private HashSet<String> included = new HashSet<String>();
	private HashSet<String> pending = new HashSet<String>();

	public Simulation(String id, Process process) {
		this.process = process;
		for (Activity a : process.getActivities()) {
			included.add(a.getId());
		}
	}

	public Set<String> getEnabledActivities() {
		HashSet<String> result = new HashSet<String>(included);
		for (Relation r : process.getRelations()) {
			switch (r.getRelation()) {
			case CONDITION:
				if ((included.contains(r.getSourceId()) && !executedActivities.contains(r.getSourceId()))) {
					result.remove(r.getTargetId());
				}
				break;
			case MILESTONE:
				if ((included.contains(r.getSourceId()) && pending.contains(r.getSourceId()))) {
					result.remove(r.getTargetId());
				}
				break;
			default:
				break;
			}
		}
		return result;
	}

	public void execute(String activityId) {
		pending.remove(activityId);
		executedActivities.add(activityId);

		for (Relation r : process.getRelations()) {
			if (!r.getSourceId().equals(activityId)) {
				continue;
			}

			switch (r.getRelation()) {
			case EXCLUDE:
				included.remove(r.getTargetId());
				break;
			case INCLUDE:
				included.add(r.getTargetId());
				break;
			case RESPONSE:
				pending.add(r.getTargetId());
				break;
			default:
				break;
			}
		}
	}

	public boolean isAccepting() {
		for (String a : pending) {
			if (included.contains(a)) {
				return false;
			}
		}
		return true;
	}

	public SimulationStatus buildStatus() {
		SimulationStatus toSerialize = new SimulationStatus();
		toSerialize.setEnabledActivities(getEnabledActivities());
		toSerialize.setPendingActivities(pending);
		toSerialize.setAccepting(isAccepting());
		return toSerialize;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Simulation) {
			return id.equals(((Simulation) obj).id);
		}
		return false;
	}
}
