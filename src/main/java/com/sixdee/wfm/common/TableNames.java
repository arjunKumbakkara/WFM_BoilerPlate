package com.sixdee.wfm.common;

import org.springframework.stereotype.Component;

/**
 * @author Arjun Kumbakkara
 * @version 1.0.0
 * @Sixdee
 */

@Component
public class TableNames {

	/* Schema Prefix */
	public static final String prefix = "WFM_";

	/* Work Force Management Tables */
	public static final String Departments = prefix + "Departments";
	public static final String Sections = prefix + "Sections";
	public static final String Resources = prefix + "Resources";
	public static final String Users = prefix + "users";
	public static final String Roles = prefix + "roles";
	public static final String Logins = prefix + "logins";

	public static final String Skills = prefix + "Skills";
	public static final String SkillLevelDesignationMapper = prefix + "SkillLevelDesignationMapper";
	public static final String Levels = prefix + "Level";
	public static final String Task = prefix + "Tasks";
	public static final String Category = prefix + "Categories";
	public static final String CategoryProjectMapping = prefix + "CategoryProjectMapping";
	public static final String Question = prefix + "Questions";
	public static final String Project = prefix + "Projects";
	public static final String ProjectTaskSequence = prefix + "ProjectTaskSequenceJunction";

	public static final String Locations = prefix + "Locations";
	public static final String Country = prefix + "Countries";
	public static final String State = prefix + "States";
	public static final String City = prefix + "Cities";
	public static final String Community = prefix + "Communities";
	public static final String Designations = prefix + "Designations";
	public static final String Shifts = prefix + "Shifts";

	public static final String Validations = prefix + "Validation";
	public static final String RegularExpression = prefix + "RegExp";
	public static final String ErrorCodes = prefix + "ErrorCodes";
	
	
	
	/*Junction tables in the System*/
	public static final String ResourceToSkillJunction = prefix + "ResourceToSkillJunction";
	

	public static final String FleetManagement = prefix + "FleetManagement";
	public static final String VehicleType = prefix + "VehicleType";
	public static final String FleetManagementStatus = prefix + "FleetManagementStatus";

}
