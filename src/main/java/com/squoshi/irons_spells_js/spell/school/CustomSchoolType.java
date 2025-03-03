package com.squoshi.irons_spells_js.spell.school;

import io.redspace.ironsspellbooks.api.spells.SchoolType;

public class CustomSchoolType extends SchoolType {
	public CustomSchoolType(SchoolTypeJSBuilder builder) {
		super(builder.id, builder.focus, builder.displayName, builder.powerAttribute, builder.resistanceAttribute, builder.defaultCastSound, builder.damageType);
	}

}
