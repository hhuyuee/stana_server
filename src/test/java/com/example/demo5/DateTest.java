package com.example.demo5;

import com.example.demo5.util.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * description
 *
 * @author huyue87@jd.com
 * @date 2021/1/6 10:15
 * @since 2.0.0
 */
@SpringBootTest
public class DateTest {
    @Test
    void test() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("2008-02-04");
        System.out.println(date);
        System.out.println(DateUtils.getWeekDay(date));
    }
}
