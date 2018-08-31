package com.tadbolmont.tabletopdeveloper;

import java.text.NumberFormat;
import java.text.ParseException;

public class DamageResistance{
	final String damageType;
	final double resistanceLevel;
	
	public DamageResistance(String[] resistenceString){
		damageType= resistenceString[0];
		
		NumberFormat format= NumberFormat.getPercentInstance();
		Number value= null;
		try{
			value= format.parse(resistenceString[1]);
		} catch(ParseException e){
			e.printStackTrace();
		}
		
		resistanceLevel= value.doubleValue();
	}
}
