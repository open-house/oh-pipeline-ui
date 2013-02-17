package sk.openhouse.automation.pipelineui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sk.openhouse.automation.pipelineui.service.PipelineService;

/**
 * @author pete
 */
@Controller
@RequestMapping("/settings/projects/")
public class ProjectSettingsController {

    private final PipelineService pipelineService;

    @Autowired
    public ProjectSettingsController(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    @RequestMapping("{project}")
    public ModelAndView getHandler(@PathVariable("project") String projectName) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("settings/project");

        // TODO - check/validate if the project exists
        System.out.println(String.format("%s project name ...", projectName));
        return mav;
    }

    // TODO post for updating existing project
}
