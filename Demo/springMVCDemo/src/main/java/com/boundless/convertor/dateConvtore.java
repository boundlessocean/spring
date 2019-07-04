package com.boundless.convertor;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dateConvtore implements Converter<String,Date> {
    @Override
    public Date convert(String source) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = sf.parse(source);
            return d;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}

