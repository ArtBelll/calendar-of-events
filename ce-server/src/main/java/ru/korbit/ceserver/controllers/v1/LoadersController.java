package ru.korbit.ceserver.controllers.v1;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.korbit.cecommon.config.Constants;
import ru.korbit.cecommon.services.RamblerKassaAsyncService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

/**
 * Created by Artur Belogur on 13.10.17.
 */
@RestController
@RequestMapping(value = "load")
@Slf4j
public class LoadersController {

    private RedissonClient redissonClient;

    @Autowired
    public LoadersController(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @GetMapping(value = "rambler-kassa")
    public ResponseEntity<?> loadRamblerKassa() throws ExecutionException, InterruptedException {
        val remoteService = redissonClient.getRemoteService(Constants.QUEUE_NAME);
        val ramblerKassaService = remoteService.get(RamblerKassaAsyncService.class);

        val startTime = LocalDateTime.now();
        log.info("Start load Rambler.Kassa data manually");

        try {
            ramblerKassaService.load().get();
        } catch (Throwable e) {
            throw new RuntimeException("Load field");
        }

        log.info("Load success. Load time = {}", Duration.between(startTime, LocalDateTime.now()));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
