package sk.openhouse.automation.pipelineui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sk.openhouse.automation.pipelinedomain.domain.response.ProjectResponse;
import sk.openhouse.automation.pipelineui.service.PipelineService;

import java.util.List;

/**
 * @author pete
 */
@Controller
@RequestMapping("/settings/versions")
public class VersionsSettingsController {

    private final PipelineService pipelineService;

    @Autowired
    public VersionsSettingsController(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getHandler() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("settings/versions");

        List<ProjectResponse> projects = pipelineService.getProjectResponses();
        mav.addObject("projects", projects);
        mav.addObject("versions", pipelineService.getVersionResponses(projects.get(0).getName()));
        return mav;
    }
}
