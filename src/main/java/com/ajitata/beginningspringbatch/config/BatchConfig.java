package com.ajitata.beginningspringbatch.config;

import com.ajitata.beginningspringbatch.batch.JobCompletionPayRollListener;
import com.ajitata.beginningspringbatch.batch.PayrollItemProcessor;
import com.ajitata.beginningspringbatch.dto.PayrollDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;


@Configuration
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job importPayRollJob(JobCompletionPayRollListener listener, Step step) {
        return jobBuilderFactory.get("importPayRollJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step)
                .end()
                .build();
    }

    @Bean
    public Step payrollBatchStep(JdbcBatchItemWriter<PayrollDTO> writer) {
        return stepBuilderFactory.get("payrollBatchStep")
                .<PayrollDTO, PayrollDTO> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    // READ THE INPUT DATA
    @Bean
    public FlatFileItemReader<PayrollDTO> reader() {
        return new FlatFileItemReaderBuilder<PayrollDTO>()
                .name("payrollItemReader")
                .resource(new ClassPathResource("payroll-data.csv"))
                .delimited()
                .names("identification", "currency", "amount", "accountType", "accountNumber", "description", "firstName", "lastName")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<PayrollDTO>() {{
                    setTargetType(PayrollDTO.class);
                }})
                .build();
    }

    // PROCESS THE DATA
    @Bean
    public PayrollItemProcessor processor() {
        return new PayrollItemProcessor();
    }

    // WRITE THE PRODUCED DATA
    @Bean
    public JdbcBatchItemWriter<PayrollDTO> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<PayrollDTO>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql(
                    "INSERT INTO `transaction`(`person_identification`,`currency`,`tx_amount`,`account_type`,`account_id`,`tx_description`,`first_name`,`last_name`) " +
                    "VALUES(:identification,:currency,:amount,:accountType,:accountNumber,:description,:firstName,:lastName)"
                )
                .dataSource(dataSource)
                .build();
    }

}
