package com.piskunov.xmlconverter.mapping;

import java.util.HashMap;

/**
 * Created by Vladimir Piskunov on 2/25/16.
 */


public class InputData {

    private String source;
    private HashMap<String, String> pairs = new HashMap<>();

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public HashMap<String, String> getPairs() {
        return pairs;
    }

    public void setPairs(HashMap<String, String> pairs) {
        this.pairs = pairs;
    }
}