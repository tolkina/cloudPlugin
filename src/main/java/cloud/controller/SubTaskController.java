package cloud.controller;

import cloud.service.ProjectKeyService;
import com.atlassian.connect.spring.AtlassianHostRestClients;
import com.atlassian.connect.spring.AtlassianHostUser;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.*;
import com.squareup.okhttp.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.net.HttpURLConnection;
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

    //    @GetMapping(value = "/config")
//    public ModelAndView configure() {
//        ModelAndView model = new ModelAndView();
//        model.setViewName("configure");
//        return model;
//    }

    @GetMapping(value = "/change-status")
    public ModelAndView changeStatus(@RequestParam String issueKey) {
        ModelAndView model = new ModelAndView();
        model.setViewName("status");
        model.addObject("issueKey", issueKey);
        return model;
    }

    @GetMapping(value = "/config")
    public ModelAndView config(@AuthenticationPrincipal AtlassianHostUser host) throws IOException {
        String url = "https://tolkina.atlassian.net/secure/admin/EditCustomFieldOptions!add.jspa";
        String body = "Add=Add&addSelectValue=true&addValue=zxcv&fieldConfigId=10132&selectedParentOptionId=";
//        String response = request(url, body);
//        String response1 = okHttpRequest(url, body);
//        String response2 = atlassianRequest(url, body, host);
//        ResponseEntity<String> stringResponseEntity = atlassianRequest2(url);

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

    private String request(String targetURL, String body) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("X-Atlassian-Token", "no-check");
            connection.setDoOutput(true);
//            connection.setRequestProperty("Authorization", "Basic YWxleGFuZHJhLnRvbGtpbmFAZ21haWwuY29tOmZnSGpLbDE1Ng==");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            writer.write(body);
            writer.close();
            if ((connection.getResponseCode() >= 200) && (connection.getResponseCode() <= 299)) {
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String response;
                response = rd.lines().map(line -> line + '\r').collect(Collectors.joining());
                rd.close();
                return response;
            } else {
                return null;
            }
        } catch (Exception e) {
//            log.error("Connection to URL " + targetURL + " failed.");
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

        }

    }

    private String okHttpRequest(String targetUrl, String string) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://tolkina.atlassian.net/secure/admin/EditCustomFieldOptions%21add.jspa")
                .post(RequestBody.create(null, string))
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("x-atlassian-token", "no-check")
                .addHeader("Authorization", "Basic YWxleGFuZHJhLnRvbGtpbmFAZ21haWwuY29tOmZnSGpLbDE1Ng==")
                .addHeader("cache-control", "no-cache")
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);
        return response.body().string();
    }

    private String atlassianRequest(String targetUrl, String string, AtlassianHostUser host) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "application/x-www-form-urlencoded");
        headers.set("cache-control", "no-cache");
        headers.set("x-atlassian-token", "no-check");
        HttpEntity<String> requestMap = new HttpEntity<>(string, headers);
        return restClients.authenticatedAs(host).postForObject(targetUrl, requestMap, String.class);
    }

    private ResponseEntity<String> atlassianRequest2(String targetUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Atlassian-Token", "no-check");
        headers.set("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");

        UriComponentsBuilder uriComponents = UriComponentsBuilder.fromHttpUrl(targetUrl)
                .queryParam("Add", "Add")
                .queryParam("addSelectValue", "true")
                .queryParam("addValue", "123456789")
                .queryParam("fieldConfigId", "10132")
                .queryParam("selectedParentOptionId", "")
                .queryParam("atl_token", "qVVKBMh0f1IYf4kIR7aCE5E5");

        HttpEntity<?> entity = new HttpEntity<>(headers);

        return restClients.authenticatedAsAddon().exchange(
                uriComponents.build().encode().toUri(),
                HttpMethod.POST,
                entity,
                String.class);
    }
}
