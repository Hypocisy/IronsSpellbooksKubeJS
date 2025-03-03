package com.squoshi.irons_spells_js.item;

import com.squoshi.irons_spells_js.util.ISSKJSUtils;
import dev.latvian.mods.kubejs.item.custom.HandheldItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.*;

@SuppressWarnings("unused")
public class MagicSwordItemBuilderJS extends HandheldItemBuilder {
	public transient List<AttributeHolder> additionalAttributes = new ArrayList<>();
	public transient List<SpellHolder> spellHolders = new ArrayList<>();

	public MagicSwordItemBuilderJS(ResourceLocation i) {
		super(i, 3f, -2.4f);
	}

	@Info("""
			        Adds a spell to the default spell list of the item. It takes a spell ID (or just a spell object), and the spell level as an integer.
			""")
	public MagicSwordItemBuilderJS addDefaultSpell(ISSKJSUtils.SpellHolder spell, int spellLevel) {
		this.spellHolders.add(new SpellHolder(spell.getLocation(), spellLevel));
		return this;
	}

	@Info("""
			        Adds an additional attribute to the item. It takes an attribute ID (or just an attribute object), the modifier name, the modifier amount, and the modifier operation.
			        The modifier operation can be either `ADDITION`, `MULTIPLY_TOTAL` or `MULTIPLY_BASE`.
			""")
	public MagicSwordItemBuilderJS addAdditionalAttribute(ISSKJSUtils.AttributeHolder attribute, String modifierName, double modifierAmount, AttributeModifier.Operation modifierOperation) {
		additionalAttributes.add(new AttributeHolder(attribute.getLocation(), new AttributeModifier(Objects.requireNonNull(ResourceLocation.tryParse(modifierName)), modifierAmount, modifierOperation)));
		return this;
	}

	@Override
	public MagicSwordItem createObject() {
		var builder = ItemAttributeModifiers.builder();
		for (AttributeHolder holder : additionalAttributes) {
			final Holder<Attribute> attribute = BuiltInRegistries.ATTRIBUTE.getHolder(holder.attribute()).orElseThrow();
			builder.add(attribute, holder.modifier, EquipmentSlotGroup.ANY);
		}
		SpellDataRegistryHolder[] spellDataHolders = new SpellDataRegistryHolder[this.spellHolders.size()];
		var iterator = spellHolders.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			var spells = iterator.next();
			spellDataHolders[i] = new SpellDataRegistryHolder(DeferredHolder.create(spells.spell, SpellRegistry.REGISTRY.getHolder(spells.spell).orElseThrow().value().getSpellResource()), spells.spellLevel);
		}
		return new MagicSwordItem(this.toolTier, this.createItemProperties().attributes(builder.build()), spellDataHolders);
	}

	public record AttributeHolder(ResourceLocation attribute, AttributeModifier modifier) {
	}

	public record SpellHolder(ResourceLocation spell, int spellLevel) {
	}
}
