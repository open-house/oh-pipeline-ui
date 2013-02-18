package sk.openhouse.automation.pipelineui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sk.openhouse.automation.pipelineui.form.Project;
import sk.openhouse.automation.pipelineui.service.PipelineService;

/**
 * @author pete
 */
@Controller
@RequestMapping("/settings/projects")
public class ProjectsSettingsController {

    private final PipelineService pipelineService;

    @ModelAttribute("project")
    public Project getProject() {
        return new Project();
    }

    @Autowired
    public ProjectsSettingsController(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getHandler() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("settings/projects");

        mav.addObject("projects", pipelineService.getProjectResponses());
        return mav;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView postHandler(@ModelAttribute("project") Project project, BindingResult result) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("settings/projects");

        // TODO - validate (add hibernate validator)
        if (result.hasErrors()) {
            return mav;
        }

        /* success */
        if (pipelineService.addProject(project.getName())) {
            mav.setViewName("redirect:/settings/projects");
            return mav;
        }

        // TODO - display error on the page
        mav.addObject("error", String.format("Project %s has not been added.", project.getName()));
        return mav;
    }
}
