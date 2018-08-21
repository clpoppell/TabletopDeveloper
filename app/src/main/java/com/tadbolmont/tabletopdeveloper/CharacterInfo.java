package com.tadbolmont.tabletopdeveloper;

import android.content.res.Resources;

import java.util.HashMap;
import java.util.Map;

public final class CharacterInfo{
	private static final Resources RES= App.getContext().getResources();
	
	private static final Map<String, CharacterRace> RACE_INFO= createRaceInfo();
	
	private static Map<String, CharacterRace> createRaceInfo(){
		Map<String, CharacterRace> races= new HashMap<>();
		String[] raceDesc= RES.getStringArray(R.array.race_info);
		for(String charRace : raceDesc){
			String[] raceFeatures= charRace.split(" / ");
			CharacterRace race= new CharacterRace(raceFeatures);
			races.put(raceFeatures[0].trim(), race);
		}
		return races;
	}
	
	//region List Accessor Methods
	public static CharacterRace getRace(String key){ return RACE_INFO.get(key); }
	//endregion
}
