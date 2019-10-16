package com.guodian.domin.supplierpage;

import java.io.Serializable;
import java.util.List;

public class BaojiaListClass implements Serializable {

    private static final long serialVersionUID = 5039783654229514824L;

    private String inputText;
    private List<BaoJiaClass> data;

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public List<BaoJiaClass> getData() {
        return data;
    }

    public void setData(List<BaoJiaClass> data) {
        this.data = data;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "BaojiaListClass [inputText=" + inputText + ", data=" + data + "]";
    }
}
