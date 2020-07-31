package com.commons.convert;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Slf4j
public class DateConverter implements Converter<String,Date> {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";


    @Override
    public Date convert(String source) {
        if(StringUtils.isEmpty(source)) {
            return null;
        }
        try{
            source = source.trim();
            if (source.contains(":")){
                SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
                return formatter.parse(source);
            }
            else if(source.matches("^\\d+$")){
                Long time = Long.valueOf(source);
                return new Date(time);
            }
        } catch (ParseException e) {
            log.info("[DateConverter] convert date parseException ",e );
        }
        log.info("[DateConverter] not match timestamp or date ");
        return null;
    }
}
