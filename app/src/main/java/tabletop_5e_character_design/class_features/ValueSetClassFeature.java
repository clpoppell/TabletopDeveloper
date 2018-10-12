package tabletop_5e_character_design.class_features;

import lombok.Getter;

@Getter
public class ValueSetClassFeature extends ClassFeature{
	private String valueToSet;
	private String value;
	
	ValueSetClassFeature(String name, String parentFeature, String values, String valueSet, String desc){
		super(name, parentFeature, desc);
		
		value= values;
		valueToSet= valueSet;
	}
	
	@Override
	public ValueSetClassFeature copy(){ return new ValueSetClassFeature(name, parentFeature, value, valueToSet, desc); }
}