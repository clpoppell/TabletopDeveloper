package tabletop_5e_character_design.class_features;

import android.util.SparseIntArray;

import lombok.Getter;

@Getter
public class IncreaseClassFeature extends ClassFeature{
	String valueToIncrease;
	int increaseValue;
	SparseIntArray levelIncreaseList= new SparseIntArray();
	
	IncreaseClassFeature(String name, String parentFeature, String valueModified, String values, String desc){
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
	
	IncreaseClassFeature(String name, String parentFeature, String valueToIncrease, SparseIntArray levelIncreaseList, String desc){
		super(name, parentFeature, desc);
		
		this.levelIncreaseList= levelIncreaseList.clone();
		this.valueToIncrease= valueToIncrease;
	}
	
	private void setIncreaseValue(int classLevel){ increaseValue= levelIncreaseList.get(classLevel, increaseValue); }
	
	@Override
	public IncreaseClassFeature copy(){ return new IncreaseClassFeature(name, parentFeature, valueToIncrease, levelIncreaseList, desc); }
}
