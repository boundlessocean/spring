package com.boundless.model;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            Date d =  new SimpleDateFormat("yyyy-MM-dd").parse(text);
            CustomDate cd = new CustomDate(d);
            setValue(cd);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public String getAsText() {
        return super.getAsText();
    }
}
