package co.vuckovic.aicodereview.bitbucket.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class BitbucketFileContentService {

    private final RestTemplate restTemplate;


    public String getFileContentFromBitbucket(

            String username,
            String repository,
            String branch,
            String repositoryAccessToken,
            String filePath ) throws URISyntaxException {

        String url = "https://api.bitbucket.org/2.0/repositories/{username}/{repository}/src/{branch}/{filePath}";

        URI expandedUrl = new URI(url
                .replace("{username}", username)
                .replace("{repository}", repository)
                .replace("{branch}", branch)
                .replace("{filePath}", filePath)
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + repositoryAccessToken);


        RequestEntity<Void> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, expandedUrl);

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        return responseEntity.getBody();
    }
}