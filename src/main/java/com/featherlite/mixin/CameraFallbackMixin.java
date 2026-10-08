package com.featherlite.mixin;

import com.featherlite.Features;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Backup: set the camera rotation directly if the player angle hook did not run. */
@Mixin(Camera.class)
public abstract class CameraFallbackMixin {
    @Shadow
    protected abstract void setRotation(float yaw, float pitch);

    @Inject(method = "update", at = @At("TAIL"))
    private void featherlite$tail(CallbackInfo ci) {
        if (Features.needFallback()) {
            Features.hook("camera-fallback");
            this.setRotation(Features.flYaw, Features.flPitch);
        }
    }
}
