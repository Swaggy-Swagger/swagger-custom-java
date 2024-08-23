package io.swaggy.swagger.customlib.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChangesInPaths {
    private List<Map<String, Object>> changedEndpoints = new ArrayList<>();
    private List<Map<String, Object>> changedParameters = new ArrayList<>();

    public ChangesInPaths(List<Map<String, Object>> changedEndpoints, List<Map<String, Object>> changedParameters) {
        this.changedEndpoints = changedEndpoints;
        this.changedParameters = changedParameters;
    }

    public List<Map<String, Object>> getChangedEndpoints() {
        return changedEndpoints;
    }

    public List<Map<String, Object>> getChangedParameters() {
        return changedParameters;
    }
}
