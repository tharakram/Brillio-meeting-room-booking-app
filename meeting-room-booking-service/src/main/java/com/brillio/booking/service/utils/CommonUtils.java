package com.brillio.booking.service.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Service
@Slf4j
public class CommonUtils {

    public static String covertObjectTOJson(Collection<?> object) {
        //Gson gsonBuilder = new GsonBuilder().cre
        return null;
    }

    public static String getDateForRef() {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        return sdf.format(new Date());
    }

}
