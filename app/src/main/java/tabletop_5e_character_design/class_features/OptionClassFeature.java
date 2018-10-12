package tabletop_5e_character_design.class_features;

import java.util.Arrays;

import lombok.Getter;

@Getter
public class OptionClassFeature extends ActionClassFeature{
	private String[] optionsGained;
	
	OptionClassFeature(String name, String parentFeature, String values, String actionToUse, String desc){
		super(name, parentFeature, actionToUse, desc);
		
		optionsGained= values.split(", ");
	}
	
	OptionClassFeature(String name, String parentFeature, String actionToUse, String[] optionsGained, String desc){
		super(name, parentFeature, actionToUse, desc);
		
		this.optionsGained=Arrays.copyOf(optionsGained, optionsGained.length);
	}
	
	public OptionClassFeature copy(){ return new OptionClassFeature(name, parentFeature, actionToUse, optionsGained, desc); }
}
