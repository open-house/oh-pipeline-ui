package sk.openhouse.automation.pipelineui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sk.openhouse.automation.pipelineui.service.PipelineService;

/**
 * @author pete
 */
@Controller
@RequestMapping("/settings/projects")
public class ProjectsSettingsController {

    private final PipelineService pipelineService;

    @Autowired
    public ProjectsSettingsController(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getHandler() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("settings/projects");

        mav.addObject("projects", pipelineService.getProjects());
        return mav;
    }
}
