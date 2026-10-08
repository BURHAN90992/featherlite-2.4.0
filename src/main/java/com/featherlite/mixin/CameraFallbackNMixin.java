package com.featherlite.mixin;

import com.featherlite.Features;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** CameraFallbackMixin with raw intermediary names. */
@Mixin(Camera.class)
public abstract class CameraFallbackNMixin {
    @Shadow(remap = false)
    protected abstract void method_19325(float yaw, float pitch);

    @Inject(method = "method_19321", at = @At("TAIL"), remap = false)
    private void featherlite$tail(CallbackInfo ci) {
        if (Features.needFallback()) {
            Features.hook("camera-fallback");
            this.method_19325(Features.flYaw, Features.flPitch);
        }
    }
}
