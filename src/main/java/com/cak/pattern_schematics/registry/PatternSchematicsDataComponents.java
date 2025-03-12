package com.cak.pattern_schematics.registry;

import com.cak.pattern_schematics.PatternSchematics;
import net.createmod.catnip.codecs.stream.CatnipStreamCodecs;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.UnaryOperator;

public class PatternSchematicsDataComponents {

    private static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, PatternSchematics.MOD_ID);

    public static final DataComponentType<Vec3i> SCHEMATIC_CLONE_SCALE_MIN = register(
        "schematic_clone_scale_min",
        builder -> builder.persistent(Vec3i.CODEC).networkSynchronized(CatnipStreamCodecs.VEC3I)
    );
    public static final DataComponentType<Vec3i> SCHEMATIC_CLONE_SCALE_MAX = register(
        "schematic_clone_scale_max",
        builder -> builder.persistent(Vec3i.CODEC).networkSynchronized(CatnipStreamCodecs.VEC3I)
    );
    public static final DataComponentType<Vec3i> SCHEMATIC_CLONE_OFFSET = register(
        "schematic_clone_offset",
        builder -> builder.persistent(Vec3i.CODEC).networkSynchronized(CatnipStreamCodecs.VEC3I)
    );

    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        DataComponentType<T> type = builder.apply(DataComponentType.builder()).build();
        DATA_COMPONENTS.register(name, () -> type);
        return type;
    }

    @ApiStatus.Internal
    public static void register(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
    }

}
