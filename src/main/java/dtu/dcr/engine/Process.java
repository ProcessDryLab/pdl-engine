package dtu.dcr.engine;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.tuple.Triple;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beamline.dcr.model.relations.DcrModel;
import lombok.Getter;

public class Process {

	@Getter
	private HashSet<Activity> activities = new HashSet<Activity>();
	private HashSet<Activity> executedActivities = new HashSet<Activity>();
	@Getter
	private HashSet<Relation> relations = new HashSet<Relation>();
	private HashSet<Activity> included = new HashSet<Activity>();
	private HashSet<Activity> pending = new HashSet<Activity>();

	public void addActivity(String activityName) {
		addActivity(new Activity(activityName, null));
	}

	public void addActivity(String activityName, boolean isPending) {
		addActivity(new Activity(activityName, null), isPending);
	}

	public void addActivity(Activity act) {
		addActivity(act, false);
	}

	public void addActivity(Activity act, boolean isPending) {
		activities.add(act);
		included.add(act);
		if (isPending) {
			pending.add(act);
		}
	}

	public Activity getActivity(String activityName) {
		for (Activity a : activities) {
			if (a.getName().equals(activityName)) {
				return a;
			}
		}
		return null;
	}

	public void addRelation(Activity source, Relation.TYPES arr, Activity target) {
		addActivity(source);
		addActivity(target);
		relations.add(new Relation(source, arr, target));
	}

	public Set<Activity> getEnabledActivities() {
		HashSet<Activity> result = new HashSet<Activity>(included);
		for (Relation r : relations) {
			switch (r.getRelation()) {
			case CONDITION:
				if ((included.contains(r.getSource()) && !executedActivities.contains(r.getSource()))) {
					result.remove(r.getTarget());
				}
				break;
			case MILESTONE:
				if ((included.contains(r.getSource()) && pending.contains(r.getSource()))) {
					result.remove(r.getTarget());
				}
				break;
			default:
				break;
			}
		}
		return result;
	}

	public void execute(Activity e) {
		pending.remove(e);
		executedActivities.add(e);

		for (Relation r : this.relations) {
			if (!r.getSource().equals(e)) {
				continue;
			}

			switch (r.getRelation()) {
			case EXCLUDE:
				included.remove(r.getTarget());
				break;
			case INCLUDE:
				included.add(r.getTarget());
				break;
			case RESPONSE:
				pending.add(r.getTarget());
				break;
			default:
				break;
			}
		}
	}

	public boolean isAccepting() {
		for (Activity a : pending) {
			if (included.contains(a)) {
				return false;
			}
		}
		return true;
	}

	public DcrModel exportToBeamlineModel() {
		DcrModel m = new DcrModel();
		for (Activity a : activities) {
			m.addActivity(a.getName());
		}
		for (Relation r : relations) {
			m.addRelation(Triple.of(r.getSource().getName(), r.getTarget().getName(),
					DcrModel.RELATION.valueOf(r.getRelation().toString())));
		}
		return m;
	}

	public static Process importFromJson(String json) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.fromJson(json, Process.class);
	}

	public String exportToJson() {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.toJson(this);
	}
}
