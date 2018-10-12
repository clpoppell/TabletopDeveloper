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
	private static final int ELEMENT_GAINED= "ElementGained: ".length();
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
		switch(feature[2].substring(TYPE)){
			case "Plain Text":
			case "General":
				return new ClassFeature(feature[0].substring(NAME), feature[1].substring(PARENT_FEATURE), feature[feature.length-1].substring(DESC));
			case "Advantage":
				return new AdvantageClassFeature(feature[0].substring(NAME), feature[1].substring(PARENT_FEATURE), feature[3].substring(VALUES), feature[feature.length-1].substring(DESC));
			case "Resistance":
				return new ResistanceClassFeature(feature[0].substring(NAME), feature[1].substring(PARENT_FEATURE), feature[3].substring(VALUES), feature[feature.length-1].substring(DESC));
			case "Status Defense":
				return new StatusDefenseClassFeature(feature[0].substring(NAME), feature[1].substring(PARENT_FEATURE), feature[3].substring(VALUES), feature[feature.length-1].substring(DESC));
			case "Set Value":
				return new ValueSetClassFeature(feature[0].substring(NAME), feature[1].substring(PARENT_FEATURE), feature[3].substring(VALUES), feature[4].substring(SET), feature[feature.length-1].substring(DESC));
			case "Increase":
				return new IncreaseClassFeature(feature[0].substring(NAME), feature[1].substring(PARENT_FEATURE), feature[3].substring(MODIFIED), feature[4].substring(VALUES), feature[feature.length-1].substring(DESC));
			case "Usable":
				return new UsableClassFeature(feature[0].substring(NAME), feature[1].substring(PARENT_FEATURE), feature[3].substring(VALUES), feature[4].substring(ACTION), feature[feature.length-1].substring(DESC));
			case "Option":
				return new OptionClassFeature(feature[0].substring(NAME), feature[1].substring(PARENT_FEATURE), feature[3].substring(VALUES), feature[4].substring(ACTION), feature[feature.length-1].substring(DESC));
			case "Choice":
				return new ChoiceClassFeature(feature[0].substring(NAME), feature[1].substring(PARENT_FEATURE), feature[3].substring(VALUES), feature[feature.length-1].substring(DESC));
			case "Gain Element":
				return new ElementGainClassFeature(feature[0].substring(NAME), feature[1].substring(PARENT_FEATURE), feature[3].substring(ELEMENT_GAINED), feature[4].substring(VALUES), feature[feature.length-1].substring(DESC));
			default: return null;
		}
	}
	
	/**
	 * Creates and returns a copy of this object.
	 * @return copy of this instance
	 */
	public ClassFeature copy(){ return new ClassFeature(name, parentFeature, desc); }
	
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
