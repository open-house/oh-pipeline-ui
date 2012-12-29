
package sk.openhouse.automation.pipelineui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sk.openhouse.automation.pipelineui.form.ProjectVersion;
import sk.openhouse.automation.pipelineui.model.Build;
import sk.openhouse.automation.pipelineui.service.PipelineService;

@Controller
@RequestMapping("/")
public class IndexController {

    private static final float FIRST_COL_WIDTH_RATIO = 1.5f;

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
        mav.addObject("versions", new ArrayList<String>());
        mav.addObject("builds", new ArrayList<Build>());
        mav.addObject("phases", new ArrayList<String>());
        mav.addObject("columnWidth", getColumnWidth(0));

        /* no projects found */
        List<String> projects = pipelineService.getProjectNames();
        if (projects.isEmpty()) {
            mav.addObject("projects", projects);
            projectVersion.setProjectName(null);
            projectVersion.setVersionNumber(null);
            return mav;
        }
        mav.addObject("projects", projects);

        /* project name has not been set yet, or does not exist */
        String selectedProjectName = projectVersion.getProjectName();
        if (null == selectedProjectName || !projects.contains(selectedProjectName)) {
            projectVersion.setProjectName(projects.get(0));
            selectedProjectName = projectVersion.getProjectName();
        }

        /* no project versions found */
        List<String> versions = pipelineService.getVersionNumbers(selectedProjectName);
        if (versions.isEmpty()) {
            projectVersion.setVersionNumber(null);
            return mav;
        }
        mav.addObject("versions", versions);

        /* version number has not been set yet or has been set to incorrect version */
        String selectedVersionNumber = projectVersion.getVersionNumber();
        if (null == selectedVersionNumber || !versions.contains(selectedVersionNumber)) {
            projectVersion.setVersionNumber(versions.get(0));
            selectedVersionNumber = projectVersion.getVersionNumber();
        }

        List<String> phaseNames = pipelineService.getPhaseNames(selectedProjectName, selectedVersionNumber);
        mav.addObject("builds", pipelineService.getBuilds(selectedProjectName, selectedVersionNumber));
        mav.addObject("phases", phaseNames);

        mav.addObject("columnWidth", getColumnWidth(phaseNames.size()));
        return mav;
    }

    /**
     * returns width of the phase column (except first 'build' column)
     */
    private int getColumnWidth(int phasesSize) {
        return (int) Math.floor(100 / (phasesSize + FIRST_COL_WIDTH_RATIO));
    }
}
