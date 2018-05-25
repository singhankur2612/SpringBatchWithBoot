package com.test.batchtestapp.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;

import com.test.batchtestapp.model.BatchDetails;

public class BatchDetailWriter implements ItemWriter<BatchDetails>{

	@Override
	public void write(List<? extends BatchDetails> items) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/*@Override
	public FlatFileItemWriter<BatchDetails> write(List<? extends BatchDetails> items) throws Exception {
		// TODO Auto-generated method stub
		FlatFileItemWriter<BatchDetails> itemWriter=new FlatFileItemWriter<>();
    	return itemWriter;
	}*/

}
