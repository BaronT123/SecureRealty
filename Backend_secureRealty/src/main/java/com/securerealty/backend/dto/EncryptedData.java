package com.securerealty.backend.dto;

public class EncryptedData {

    private String cipherText;
    private String iv;

    public EncryptedData() {
    }

    public EncryptedData(String cipherText, String iv) {
        this.cipherText = cipherText;
        this.iv = iv;
    }

    public String getCipherText() {
        return cipherText;
    }

    public void setCipherText(String cipherText) {
        this.cipherText = cipherText;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}