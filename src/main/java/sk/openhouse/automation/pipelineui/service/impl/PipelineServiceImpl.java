package sk.openhouse.automation.pipelineui.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sk.openhouse.automation.pipelineclient.BuildClient;
import sk.openhouse.automation.pipelineclient.PhaseClient;
import sk.openhouse.automation.pipelineclient.ProjectClient;
import sk.openhouse.automation.pipelineclient.VersionClient;
import sk.openhouse.automation.pipelinedomain.domain.PhaseState;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildPhaseResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.BuildResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.PhaseResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.ProjectResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.StateResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.VersionResponse;
import sk.openhouse.automation.pipelineui.model.Build;
import sk.openhouse.automation.pipelineui.service.PipelineService;

public class PipelineServiceImpl implements PipelineService {

    private ProjectClient projectClient;
    private VersionClient versionClient;
    private BuildClient buildClient;
    private PhaseClient phaseClient;

    public PipelineServiceImpl(ProjectClient projectClient, VersionClient versionClient,
            BuildClient buildClient, PhaseClient phaseClient) {

        this.projectClient = projectClient;
        this.versionClient = versionClient;
        this.buildClient = buildClient;
        this.phaseClient = phaseClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getProjectNames() {

        List<String> projectNames = new ArrayList<String>();
        List<ProjectResponse> projects = projectClient.getProjects().getProjects();
        for (ProjectResponse project : projects) {
            projectNames.add(project.getName());
        }

        return projectNames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getVersionNumbers(String projectName) {

        List<String> versionNumbers = new ArrayList<String>();
        List<VersionResponse> versions = versionClient.getVersions(projectName).getVersions();
        for (VersionResponse version : versions) {
            versionNumbers.add(version.getVersionNumber());
        }

        return versionNumbers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Build> getBuilds(String projectName, String versionNumber) {

        List<Build> builds = new ArrayList<Build>();
        List<BuildResponse> buildResponses = buildClient.getBuilds(projectName, versionNumber).getBuilds();
        for (BuildResponse buildResponse : buildResponses) {
            List<BuildPhaseResponse> buildPhases = buildResponse.getBuildPhases().getBuildPhases();
            Build build = new Build(buildResponse.getNumber(), getBuildStates(buildPhases));
            builds.add(build);
        }

        return builds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getPhaseNames(String projectName, String versionNumber) {

        List<String> phaseNames = new ArrayList<String>();
        List<PhaseResponse> phases = phaseClient.getPhases(projectName, versionNumber).getPhases();
        for (PhaseResponse phase : phases) {
            phaseNames.add(phase.getName());
        }

        return phaseNames;
    }

    /**
     * Creates map from supplied build phases, where key is the phase name and value is the last state
     * 
     * @param buildPhases
     * @return
     */
    private Map<String, PhaseState> getBuildStates(List<BuildPhaseResponse> buildPhases) {

        Map<String, PhaseState> buildStates = new HashMap<String, PhaseState>();
        for (BuildPhaseResponse buildPhase : buildPhases) {
            List<StateResponse> states = buildPhase.getStates().getStates();
            int index = states.size() - 1;
            PhaseState state = (index < 0) ? null : states.get(index).getName();
            buildStates.put(buildPhase.getName(), state);
        }

        return buildStates;
    }
}
