package sk.openhouse.automation.pipelineui.model;

import java.util.Map;

import sk.openhouse.automation.pipelinedomain.domain.PhaseState;

public class Build {

    private final int number;
    private final Map<String, PhaseState> states;

    /**
     * @param number
     * @param states build states, where key is phase name and value is its state
     */
    public Build(int number, Map<String, PhaseState> states) {

        this.number = number;
        this.states = states;
    }

    /**
     * @return build number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return build states, where key is phase name and value is its state
     */
    public Map<String, PhaseState> getStates() {
        return states;
    }
}
