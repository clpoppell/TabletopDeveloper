package tabletop_5e_character_design.class_features;

import java.util.LinkedHashSet;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true) @Getter
public class ClassFeature{
	private static final int NAME= "Name: ".length();
	private static final int PARENT_FEATURE= "ParentFeature: ".length();
	private static final int TYPE= "Type: ".length();
	private static final int VALUES= "Values: ".length();
	private static final int MODIFIED= "ValueModified: ".length();
	private static final int SET= "ValueSet: ".length();
	private static final int ACTION= "ActionToUse: ".length();
	
	private static final int DESC= 6;

	private Set<ClassFeature> childFeatures= new LinkedHashSet<>();
	
	@EqualsAndHashCode.Include String name;
	String parentFeature;
	String desc;
	
	ClassFeature(String name, String parentFeature, String desc){
		this.name= name;
		this.parentFeature= parentFeature;
		this.desc= desc;
	}
	
	public static ClassFeature getClassFeature(String[] feature){
		String type= feature[2].substring(TYPE);
		
		if("Plain Text".equalsIgnoreCase(type)){ return new ClassFeature(feature[0].substring(NAME), feature[1].substring(PARENT_FEATURE), feature[feature.length-1].substring(DESC)); }
		if("Advantage".equalsIgnoreCase(type)){ return new AdvantageClassFeature(feature[0].substring(NAME), feature[1].substring(PARENT_FEATURE), feature[3].substring(VALUES), feature[feature.length-1].substring(DESC)); }
		if("Resistance".equalsIgnoreCase(type)){ return new ResistanceClassFeature(feature[0].substring(NAME), feature[1].substring(PARENT_FEATURE), feature[3].substring(VALUES), feature[feature.length-1].substring(DESC)); }
		if("Status Defense".equalsIgnoreCase(type)){ return new StatusDefenseClassFeature(feature[0].substring(NAME), feature[1].substring(PARENT_FEATURE), feature[3].substring(VALUES), feature[feature.length-1].substring(DESC)); }
		if("Set Value".equalsIgnoreCase(type)){ return new ValueSetClassFeature(feature[0].substring(NAME), feature[1].substring(PARENT_FEATURE), feature[3].substring(VALUES), feature[4].substring(SET), feature[feature.length-1].substring(DESC)); }
		if("Increase".equalsIgnoreCase(type)){ return new IncreaseClassFeature(feature[0].substring(NAME), feature[1].substring(PARENT_FEATURE), feature[3].substring(VALUES), feature[4].substring(MODIFIED), feature[feature.length-1].substring(DESC)); }
		if("Usable".equalsIgnoreCase(type)){ return new UsableClassFeature(feature[0].substring(NAME), feature[1].substring(PARENT_FEATURE), feature[3].substring(VALUES), feature[4].substring(ACTION), feature[feature.length-1].substring(DESC)); }
		if("Option".equalsIgnoreCase(type)){ return new OptionClassFeature(feature[0].substring(NAME), feature[1].substring(PARENT_FEATURE), feature[3].substring(VALUES), feature[4].substring(ACTION), feature[feature.length-1].substring(DESC)); }
		if("Choice".equalsIgnoreCase(type)){ return new ChoiceClassFeature(feature[0].substring(NAME), feature[1].substring(PARENT_FEATURE), feature[3].substring(VALUES), feature[feature.length-1].substring(DESC)); }
		if("Archetype".equalsIgnoreCase(type)){ return new ArchetypeClassFeature(feature[0].substring(NAME)); }
		
		return null;
	}
	
	public  static ClassFeature getClassFeature(ClassFeature feature){
		//noinspection IfStatementWithTooManyBranches
		if(feature instanceof AdvantageClassFeature){
			return new AdvantageClassFeature(feature.name, feature.parentFeature, ((AdvantageClassFeature)feature).advantageOptions, feature.desc);
		}
		else if(feature instanceof ResistanceClassFeature){
			return new ResistanceClassFeature(feature.name, feature.parentFeature, ((ResistanceClassFeature) feature).resistancesGained, ((ResistanceClassFeature) feature).desc);
		}
		else if(feature instanceof StatusDefenseClassFeature){
			return new StatusDefenseClassFeature(feature.name, feature.parentFeature, ((StatusDefenseClassFeature) feature).statusDefenses, feature.desc);
		}
		else if(feature instanceof ValueSetClassFeature){
			return new ValueSetClassFeature(feature.name, feature.parentFeature, ((ValueSetClassFeature) feature).value, ((ValueSetClassFeature)feature).valueToSet, ((ValueSetClassFeature) feature).desc);
		}
		else if(feature instanceof IncreaseClassFeature){
			return new IncreaseClassFeature(feature.name, feature.parentFeature, ((IncreaseClassFeature) feature).getLevelIncreaseList(), ((IncreaseClassFeature) feature).valueToIncrease, feature.desc);
		}
		else if(feature instanceof UsableClassFeature){
			return new UsableClassFeature(feature.name, feature.parentFeature, ((UsableClassFeature)feature).actionToUse, ((UsableClassFeature)feature).getLevelNumTimeList(), ((UsableClassFeature)feature).getNumTimes(), feature.desc);
		}
		else if(feature instanceof OptionClassFeature){
			return new OptionClassFeature(feature.name, feature.parentFeature, ((OptionClassFeature) feature).actionToUse, ((OptionClassFeature) feature).optionsGained, feature.desc);
		}
		else if(feature instanceof ChoiceClassFeature){
			return new ChoiceClassFeature(feature.name, feature.parentFeature, ((ChoiceClassFeature)feature).choices, feature.desc);
		}
		else if(feature instanceof ArchetypeClassFeature){
			return new ArchetypeClassFeature(feature.name);
		}
		else{
			return new ClassFeature(feature.name, feature.parentFeature, feature.desc);
		}
	}
	
	public void addChildFeature(ClassFeature childFeature){ childFeatures.add(childFeature); }
	
	@Override
	public String toString(){
		StringBuilder s= new StringBuilder(name);
		s.append("\n").append(desc);
		for(ClassFeature child : childFeatures){
			String childName= child.getName();
			if(childName.contains("(")){
				childName= childName.substring(0, childName.indexOf("("));
			}
			if(!(childName.length() == 0)){ s.append("\n\t").append(child.toString()); }
		}
		s.append("\n");
		return s.toString();
	}
}
