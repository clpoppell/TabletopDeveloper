package tabletop_5e_character_design.class_features;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassFeature{
	private static final int NAME= "Name: ".length();
	private static final int PARENT_FEATURE= "ParentFeature: ".length();
	private static final int TYPE= "Type: ".length();
	private static final int VALUES= "Values: ".length();
	private static final int MODIFIED= "ValueModified: ".length();
	private static final int SET= "ValueSet: ".length();
	private static final int ACTION= "ActionToUse: ".length();
	
	private static final int DESC= 6;

	private final List<ClassFeature> childFeatures= new ArrayList<>();
	
	public final String name;
	public final String parentFeature;
	public final String desc;
	
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
		
		return null;
	}
	
	public  static ClassFeature getClassFeature(ClassFeature feature){
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
		else{
			return new ClassFeature(feature.name, feature.parentFeature, feature.desc);
		}
	}
	
	public void addChildFeature(ClassFeature childFeature){
		if(!childFeatures.contains(childFeature)){ childFeatures.add(childFeature); }
	}
	
	public List<ClassFeature> getChildFeatures(){ return childFeatures; }
	
	@Override
	public boolean equals(Object o){
		if(o == this){ return true; }
		if(!(o instanceof ClassFeature)){ return false; }
		
		ClassFeature c= (ClassFeature)o;
		return c.name.equalsIgnoreCase(this.name);
	}
	
	@Override
	public int hashCode(){ return Objects.hash(name); }
	
	@Override
	public String toString(){
		StringBuilder s= new StringBuilder(name);
		s.append("\n").append(desc);
		for(ClassFeature child : childFeatures){
			s.append("\n\t").append(child.toString());
		}
		return s.toString();
	}
}
