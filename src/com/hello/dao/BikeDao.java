package com.hello.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hello.model.PowerBikeOperationLockRecord;
import com.hello.model.Tran;


public interface BikeDao {
    void insertPowerBikeBatteryChange(@Param("powerBike")PowerBikeOperationLockRecord record);
    
    List<PowerBikeOperationLockRecord> getPowerBikeBatteryChangeRecord(@Param("powerInfo")PowerBikeOperationLockRecord params);
    
    void updateBatteryChangeRecord(@Param("updatePower")PowerBikeOperationLockRecord params);
    
    void insertTran(@Param("tran")Tran tran);
    	
    void updateTran(@Param("tran")Tran tran);
}
