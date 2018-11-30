package com.buyint.mergerbot.dto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by huheming on 2018/5/23.
 */

public class Result implements Serializable {

    private ArrayList<String> uncertain_word;
    private ArrayList<String> word;

    public ArrayList<String> getUncertain_word() {
        return uncertain_word;
    }

    public void setUncertain_word(ArrayList<String> uncertain_word) {
        this.uncertain_word = uncertain_word;
    }

    public ArrayList<String> getWord() {
        return word;
    }

    public void setWord(ArrayList<String> word) {
        this.word = word;
    }
}
