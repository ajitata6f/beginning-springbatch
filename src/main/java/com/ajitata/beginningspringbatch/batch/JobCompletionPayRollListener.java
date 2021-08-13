package com.ajitata.beginningspringbatch.batch;

import com.ajitata.beginningspringbatch.dto.PayrollDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionPayRollListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionPayRollListener.class);
    private final JdbcTemplate jdbcTemplate;

    public JobCompletionPayRollListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info(">>>>> PAY ROLL JOB FINISHED! ");
            jdbcTemplate.query("SELECT `person_identification`,`currency`,`tx_amount`,`account_type`,`account_id`,`tx_description`,`first_name`,`last_name` FROM `transaction`",
            (rs, row) -> {
                PayrollDTO payrollDTO = new PayrollDTO();
                payrollDTO.setIdentification(rs.getString("person_identification"));
                payrollDTO.setCurrency(rs.getString("currency"));
                payrollDTO.setAmount(rs.getDouble("tx_amount"));
                payrollDTO.setAccountType(rs.getString("account_type"));
                payrollDTO.setAccountNumber(rs.getString("account_id"));
                payrollDTO.setDescription(rs.getString("tx_description"));
                payrollDTO.setFirstName(rs.getString("first_name"));
                payrollDTO.setLastName(rs.getString("last_name"));

                return payrollDTO;
            }).forEach(payroll ->
                    log.info("Found <" + payroll + "> in the database.")
                );
            }
    }

}
