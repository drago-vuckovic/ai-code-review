package co.vuckovic.aicodereview.codereview.schedulers;

import co.vuckovic.aicodereview.codereview.dtos.BitbucketDTO;
import co.vuckovic.aicodereview.codereview.services.CodeReviewService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class BitbucketScheduler {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private final CodeReviewService codeReviewService;

    @Value("${scheduler.period}")
    @Setter
    private int schedulerPeriod;

    private final BitbucketRepoPropertiesReader bitbucketRepoPropertiesReader;

    @Scheduled(fixedDelayString = "${scheduler.period}")
    public void runTask() {

        LocalDateTime currentTime = LocalDateTime.now();
        String formattedTime = currentTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        try{
            List<BitbucketDTO> bitbucketList = bitbucketRepoPropertiesReader.readListFromYaml();

            bitbucketList.forEach(bitbucket -> {
                codeReviewService.sendCodeReviewToBitbucket(
                        bitbucket.getUsername(),
                        bitbucket.getRepo(),
                        bitbucket.getBranch(),
                        bitbucket.getRepositoryAccessToken());
            });

        } catch (Exception e) {
            LOG.error("BitbucketScheduler Error Occurred At {}", formattedTime);
            LOG.error(e.getMessage());
        }

    }
}
