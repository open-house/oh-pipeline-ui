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
     */
    List<String> getProjectNames();

    /**
     * @param projectName
     * @return all versions of the specified project
     */
    List<String> getVersionNumbers(String projectName);

    /**
     * @param projectName
     * @param versionNumber
     * @return all builds of the specified project and version
     */
    List<Build> getBuilds(String projectName, String versionNumber);

    /**
     * @param projectName
     * @param versionNumber
     * @return phase names of the specified project and version
     */
    List<String> getPhaseNames(String projectName, String versionNumber);
}
