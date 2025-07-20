package com.stepa7.starter.config;

import com.stepa7.starter.android.AndroidService;
import com.stepa7.starter.command.Command;
import com.stepa7.starter.metrics.MetricsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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

    @Bean
    @ConditionalOnMissingBean(MetricsService.class)
    public MetricsService metricsService() {
        return new MetricsService() {
            @Override
            public void recordCommandExecuted(Command command) {
                // no-op
            }

            @Override
            public void updateQueueSize(int size) {
                // no-op
            }
        };
    }
}
