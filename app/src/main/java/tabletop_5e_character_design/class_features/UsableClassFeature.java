package tabletop_5e_character_design.class_features;

import android.util.SparseIntArray;

class UsableClassFeature extends ActionClassFeature{
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
				int valueInt= Integer.parseInt(valueSplit[1]);
				
				levelNumTimeList.put(key, valueInt);
			}
			setNumTimes(levelNumTimeList.keyAt(0));
		}
		else{ numTimes= Integer.parseInt(valuesList[0]); }
		
		timesLeft= numTimes;
	}
	
	UsableClassFeature(String name, String parentFeature, String actionToUse, SparseIntArray levelNumTimeList, int numTimes, String desc){
		super(name, parentFeature, actionToUse, desc);
		
		this.levelNumTimeList= levelNumTimeList.clone();
		this.numTimes= numTimes;
		
		timesLeft= numTimes;
	}
	
	public void UseFeature(){}
	
	public SparseIntArray getLevelNumTimeList(){ return levelNumTimeList; }
	
	public int getNumTimes(){ return numTimes; }
	
	public void setNumTimes(int classLevel){ numTimes= levelNumTimeList.get(classLevel, numTimes); }
}
