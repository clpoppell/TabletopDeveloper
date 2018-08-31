package tabletop_5e_character_design;

import java.util.Arrays;

public class ClassSkillList{
	final int numSkills;
	final private String[] skills;
	
	public ClassSkillList(int num, String[] list){
		numSkills= num;
		
		skills= list;
	}
	
	public String[] getSkills(){ return Arrays.copyOf(skills, skills.length); }
}