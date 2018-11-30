package com.buyint.mergerbot.dto;

import java.io.Serializable;

/**
 * Created by huheming on 2018/5/23.
 */

public class OriginResult implements Serializable {

    private String error;
    private String sub_error;
    private String sn;
    private String desc;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSub_error() {
        return sub_error;
    }

    public void setSub_error(String sub_error) {
        this.sub_error = sub_error;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
