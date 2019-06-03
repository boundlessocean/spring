package com.boundless.convertor;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePeopertyEditor extends PropertyEditorSupport {

    private DateFormat dateFormat;

    public DatePeopertyEditor(DateFormat format){
        setDateFormat(format);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        try {
            Date d = getDateFormat().parse(text);
            setValue(d);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }
}
