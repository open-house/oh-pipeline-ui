package sk.openhouse.automation.pipelineui.controller;

import junit.framework.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.openhouse.automation.pipelineui.form.Project;
import sk.openhouse.automation.pipelineui.service.PipelineService;

public class ProjectsSettingsControllerTest {

    @Mock
    private PipelineService pipelineService;

    @Mock
    private BindingResult bindingResult;

    private ProjectsSettingsController controller;

    @BeforeMethod
    public void beforeMethod() {

        MockitoAnnotations.initMocks(this);
        this.controller = new ProjectsSettingsController(pipelineService);
    }

    @Test
    public void testGetHandler() {

        controller.getHandler();
        Mockito.verify(pipelineService, Mockito.times(1)).getProjectResponses();
    }

    @Test
    public void testGetProject() {
        Assert.assertNotNull(controller.getProject());
    }

    @Test
    public void testPostHandlerErrors() {

        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        ModelAndView mav = controller.postHandler(new Project(), bindingResult);

        Assert.assertEquals("settings/projects", mav.getViewName());
    }

    @Test
    public void testPostHandler() {

        String projectName = "test_project";
        Project project = new Project();
        project.setName(projectName);

        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        Mockito.when(pipelineService.addProject(project.getName())).thenReturn(true);
        ModelAndView mav = controller.postHandler(project, bindingResult);

        Assert.assertEquals("redirect:/settings/projects", mav.getViewName());
    }

    @Test
    public void testPostHandlerFail() {

        String projectName = "test_project";
        Project project = new Project();
        project.setName(projectName);

        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        Mockito.when(pipelineService.addProject(project.getName())).thenReturn(false);
        ModelAndView mav = controller.postHandler(project, bindingResult);

        Assert.assertEquals("settings/projects", mav.getViewName());
        Assert.assertNotNull(mav.getModel().get("error"));
    }
}
