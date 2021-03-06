
package sk.openhouse.automation.pipelineui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sk.openhouse.automation.pipelinedomain.domain.response.ProjectResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.VersionResponse;
import sk.openhouse.automation.pipelineui.form.ProjectVersion;
import sk.openhouse.automation.pipelineui.model.Build;
import sk.openhouse.automation.pipelineui.service.PipelineException;
import sk.openhouse.automation.pipelineui.service.PipelineService;

@Controller
@RequestMapping("/")
public class IndexController {

    /** relative size (to the rest of the columns) of the first column in builds table */
    private static final float FIRST_COL_WIDTH_RATIO = 1.5f;
    /** maximum number of builds displayed on the page */
    private static final int BUILDS_LIMIT = 10;

    private final PipelineService pipelineService;

    @Autowired
    public IndexController(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    @ModelAttribute
    public ProjectVersion getProjectVersion() {
        return new ProjectVersion();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getHandler(ProjectVersion projectVersion) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");

        /* default values */
        setDefaultValues(mav);

        /* set projects */
        if (!setProjects(mav, projectVersion)) {
            return mav;
        }

        if (!setVersions(mav, projectVersion)) {
            return mav;
        }

        String selectedProjectName = projectVersion.getProjectName();
        String selectedVersionNumber = projectVersion.getVersionNumber();

        List<String> phaseNames = pipelineService.getPhaseNames(selectedProjectName, selectedVersionNumber);
        mav.addObject("builds", pipelineService.getBuilds(selectedProjectName, selectedVersionNumber, BUILDS_LIMIT));
        mav.addObject("phases", phaseNames);

        mav.addObject("columnWidth", getColumnWidth(phaseNames.size()));
        return mav;
    }

    /**
     * Sets default model values on supplied mode and view
     */
    private void setDefaultValues(ModelAndView mav) {

        mav.addObject("projects", new ArrayList<String>());
        mav.addObject("versions", new ArrayList<String>());
        mav.addObject("builds", new ArrayList<Build>());
        mav.addObject("phases", new ArrayList<String>());
        mav.addObject("columnWidth", getColumnWidth(0));
    }

    /**
     * Sets projects on supplied model and view, sets selected project on supplied project version
     * and returns true if successful, false otherwise
     * 
     * @param mav sets 'projects' on supplied model and view if true is returned
     * @param projectVersion from user request (form)
     * @return true if successful, false otherwise
     */
    private boolean setProjects(ModelAndView mav, ProjectVersion projectVersion) {

        List<ProjectResponse> projects = new ArrayList<ProjectResponse>();
        try {
            projects = pipelineService.getProjectResponses();
        } catch (PipelineException e) {
            mav.addObject("error", e.getMessage());
        }

        /* no projects found */
        if (projects.isEmpty()) {
            projectVersion.setProjectName(null);
            projectVersion.setVersionNumber(null);
            return false;
        }
        mav.addObject("projects", projects);

        /* project name has not been set yet, or does not exist */
        ProjectResponse selectedProject = new ProjectResponse();
        selectedProject.setName(projectVersion.getProjectName());
        if (!projects.contains(selectedProject)) {
            projectVersion.setProjectName(projects.get(0).getName());
        }
        return true;
    }

    /**
     * Sets versions on supplied model and view, sets selected version on supplied project version
     * and returns true if successful, false otherwise
     * 
     * @param mav sets 'versions' on supplied model and view if true is returned
     * @param projectVersion from user request (form)
     * @return true if successful, false otherwise
     */
    boolean setVersions(ModelAndView mav, ProjectVersion projectVersion) {

        /* no project versions found */
        String selectedProjectName = projectVersion.getProjectName();
        List<VersionResponse> versions = pipelineService.getVersionResponses(selectedProjectName);
        if (versions.isEmpty()) {
            projectVersion.setVersionNumber(null);
            return false;
        }
        mav.addObject("versions", versions);

        /* version number has not been set yet or has been set to incorrect version */
        VersionResponse selectedVersion = new VersionResponse();
        selectedVersion.setVersionNumber(projectVersion.getVersionNumber());
        if (!versions.contains(selectedVersion)) {
            projectVersion.setVersionNumber(versions.get(0).getVersionNumber());
        }
        return true;
    }

    /**
     * returns width of the phase column (except first 'build' column)
     */
    private int getColumnWidth(int phasesSize) {
        return (int) Math.floor(100 / (phasesSize + FIRST_COL_WIDTH_RATIO));
    }
}
