package dtu.dcr.engine;

import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
public class SimulationStatus {

	@Getter
	@Setter
	private Set<String> enabledActivities;
	@Getter
	@Setter
	private boolean isAccepting;

}
