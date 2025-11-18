package com.your_company.your_project.domain.properties;

public class Crypto {
    
    private String text;

    public Crypto() {
        super();
    }

    public Crypto(String text) {
        this.text = text;
    }    

    @Override
    public String toString() {
        return text;
    }
}
