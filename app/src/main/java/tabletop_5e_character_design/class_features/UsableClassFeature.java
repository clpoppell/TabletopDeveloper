package tabletop_5e_character_design.class_features;

import android.util.SparseIntArray;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

@Getter
public class UsableClassFeature extends ActionClassFeature{
	private static final int UNLIMITED= 0;
	private static final int USES_STRENGTH= -1;
	private static final int USES_DEXTERITY= -2;
	private static final int USES_CONSTITUTION= -3;
	private static final int USES_WISDOM= -4;
	private static final int USES_INTELLIGENCE= -5;
	private static final int USES_CHARISMA= -6;
	
	private int numTimes;
	private SparseIntArray levelNumTimeList= new SparseIntArray();
	
	private int timesLeft= 0;
	
	UsableClassFeature(String name, String parentFeature, String values, String actionToUse, String desc){
		super(name, parentFeature, actionToUse, desc);
		
		String[] valuesList= values.split(" ! ");
		if(valuesList.length > 1){
			for(String value : valuesList){
				String[] valueSplit= value.split(":");
				
				int key= Integer.parseInt(valueSplit[0]);
				int valueInt= parseValue(valueSplit[1]);
				
				levelNumTimeList.put(key, valueInt);
			}
			setNumTimes(levelNumTimeList.keyAt(0));
		}
		else{ numTimes= parseValue(valuesList[0]); }
		
		timesLeft= numTimes;
	}
	
	UsableClassFeature(String name, String parentFeature, String actionToUse, SparseIntArray levelNumTimeList, int numTimes, String desc){
		super(name, parentFeature, actionToUse, desc);
		
		this.levelNumTimeList= levelNumTimeList.clone();
		this.numTimes= numTimes;
		
		timesLeft= numTimes;
	}
	
	public void UseFeature(){}
	
	private int parseValue(String valueString){
		if(StringUtils.isNumeric(valueString)){ return Integer.parseInt(valueString); }
		else{
			switch(valueString){
				case "Str": return USES_STRENGTH;
				case "Dex": return USES_DEXTERITY;
				case "Con": return USES_CONSTITUTION;
				case "Int": return USES_INTELLIGENCE;
				case "Wis": return USES_WISDOM;
				case "Cha": return USES_CHARISMA;
				default: return UNLIMITED;
			}
		}
	}
	
	public void setNumTimes(int classLevel){ numTimes= levelNumTimeList.get(classLevel, numTimes); }
	
	@Override
	public UsableClassFeature copy(){ return new UsableClassFeature(name, parentFeature, actionToUse, levelNumTimeList, numTimes, desc); }
}
