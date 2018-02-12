package cloud.controller;

import cloud.service.ProjectKeyService;
import com.atlassian.connect.spring.AtlassianHostRestClients;
import com.atlassian.connect.spring.AtlassianHostUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@RestController
public class SubTaskController {
    private ProjectKeyService projectKeyService;
    private AtlassianHostRestClients restClients;

    public SubTaskController(ProjectKeyService projectKeyService, AtlassianHostRestClients restClients) {
        this.projectKeyService = projectKeyService;
        this.restClients = restClients;
    }

    @GetMapping(value = "/change-summary")
    public ModelAndView changeSummary(@RequestParam String issueKey) {
        ModelAndView model = new ModelAndView();
        Object issue = getIssue(issueKey);
        model.setViewName("summary");
        model.addObject("issueKey", issueKey);
        model.addObject("issue", issue);
        return model;
    }

    @GetMapping(value = "/change-status")
    public ModelAndView changeStatus(@RequestParam String issueKey) {
        ModelAndView model = new ModelAndView();
        model.setViewName("status");
        model.addObject("issueKey", issueKey);
        Object issue = getIssue(issueKey);
        model.addObject("issue", issue);
        return model;
    }

    @GetMapping(value = "/config")
    public ModelAndView config(@AuthenticationPrincipal AtlassianHostUser host) throws IOException {
        ModelAndView model = new ModelAndView();
        model.setViewName("configure");
        model.addObject("projectKeys", projectKeyService.findSubTaskPluginEntity());
        return model;
    }

    @PostMapping(value = "/save-keys")
    public ResponseEntity createSubTaskPluginEntity(@RequestParam String[] projectKeys) {
        projectKeyService.createSubTaskPluginEntity(projectKeys);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/error")
    public ModelAndView error() {
        ModelAndView model = new ModelAndView();
        model.setViewName("error");
        return model;
    }

    @GetMapping(value = "/condition")
    public ResponseEntity<Map<String, Boolean>> condition(@RequestParam String issueKey) {
        Map<String, Boolean> shouldDisplay = new HashMap<>();
        shouldDisplay.put("shouldDisplay", isAllowed(issueKey));
        return new ResponseEntity<>(shouldDisplay, HttpStatus.OK);
    }

    private boolean isAllowed(String issueKey) {
        return Stream.of(projectKeyService.findSubTaskPluginEntity()).anyMatch(key -> key.equals(issueKey));
    }

    private Object getIssue(String issueKey) {
        return restClients.authenticatedAsAddon().getForObject("/rest/api/2/issue/" + issueKey, Object.class);
    }

}
