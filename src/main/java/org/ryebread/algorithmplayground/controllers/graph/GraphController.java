package org.ryebread.algorithmplayground.controllers.graph;

import java.util.List;

import org.ryebread.algorithmplayground.services.graph.GraphService;
import org.ryebread.algorithmplayground.structures.graph.Graph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/graph")
public class GraphController {

	@Autowired
	private GraphService graphService;

	@PostMapping("/topological")
	public @ResponseBody <T extends Comparable<T>> List<T> topologicalSort(
			@RequestBody GraphComponents<T> graphComponents) {

		return null;
	}

}
