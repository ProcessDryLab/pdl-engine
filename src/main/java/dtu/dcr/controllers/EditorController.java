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
import dtu.dcr.engine.Relation;
import dtu.dcr.io.json.DCRJson;

@RestController
@RequestMapping("/api/v1/dcr/")
@CrossOrigin
public class EditorController {

	@PostMapping("/edit/add/{source}/{relation}/{target}")
	public ResponseEntity<String> visualize(@RequestBody String json, @PathVariable String source,
			@PathVariable String relation, @PathVariable String target) {
		Process p = DCRJson.importFromJson(json);
		p.addRelation(new Activity(source), Relation.TYPES.valueOf(relation), new Activity(target));
		return ResponseEntity.ok(new DCRJson(p).exportToJson());
	}
}
