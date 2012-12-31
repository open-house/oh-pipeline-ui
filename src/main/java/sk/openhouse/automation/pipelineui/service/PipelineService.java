package sk.openhouse.automation.pipelineui.service;

import java.util.List;

import sk.openhouse.automation.pipelineui.model.Build;

/**
 * 
 * @author pete
 */
public interface PipelineService {

    /**
     * @return all project names
     * @throws PipelineException if the response cannot be retrieved from pipeline service
     */
    List<String> getProjectNames() throws PipelineException;

    /**
     * @param projectName
     * @return all versions of the specified project
     * @throws PipelineException if the response cannot be retrieved from pipeline service
     */
    List<String> getVersionNumbers(String projectName) throws PipelineException;

    /**
     * @param projectName
     * @param versionNumber
     * @return all builds of the specified project and version
     * @throws PipelineException if the response cannot be retrieved from pipeline service
     */
    List<Build> getBuilds(String projectName, String versionNumber) throws PipelineException;

    /**
     * @param projectName
     * @param versionNumber
     * @return phase names of the specified project and version
     * @throws PipelineException if the response cannot be retrieved from pipeline service
     */
    List<String> getPhaseNames(String projectName, String versionNumber) throws PipelineException;
}
