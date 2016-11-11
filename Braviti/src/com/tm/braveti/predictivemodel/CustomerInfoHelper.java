package com.tm.braveti.predictivemodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.tm.braveti.model.User;
import com.tm.braveti.service.LoadStaticData;

public class CustomerInfoHelper {

	public static User getUser(String username, LoadStaticData staticData){
		List<User> users = staticData.getUsers();
		for (User user : users) {
			if (user.getFname().equals(username)){
				return user;
			}
		}
		return null;
		
	}
	
	public static boolean checkForBithdayWeek(User user){
//		check month and date only  not year
		String userDob=user.getDob();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		try {
		    Date userDateOfBirth = df.parse(userDob);
		    Calendar cal=Calendar.getInstance();
		    cal.setTime(userDateOfBirth);
		    int month = cal.get(Calendar.MONTH)+1;
	        int day = cal.get(Calendar.DAY_OF_MONTH);
	        
		    Calendar cal2=Calendar.getInstance();
		    int currentMonth = cal2.get(Calendar.MONTH)+1;
	        int CurrentDay = cal2.get(Calendar.DAY_OF_MONTH);
	        
		    if(currentMonth==month&&(day-CurrentDay)<=7)
		    	
		    {
		    	return true;
		    }
		    else {
		    	return false;
		    }
		} catch (ParseException e) {
			
			System.out.println("caught exception while parsing date of birth" +e);
		}
		return false;
	}
	
		
}
