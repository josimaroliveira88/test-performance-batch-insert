package br.com.josimar.performance;

import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;

@ApplicationScoped
public class InstituicaoBean {
    private AtomicInteger counter = new AtomicInteger();

    public int getCounter() {
        return counter.get();
    }

    // @Scheduled(every = "10s")
    void incrementCounter() {
        counter.incrementAndGet();
        System.out.println("Counter incremented: " + counter);
    }

    @Scheduled(cron = "0 39 23 * * ?")
    void cronJob(ScheduledExecution execution) {
        counter.incrementAndGet();
        System.out.println(execution.getScheduledFireTime());
    }

    // @Scheduled(cron = "{cron.expr}")
    void cronJobWithExpressionInConfig(ScheduledExecution execution) {
        counter.incrementAndGet();
        System.out.println("Cron expression configured in application.properties at " + execution.getScheduledFireTime());
    }
}
