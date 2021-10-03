package dtu.dcr.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import beamline.dcr.view.DcrModelView;
import dtu.dcr.engine.Process;
import dtu.dcr.io.json.DCRJson;

@RestController
@RequestMapping("/api/v1/dcr/")
@CrossOrigin
public class VisualizerController {

	@PostMapping("/dcr2graphviz")
	public ResponseEntity<String> visualize(@RequestBody String json) {
		Process p = DCRJson.importFromJson(json);
		DcrModelView view = new DcrModelView(p.exportToBeamlineModel());
		return ResponseEntity.ok(view.toString());
	}
}