package sk.openhouse.automation.pipelineui.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;

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
import sk.openhouse.automation.pipelineui.service.PipelineException;
import sk.openhouse.automation.pipelineui.service.PipelineService;

public class PipelineServiceImpl implements PipelineService {

    private static final Logger logger = Logger.getLogger(PipelineServiceImpl.class);

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
    public List<ProjectResponse> getProjectResponses() {

        List<ProjectResponse> projects;
        try {
            projects = projectClient.getProjects().getProjects();
        } catch (UniformInterfaceException e) {
            String message = String.format("Unexpected response from pipeline service - %s", e.getMessage());
            logger.fatal(message);
            throw new PipelineException(message, e);
        } catch(ClientHandlerException e) {
            String message = String.format("Failed to process HTTP Reqeust/Response - %s", e.getMessage());
            logger.fatal(message);
            throw new PipelineException(message, e);
        }

        return projects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addProject(String projectName) {

        try {
            return projectClient.addProject(projectName);
        } catch (ClientHandlerException e) {
            logger.fatal(String.format("Failed to add %s project.", projectName), e);
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<VersionResponse> getVersionResponses(String projectName) {

        List<VersionResponse> versions;
        try {
            versions = versionClient.getVersions(projectName).getVersions();
        } catch (UniformInterfaceException e) {
            String message = String.format("Unexpected response from pipeline service - %s", e.getMessage());
            logger.fatal(message);
            throw new PipelineException(message, e);
        } catch(ClientHandlerException e) {
            String message = String.format("Failed to process HTTP Reqeust/Response - %s", e.getMessage());
            logger.fatal(message);
            throw new PipelineException(message, e);
        }

        return versions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Build> getBuilds(String projectName, String versionNumber, int limit) {

        List<Build> builds = new ArrayList<Build>();
        List<BuildResponse> buildResponses = new ArrayList<BuildResponse>();

        try {
            // TODO - enable when fixed in pipeline service
            //buildResponses = buildClient.getBuilds(projectName, versionNumber, limit).getBuilds();
            buildResponses = buildClient.getBuilds(projectName, versionNumber).getBuilds();
        } catch (UniformInterfaceException e) {
            String message = String.format("Unexpected response from pipeline service - %s", e.getMessage());
            logger.fatal(message);
            throw new PipelineException(message, e);
        } catch(ClientHandlerException e) {
            String message = String.format("Failed to process HTTP Reqeust/Response - %s", e.getMessage());
            logger.fatal(message);
            throw new PipelineException(message, e);
        }

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
        List<PhaseResponse> phases = new ArrayList<PhaseResponse>();

        try {
            phases = phaseClient.getPhases(projectName, versionNumber).getPhases();
        } catch (UniformInterfaceException e) {
            String message = String.format("Unexpected response from pipeline service - %s", e.getMessage());
            logger.fatal(message);
            throw new PipelineException(message, e);
        } catch(ClientHandlerException e) {
            String message = String.format("Failed to process HTTP Reqeust/Response - %s", e.getMessage());
            logger.fatal(message);
            throw new PipelineException(message, e);
        }

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
