package com.test.batchtestapp.model;

public class BatchDetails {
	
	private int BTCH_LOG_DLY_ID;
	private int BTCH_JOB_STAT_ID;
	private String BTCH_RUL_CDE;
	private int RUL_ELGBLE_CSE_CNT;
	
	public BatchDetails() {}
	
	public BatchDetails(int bTCH_LOG_DLY_ID, int bTCH_JOB_STAT_ID, String bTCH_RUL_CDE, int rUL_ELGBLE_CSE_CNT) {
		super();
		BTCH_LOG_DLY_ID = bTCH_LOG_DLY_ID;
		BTCH_JOB_STAT_ID = bTCH_JOB_STAT_ID;
		BTCH_RUL_CDE = bTCH_RUL_CDE;
		RUL_ELGBLE_CSE_CNT = rUL_ELGBLE_CSE_CNT;
	}
	
	
	public int getBTCH_LOG_DLY_ID() {
		return BTCH_LOG_DLY_ID;
	}
	
	public void setBTCH_LOG_DLY_ID(int bTCH_LOG_DLY_ID) {
		BTCH_LOG_DLY_ID = bTCH_LOG_DLY_ID;
	}
	public int getBTCH_JOB_STAT_ID() {
		return BTCH_JOB_STAT_ID;
	}
	public void setBTCH_JOB_STAT_ID(int bTCH_JOB_STAT_ID) {
		BTCH_JOB_STAT_ID = bTCH_JOB_STAT_ID;
	}
	public String getBTCH_RUL_CDE() {
		return BTCH_RUL_CDE;
	}
	public void setBTCH_RUL_CDE(String bTCH_RUL_CDE) {
		BTCH_RUL_CDE = bTCH_RUL_CDE;
	}
	public int getRUL_ELGBLE_CSE_CNT() {
		return RUL_ELGBLE_CSE_CNT;
	}
	public void setRUL_ELGBLE_CSE_CNT(int rUL_ELGBLE_CSE_CNT) {
		RUL_ELGBLE_CSE_CNT = rUL_ELGBLE_CSE_CNT;
	}
	
	
	@Override
	public String toString() {
		return "BatchDetails [BTCH_LOG_DLY_ID=" + BTCH_LOG_DLY_ID + ", BTCH_JOB_STAT_ID=" + BTCH_JOB_STAT_ID
				+ ", BTCH_RUL_CDE=" + BTCH_RUL_CDE + ", RUL_ELGBLE_CSE_CNT=" + RUL_ELGBLE_CSE_CNT + "]";
	}

}
