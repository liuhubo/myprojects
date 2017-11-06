package com.hello.model;

import java.util.Objects;

public enum EnumLockType {
    None(-999, null),
    UnKnow(-99, "未知"),
    Open(0, "开锁"),
    Close(1, "关锁");

    public final int status;
    public final String info;

    EnumLockType(int status, String info) {
        this.status = status;
        this.info = info;
    }

    public static EnumLockType parse(Integer status) {
        if (status == null) return None;

        for (EnumLockType enumBikeStatus : EnumLockType.values()) {
            if (Objects.equals(enumBikeStatus.status, status)) {
                return enumBikeStatus;
            }
        }

        return UnKnow;
    }
}
