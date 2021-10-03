package dtu.dcr.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import beamline.dcr.view.DcrModelView;
import dtu.dcr.engine.Relation.TYPES;
import dtu.dcr.io.json.DCRJson;

public class Test {

	public static void main(String[] args) throws IOException {

		boolean testInput = false;

		if (testInput) {

			Process p = new Process();

			Activity create = new Activity("Create");
			Activity approve = new Activity("Approve");
			Activity reject = new Activity("Reject");
			Activity payout = new Activity("Payout");

			// BPM Expense claim
			p.addActivity(create, true);
			p.addRelation(create, TYPES.EXCLUDE, create);
			p.addRelation(create, TYPES.CONDITION, approve);
			p.addRelation(create, TYPES.CONDITION, reject);
			p.addRelation(create, TYPES.RESPONSE, payout);
			p.addRelation(approve, TYPES.CONDITION, payout);
			p.addRelation(approve, TYPES.INCLUDE, payout);
			p.addRelation(reject, TYPES.EXCLUDE, payout);

			DCRJson json = new DCRJson(p);
			System.out.println(json.exportToJson());

		} else {

			System.out.print("enter json: ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			Process p = DCRJson.importFromJson(br.readLine());

			DcrModelView view = new DcrModelView(p.exportToBeamlineModel());
			System.out.println(view);

		}
	}
}
