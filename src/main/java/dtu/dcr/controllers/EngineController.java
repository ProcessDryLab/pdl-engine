package dtu.dcr.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dtu.dcr.engine.Process;
import dtu.dcr.engine.Simulation;
import dtu.dcr.engine.SimulationStatus;

@RestController
@RequestMapping("/api/v1/dcr/")
@CrossOrigin
public class EngineController {

	private Map<String, Simulation> simulations = new HashMap<String, Simulation>();

	@PostMapping("/simulation/initialize")
	public ResponseEntity<String> initialize(@RequestBody Process p) {
		String id = UUID.randomUUID().toString();
		Simulation s = new Simulation(id, p);
		simulations.put(id, s);
		return ResponseEntity.ok(id);
	}

	@GetMapping("/simulation/{simulationId}/status")
	public ResponseEntity<SimulationStatus> status(@PathVariable String simulationId) {
		if (simulations.containsKey(simulationId)) {
			return ResponseEntity.ok(simulations.get(simulationId).buildStatus());
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/simulation/{id}/reset")
	public ResponseEntity<String> reset(@PathVariable String id) {
		Process p = simulations.get(id).getProcess();
		return initialize(p);
	}

	@GetMapping("/simulation/{simulationId}/execute/{activityId}")
	public ResponseEntity<SimulationStatus> status(@PathVariable String simulationId, @PathVariable String activityId) {
		if (simulations.containsKey(simulationId)) {
			Simulation s = simulations.get(simulationId);
			s.execute(activityId);
			return ResponseEntity.ok(s.buildStatus());
		}
		return ResponseEntity.notFound().build();
	}
}
