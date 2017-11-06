package com.hello.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;


public class BaseDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new NoNullFieldStringStyle());
    }
}
