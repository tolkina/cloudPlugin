package cloud.controller;

import cloud.service.ProjectKeyService;
import com.atlassian.connect.spring.AtlassianHostRestClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SubTaskController {
    private final String uri = "/rest/api/2/issue/";
    private ProjectKeyService projectKeyService;
    private AtlassianHostRestClients restClients;

    public SubTaskController(ProjectKeyService projectKeyService, AtlassianHostRestClients restClients) {
        this.projectKeyService = projectKeyService;
        this.restClients = restClients;
    }

    @GetMapping(value = "/change-summary")
    public ModelAndView changeSummary(@RequestParam String issueKey) {
        ModelAndView model = new ModelAndView();
        model.setViewName("summary");
        model.addObject("issueKey", issueKey);
        Object issue = restClients.authenticatedAsAddon().getForObject(uri + issueKey, Object.class);
        model.addObject("issue", issue);
        return model;
    }

    @GetMapping(value = "/change-status")
    public ModelAndView changeStatus(@RequestParam String issueKey) {
        ModelAndView model = new ModelAndView();
        model.setViewName("status");
        model.addObject("issueKey", issueKey);
        return model;
    }

    @GetMapping(value = "/config")
    public ModelAndView configure() {
        ModelAndView model = new ModelAndView();
        model.setViewName("configure");
        return model;
    }

    @GetMapping(value = "/config/get")
    public ResponseEntity<String[]> getSubTaskPluginEntity() {
        String[] projectKeys = projectKeyService.findSubTaskPluginEntity();
        return new ResponseEntity<>(projectKeys, HttpStatus.OK);
    }

    @PostMapping(value = "/config/create")
    public ResponseEntity createSubTaskPluginEntity(@RequestParam String[] projectKeys) {
        projectKeyService.createSubTaskPluginEntity(projectKeys);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping(value = "/config/delete")
    public ResponseEntity deleteSubTaskPluginEntity() {
        projectKeyService.deleteSubTaskPluginEntity();
        return new ResponseEntity(HttpStatus.OK);
    }
}
