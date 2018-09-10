package tabletop_5e_character_design.class_features;

import android.util.SparseIntArray;

public class IncreaseClassFeature extends ClassFeature{
	final String valueToIncrease;
	private int increaseValue;
	private SparseIntArray levelIncreaseList= new SparseIntArray();
	
	IncreaseClassFeature(String name, String parentFeature, String values, String valueModified, String desc){
		super(name, parentFeature, desc);
		
		String[] valuesList= values.split(" ! ");
		if(valuesList.length > 1){
			for(String value : valuesList){
				String[] valueSplit= value.split(":");
				int key= Integer.parseInt(valueSplit[0]);
				int valueInt= Integer.parseInt(valueSplit[1]);
				
				levelIncreaseList.put(key,valueInt);
			}
			setIncreaseValue(levelIncreaseList.keyAt(0));
		}
		else{ increaseValue= Integer.parseInt(valuesList[0]); }
		
		valueToIncrease= valueModified;
	}
	
	IncreaseClassFeature(String name, String parentFeature, SparseIntArray levelIncreaseList, String valueToIncrease, String desc){
		super(name, parentFeature, desc);
		
		this.levelIncreaseList= levelIncreaseList.clone();
		this.valueToIncrease= valueToIncrease;
	}
	
	public SparseIntArray getLevelIncreaseList(){
		return levelIncreaseList;
	}
	
	public void setIncreaseValue(int classLevel){
		increaseValue= levelIncreaseList.get(classLevel, increaseValue);
	}
}
