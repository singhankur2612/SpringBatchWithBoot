package com.test.batchtestapp.listner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.test.batchtestapp.model.BatchDetails;
import com.test.batchtestapp.model.Person;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!! JOB FINISHED! Time to verify the results");
			System.out.println("==================================================job finished=================================");
			List<BatchDetails> results = jdbcTemplate.query("SELECT * FROM BTCH_LOG_DLY WHERE BTCH_JOB_STAT_ID=5555", new RowMapper<BatchDetails>() {
				@Override
				public BatchDetails mapRow(ResultSet rs, int row) throws SQLException {
					return new BatchDetails(rs.getInt(1), rs.getInt(2),rs.getString(3),rs.getInt(4));
				}
			});
			
			for (BatchDetails batchDetails : results) {
				System.out.println("+++++++++++++++++++++++"+batchDetails.toString());
				log.info("Found <" + batchDetails + "> in the database.");
			}

		}
	}
}