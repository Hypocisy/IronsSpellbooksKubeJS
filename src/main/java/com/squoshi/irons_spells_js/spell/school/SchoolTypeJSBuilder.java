package com.squoshi.irons_spells_js.spell.school;

import com.squoshi.irons_spells_js.spell.CustomSpell;
import com.squoshi.irons_spells_js.util.ISSKJSUtils;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.typings.Info;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;

@SuppressWarnings("unused")
public class SchoolTypeJSBuilder extends BuilderBase<SchoolType> {
	public transient ResourceLocation schoolResource;
	public transient TagKey<Item> focus;
	public transient Component name;
	public transient Holder<Attribute> powerAttribute;
	public transient Holder<Attribute> resistanceAttribute;
	public transient Holder<SoundEvent> defaultCastSound;
	public transient ResourceKey<DamageType> damageType;

	public SchoolTypeJSBuilder(ResourceLocation i) {
		super(i);
		this.schoolResource = i;
	}

	@Info("""
			        Sets the ID of the item tag used for the focus item.
			        Focus items need the `"irons_spellbooks:school_focus"` tag, as well as the tag specified here.
			""")
	public SchoolTypeJSBuilder setFocus(ResourceLocation focus) {
		this.focus = ItemTags.create(focus);
		return this;
	}

	@Info("""
			        Sets the name of the school. It requires a `Component`, which allows custom colors and formatting. You can also use `Text`.
			""")
	public SchoolTypeJSBuilder setName(Component name) {
		this.name = name;
		return this;
	}

	@Info("""
			        Sets the power attribute of the school. It takes either a String, ResourceLocation, or just an Attribute.
			""")
	public SchoolTypeJSBuilder setPowerAttribute(ISSKJSUtils.AttributeHolder powerAttribute) {
		this.powerAttribute = BuiltInRegistries.ATTRIBUTE.getHolder(powerAttribute.getLocation()).orElseThrow();
		return this;
	}

	@Info("""
			        Sets the resistance attribute of the school. It takes either a String, ResourceLocation, or just an Attribute.
			""")
	public SchoolTypeJSBuilder setResistanceAttribute(ISSKJSUtils.AttributeHolder resistanceAttribute) {
		this.resistanceAttribute = BuiltInRegistries.ATTRIBUTE.getHolder(resistanceAttribute.getLocation()).orElseThrow();
		return this;
	}

	@Info("""
			        Sets the default cast sound of the school. It takes either a String, ResourceLocation, or just a SoundEvent.
			""")
	public SchoolTypeJSBuilder setDefaultCastSound(ISSKJSUtils.SoundEventHolder defaultCastSound) {
		this.defaultCastSound = BuiltInRegistries.SOUND_EVENT.getHolder(defaultCastSound.getLocation()).orElseThrow();
		return this;
	}

	@Info("""
			        Sets the damage type of the school. It takes either a String, ResourceLocation, or just a DamageType.
			        Damage types can be created using datapacks or server scripts, or you can use an existing damage type.
			""")
	public SchoolTypeJSBuilder setDamageType(ISSKJSUtils.DamageTypeHolder damageType) {
		this.damageType = ResourceKey.create(Registries.DAMAGE_TYPE, damageType.getLocation());
		return this;
	}

	@Override
	public SchoolType createObject() {
		return new SchoolType(
				this.schoolResource,
				this.focus,
				this.name,
				this.powerAttribute,
				this.resistanceAttribute,
				this.defaultCastSound,
				this.damageType
		);
	}
}
