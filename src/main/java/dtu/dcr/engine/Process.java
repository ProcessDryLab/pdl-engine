package dtu.dcr.engine;

import java.util.HashSet;

import org.apache.commons.lang3.tuple.Triple;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beamline.dcr.model.relations.DcrModel;
import dtu.dcr.engine.Relation.TYPES;
import lombok.Getter;

public class Process {

	@Getter
	private HashSet<Activity> activities = new HashSet<Activity>();
	@Getter
	private HashSet<Relation> relations = new HashSet<Relation>();

	public Activity addActivity(String activityName) {
		Activity a = new Activity(activityName);
		addActivity(a);
		return a;
	}

	public Activity addActivity(Activity act) {
		activities.add(act);
		return act;
	}

	public Activity getActivityFromName(String activityName) {
		for (Activity a : activities) {
			if (a.getName().equals(activityName)) {
				return a;
			}
		}
		return null;
	}

	public Activity getActivityFromId(String activityId) {
		for (Activity a : activities) {
			if (a.getId().equals(activityId)) {
				return a;
			}
		}
		return null;
	}

	public void addRelation(String sourceId, String relation, String targetId) {
		Activity srcActivity = getActivityFromId(sourceId);
		Activity trtActivity = getActivityFromId(targetId);
		TYPES relationType = TYPES.valueOf(relation);
		addRelation(srcActivity, relationType, trtActivity);

	}

	public void addRelation(Activity source, Relation.TYPES arr, Activity target) {
		relations.add(new Relation(source, arr, target));
	}

	public DcrModel exportToBeamlineModel() {
		DcrModel m = new DcrModel();
		for (Activity a : activities) {
			m.addActivity(a.getName());
		}
		for (Relation r : relations) {
			Activity source = getActivityFromId(r.getSourceId());
			Activity target = getActivityFromId(r.getTargetId());
			m.addRelation(Triple.of(source.getName(), target.getName(),
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
