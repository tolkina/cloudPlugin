package cloud.controller;

import cloud.service.ProjectKeyService;
import com.atlassian.connect.spring.AtlassianHostRestClients;
import com.atlassian.connect.spring.AtlassianHostUser;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    //    @GetMapping(value = "/config")
//    public ModelAndView configure() {
//        ModelAndView model = new ModelAndView();
//        model.setViewName("configure");
//        return model;
//    }


    @GetMapping(value = "/config")
    public ModelAndView config(@AuthenticationPrincipal AtlassianHostUser host) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("Add", "Add");
//        map.add("addSelectValue", "true");
//        map.add("addValue", "123456789");
//        map.add("atl_token", "00c532d0-0dfd-4bf9-9fb5-ccdc561c868e|5c43a29060a891e312adea0e5c4a393f8339a8a1|lin");
//        map.add("fieldConfigId", "1032");
//        map.add("selectedParentOptionId", "");
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        String request = "Add=Add&addSelectValue=true&addValue=123456789&atl_token=00c532d0-0dfd-4bf9-9fb5-ccdc561c868e|5c43a29060a891e312adea0e5c4a393f8339a8a1|lin&fieldConfigId=1032&selectedParentOptionId=";
        String s = restClients.authenticatedAsAddon()
                .postForObject("/secure/admin/EditCustomFieldOptions!add.jspa", request, String.class);

//        OkHttpClient client = new OkHttpClient();
//
//  MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//        RequestBody body = RequestBody.create(mediaType, "atl_token=");
//        Request request = new Request.Builder()
//                .url("https://tolkina.atlassian.net/secure/admin/EditCustomFieldOptions%21add.jspa")
//                .post(body)
//                .addHeader("content-type", "application/x-www-form-urlencoded")
//                .addHeader("cache-control", "no-cache")
//                .addHeader("postman-token", "c7699c26-b3bc-c539-5897-fa696c4d5abe")
//                .build();
//
//        Response response = client.newCall(request).execute();

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
