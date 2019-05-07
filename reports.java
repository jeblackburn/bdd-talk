package com.ntrs.orc.cucumber.steps;

import com.ntrs.orc.entity.OrcUserEntity;
import com.ntrs.orc.entity.OrcUserLibry;
import com.ntrs.orc.model.ReportRequest;
import com.ntrs.orc.msg.handler.NTRSMessageHandler;
import com.ntrs.orc.repository.ModePreferenceRepository;
import com.ntrs.orc.repository.OrcUserRepository;
import com.ntrs.orc.repository.OrcUsrLibraryRepository;
import com.ntrs.orc.server.dao.UtilityDao;
import com.ntrs.orc.server.manager.QueryManager;
import com.ntrs.orc.server.manager.ReportManager;
import com.ntrs.orc.server.manager.RequestManager;
import com.ntrs.orc.server.manager.impl.ReportManagerImpl;
import com.ntrs.orc.server.model.*;
import com.ntrs.orc.server.service.impl.ReportServiceImpl;
import com.ntrs.wmh.ntappsec.authorization.User;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import static com.ntrs.orc.server.model.Node.MY_REPORT_LIBRARY_NAME;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ReportManagerSteps {

    private ReportRequest reportRequest;
    private OrcUserRepository userRepo = mock(OrcUserRepository.class);
    private ModePreferenceRepository modePreferenceRepository = mock(ModePreferenceRepository.class);
    private OrcUsrLibraryRepository userLibraryRepo = mock(OrcUsrLibraryRepository.class);
    private UtilityDao utilityDao = mock(UtilityDao.class);
    private QueryManager queryManager = mock(QueryManager.class);
    private RequestManager requestManager = mock(RequestManager.class);
    private NTRSMessageHandler messageHandler = mock(NTRSMessageHandler.class);
    private UserLibraryManager userLibraryManager = mock(UserLibraryManager.class);
    
    private ReportManager manager = new ReportManagerImpl(null, null, null, null, null, null, queryManager, requestManager, userLibraryManager, null, utilityDao, null, null, messageHandler);

    @Given("a report request specifying a template")
    public void a_report_request_specifying_a_report_template() {
        reportRequest = new ReportRequest();
        reportRequest.setNodeType("T");
        reportRequest.setRunnable(true);
        reportRequest.setLibraryName(MY_REPORT_LIBRARY_NAME);
        reportRequest.setUniqueId(123);
    }

    @When("the report is submitted")
    public void the_report_is_submitted() {
        OrcUserLibry orcUserLibry = new OrcUserLibry();
        orcUserLibry.setMstrLibryNodeSk(100);
        orcUserLibry.setTemplSk(999);

        when(userLibraryRepo.getOrcUserLibryByNodeSk(any())).thenReturn(orcUserLibry);
        when(userRepo.findByUserId(any())).thenReturn(new OrcUserEntity(200, "userId", "clientID", false));
        when(requestManager.getReportRequest(any())).thenReturn(new RequestTemplateNode());
        List<RequestRun> requests = singletonListWithMessage("Here's a run request message");
        when(requestManager.getRequestRunMessages(anyInt())).thenReturn(requests);
        when(queryManager.getQueryForNodes(any(), any(), eq(false), eq(false), any())).thenReturn(createQuery());

        var service = new ReportServiceImpl(manager, userRepo, modePreferenceRepository, userLibraryRepo);
        service.processReportRequestAndRun(reportRequest, new User());
    }

    @Then("^(.*) reports are sent to NMR")
    public void report_is_sent_to_NMR(Integer reportCount) {
        verify(messageHandler, times(reportCount)).sendMessage(any());
    }

    private Query createQuery() {
        Query query = new Query();
        QueryParams queryParams = new QueryParams();
        queryParams.setDestination(new Destination());
        query.setQueryParams(queryParams);
        return query;
    }

    private List<RequestRun> singletonListWithMessage(String runRequestMessage) {
        RequestRun requestRun = new RequestRun();
        requestRun.setRunRequestMessage(runRequestMessage);
        return List.of(requestRun);
    }

}
