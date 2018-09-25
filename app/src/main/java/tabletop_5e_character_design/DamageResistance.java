package tabletop_5e_character_design;

import com.tadbolmont.tabletopdeveloper.GameInfo;

import java.text.NumberFormat;
import java.text.ParseException;

public class DamageResistance{
	private GameInfo.damageType damageType;
	public final double resistanceLevel;
	
	DamageResistance(String[] resistenceString){
		try{ damageType= GameInfo.damageType.valueOf(resistenceString[0]); }
		catch(IllegalArgumentException e){ damageType= GameInfo.damageType.NOTFOUND; }
		
		NumberFormat format= NumberFormat.getPercentInstance();
		Number value= 0;
		try{ value= format.parse(resistenceString[1]); }
		catch(ParseException e){ e.printStackTrace(); }
		
		resistanceLevel= value.doubleValue();
	}
	
	public GameInfo.damageType getDamageType(){ return damageType; }
}
