package com.buyint.mergerbot.tts;

/**
 * Created by AQEEL on 8/7/2018
 */

public interface PlayingListener {
    void onSpeakBegin();

    void onSpeakPaused();

    void onSpeakProgress(int progress, int total);

    void onCompleted();

    void onError();

    String getpath();

    int getvolume();

}
