package io.pivotal.pal.tracker;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public TimeEntry create(TimeEntry timeEntry){
        String sql = "insert into time_entries (project_id,user_id,date,hours) VALUES (:projectid,:userid,:date,:hours)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("projectid",timeEntry.getProjectId())
                .addValue("userid",timeEntry.getUserId())
                .addValue("date",timeEntry.getDate())
                .addValue("hours",timeEntry.getHours());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        namedJdbcTemplate.update(sql,params,keyHolder);
        long id = keyHolder.getKey().longValue();

        return new TimeEntry(id,timeEntry.getProjectId(),timeEntry.getUserId(),timeEntry.getDate(),timeEntry.getHours());
    }

    public TimeEntry find(long id){
        String sql = "select * from time_entries where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id",id);
        NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        try {
            TimeEntry te = namedJdbcTemplate.queryForObject(sql, params, new TimeEntryRowMapper());
            System.out.println(te.toString());
            return te;
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<TimeEntry> list(){
        String sql = "select * from time_entries";
        return jdbcTemplate.query(sql,new TimeEntryRowMapper());
    }

    public void delete(long id){
        String sql = "delete from time_entries where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id",id);
        NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        namedJdbcTemplate.update(sql,params);
    }

    public TimeEntry update(long id, TimeEntry timeEntry){
        String sql = "update time_entries set project_id = :projectid, " +
                "user_id = :userid, date = :date, hours = :hours " +
                "where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("projectid",timeEntry.getProjectId())
                .addValue("userid",timeEntry.getUserId())
                .addValue("date",timeEntry.getDate())
                .addValue("hours",timeEntry.getHours());
        NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        namedJdbcTemplate.update(sql,params);
        return new TimeEntry(id,timeEntry.getProjectId(),timeEntry.getUserId(),timeEntry.getDate(),timeEntry.getHours());
    }
}
