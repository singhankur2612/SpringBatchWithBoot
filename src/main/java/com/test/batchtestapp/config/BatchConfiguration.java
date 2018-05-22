package com.test.batchtestapp.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;

import com.test.batchtestapp.listner.JobCompletionNotificationListener;
import com.test.batchtestapp.model.BatchDetails;
import com.test.batchtestapp.model.Person;
import com.test.batchtestapp.processors.PersonItemProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration extends DefaultBatchConfigurer{

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    public DataSource dataSource;
    
    @Override
    public void setDataSource(DataSource dataSource) {}
    
    @Bean
    public FlatFileItemReader<BatchDetails> reader1() {
    	System.out.println("*********************in reader1*************************");
    	FlatFileItemReader<BatchDetails> itemreader =new FlatFileItemReader<BatchDetails>();
    	itemreader.setResource(new ClassPathResource(System.getProperty("inpFile")));
    	//itemreader.setLinesToSkip(1);
    	itemreader.setLineMapper(new DefaultLineMapper<BatchDetails>(){{
    		setLineTokenizer(new DelimitedLineTokenizer(){{
    			setNames("BTCH_LOG_DLY_ID","BTCH_JOB_STAT_ID","BTCH_RUL_CDE","RUL_ELGBLE_CSE_CNT");
    		}});
    		setFieldSetMapper(new BeanWrapperFieldSetMapper<BatchDetails>(){{
    			setTargetType(BatchDetails.class);
    		}});		
    				
    	}});
    	return itemreader;
    }
    
    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }
    
    @Bean
    public FlatFileItemWriter<BatchDetails> writer2() {
    	System.out.println("*********************in writer2*************************");
    	FlatFileItemWriter<BatchDetails> itemWriter=new FlatFileItemWriter<>();
    	try {
    		itemWriter.setResource(new FileSystemResource(System.getProperty("outFile")));
    		DelimitedLineAggregator<BatchDetails> lineAggregator = new DelimitedLineAggregator<>();
    		lineAggregator.setDelimiter("@");
    		BeanWrapperFieldExtractor extractor = new BeanWrapperFieldExtractor();
    		extractor.setNames(new String[]{"BTCH_LOG_DLY_ID","BTCH_JOB_STAT_ID","BTCH_RUL_CDE","RUL_ELGBLE_CSE_CNT"});
    		lineAggregator.setFieldExtractor(extractor);
    		itemWriter.setLineAggregator(lineAggregator);
    		
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return itemWriter;
    	
    }
    
    @Bean
    public JdbcBatchItemWriter<BatchDetails> writer1() {
    	JdbcBatchItemWriter<BatchDetails> itemWriter=new JdbcBatchItemWriter<>();
    	itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<BatchDetails>());
		itemWriter.setSql("INSERT INTO BTCH_LOG_DLY(BTCH_LOG_DLY_ID,BTCH_JOB_STAT_ID,BTCH_RUL_CDE,RUL_ELGBLE_CSE_CNT)"
				+ "values(:BTCH_LOG_DLY_ID,:BTCH_JOB_STAT_ID,:BTCH_RUL_CDE,:RUL_ELGBLE_CSE_CNT)");
		itemWriter.setDataSource(dataSource);
		return itemWriter;
    }
    
    @Bean
    public Job Job1(JobCompletionNotificationListener listener) {
    	return jobBuilderFactory.get("Job1")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1())
            .end()
            .build();
    }
    
    @Bean
    public Job Job2(JobCompletionNotificationListener listener) {
    	return jobBuilderFactory.get("Job2")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step2())
            .end()
            .build();
    }

    @Bean
    public Step step1() {
    	return stepBuilderFactory.get("step1")
            .<BatchDetails, BatchDetails> chunk(5)
            .reader(reader1())
            //.processor(processor())
            .writer(writer1())
            .build();
    }
    
    @Bean
    public Step step2() {
    	return stepBuilderFactory.get("step2")
        	.<BatchDetails,BatchDetails> chunk(5)
            .reader(reader1())
            //.processor(processor())
            .writer(writer2())
            .build();
    }
    // end::jobstep[]
}