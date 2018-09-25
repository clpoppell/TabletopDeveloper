package tabletop_5e_character_design;

import java.util.ArrayList;
import java.util.List;

public class ClassEquipmentList{
	private static final int PACK= "Pack: ".length();
	private static final int ARMOR= "Armor: ".length();
	private static final int WEAPON= "Weapon: ".length();
	private static final int TOOL= "Tool: ".length();
	
	public final String pack;
	public final String armor;
	final private List<String> weaponEquipment= new ArrayList<>();
	public final String tool;
	
	public ClassEquipmentList(String[] equipmentChoices){
		pack= equipmentChoices[1].substring(PACK);
		armor= equipmentChoices[2].substring(ARMOR);
		String[] weaponStrings= equipmentChoices[3].split(" # ");
		for(String w : weaponStrings){ weaponEquipment.add(w.substring(WEAPON)); }
		tool= equipmentChoices[4].substring(TOOL);
	}
	
	public List<String> getWeaponEquipment(){ return new ArrayList<>(weaponEquipment); }
}
