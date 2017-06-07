package Extras;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.datatype.XMLGregorianCalendar;

public abstract class Utilidad {

    public static String getDominio() {
        String Ret = "http://localhost:10016/";
        try {
            Ret = "http://" + InetAddress.getLocalHost().getHostAddress() + ":10016/";
        } catch (Exception e) {
        }
        return Ret;
    }

    public static Date toDate(XMLGregorianCalendar xml) {
        int date = xml.getDay();
        int hrs = xml.getHour();
        int min = xml.getMinute();
        int month = xml.getMonth();
        int year = xml.getYear() - 1900;
        int sec = xml.getSecond();

        Date Ret = new Date(year, month, date, hrs, min, sec);
        return Ret;
    }

    public static Date toDate2(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

    public static String formatL(Date D) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - hh:mm");
        return sdf.format(D);
    }

    public static String formatL(XMLGregorianCalendar D) {
        return formatL(toDate(D));
    }

    public static String formatS(Date D) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(D);
    }

    public static String formatS(XMLGregorianCalendar D) {
        return formatS(toDate2(D));
    }
}
