package com.squoshi.irons_spells_js.events;

import com.squoshi.irons_spells_js.util.ISpellModify;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

import static com.squoshi.irons_spells_js.IronsSpellsJSMod.MODID;
import static com.squoshi.irons_spells_js.events.SpellModificationEventJS.getOrCreate;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class ServerEvents {
	@SubscribeEvent
	public static void onSpellRegistry(ServerStartedEvent event) {
		SpellRegistry.getEnabledSpells().forEach(spell -> {
			if (spell instanceof ISpellModify spellModify) {
				if (IronsSpellsJSEvents.modifySpell.hasListeners()) {
					var eventJS = getOrCreate(spell.getSpellResource());
					spellModify.irons_spells_js$setBuilder(spell.getSpellResource());
					IronsSpellsJSEvents.modifySpell.post(eventJS);
				}
			}
		});
	}
}
