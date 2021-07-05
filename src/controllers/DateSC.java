package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateSC {

    // متد محاسبه مهلت تحویل از تاریخ فعلی سیستم نسبت به تاریخ بازگشت امانت
    public static String mohlatTahvil(String amtDateRtrn) throws ParseException {
        String[] date = amtDateRtrn.split("/");
        Roozh roozh = new Roozh();
        roozh.persianToGregorian(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]));
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date RtrnDate = format.parse(String.valueOf(roozh));
        Date nowDate = new Date();
        long timeDiff = RtrnDate.getTime() - nowDate.getTime();
        long hoursDiff = (timeDiff / (1000 * 60 * 60));
        long daysDiff = (timeDiff / (1000 * 60 * 60 * 24)) % 365;
        long yearsDiff = (timeDiff / (1000l * 60 * 60 * 24 * 365));
        if(hoursDiff<0){
            return "گذشتن مهلت";
        }else if (daysDiff==0){
            return String.format("%s ساعت", String.valueOf(hoursDiff));
        }else if (yearsDiff == 0) {
            return String.format("%s روز", String.valueOf(daysDiff));
        }else {
            return String.format("بیش از %s سال", String.valueOf(yearsDiff));
        }

    }



    // محسابه تاریخ برگشت امانت با احتساب تعداد روز مهلت آن
    public static String tamdidMohalat(String dateInput,int day) throws ParseException {
        Roozh roozh = new Roozh();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String[] date = dateInput.split("/");
        roozh.persianToGregorian(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]));
        Date RtrnDate = null;
        RtrnDate = format.parse(String.valueOf(roozh));
        long dayMohlatMili = day*24*60*60*1000;
        long newTime = RtrnDate.getTime()+dayMohlatMili;
        RtrnDate = new Date(newTime);
        date = format.format(RtrnDate).split("/");
        roozh.gregorianToPersian(Integer.parseInt(date[0]),Integer.parseInt(date[1]),Integer.parseInt(date[2]));

        return String.valueOf(roozh);
    }

}

