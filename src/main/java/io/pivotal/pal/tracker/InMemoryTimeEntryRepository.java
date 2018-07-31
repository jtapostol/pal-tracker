package io.pivotal.pal.tracker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    Map<Long, TimeEntry> timeEntries = new HashMap<Long, TimeEntry>();
    private long idSequence = 1;


    public TimeEntry find(long id) {
        return timeEntries.get(id);
    }

    public TimeEntry create(TimeEntry timeEntry) {
        TimeEntry saved = new TimeEntry(idSequence++, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        timeEntries.put(saved.getId(), saved);
        return saved;
    }

    public List<TimeEntry> list() {
        return timeEntries.keySet().stream().map(k -> {
            return timeEntries.get(k);
        }).collect(Collectors.toList());
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        if (timeEntries.get(id) == null) {
            return null;
        }
        TimeEntry saved = new TimeEntry(id, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
        timeEntries.put(saved.getId(), saved);
        return saved;
    }

    public void delete(long id) {
        timeEntries.remove(id);
    }

}
