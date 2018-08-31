package tabletop_5e_character_design;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

import java.util.ArrayList;
import java.util.List;

public class ClassEquipmentList{
	final String pack;
	final private List<String> weaponEquipment= new ArrayList<>();
	final String tool;
	
	public ClassEquipmentList(String[] equipmentChoices){
		pack= CharMatcher.anyOf("PACK: ").trimLeadingFrom(equipmentChoices[1]);
		weaponEquipment.addAll(Splitter.on(" # ").trimResults(CharMatcher.anyOf("WEAPON: ")).splitToList(equipmentChoices[3]));
		tool= CharMatcher.anyOf("TOOL: ").trimLeadingFrom(equipmentChoices[4]);
	}
	
	public List<String> getWeaponEquipment(){ return new ArrayList<>(weaponEquipment); }
}
