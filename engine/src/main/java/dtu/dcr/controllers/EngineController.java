package dtu.dcr.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import dtu.dcr.engine.Activity;
import dtu.dcr.engine.Process;

@RestController
@RequestMapping("/api/v1/dcr/")
@CrossOrigin
public class EngineController {

	private Map<String, Process> models = new HashMap<String, Process>();

	@PostMapping("/simulation/initialize")
	public ResponseEntity<String> initialize(@RequestBody String json) {
		Process p = Process.importFromJson(json);
		String id = UUID.randomUUID().toString();
		models.put(id, p);
		return ResponseEntity.ok(id);
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/simulation/{id}/status")
	public ResponseEntity<String> status(@PathVariable String id) {
		if (models.containsKey(id)) {
			final Process p = models.get(id);
			SimulationStatus toSerialize = new SimulationStatus();
			toSerialize.enabledActivities = p.getEnabledActivities();
			toSerialize.isAccepting = p.isAccepting();
			Gson gson = new Gson();
			return ResponseEntity.ok(gson.toJson(toSerialize));
		}
		return (ResponseEntity<String>) ResponseEntity.notFound();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/simulation/{id}/execute/{activity}")
	public ResponseEntity<String> status(@PathVariable String id, @PathVariable String activity) {
		if (models.containsKey(id)) {
			Process p = models.get(id);
			p.execute(p.getActivity(activity));
			return ResponseEntity.ok("ack");
		}
		return (ResponseEntity<String>) ResponseEntity.notFound();
	}
}

class SimulationStatus {
	public Set<Activity> enabledActivities;
	public boolean isAccepting;
}
