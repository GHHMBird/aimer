package com.buyint.mergerbot.dto;

import java.io.Serializable;
import java.util.List;

/**
 * created by licheng  on date 2018/8/16
 */
public class LayerSendRequest implements Serializable {


    private String emailContent;
    private AmiAccessBean lawyerAuthenticationMessage;
    private List<layersList> layers;


    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public AmiAccessBean getLawyerAuthenticationMessage() {
        return lawyerAuthenticationMessage;
    }

    public void setLawyerAuthenticationMessage(AmiAccessBean lawyerAuthenticationMessage) {
        this.lawyerAuthenticationMessage = lawyerAuthenticationMessage;
    }

    public List<layersList> getLayers() {
        return layers;
    }

    public void setLayers(List<layersList> layers) {
        this.layers = layers;
    }


}
