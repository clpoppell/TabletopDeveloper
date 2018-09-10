package tabletop_5e_character_design.class_features;

import java.util.Arrays;

public class AdvantageClassFeature extends ClassFeature{
	public final String[] advantageOptions;
	
	AdvantageClassFeature(String name, String parentFeature, String values, String desc){
		super(name, parentFeature, desc);
		
		advantageOptions= values.split(", ");
	}
	
	AdvantageClassFeature(String name, String parentFeature, String[] advantageOptions, String desc){
		super(name, parentFeature, desc);
		
		this.advantageOptions= Arrays.copyOf(advantageOptions, advantageOptions.length);
	}
}
