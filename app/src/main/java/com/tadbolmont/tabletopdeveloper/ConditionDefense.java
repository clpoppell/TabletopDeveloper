package com.tadbolmont.tabletopdeveloper;

public class ConditionDefense{
	final String condition;
	final String defenseLevel;
	
	public ConditionDefense(String[] defenseString){
		condition= defenseString[0];
		defenseLevel= defenseString[1];
	}
	
	@Override
	public String toString(){
		String s= defenseLevel;
		
		if(defenseLevel.equals("advantage")){ s += " against "; }
		else{ s += " to "; }
		
		s += condition;
		return s.substring(0,1).toUpperCase() + s.substring(1);
	}
}
