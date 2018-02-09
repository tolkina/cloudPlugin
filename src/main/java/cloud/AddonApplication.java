package cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableJpaRepositories
public class AddonApplication {

    public static void main(String[] args) throws Exception {
        new SpringApplication(AddonApplication.class).run(args);
//        String targetURL = "https://tolkina.atlassian.net/secure/admin/EditCustomFieldOptions!add.jspa";
//        String body = "Add=Add&addSelectValue=true&addValue=qwerty&atl_token=00c532d0-0dfd-4bf9-9fb5-ccdc561c868e|32586e0b48c960edbf3fa632de4069428a7c5758|lin&fieldConfigId=10132&selectedParentOptionId=";
//        HttpURLConnection connection = null;
//        try {
//            URL url = new URL(targetURL);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            connection.setDoOutput(true);
//
//            connection.setRequestProperty("Authorization", "Basic YWxleGFuZHJhLnRvbGtpbmFAZ21haWwuY29tOmZnSGpLbDE1Ng==");
//
//            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
//            writer.write(body);
//            writer.close();
//            if ((connection.getResponseCode() >= 200) && (connection.getResponseCode() <= 299)) {
//                InputStream is = connection.getInputStream();
//                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//                String response;
//                response = rd.lines().map(line -> line + '\r').collect(Collectors.joining());
//                rd.close();
//                System.out.println(response);
//            } else {
//                System.out.println("null");
//            }
//        } catch (Exception e) {
//            System.out.println("Connection to URL " + targetURL + " failed.");
//        } finally {
//            if (connection != null) {
//                connection.disconnect();
//            }
//
//        }
    }


}
