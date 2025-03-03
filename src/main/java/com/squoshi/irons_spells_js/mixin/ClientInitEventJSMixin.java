package com.squoshi.irons_spells_js.mixin;

import com.squoshi.irons_spells_js.util.ClientInitISSKJS;
import dev.latvian.mods.kubejs.client.KubeJSClient;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(KubeJSClient.class)
public class ClientInitEventJSMixin implements ClientInitISSKJS {
}