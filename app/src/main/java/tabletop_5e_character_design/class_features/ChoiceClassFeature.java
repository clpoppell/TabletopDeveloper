package tabletop_5e_character_design.class_features;

import lombok.Getter;

//TODO Possibly rework to account for multi-part choice features
@Getter
public class ChoiceClassFeature extends ClassFeature{
	String[] choices;
	
	ChoiceClassFeature(String name, String parentFeature, String values, String desc){
		super(name, parentFeature, desc);
		
		choices= values.split(", ");
	}
	
	ChoiceClassFeature(String name, String parentFeature, String[] choices, String desc){
		super(name, parentFeature, desc);
		
		this.choices= choices;
	}
	
	@Override
	public ChoiceClassFeature copy(){ return new ChoiceClassFeature(name, parentFeature, choices, desc); }
}
