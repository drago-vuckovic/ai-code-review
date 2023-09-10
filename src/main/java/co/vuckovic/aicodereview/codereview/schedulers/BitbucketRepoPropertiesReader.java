package co.vuckovic.aicodereview.codereview.schedulers;


import co.vuckovic.aicodereview.codereview.dtos.BitbucketDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BitbucketRepoPropertiesReader {

    private final ResourceLoader resourceLoader;


    public List<BitbucketDTO> readListFromYaml() throws IOException {
        List<BitbucketDTO> dtoList = new ArrayList<>();
        Resource resource = resourceLoader.getResource("classpath:bitbucketconfig/repos.yml");
        try (InputStream inputStream = resource.getInputStream()) {
            Yaml yaml = new Yaml();
            List<Map<?, ?>> yamlObjects = yaml.load(inputStream);
            for (Map<?, ?> yamlMap : yamlObjects) {
                String username = (String) yamlMap.get("username");
                String branch = (String) yamlMap.get("branch");
                String repo = (String) yamlMap.get("repo");
                String repositoryAccessToken = (String) yamlMap.get("repositoryAccessToken");

                BitbucketDTO dto = BitbucketDTO.builder()
                        .username(username)
                        .branch(branch)
                        .repo(repo)
                        .repositoryAccessToken(repositoryAccessToken)
                        .build();

                dtoList.add(dto);
            }
        }
        return dtoList;
    }
}