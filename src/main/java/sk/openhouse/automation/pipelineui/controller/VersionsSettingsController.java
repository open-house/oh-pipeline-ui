package sk.openhouse.automation.pipelineui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sk.openhouse.automation.pipelinedomain.domain.response.ProjectResponse;
import sk.openhouse.automation.pipelineui.form.Version;
import sk.openhouse.automation.pipelineui.service.PipelineService;

import java.util.List;

/**
 * @author pete
 */
@Controller
@RequestMapping("/settings/versions")
public class VersionsSettingsController {

    private final PipelineService pipelineService;

    @ModelAttribute("version")
    public Version getVersion() {
        return new Version();
    }

    @Autowired
    public VersionsSettingsController(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView versionsHandler() {

        /* no project selected - redirect to projects page */
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/settings/projects");
        return mav;
    }

    @RequestMapping(value = "{project}", method = RequestMethod.GET)
    public ModelAndView getHandler(@PathVariable("project") String projectName) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("settings/versions");

        // TODO - check if project name exists
        mav.addObject("projectName", projectName);
        mav.addObject("versions", pipelineService.getVersionResponses(projectName));
        return mav;
    }

    @RequestMapping(value = "{project}", method = RequestMethod.POST)
    public ModelAndView postHandler(@PathVariable("project") String projectName,
                                    @ModelAttribute("version") Version version,
                                    BindingResult result) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("projectName", projectName);
        mav.setViewName("settings/versions");

        // TODO - validate (add hibernate validator)
        if (result.hasErrors()) {
            return mav;
        }

        /* success */
//        if (pipelineService.addVersion(projectName, version.getNumber())) {
//            mav.setViewName("redirect:/settings/versions/" + projectName);
//            return mav;
//        }

        // TODO - display error on the page
        mav.addObject("error", String.format("Version %s has not been added.", version.getNumber()));
        return mav;
    }

    @RequestMapping(value = "{project}/{version}", method = RequestMethod.GET)
    public ModelAndView getVersionHandler(@PathVariable("project") String projectName,
                                          @PathVariable("version") String versionNumber) {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("settings/version");

        // TODO - check if project name and version number exists
        mav.addObject("projectName", projectName);
        mav.addObject("versionNumber", versionNumber);
        return mav;
    }
}
