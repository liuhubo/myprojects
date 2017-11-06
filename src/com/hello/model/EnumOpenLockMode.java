package com.hello.model;

import java.util.Objects;

/**
 * Created by treec on 2017/9/19.
 */
public enum EnumOpenLockMode {
    None(-99,""),
    UnKnow(-999,"未知"),
    QR(1,"扫码");

    public final String info;
    public final int type;

    EnumOpenLockMode(int type, String info) {
        this.type = type;
        this.info = info;
    }

    public static EnumOpenLockMode parse(Integer type) {
        if (type == null) return None;
        for (EnumOpenLockMode enumOpenLockMode : EnumOpenLockMode.values()) {
            if (Objects.equals(enumOpenLockMode.type, type)) {
                return enumOpenLockMode;
            }
        }
        return UnKnow;
    }
}
