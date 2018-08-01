package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TimeEntryRowMapper implements RowMapper<TimeEntry> {
    public TimeEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
        TimeEntry timeEntry = new TimeEntry(rs.getLong("id"),
                rs.getLong("project_id"),
                rs.getLong("user_id"),
                rs.getDate("date").toLocalDate(),
                rs.getInt("hours"));
        return timeEntry;
    }
}
