package com.hello.model;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class BaseInfo extends BaseDO {
    protected String guid;
    protected Timestamp createDate;
    protected Timestamp updateDate;
    protected String createUserGuid;
    protected String updateUserGuid;
    protected String createUserName;
    private String updateUserName;

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCreateDateName() {
        return createDate == null ? null : createDate.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getUpdateDateName() {
        return updateDate == null ? null : updateDate.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateUserGuid() {
        return createUserGuid;
    }

    public void setCreateUserGuid(String createUserGuid) {
        this.createUserGuid = createUserGuid;
    }

    public String getUpdateUserGuid() {
        return updateUserGuid;
    }

    public void setUpdateUserGuid(String updateUserGuid) {
        this.updateUserGuid = updateUserGuid;
    }
}
