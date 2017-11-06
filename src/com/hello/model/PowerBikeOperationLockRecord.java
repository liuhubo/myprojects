package com.hello.model;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class PowerBikeOperationLockRecord extends BaseInfo {
    private String bikeNo;
    private Integer operationType;
    private Integer openLockMode;
    private String operationRealName;
    private String cityGuid;
    private String cityName;
    private Timestamp unlockDate;
    private String capacityBefore;
    private String userPhone;
    private Timestamp unlockStart;
    private Timestamp unlockEnd;
    private String unlockFailedDesc;
    private String unlockType;
    private Integer unlockState;
    public String getBikeNo() {
        return bikeNo;
    }

    public void setBikeNo(String bikeNo) {
        this.bikeNo = bikeNo;
    }

    public Integer getOperationType() {
        return this.operationType;
    }

    public String getOperationTypeName(){
        return EnumLockType.parse(this.operationType).info;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Integer getOpenLockMode() {
        return openLockMode;
    }

    public String getOpenLockModeName(){
        return EnumOpenLockMode.parse(this.openLockMode).info;
    }

    public void setOpenLockMode(Integer openLockMode) {
        this.openLockMode = openLockMode;
    }

    public String getOperationRealName() {
        return operationRealName;
    }

    public void setOperationRealName(String operationRealName) {
        this.operationRealName = operationRealName;
    }

    public String getCityGuid() {
        return cityGuid;
    }

    public void setCityGuid(String cityGuid) {
        this.cityGuid = cityGuid;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

	public Timestamp getUnlockDate() {
		return unlockDate;
	}

	public void setUnlockDate(Timestamp unlockDate) {
		this.unlockDate = unlockDate;
	}

	public String getCapacityBefore() {
		return capacityBefore;
	}

	public void setCapacityBefore(String capacityBefore) {
		this.capacityBefore = capacityBefore;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public Timestamp getUnlockStart() {
		return unlockStart;
	}

	public void setUnlockStart(Timestamp unlockStart) {
		this.unlockStart = unlockStart;
	}

	public Timestamp getUnlockEnd() {
		return unlockEnd;
	}

	public void setUnlockEnd(Timestamp unlockEnd) {
		this.unlockEnd = unlockEnd;
	}

	public String getUnlockFailedDesc() {
		return unlockFailedDesc;
	}

	public void setUnlockFailedDesc(String unlockFailedDesc) {
		this.unlockFailedDesc = unlockFailedDesc;
	}

	public String getUnlockType() {
		return unlockType;
	}

	public void setUnlockType(String unlockType) {
		this.unlockType = unlockType;
	}

	public Integer getUnlockState() {
		return unlockState;
	}

	public void setUnlockState(Integer unlockState) {
		this.unlockState = unlockState;
	}

	public String getUnlockDateStr() {
		DateTimeFormatter df =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		if(this.unlockDate!=null) {
			return unlockDate.toLocalDateTime().format(df);
		}
		 return null;
	}
}
