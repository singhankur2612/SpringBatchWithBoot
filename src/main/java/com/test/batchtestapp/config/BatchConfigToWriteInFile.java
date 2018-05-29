package com.test.batchtestapp.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.test.batchtestapp.constants.PropertyConstants;
import com.test.batchtestapp.listner.JobCompletionNotificationListener;
import com.test.batchtestapp.model.BatchDetails;
import com.test.batchtestapp.processors.BatchDetailItemProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfigToWriteInFile {
	
	@Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Bean
    public Job jobWriteDataInFile(JobCompletionNotificationListener listener) {
    	System.out.println("*********************in jobWriteDataInFile*************************");
    	return jobBuilderFactory.get("jobWriteDataInFile")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(stepWriteDataInFile())
            .end()
            .build();
    }

   
    
    @Bean
    public Step stepWriteDataInFile() {
    	System.out.println("*********************in stepWriteDataInFile*************************");
    	return stepBuilderFactory.get("stepWriteDataInFile")
        	.<BatchDetails,BatchDetails> chunk(5)
            .reader(dataReader())
            .processor(processor())
            .writer(writerToWriteDataInFile())
            .build();
    }
    
    @Bean
    public FlatFileItemReader<BatchDetails> dataReader() {
    	System.out.println("*********************in dataReader*************************");
    	FlatFileItemReader<BatchDetails> itemreader =new FlatFileItemReader<BatchDetails>();
    	itemreader.setResource(new FileSystemResource(System.getProperty("inpFile")));
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
    public BatchDetailItemProcessor processor() {
    	System.out.println("*********************in BatchDetailItemProcessor.processor*************************");
        return new BatchDetailItemProcessor();
    }
    
    @Bean
    public FlatFileItemWriter<BatchDetails> writerToWriteDataInFile() {
    	System.out.println("*********************in writerToWriteDataInFile*************************");
    	FlatFileItemWriter<BatchDetails> itemWriter=new FlatFileItemWriter<>();
    	try {
    		itemWriter.setResource(new FileSystemResource(System.getProperty("outFile")));
    		DelimitedLineAggregator<BatchDetails> lineAggregator = new DelimitedLineAggregator<>();
    		lineAggregator.setDelimiter("@");
    		BeanWrapperFieldExtractor extractor = new BeanWrapperFieldExtractor();
    		extractor.setNames(PropertyConstants.sqlParam);
    		lineAggregator.setFieldExtractor(extractor);
    		itemWriter.setLineAggregator(lineAggregator);
    		System.out.println("*********************end writerToWriteDataInFile*************************");
		} catch (Exception e) {
			System.out.println("*********************catch writerToWriteDataInFile*************************");
			e.printStackTrace();
		}
    	return itemWriter;
    	
    }

}
