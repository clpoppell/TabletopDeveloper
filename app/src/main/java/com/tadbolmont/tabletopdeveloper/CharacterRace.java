package com.tadbolmont.tabletopdeveloper;

public class CharacterRace{
	private final String name;
	private final String[] statMods;
	private final int[] ageRange= new int[2];
	
	public CharacterRace(String raceName, String[] stats){
		name= raceName;
		statMods= stats;
	}
	
	//region Accessors
	public String getName(){ return name; }
		
	public int[] getAgeRange(){	return ageRange; }
	//endregion
	
	@Override
	public String toString(){
		String s= name+"\n";
		for(int i=0; i<statMods.length; i++){
			if(i < statMods.length-1){ s += statMods[i] + ", "; }
			else{
				s += statMods[i];
				break;
			}
		}
		return s;
	}
}