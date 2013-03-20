package sk.openhouse.automation.pipelineui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author pete
 */
@Controller
@RequestMapping("/settings")
public class SettingsController {


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getHandler() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("settings");

        return mav;
    }
}
