package cn.zeroall.cow.web.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{

    public static final String yyMMdd="yy-MM-dd";
    public static final String MMdd="MM-dd";
    public static final String yyyyMMdd="yyyy-MM-dd";
    public static final String HHmmss="HH:mm:ss";
    public static final String yyyyMMddHHmmss="yyyy-MM-dd hh:mm:ss";
    public static final String yyMMddHHmmss="yy-MM-dd HH:mm:ss";

    public static Date parseToDate(String s, String style)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(style);
        Date date = null;
        if(s==null||s.length()<8)
        {
            return null;
        }
        try 
        {
            date = simpleDateFormat.parse(s);
        }
        catch (ParseException e) 
        {
            e.printStackTrace();
        }
        return date;
    }

    public static String parseToString(String s, String style)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(style);
        Date date = null;
        String str=null;
        if(s==null||s.length()<8)
            return null;
        try
        {
            date = simpleDateFormat.parse(s);
            str=simpleDateFormat.format(date);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return str;
    }

    public static String parseToString(Date date, String style)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(style);
        String str=null;
        if(date==null)
            return null;
        str=simpleDateFormat.format(date);
        return str;
    }

}
