package cloud.controller;

import cloud.service.ProjectKeyService;
import com.atlassian.connect.spring.AtlassianHostRestClients;
import com.atlassian.connect.spring.AtlassianHostUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

@RestController
public class SubTaskController {
    private final String uri = "/rest/api/2/issue/";
    private ProjectKeyService projectKeyService;
    private AtlassianHostRestClients restClients;
    @Autowired
    private RestTemplate restTemplate;

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
//        headers.set("Authorization", "Basic YWxleGFuZHJhLnRvbGtpbmFAZ21haWwuY29tOmZnSGpLbDE1Ng==");
//
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("Add", "Add");
//        map.add("addSelectValue", "true");
//        map.add("addValue", "123456789");
//        map.add("atl_token", "00c532d0-0dfd-4bf9-9fb5-ccdc561c868e|32586e0b48c960edbf3fa632de4069428a7c5758|lin");
//        map.add("fieldConfigId", "1032");
//        map.add("selectedParentOptionId", "");
//        HttpEntity<MultiValueMap<String, String>> requestMap = new HttpEntity<>(map, headers);
//
        String request = "Add=Add&addSelectValue=true&addValue=123456789&atl_token=00c532d0-0dfd-4bf9-9fb5-ccdc561c868e|32586e0b48c960edbf3fa632de4069428a7c5758|lin&fieldConfigId=1032&selectedParentOptionId=";
//
//        String s1 = restTemplate.postForObject("/secure/admin/EditCustomFieldOptions!add.jspa", requestMap, String.class);
        String s = restClients.authenticatedAs(host)
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
//    private String request(String targetURL, String body) {
//
//        HttpURLConnection connection = null;
//
//        try {
//
//            URL url = new URL(targetURL);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Content-Type",
//                    "application/json; charset=utf-8");
//
//            connection.setRequestProperty("Authorization",
//                    changeConfigService.get().getSapUrl());
//
//            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
//            writer.write(body);
//            writer.close();
//
//
//            if ((connection.getResponseCode() >= 200) && (connection.getResponseCode() <= 299)) {
//
//                InputStream is = connection.getInputStream();
//                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//                String response;
//                response = rd.lines().map(line -> line + '\r').collect(Collectors.joining());
//                rd.close();
//                return response;
//
//
//            } else {
//
//                return null;
//
//            }
//
//
//        } catch (Exception e) {
//
//            log.error("Connection to URL " + targetURL + " failed.");
//            return null;
//
//        } finally {
//
//            if (connection != null) {
//
//                connection.disconnect();
//
//            }
//
//        }
//
//    }
}
