package com.tadbolmont.tabletopdeveloper;

import android.content.res.Resources;

import java.util.HashMap;
import java.util.Map;

public final class CharacterInfo{
	private static final Resources RES= App.getContext().getResources();
	
	private static final Map<String, CharacterRace> RACE_INFO= createRaceInfo();
	
	private static Map<String, CharacterRace> createRaceInfo(){
		Map<String, CharacterRace> races= new HashMap<>();
		String[] raceDesc= (RES.getString(R.string.race_info)).split("#");
		for(String charRace : raceDesc){
			String key= charRace.substring(0,charRace.indexOf('/')).trim();
			String[] value= charRace.split(" / ");
			String[] statMods= value[1].substring(5).split(", ");
			
			CharacterRace race= new CharacterRace(value[0], statMods);
			
			races.put(key, race);
		}
		return races;
	}
	
	//region List Accessor Methods
	public static CharacterRace getRace(String key){ return RACE_INFO.get(key); }
	//endregion
}
