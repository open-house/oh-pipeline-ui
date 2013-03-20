package sk.openhouse.automation.pipelineui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sk.openhouse.automation.pipelineui.form.Project;
import sk.openhouse.automation.pipelineui.service.PipelineService;

/**
 * @author pete
 */
@Controller
@RequestMapping("/settings/projects/")
public class ProjectSettingsController {

    private final PipelineService pipelineService;

    @ModelAttribute("project")
    public Project getProject() {
        return new Project();
    }

    @Autowired
    public ProjectSettingsController(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    @RequestMapping("{project}")
    public ModelAndView getHandler(@PathVariable("project") String projectName) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("settings/project");

        // TODO - check/validate if the project exists
        mav.addObject("projectName", projectName);
        return mav;
    }

    @RequestMapping(value = "{project}", method = RequestMethod.POST)
    public ModelAndView postHandler(@PathVariable("project") String projectName) {

        ModelAndView mav = new ModelAndView();
        // TODO update project

        // if success:
        mav.setViewName("redirect:/settings/projects");
        return mav;
    }
}
