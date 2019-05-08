package com.jeb.bdd.cucumber.steps;

import com.jayway.jsonpath.spi.cache.LRUCache;
import com.jeb.bdd.BddApplication;
import com.jeb.bdd.controllers.MasterLibraryController;
import com.jeb.bdd.jpa.LibraryRepository;
import com.jeb.bdd.testhelpers.TestMothers;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {BddApplication.class})
public class MasterLibrarySteps {

    private MockMvc mockMvc;

    @MockBean
    private LibraryRepository libraryRepository = mock(LibraryRepository.class);

    @Autowired
    @InjectMocks
    private MasterLibraryController masterLibraryController = new MasterLibraryController(libraryRepository);

    private String user;
    private ResultActions resultActions;
    private List<String> expectedTemplateNames;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(masterLibraryController).build();
        user = "super-user";
    }

    @Given("a user with super-user privileges")
    public void a_user_with_super_user_privileges() {
        // maybe mock out oAuth, etc.
    }

    @Given("a user with access to templates (.*)")
    public void a_user_with_access_to_templates(String accessibleTemplates) {
        expectedTemplateNames = Arrays.asList(accessibleTemplates.split(","));
    }

    @Given("our example master library")
    public void our_example_master_library() {
        var masterLibrary = TestMothers.masterLibrary();
        when(libraryRepository.read(user)).thenReturn(masterLibrary);
    }

    @When("the user views the master library")
    public void the_user_views_the_master_library() throws Exception {
        resultActions = mockMvc.perform(get("/master-library/" + user));
    }

    @Then("The user sees {int} folders and {int} templates")
    public void the_user_sees_folders_and_templates(Integer folderCount, Integer templateCount) throws Exception {
        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[?(@.nodeType=='F')]").value(hasSize(folderCount)))
                .andExpect(jsonPath("$[?(@.nodeType=='T')]").value(hasSize(templateCount)));
    }
}
