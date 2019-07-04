package com.boundless.convertor;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePeopertyEditor extends PropertyEditorSupport {

    private String dateFormat;

    public DatePeopertyEditor(String format){
        setDateFormat(format);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        try {
            DateFormat format = new SimpleDateFormat(getDateFormat());
            Date d = format.parse(text);
            setValue(d);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
