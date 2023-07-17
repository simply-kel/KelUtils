package ru.simplykel.kelutils.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import ru.simplykel.kelutils.client.config.F3Config;

import java.util.List;

@Mixin(value = DebugHud.class, priority = 900)
public class DebugHudMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(
            method = "drawLeftText",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    shift = At.Shift.AFTER,
                    ordinal = 2
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void onRenderLeftText(DrawContext context, CallbackInfo ci, List<String> lines) {
        if (F3Config.DISABLE_ACTIVE_RENDERER) {
            lines.removeIf(s -> s.startsWith("[Fabric] Active renderer:"));
        }

        if (F3Config.DISABLE_DEBUG_HINTS) {
            lines.removeIf(s -> s.contains("Debug: Pie"));
        }

        if (F3Config.DISABLE_HELP_SHORTCUT) {
            lines.removeIf(s -> s.equals("For help: press F3 + Q"));
        }

        if (F3Config.DISABLE_IRIS) {
            lines.removeIf(s -> s.startsWith("[Iris]"));
            lines.removeIf(s -> s.startsWith("[Entity Batching]"));
        }

        if (F3Config.DISABLE_LITEMATICA) {
            lines.removeIf(s -> s.startsWith("§6[Litematica]§r"));
        }

        if (F3Config.DISABLE_ENTITY_CULLING) {
            lines.removeIf(s -> s.startsWith("[Culling]"));
        }

        if (F3Config.DISABLE_VIA_FABRIC) {
            lines.removeIf(s -> s.startsWith("[ViaFabric]"));
        }
    }

    @ModifyVariable(method = "drawRightText", at = @At(value = "STORE"))
    private List<String> modifyLines(List<String> lines) {

        // using old switch without break on purpose
        switch (F3Config.HARDWARE_MODE) {
            case "hide": {
                lines.removeIf(s -> s.startsWith("Java:"));
                lines.removeIf(s -> s.startsWith("Mem:"));
                lines.removeIf(s -> s.startsWith("Allocation rate:"));
                lines.removeIf(s -> s.startsWith("Allocated:"));
                lines.removeIf(s -> s.startsWith("Off-Heap:"));
                lines.removeIf(s -> s.startsWith("Direct Buffers:"));
            }
            case "reduced": {
                var cpuIndex = -1;

                for (var i = 0; i < lines.size(); i++) {
                    if (lines.get(i).startsWith("CPU: ")) {
                        cpuIndex = i;
                        break;
                    }
                }

                if (cpuIndex != -1) {
                    lines.subList(cpuIndex, cpuIndex + 5).clear();
                }
            }
        }

        if(F3Config.DISABLE_TARGET_BLOCK){
            lines.removeIf(s -> s.contains("Targeted Block:"));
            lines.removeIf(s -> s.startsWith("minecraft:"));
            lines.removeIf(s -> s.startsWith("snowy:"));
            lines.removeIf(s -> s.startsWith("Primitive"));
            lines.removeIf(s -> s.startsWith("default:"));
            lines.removeIf(s -> s.startsWith("PF Sounds"));
            lines.removeIf(s -> s.startsWith("wet:"));
            lines.removeIf(s -> s.startsWith("PF Prims"));
            lines.removeIf(s -> s.startsWith("facing:"));
            lines.removeIf(s -> s.startsWith("half:"));
            lines.removeIf(s -> s.startsWith("shape:"));
            lines.removeIf(s -> s.startsWith("waterlogged:"));
            lines.removeIf(s -> s.startsWith("layers:"));
            lines.removeIf(s -> s.startsWith("messy:"));
            lines.removeIf(s -> s.startsWith("foliage:"));
            lines.removeIf(s -> s.startsWith("east:"));
            lines.removeIf(s -> s.startsWith("north:"));
            lines.removeIf(s -> s.startsWith("south:"));
            lines.removeIf(s -> s.startsWith("west:"));
            lines.removeIf(s -> s.startsWith("age:"));
            lines.removeIf(s -> s.endsWith("hardmetal"));
            lines.removeIf(s -> s.startsWith("in_wall:"));
            lines.removeIf(s -> s.startsWith("open:"));
            lines.removeIf(s -> s.startsWith("powered:"));
            lines.removeIf(s -> s.startsWith("hinge:"));
            lines.removeIf(s -> s.startsWith("persistent:"));
            lines.removeIf(s -> s.startsWith("distance:"));
            lines.removeIf(s -> s.startsWith("up:"));
            lines.removeIf(s -> s.startsWith("axis:"));
            //
            lines.removeIf(s -> s.startsWith("falling:"));
            lines.removeIf(s -> s.startsWith("type:"));
            lines.removeIf(s -> s.startsWith("occupied:"));
            lines.removeIf(s -> s.startsWith("part:"));
            lines.removeIf(s -> s.startsWith("power:"));
            lines.removeIf(s -> s.startsWith("sculk_sensor_phase:"));
            lines.removeIf(s -> s.startsWith("mode:"));
            lines.removeIf(s -> s.startsWith("delay:"));
            lines.removeIf(s -> s.startsWith("locked:"));
            lines.removeIf(s -> s.startsWith("slot_"));
            lines.removeIf(s -> s.startsWith("has_book:"));
            lines.removeIf(s -> s.startsWith("can_summon:"));
            lines.removeIf(s -> s.startsWith("shrieking:"));
            lines.removeIf(s -> s.startsWith("extended:"));
            lines.removeIf(s -> s.startsWith("level:"));
            lines.removeIf(s -> s.startsWith("let:"));
            lines.removeIf(s -> s.startsWith("lit:"));
            lines.removeIf(s -> s.startsWith("signal_fire:"));
            lines.removeIf(s -> s.startsWith("PF Golem Sounds"));
            lines.removeIf(s -> s.startsWith("pf."));
            lines.removeIf(s -> s.startsWith("UNASSIGNED"));
            lines.removeIf(s -> s.contains("Target Entity"));
            lines.removeIf(s -> s.contains("Targeted Entity"));
            lines.removeIf(s -> s.contains("Targeted Fluid"));
            lines.removeIf(s -> s.startsWith("cracked:"));
            lines.removeIf(s -> s.startsWith("handing:"));
            lines.removeIf(s -> s.startsWith("hanging:"));
            lines.removeIf(s -> s.startsWith("moisture:"));
            lines.removeIf(s -> s.startsWith("attached:"));
            lines.removeIf(s -> s.startsWith("rotation:"));
            lines.removeIf(s -> s.startsWith("face:"));
            lines.removeIf(s -> s.startsWith("carpet:"));
            lines.removeIf(s -> s.startsWith("bigger:"));
            lines.removeIf(s -> s.startsWith("flower_amount:"));
        }

        if (F3Config.DISABLE_TAGS) {
            lines.removeIf(s -> s.startsWith("#"));
        }

        if (F3Config.DISABLE_SODIUM) {
            var sodiumIndex = lines.indexOf("Sodium Renderer");

            if (sodiumIndex != -1) {
                lines.subList(sodiumIndex, sodiumIndex + 6).clear();
            }
        }

        if (F3Config.DISABLE_IRIS) {
            lines.removeIf(s -> s.startsWith("[Iris]"));
        }

        if (F3Config.DISABLE_DISTANT_HORIZONS) {
            lines.removeIf(s -> s.startsWith("Distant Horizons"));
        }

        if (F3Config.DISABLE_IMMEDIATELYFAST) {
            lines.removeIf(s -> s.startsWith("ImmediatelyFast"));
            lines.removeIf(s -> s.startsWith("Buffer Pool:"));
        }

        return lines;
    }

    @Redirect(method = "getRightText", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/hit/HitResult;getType()Lnet/minecraft/util/hit/HitResult$Type;", ordinal = 1))
    protected final HitResult.Type fluidVisibility(HitResult result) {
        if (F3Config.SHY_FLUIDS && result instanceof BlockHitResult blockHitResult) {
            if (client.world != null && client.world.getFluidState(blockHitResult.getBlockPos()).isEmpty()) {
                return HitResult.Type.MISS;
            }
        }

        return result.getType();
    }
}