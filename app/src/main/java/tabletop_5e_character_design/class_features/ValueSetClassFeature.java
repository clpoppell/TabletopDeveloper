package tabletop_5e_character_design.class_features;

class ValueSetClassFeature extends ClassFeature{
	public final String valueToSet;
	public final String value;
	
	ValueSetClassFeature(String name, String parentFeature, String values, String valueSet, String desc){
		super(name, parentFeature, desc);
		
		value= values;
		valueToSet= valueSet;
	}
}
