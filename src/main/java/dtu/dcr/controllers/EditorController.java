package dtu.dcr.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dtu.dcr.engine.Activity;
import dtu.dcr.engine.Process;
import dtu.dcr.io.json.DCRJson;

@RestController
@RequestMapping("/api/v1/dcr/")
@CrossOrigin
public class EditorController {

	@PostMapping("/edit/add/{source}/{relation}/{target}")
	public ResponseEntity<Process> visualize(@RequestBody Process p, @PathVariable String source,
			@PathVariable String relation, @PathVariable String target) {
		Activity s = p.getActivityFromName(source);
		if (s == null) {
			s = p.addActivity(source);
		}
		Activity t = p.getActivityFromName(target);
		if (t == null) {
			t = p.addActivity(target);
		}
		p.addRelation(s.getId(), relation, t.getId());
		return ResponseEntity.ok(p);
	}

	@PostMapping("/importFromDcrJson")
	public ResponseEntity<Process> importFromDCRJson(@RequestBody String json) {
		Process p = DCRJson.importFromJson(json);
		return ResponseEntity.ok(p);
	}

	@PostMapping("/exportToDcrJson")
	public ResponseEntity<String> exportToDCRJson(@RequestBody Process p) {
		return ResponseEntity.ok(new DCRJson(p).exportToJson());
	}
}
