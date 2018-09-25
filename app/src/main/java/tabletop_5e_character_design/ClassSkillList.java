package tabletop_5e_character_design;

import java.util.Arrays;

import lombok.Getter;

public class ClassSkillList{
	@Getter private final int numSkills;
	private final String[] skills;
	
	ClassSkillList(int num, String[] list){
		numSkills= num;
		skills= list;
	}
	
	public String[] getSkills(){ return Arrays.copyOf(skills, skills.length); }
}