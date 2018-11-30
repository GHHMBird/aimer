package com.buyint.mergerbot.UIs.main.adapter;

import com.buyint.mergerbot.dto.IndustrySuperiorResponse;
import com.buyint.mergerbot.dto.MatchCompanyModel;

import java.io.Serializable;

/**
 * Created by huheming on 2018/5/31
 */
public class QM3QABean implements Serializable {

    public long speakCount;//第几轮对话

    public long time;

    public String name;

    public String code;

    public MatchCompanyModel data;

    public String type;

    public IndustrySuperiorResponse is;

}
