package tabletop_5e_character_design.class_features;

import java.util.Arrays;

public class ResistanceClassFeature extends ClassFeature{
	public final String[] resistancesGained;
	
	ResistanceClassFeature(String name, String parentFeature, String values, String desc){
		super(name, parentFeature, desc);
		
		resistancesGained= values.split(", ");
	}
	
	ResistanceClassFeature(String name, String parentFeature, String[] resistancesGained, String desc){
		super(name, parentFeature, desc);
		
		this.resistancesGained= Arrays.copyOf(resistancesGained, resistancesGained.length);
	}
	
	@Override
	public ResistanceClassFeature copy(){ return new ResistanceClassFeature(name, parentFeature, resistancesGained, desc); }
}
