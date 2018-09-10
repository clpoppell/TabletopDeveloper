package tabletop_5e_character_design.class_features;

abstract class ActionClassFeature extends ClassFeature{
	String actionToUse;
	
	ActionClassFeature(String name, String parentFeature, String actionToUse, String desc){
		super(name, parentFeature, desc);
		
		this.actionToUse= actionToUse;
	}
}
