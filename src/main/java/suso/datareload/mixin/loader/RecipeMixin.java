package suso.datareload.mixin.loader;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import suso.datareload.Utility;

import java.util.Iterator;
import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeMixin {
    @Inject(
            method = "apply",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    public void error(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci, Map<RecipeType<?>, ImmutableMap.Builder<Identifier, Recipe<?>>> map2, ImmutableMap.Builder<Identifier, Recipe<?>> builder, Iterator<Map<Identifier, JsonElement>> var5, Map.Entry<Identifier, JsonElement> entry, Identifier identifier, RuntimeException var9) {
        Text t = Utility.strToText("- Parsing error loading recipe ", Formatting.RED)
                .append(Utility.strToText(identifier.toString(), Formatting.AQUA))
                .append(Utility.strToText("\n "))
                .append(Utility.strToText(var9.getMessage()));
        Utility.sendMessage(t);
    }
}
