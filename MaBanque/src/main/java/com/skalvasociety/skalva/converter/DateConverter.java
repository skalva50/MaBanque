package com.skalvasociety.skalva.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConverter {	
	public Date stringToDate(String sDate, String formatDate) throws ParseException{
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat(formatDate);
		date = formatter.parse(sDate);
		return date;	
	}
	
	public String dateToString(Date date, String formatDate){
		String sDate = null;
		
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);        
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (formatDate.equals("yyyy-MM-dd")){
        	sDate = year+"-"+month+"-"+day; 
        }
        return sDate;        
	}
	
	/**
	 * Transforme une date en string en ne tenant pas compte du jour (01 par defaut pour le jour)
	 * @param date
	 * @param formatDate
	 * @return
	 */
	public String dateToStringMonth(Date date, String formatDate){
		String sDate = null;
		
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);        
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);
        int day = 1;
        if (formatDate.equals("yyyy-MM-dd")){
        	sDate = year+"-"+month+"-"+day; 
        }
        return sDate;        
	}
	
	public String dateToStringAddMonth(Date date, int nbMonth, String formatDate){
		String sDate = null;
		
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);      
        calendar.add(Calendar.MONTH, nbMonth);
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (formatDate.equals("yyyy-MM-dd")){
        	sDate = year+"-"+month+"-"+day; 
        }
        return sDate;        
	}
}
