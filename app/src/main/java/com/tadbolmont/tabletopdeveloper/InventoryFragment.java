package com.tadbolmont.tabletopdeveloper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tabletop_5e_character_design.PlayerCharacter;
import tabletop_5e_character_design.equipment.Armor;
import tabletop_5e_character_design.equipment.Item;
import tabletop_5e_character_design.equipment.Tool;
import tabletop_5e_character_design.equipment.Weapon;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryFragment extends Fragment{
	PlayerCharacter character= PlayerCharacter.getPlayerCharacter();
	View v;
	
	@BindView(R.id.weapon_list) TableLayout weaponList;
	@BindView(R.id.armor_list) TableLayout armorList;
	@BindView(R.id.tool_list) TableLayout toolList;
	TableLayout otherList;
	
	private Unbinder unbinder;
	
	public InventoryFragment(){}
	
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		v= inflater.inflate(R.layout.fragment_inventory_screen, container, false);
		
		unbinder= ButterKnife.bind(this, v);
		
		populateWeaponList();
		populateArmorList();
		populateToolList();
		//populateOtherItemList();
		
		return v;
	}
	
	@Override
	public void onDestroyView(){
		super.onDestroyView();
		unbinder.unbind();
	}
	
	private void populateWeaponList(){
		Map<String, Integer> weapons= character.getWeaponList();
		
		for(String weapon : weapons.keySet()){
			Weapon w=GameInfo.getWeapon(weapon);
			
			TableRow row=new TableRow(getActivity());
			TextView weaponInfo=new TextView(getActivity());
			
			weaponInfo.setText(w.getName() + " - " + weapons.get(weapon));
			weaponInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			weaponInfo.setTextColor(getResources().getColor(R.color.textColorPrimary));
			
			row.addView(weaponInfo);
			weaponList.addView(row);
		}
	}
	
	private void populateArmorList(){
		Map<String, Integer> armors= character.getArmorList();
		
		for(String armor : armors.keySet()){
			Armor a= GameInfo.getArmor(armor);
			
			TableRow row= new TableRow(getActivity());
			
			TextView armorInfo= new TextView(getActivity());
			
			armorInfo.setText(a.getName() + " - " + armors.get(armor));
			armorInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			armorInfo.setTextColor(getResources().getColor(R.color.textColorPrimary));
			
			row.addView(armorInfo);
			armorList.addView(row);
		}
	}
	
	private void populateToolList(){
		Map<String, Integer> tools= character.getToolItemsList();
		
		for(String tool : tools.keySet()){
			Tool a= GameInfo.getTool(tool);
			
			TableRow row= new TableRow(getActivity());
			
			TextView toolInfo= new TextView(getActivity());
			
			toolInfo.setText(a.getName() + " - " + tools.get(tool));
			toolInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			toolInfo.setTextColor(getResources().getColor(R.color.textColorPrimary));
			
			row.addView(toolInfo);
			toolList.addView(row);
		}
	}
	
	private void populateOtherItemList(){
		Map<String, Integer> items=character.getMiscItemsList();
		
		for(String item : items.keySet()){
			Item i= null;
			
			TableRow row= new TableRow(getActivity());
			
			TextView itemInfo= new TextView(getActivity());
			
			itemInfo.setText(i.getName() + " - " + items.get(item));
			itemInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			itemInfo.setTextColor(getResources().getColor(R.color.textColorPrimary));
			
			row.addView(itemInfo);
			otherList.addView(row);
		}
	}
}