package com.squoshi.irons_spells_js;

import com.squoshi.irons_spells_js.entity.attribute.SpellAttributeBuilderJS;
import com.squoshi.irons_spells_js.events.IronsSpellsJSEvents;
import com.squoshi.irons_spells_js.item.MagicSwordItemBuilderJS;
import com.squoshi.irons_spells_js.item.SpellBookBuilderJS;
import com.squoshi.irons_spells_js.item.StaffItemBuilderJS;
import com.squoshi.irons_spells_js.spell.AbstractSpellWrapper;
import com.squoshi.irons_spells_js.spell.CustomSpell;
import com.squoshi.irons_spells_js.spell.school.CustomSchoolType;
import com.squoshi.irons_spells_js.spell.school.SchoolTypeJSBuilder;
import com.squoshi.irons_spells_js.util.AlchemistCauldronKubeJSRecipes;
import com.squoshi.irons_spells_js.util.ISSKJSUtils;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.BuilderTypeRegistry;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import dev.latvian.mods.kubejs.script.TypeWrapperRegistry;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import io.redspace.ironsspellbooks.entity.mobs.goals.*;
import io.redspace.ironsspellbooks.registries.PotionRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.Potions;

public class IronsSpellsJSPlugin implements KubeJSPlugin {
//	public static final RegistryType<AbstractSpell> SPELL_REGISTRY = RegistryType(SpellRegistry.SPELL_REGISTRY_KEY);
//	public static final RegistryInfo<SchoolType> SCHOOL_REGISTRY = RegistryInfo.of(SchoolRegistry.SCHOOL_REGISTRY_KEY, SchoolType.class);

	@Override
	public void init() {

	}

	@Override
	public void registerBuilderTypes(BuilderTypeRegistry registry) {
		registry.of(SpellRegistry.SPELL_REGISTRY_KEY, reg -> {
			reg.add("basic", CustomSpell.Builder.class, CustomSpell.Builder::new);
		});
		registry.of(SpellRegistry.SCORCH_SPELL, reg -> {
			reg.add("basic", SchoolType.class, SchoolTypeJSBuilder::new);
		});
		registry.of(BuiltInRegistries.ATTRIBUTE, reg -> reg.add("spell", SpellAttributeBuilderJS.class, SpellAttributeBuilderJS::new));
		registry.of(BuiltInRegistries.ATTRIBUTE, reg -> reg.add("irons_spells_js:spell", SpellAttributeBuilderJS.class, SpellAttributeBuilderJS::new));
		registry.of(BuiltInRegistries.ITEM, reg -> reg.add("irons_spells_js:spellbook", SpellBookBuilderJS.class, SpellBookBuilderJS::new));
		registry.of(BuiltInRegistries.ITEM, reg -> reg.add("irons_spells_js:staff", StaffItemBuilderJS.class, StaffItemBuilderJS::new));
		registry.of(BuiltInRegistries.ITEM, reg -> reg.add("irons_spells_js:magic_sword", MagicSwordItemBuilderJS.class, MagicSwordItemBuilderJS::new));
	}

	@Override
	public void registerBindings(BindingRegistry event) {
		event.add("SpellRarity", SpellRarity.class);
		event.add("SchoolRegistry", SchoolRegistry.class);
		event.add("CastType", CastType.class);
		event.add("IronsSpellsParticleHelper", ParticleHelper.class);
		event.add("SpellRegistry", SpellRegistry.class);
		event.add("ItemTags", ItemTags.class);
		event.add("Player", Player.class);
		event.add("SpellData", SpellData.class);
		event.add("Spell", AbstractSpellWrapper.class);
		event.add("ISSAnimationHolder", AnimationHolder.class);
//		event.add("ISSUpdateClient", UpdateClient.class);
		event.add("ISSUtils", Utils.class);
		event.add("TargetEntityCastData", TargetEntityCastData.class);
		event.add("Potions", Potions.class);
		event.add("ISSPotionRegistry", PotionRegistry.class);
		event.add("AlchemistCauldronRecipeBuilder", AlchemistCauldronKubeJSRecipes.AlchemistCauldronRecipeBuilder.class);
		event.add("WizardAttackGoal", WizardAttackGoal.class);
		event.add("WarlockAttackGoal", WarlockAttackGoal.class);
		event.add("WizardRecoverGoal", WizardRecoverGoal.class);
		event.add("WizardSupportGoal", WizardSupportGoal.class);
		event.add("SpellBarrageGoal", SpellBarrageGoal.class);
		event.add("GustDefenseGoal", GustDefenseGoal.class);
		event.add("WispAttackGoal", WispAttackGoal.class);
	}


	@Override
	public void registerTypeWrappers(TypeWrapperRegistry registry) {
		registry.register(ISSKJSUtils.AttributeHolder.class, ISSKJSUtils.AttributeHolder::of);
		registry.register(ISSKJSUtils.SoundEventHolder.class, ISSKJSUtils.SoundEventHolder::of);
		registry.register(ISSKJSUtils.SpellHolder.class, ISSKJSUtils.SpellHolder::of);
		registry.register(ISSKJSUtils.SchoolHolder.class, ISSKJSUtils.SchoolHolder::of);
		registry.register(ISSKJSUtils.DamageTypeHolder.class, ISSKJSUtils.DamageTypeHolder::of);
		registry.register(AbstractSpell.class, o -> {
			if (o instanceof AbstractSpell spell) return spell;
			return SpellRegistry.getSpell(ISSKJSUtils.SpellHolder.of(o).getLocation());
		});
		registry.register(SchoolType.class, o -> {
			if (o instanceof SchoolType school) return school;
			return SchoolRegistry.getSchool(ISSKJSUtils.SchoolHolder.of(o).getLocation());
		});
	}

	@Override
	public void registerEvents(EventGroupRegistry registry) {
		registry.register(IronsSpellsJSEvents.GROUP);
	}
}