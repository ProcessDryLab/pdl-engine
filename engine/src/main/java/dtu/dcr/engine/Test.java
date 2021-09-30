package dtu.dcr.engine;

public class Test {

//	public static void main(String[] args) throws IOException {
//
//		System.out.print("enter json: ");
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		Process p = Process.importFromJson(br.readLine());
////		Process p = new Process();
//
////		Activity create = new Activity("Create");
////		Activity approve = new Activity("Approve");
////		Activity reject = new Activity("Reject");
////		Activity payout = new Activity("Payout");
////
////		// BPM Expense claim
////		p.addActivity(create, true);
////		p.addRelation(create, TYPES.EXCLUDE, create);
////		p.addRelation(create, TYPES.CONDITION, approve);
////		p.addRelation(create, TYPES.CONDITION, reject);
////		p.addRelation(create, TYPES.RESPONSE, payout);
////		p.addRelation(approve, TYPES.CONDITION, payout);
////		p.addRelation(approve, TYPES.INCLUDE, payout);
////		p.addRelation(reject, TYPES.EXCLUDE, payout);
//
////		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
////		while (true) {
//		System.out.println("Enabled activities: " + p.enabled());
//		System.out.println("Process complete: " + p.isAccepting());
////			System.out.print("Enter the activity to execute: ");
////			String in = br.readLine();
////			p.execute(p.getActivity(in));
////
////		}
//
//		DcrModelView view = new DcrModelView(p.exportToBeamlineModel());
//		System.out.println(view);
////		System.out.println(p.exportToJson());
//	}
}
