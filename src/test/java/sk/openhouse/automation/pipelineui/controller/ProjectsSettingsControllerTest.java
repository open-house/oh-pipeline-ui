package sk.openhouse.automation.pipelineui.controller;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.openhouse.automation.pipelineui.service.PipelineService;

public class ProjectsSettingsControllerTest {

    @Mock
    private PipelineService pipelineService;

    private ProjectsSettingsController controller;

    @BeforeMethod
    public void beforeMethod() {

        MockitoAnnotations.initMocks(this);
        this.controller = new ProjectsSettingsController(pipelineService);
    }

    @Test
    public void getHandler() {

        controller.getHandler();
        Mockito.verify(pipelineService, Mockito.times(1)).getProjects();
    }

}
