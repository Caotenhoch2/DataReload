package suso.datareload.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Keyboard.class)
public class KeyMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(at = @At("RETURN"), method = "processF3", cancellable = true)
    public void inputReload(int key, CallbackInfoReturnable<Boolean> cir) {
        switch (key) {

            case 'Q':
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal("F3 + Y = Reload datapacks"));
                return;

            case 'Y':
                MinecraftClient client = MinecraftClient.getInstance();
                if(client.player != null && client.player.networkHandler != null) {
                    client.player.networkHandler.sendChatCommand("reload");
                }
                cir.setReturnValue(true);

        }
    }
}
