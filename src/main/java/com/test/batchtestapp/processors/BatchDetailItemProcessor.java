package com.test.batchtestapp.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.test.batchtestapp.model.BatchDetails;
import com.test.batchtestapp.model.Person;

public class BatchDetailItemProcessor implements ItemProcessor<BatchDetails, BatchDetails> {

	private static final Logger log = LoggerFactory.getLogger(BatchDetailItemProcessor.class);

    @Override
    public BatchDetails process(final BatchDetails batchDetails) throws Exception {
    	final String BTCH_RUL_CDE = batchDetails.getBTCH_RUL_CDE().toLowerCase();
        batchDetails.setRUL_ELGBLE_CSE_CNT(3636);

        final BatchDetails transformedBatchDetails = new BatchDetails(batchDetails.getBTCH_LOG_DLY_ID(),
        		batchDetails.getBTCH_JOB_STAT_ID(),BTCH_RUL_CDE,batchDetails.getRUL_ELGBLE_CSE_CNT());

        log.info("Converting (" + batchDetails + ") into (" + transformedBatchDetails + ")");

        return transformedBatchDetails;
    }


}