package sk.openhouse.automation.pipelineui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import sk.openhouse.automation.pipelinedomain.domain.PhaseState;
import sk.openhouse.automation.pipelinedomain.domain.response.ProjectResponse;
import sk.openhouse.automation.pipelinedomain.domain.response.VersionResponse;
import sk.openhouse.automation.pipelineui.controller.IndexController;
import sk.openhouse.automation.pipelineui.form.ProjectVersion;
import sk.openhouse.automation.pipelineui.model.Build;
import sk.openhouse.automation.pipelineui.service.PipelineException;
import sk.openhouse.automation.pipelineui.service.PipelineService;

public class IndexControllerTest {

    @Mock
    private PipelineService pipelineService;

    private final List<ProjectResponse> projects = new ArrayList<ProjectResponse>();
    private final List<VersionResponse> versions = new ArrayList<VersionResponse>();
    private final List<String> phases = new ArrayList<String>();

    private final Map<String, PhaseState> states = new HashMap<String, PhaseState>();
    private final List<Build> builds = new ArrayList<Build>();

    private IndexController indexController;

    @BeforeMethod
    public void beforeMethod() {

        MockitoAnnotations.initMocks(this);
        Mockito.when(pipelineService.getProjectResponses()).thenReturn(projects);
        Mockito.when(pipelineService.getVersionResponses(Mockito.eq("test"))).thenReturn(versions);
        Mockito.when(pipelineService.getPhaseNames(Mockito.eq("test"), Mockito.eq("0.3")))
                .thenReturn(phases);
        Mockito.when(pipelineService.getBuilds(Mockito.eq("test"), Mockito.eq("0.3"), Mockito.eq(10)))
                .thenReturn(builds);

        indexController = new IndexController(pipelineService);

        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setName("test");
        projects.add(projectResponse);

        VersionResponse versionResponse = new VersionResponse();
        versionResponse.setVersionNumber("0.3");
        versions.add(versionResponse);
        phases.add("QA");

        states.put("QA", PhaseState.FAIL);
        builds.add(new Build(7, states));
    }

    @Test
    public void testGetProjectVersion() {
        Assert.assertNotNull(indexController.getProjectVersion());
    }

    /**
     * Test for loading page the first time (no query params). Page should
     * load with default (first product) and version
     */
    @Test
    public void testGetHandlerEmptyForm() {

        ProjectVersion projectVersion = new ProjectVersion();
        ModelAndView mav = indexController.getHandler(projectVersion);

        Assert.assertEquals(mav.getModelMap().get("projects"), projects);
        Assert.assertEquals(mav.getModelMap().get("versions"), versions);
        Assert.assertEquals(mav.getModelMap().get("phases"), phases);

        @SuppressWarnings("unchecked")
        List<Build> builds = (List<Build>) mav.getModelMap().get("builds");
        Assert.assertEquals(builds, builds);
        Assert.assertEquals(builds.get(0).getNumber(), 7);
        Assert.assertEquals(builds.get(0).getStates(), states);

        Assert.assertEquals(projectVersion.getProjectName(), "test");
        Assert.assertEquals(projectVersion.getVersionNumber(), "0.3");
    }

    /**
     * Test when values are submitted
     */
    @Test
    public void testGetHandlerPopulatedForm() {

        ProjectVersion projectVersion = new ProjectVersion();
        projectVersion.setProjectName("test");
        projectVersion.setVersionNumber("0.3");
        ModelAndView mav = indexController.getHandler(projectVersion);

        Assert.assertEquals(mav.getModelMap().get("projects"), projects);
        Assert.assertEquals(mav.getModelMap().get("versions"), versions);
        Assert.assertEquals(mav.getModelMap().get("phases"), phases);
        Assert.assertEquals(mav.getModelMap().get("builds"), builds);

        Assert.assertEquals(projectVersion.getProjectName(), "test");
        Assert.assertEquals(projectVersion.getVersionNumber(), "0.3");
    }

    /**
     * Test incorrect values submitted (defaults to first project and version)
     */
    @Test
    public void testGetHandlerIncorrectValuesForm() {

        ProjectVersion projectVersion = new ProjectVersion();
        projectVersion.setProjectName("non existing project");
        projectVersion.setVersionNumber("non existing version");
        ModelAndView mav = indexController.getHandler(projectVersion);

        Assert.assertEquals(mav.getModelMap().get("projects"), projects);
        Assert.assertEquals(mav.getModelMap().get("versions"), versions);
        Assert.assertEquals(mav.getModelMap().get("phases"), phases);
        Assert.assertEquals(mav.getModelMap().get("builds"), builds);

        Assert.assertEquals(projectVersion.getProjectName(), "test");
        Assert.assertEquals(projectVersion.getVersionNumber(), "0.3");
    }

    /**
     * Test if getProjectNames throws pipeline exception
     * @throws PipelineException
     */
    @Test
    public void testGetHandlerGetProjectsException() {

        Mockito.when(pipelineService.getProjectResponses()).thenThrow(new PipelineException("test", new Exception()));

        ProjectVersion projectVersion = new ProjectVersion();
        projectVersion.setProjectName("test");
        projectVersion.setVersionNumber("0.3");
        ModelAndView mav = indexController.getHandler(projectVersion);

        Assert.assertEquals(mav.getModelMap().get("projects"), new ArrayList<String>());
        Assert.assertEquals(mav.getModelMap().get("versions"), new ArrayList<String>());
        Assert.assertEquals(mav.getModelMap().get("phases"), new ArrayList<String>());
        Assert.assertEquals(mav.getModelMap().get("builds"), new ArrayList<String>());

        Assert.assertNull(projectVersion.getProjectName());
        Assert.assertNull(projectVersion.getVersionNumber());
    }

    /**
     * Test if no project are returned from pipeline service
     */
    @Test
    public void testGetHandlerNoProjects() {

        Mockito.when(pipelineService.getProjectResponses()).thenReturn(new ArrayList<ProjectResponse>());

        ProjectVersion projectVersion = new ProjectVersion();
        projectVersion.setProjectName("test");
        projectVersion.setVersionNumber("0.3");
        ModelAndView mav = indexController.getHandler(projectVersion);

        Assert.assertEquals(mav.getModelMap().get("projects"), new ArrayList<String>());
        Assert.assertEquals(mav.getModelMap().get("versions"), new ArrayList<String>());
        Assert.assertEquals(mav.getModelMap().get("phases"), new ArrayList<String>());
        Assert.assertEquals(mav.getModelMap().get("builds"), new ArrayList<String>());

        Assert.assertNull(projectVersion.getProjectName());
        Assert.assertNull(projectVersion.getVersionNumber());
    }

    /**
     * Test if no versions are returned from pipeline service
     */
    @Test
    public void testGetHandlerNoVersions() {

        Mockito.when(pipelineService.getVersionResponses(Mockito.anyString())).thenReturn(new ArrayList<VersionResponse>());

        ProjectVersion projectVersion = new ProjectVersion();
        projectVersion.setProjectName("test");
        projectVersion.setVersionNumber("0.3");
        ModelAndView mav = indexController.getHandler(projectVersion);

        Assert.assertEquals(mav.getModelMap().get("projects"), projects);
        Assert.assertEquals(mav.getModelMap().get("versions"), new ArrayList<String>());
        Assert.assertEquals(mav.getModelMap().get("phases"), new ArrayList<String>());
        Assert.assertEquals(mav.getModelMap().get("builds"), new ArrayList<String>());

        Assert.assertEquals(projectVersion.getProjectName(), "test");
        Assert.assertNull(projectVersion.getVersionNumber());
    }
}
