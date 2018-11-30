//
// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license.
//
// Microsoft Cognitive Services (formerly Project Oxford): https://www.microsoft.com/cognitive-services
//
// Microsoft Cognitive Services (formerly Project Oxford) GitHub:
// https://github.com/Microsoft/Cognitive-Speech-TTS
//
// Copyright (c) Microsoft Corporation
// All rights reserved.
//
// MIT License:
// Permission is hereby granted, free of charge, to any person obtaining
// a copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to
// permit persons to whom the Software is furnished to do so, subject to
// the following conditions:
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED ""AS IS"", WITHOUT WARRANTY OF ANY KIND,
// EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
// LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
// OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
// WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//
package com.buyint.mergerbot.tts;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//控制当前语音播放的各个属性和状态
public class Synthesizer {

    private Voice m_serviceVoice;

    MediaPlayer mediaPlayer = new MediaPlayer();
    ;
    PlayingListener playingListener;

    boolean ispause = false;
    CountDownTimer timer;

    float speed = (float) 0.5;

    private void playSound(final byte[] sound) {
        try {

            File file = new File(Environment.getExternalStorageDirectory(), "mmh" + ".mp3");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(sound);
            fos.close();

            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(file.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.start();
            playingListener.onSpeakBegin();
            mediaPlayer.setOnCompletionListener(mp -> playingListener.onCompleted());
            starttimer();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public enum ServiceStrategy {
        AlwaysService//, WiFiOnly, WiFi3G4GOnly, NoService
    }

    public Synthesizer(String apiKey) {
        m_serviceVoice = new Voice("en-US");
        m_eServiceStrategy = ServiceStrategy.AlwaysService;
        m_ttsServiceClient = new TtsServiceClient(apiKey);
    }

    public void SetVoice(Voice serviceVoice) {
        m_serviceVoice = serviceVoice;
    }

    public void SetServiceStrategy(ServiceStrategy eServiceStrategy) {
        m_eServiceStrategy = eServiceStrategy;
    }

    private byte[] Speak(String text) {
        String ssml = "<speak version='1.0' xml:lang='" + m_serviceVoice.lang + "'><voice xml:lang='" + m_serviceVoice.lang + "' xml:gender='" + m_serviceVoice.gender + "'";
        if (m_eServiceStrategy == ServiceStrategy.AlwaysService) {
            if (m_serviceVoice.voiceName.length() > 0) {
                ssml += " name='" + m_serviceVoice.voiceName + "'>";
            } else {
                ssml += ">";
            }
            ssml += text + "</voice></speak>";
        }
        return SpeakSSML(ssml);
    }

    public void SpeakToAudio(String text, PlayingListener listener) {
        if (ispause && mediaPlayer != null) {
            ispause = false;
            playingListener.onSpeakBegin();
            mediaPlayer.start();
            starttimer();
        } else if (!(mediaPlayer != null && mediaPlayer.isPlaying())) {
            playingListener = listener;
            playSound(Speak(text));
        }
    }

    public void PauseAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            ispause = true;
            mediaPlayer.pause();
            timer.cancel();
            playingListener.onSpeakPaused();
        }
    }

    public void stopSound() {
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                timer.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean Isplaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    private byte[] SpeakSSML(String ssml) {
        byte[] result = null;
        /*
         * check current network environment
         * to do...
         */
        if (m_eServiceStrategy == ServiceStrategy.AlwaysService) {
            result = m_ttsServiceClient.SpeakSSML(ssml);
            if (result == null || result.length == 0) {
                return null;
            }

        }
        return result;
    }

    private void starttimer() {
        timer = new CountDownTimer(120000, 300) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {
//                float volume = (float) (1 - (Math.log(100 - playingListener.getvolume()) / Math.log(100)));
//                mediaPlayer.setVolume(volume, volume);
                playingListener.onSpeakProgress(mediaPlayer.getCurrentPosition(), mediaPlayer.getDuration());
                if ((mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition()) < 100) {
                    timer.cancel();
                }
            }

            public void onFinish() {
                playingListener.onSpeakProgress(mediaPlayer.getDuration(), mediaPlayer.getDuration());
            }
        }.start();
    }

    public void Change_speed_of_voice(float speed) {
        this.speed = speed;
        if (mediaPlayer != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(speed));
            }
        }
    }

    private TtsServiceClient m_ttsServiceClient;
    private ServiceStrategy m_eServiceStrategy;

}
