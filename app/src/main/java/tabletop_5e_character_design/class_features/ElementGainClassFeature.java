package tabletop_5e_character_design.class_features;

import lombok.Getter;

@Getter
public class ElementGainClassFeature extends ClassFeature{
	String elementGained;
	String[] elements;
	
	ElementGainClassFeature(String name, String parentFeature, String element, String values, String desc){
		super(name, parentFeature, desc);
		
		elementGained= element;
		elements= values.split(", ");
	}
	
	ElementGainClassFeature(String name, String parentFeature, String elementGained, String[] elements, String desc){
		super(name, parentFeature, desc);
		
		this.elementGained= elementGained;
		this.elements= elements;
	}
	
	@Override
	public ElementGainClassFeature copy(){ return new ElementGainClassFeature(name, parentFeature, elementGained, elements, desc); }
}
