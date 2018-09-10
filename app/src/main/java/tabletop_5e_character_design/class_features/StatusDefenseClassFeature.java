package tabletop_5e_character_design.class_features;

import java.util.Arrays;

public class StatusDefenseClassFeature extends ClassFeature{
	final String[] statusDefenses;
	
	StatusDefenseClassFeature(String name, String parentFeature, String values, String desc){
		super(name, parentFeature, desc);
		
		statusDefenses= values.split(", ");
	}
	
	StatusDefenseClassFeature(String name, String parentFeature, String[] statusDefenses, String desc){
		super(name, parentFeature, desc);
		
		this.statusDefenses=Arrays.copyOf(statusDefenses, statusDefenses.length);
	}
}
