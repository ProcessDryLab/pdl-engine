package dtu.dcr.io.json;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dtu.dcr.engine.Activity;
import dtu.dcr.engine.Process;
import dtu.dcr.engine.Relation;
import dtu.dcr.engine.Relation.TYPES;
import lombok.Getter;

public class DCRJson {

	@Getter
	private Set<DCRJsonModel> DCRModel = new HashSet<>();

	public DCRJson(dtu.dcr.engine.Process p) {
		DCRJsonModel dcr = new DCRJsonModel();
		for (Activity a : p.getActivities()) {
			dcr.getEvents().add(new DCRJsonEvent(a));
		}
		for (Relation r : p.getRelations()) {
			dcr.getRules().add(new DCRJsonRule(r));
		}
		DCRModel.clear();
		DCRModel.add(dcr);
	}

	public String exportToJson() {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return gson.toJson(this);
	}

	public static Process importFromJson(String json) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		DCRJson DCRJson = gson.fromJson(json, DCRJson.class);
		DCRJsonModel model = DCRJson.getDCRModel().iterator().next();

		Process p = new Process();
		for (DCRJsonEvent e : model.getEvents()) {
			p.addActivity(e.getLabel());
		}
		for (DCRJsonRule r : model.getRules()) {
			p.addRelation(model.getEventFromId(r.getSource()).getActivity(), TYPES.valueOf(r.getType().toUpperCase()),
					model.getEventFromId(r.getTarget()).getActivity());
		}
		return p;
	}
}
