package com.heb.ImageDetector.client;


import com.google.gson.Gson;
import com.heb.ImageDetector.representation.Root;
import com.heb.ImageDetector.model.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.*;

@Component
@Slf4j
public class ImaggaClient {
    private final String url = "https://api.imagga.com/v2/tags?image_url=";

    private final Gson gson;

    @Autowired
    public ImaggaClient(Gson gson) {
        this.gson = gson;
    }

    public Root getTags(Image image) throws IOException{

        File fileToUpload = new File(image.getImageURL());

        String endpoint = "/tags";

        String crlf = "\r\n";
        String twoHyphens = "--";
        String boundary =  "Image Upload";

        URL urlObject = new URL("https://api.imagga.com/v2" + endpoint);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestProperty("Authorization", "Basic YWNjX2VkNmYzMDQzNTc5MDAyMTpkYWY3MGMzYTYyZTk5MWFjMWNhNzdiYjU5ZGEwM2UwOQ==");
        connection.setUseCaches(false);
        connection.setDoOutput(true);

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Cache-Control", "no-cache");
        connection.setRequestProperty(
                "Content-Type", "multipart/form-data;boundary=" + boundary);

        DataOutputStream request = new DataOutputStream(connection.getOutputStream());

        request.writeBytes(twoHyphens + boundary + crlf);
        request.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\"" + fileToUpload.getName() + "\"" + crlf);
        request.writeBytes(crlf);


        InputStream inputStream = new FileInputStream(fileToUpload);
        int bytesRead;
        byte[] dataBuffer = new byte[1024];
        while ((bytesRead = inputStream.read(dataBuffer)) != -1) {
            request.write(dataBuffer, 0, bytesRead);
        }

        request.writeBytes(crlf);
        request.writeBytes(twoHyphens + boundary + twoHyphens + crlf);
        request.flush();
        request.close();

        InputStream responseStream = new BufferedInputStream(connection.getInputStream());

        BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));

        String line = "";
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = responseStreamReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        responseStreamReader.close();

        String response = stringBuilder.toString();
        System.out.println(response);

        responseStream.close();
        connection.disconnect();
        return gson.fromJson(response, Root.class);
    }

//    public ImaggaResult getTags(Image image) throws IOException, InterruptedException {
//        log.info(url+ URLEncoder.encode(image.getImageURL(), StandardCharsets.UTF_16.toString()));
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(url+image.getImageURL()))
//                .header("Authorization", "Basic YWNjX2VkNmYzMDQzNTc5MDAyMTpkYWY3MGMzYTYyZTk5MWFjMWNhNzdiYjU5ZGEwM2UwOQ==")
//                .header("content-type", "application/x-www-form-urlencoded")
//                .method("POST", HttpRequest.BodyPublishers
//                        .ofString("secret=%3Cdaf70c3a62e991ac1ca77bb59da03e09%3E&key=%3Cacc_ed6f30435790021%3E&imageUrl=%3C"+ image.getImageURL() + "%3E"))
//                .build();
//        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//        log.info(response.body());
//        return objectMapper.readValue(response.body(),ImaggaResult.class);
//    }
}
