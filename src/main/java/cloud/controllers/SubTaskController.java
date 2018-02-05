package cloud.controllers;

import com.atlassian.connect.spring.AtlassianHostRestClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SubTaskController {
    @Autowired
    private AtlassianHostRestClients restClients;

    @GetMapping(value = "/change-summary")
    public ModelAndView changeSummary(@RequestParam String issueKey) {
        ModelAndView model = new ModelAndView();
        model.setViewName("summary");
        System.out.println(issueKey);
        return model;
    }
}
