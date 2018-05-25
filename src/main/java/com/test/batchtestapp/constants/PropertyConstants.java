package com.test.batchtestapp.constants;

public class PropertyConstants {
	public final static String sqlStat="INSERT INTO BTCH_LOG_DLY(BTCH_LOG_DLY_ID,BTCH_JOB_STAT_ID,BTCH_RUL_CDE,RUL_ELGBLE_CSE_CNT) "
			+ "values(:BTCH_LOG_DLY_ID,:BTCH_JOB_STAT_ID,:BTCH_RUL_CDE,:RUL_ELGBLE_CSE_CNT)";
	public final static String[] sqlParam={"BTCH_LOG_DLY_ID","BTCH_JOB_STAT_ID","BTCH_RUL_CDE","RUL_ELGBLE_CSE_CNT"};

}
