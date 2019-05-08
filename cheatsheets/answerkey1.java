Feature: Create folder to report library

  Scenario: user creates a folder in their report library
    Given user TH8 logged in to orc
    And the user has permission to create folders
    When the user requests to add a new folder to their report library
    Then a folder is created and the folder's id is returned

  Scenario: user lacks permission to create folder
    Given user TH8 logged in to orc
    And the user does not have permission to create folders
    When the user requests to add a new folder to their report library
    Then the folder is not created and an error is returned
	
	
    package com.ntrs.orc.server.cucumber.steps;

    import com.ntrs.orc.server.entity.OrcUser;
    import com.ntrs.orc.server.exception.OrcRuntimeException;
    import cucumber.api.java.en.Given;
    import cucumber.api.java.en.Then;
    import cucumber.api.java.en.When;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.test.web.servlet.MvcResult;
    import org.springframework.web.util.NestedServletException;
    
    import java.io.UnsupportedEncodingException;
    
    import static com.ntrs.orc.server.cucumber.steps.CucumberStepDefSetUp.*;
    import static org.assertj.core.api.Java6Assertions.assertThat;
    import static org.mockito.ArgumentMatchers.any;
    import static org.mockito.Mockito.when;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
    
    public class UserLibraryStepDev {
        @Given("the user has permission to create folders")
        public void the_user_has_permission_to_create_folders() {
            when(mockCreateFolderRepository.createFolder(any(), any(), any(), any())).thenReturn(10031);
        }
    
        @Given("the user does not have permission to create folders")
        public void the_user_does_not_have_permission_to_create_folders() {
            when(mockCreateFolderRepository.createFolder(any(), any(), any(), any())).thenThrow(OrcRuntimeException.class);
        }
    
        @When("the user requests to add a new folder to their report library")
        public void the_user_requests_to_add_a_new_folder_to_their_report_library() throws Exception {
            OrcUser orcUser = new OrcUser();
            orcUser.setUserId(loggedInUserId);
    
            when(mockOrcUserRepository.getByUserId(loggedInUserId)).thenReturn(orcUser);
            try {
                response = mockMvc.perform(post("/v1/reportLibrary/createFolder")
                        .content("{\n" +
                                "  \"destinationId\": 10,\n" +
                                "  \"folderName\": \"New Folder Name\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .principal(mockOauth));
            } catch (NestedServletException e) {
                thrown = e.getCause();
            }
        }
    
        @Then("the folder is not created and an error is returned")
        public void the_folder_is_not_created_and_an_error_is_returned() {
            assertThat(thrown).isInstanceOf(OrcRuntimeException.class);
        }
    
        @Then("a folder is created and the folder's id is returned")
        public void theNewFolder_isCreated_AndNodeSk_isReturned() throws UnsupportedEncodingException {
            MvcResult mvcResult = response.andReturn();
            assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(mvcResult.getResponse().getContentAsString()).contains("10031");
        }
    }
    