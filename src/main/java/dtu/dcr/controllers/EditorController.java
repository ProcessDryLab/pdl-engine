package dtu.dcr.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dtu.dcr.engine.Process;
import dtu.dcr.io.json.DCRJson;

@RestController
@RequestMapping("/api/v1/dcr/")
@CrossOrigin
public class EditorController {

	@PostMapping("/edit/add/{source}/{relation}/{target}")
	public ResponseEntity<String> visualize(@RequestBody String json, @PathVariable String source,
			@PathVariable String relation, @PathVariable String target) {
		Process p = Process.importFromJson(json);
		p.addRelation(source, relation, target);
		return ResponseEntity.ok(p.exportToJson());
	}

	@PostMapping("/importFromDcrJson")
	public ResponseEntity<String> importFromDCRJson(@RequestBody String json) {
		Process p = DCRJson.importFromJson(json);
		return ResponseEntity.ok(p.exportToJson());
	}

	@PostMapping("/exportToDcrJson")
	public ResponseEntity<String> exportToDCRJson(@RequestBody String json) {
		Process p = Process.importFromJson(json);
		return ResponseEntity.ok(new DCRJson(p).exportToJson());
	}
}
