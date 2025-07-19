package com.stepa7.starter.config;

import com.stepa7.starter.android.Android;
import com.stepa7.starter.android.AndroidService;
import com.stepa7.starter.command.Command;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan("com.stepa7.starter.command")
public class SyntheticHumanCoreAutoConfiguration {
    public static final int MAX_QUEUE_SIZE = 10;

    @Bean
    public BlockingQueue<Command> commandQueue() {
        return new LinkedBlockingQueue<>(MAX_QUEUE_SIZE);
    }

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
         return new ThreadPoolExecutor(
                2,
                4,
                20,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(MAX_QUEUE_SIZE));
    }

    @Bean
    public AndroidService androidService() {
        return new AndroidService();
    }
}
