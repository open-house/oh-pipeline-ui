package sk.openhouse.automation.pipelineui.service;

import java.util.List;

import sk.openhouse.automation.pipelinedomain.domain.response.ProjectResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.VersionResponse;
import sk.openhouse.automation.pipelineui.model.Build;

/**
 * 
 * @author pete
 */
public interface PipelineService {

    /**
     * @return all projects
     * @throws PipelineException if the response cannot be retrieved from pipeline service
     */
    List<ProjectResponse> getProjectResponses() throws PipelineException;

    /**
     * @param projectName name of the project to be added
     * @return true if the project has been added successfully
     * @throws PipelineException if there was a error while adding the project
     */
    boolean addProject(String projectName) throws PipelineException;

    /**
     * @param projectName
     * @return all versions of the specified project
     * @throws PipelineException if the response cannot be retrieved from pipeline service
     */
    List<VersionResponse> getVersionResponses(String projectName) throws PipelineException;

    /**
     * @param projectName
     * @param versionNumber
     * @param limit max number of returned builds
     * @return number of builds specified by limit for specific project version
     * @throws PipelineException if the response cannot be retrieved from pipeline service
     */
    List<Build> getBuilds(String projectName, String versionNumber, int limit) throws PipelineException;

    /**
     * @param projectName
     * @param versionNumber
     * @return phase names of the specified project and version
     * @throws PipelineException if the response cannot be retrieved from pipeline service
     */
    List<String> getPhaseNames(String projectName, String versionNumber) throws PipelineException;
}
