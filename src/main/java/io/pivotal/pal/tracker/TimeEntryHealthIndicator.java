package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class TimeEntryHealthIndicator implements HealthIndicator {

    private TimeEntryRepository timeEntryRepository;

    public TimeEntryHealthIndicator(TimeEntryRepository timeEntryRepo) {
        this.timeEntryRepository = timeEntryRepo;
    }


    @Override
    public Health health() {

        if(timeEntryRepository.list().size()<5) {
            return new Health.Builder().up().build();
        }
        return new Health.Builder().down().build();
    }
}
