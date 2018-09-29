package tabletop_5e_character_design;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public class Archetype{
	private static final int NAME= "Name: ".length();
	
	private String name;
	private SparseArray<String[]> features= new SparseArray<>();
	
	public Archetype(String[] archetypeInfo){
		name= archetypeInfo[0].substring(NAME);
		
		String[] featureStrings= archetypeInfo[2].split(" - ");
		for(String level : featureStrings){
			String[] levelStrings= level.split(": ");
			int key= Integer.parseInt(levelStrings[0]);
			String[] value= levelStrings[1].split(", ");
			features.put(key, value);
		}
	}
	
	public Archetype(Archetype archetype){
		name= archetype.name;
		features= archetype.features;
	}
	public List<String> getFeatureListForLevel(int lvl){ return new ArrayList<>(Arrays.asList(features.get(lvl))); }
	
}
