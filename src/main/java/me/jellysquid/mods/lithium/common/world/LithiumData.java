package me.jellysquid.mods.lithium.common.world;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.World;
import net.minecraft.world.event.listener.GameEventDispatcher;

public interface LithiumData {

    record Data(
            // Map of chunk position -> y section -> game event dispatcher
            // This should be faster than the chunk lookup, since there are usually a lot more chunks than
            // chunk with game event dispatchers (we only initialize them when non-empty set of listeners)
            // All Int2ObjectMap objects are also stored in a field of the corresponding WorldChunk.
            Long2ReferenceOpenHashMap<Int2ObjectMap<GameEventDispatcher>> gameEventDispatchersByChunk,

            // Cached ominous banner, must not be mutated.
            ItemStack ominousBanner,

            // Set of active mob navigations (active = have a path)
            ReferenceOpenHashSet<EntityNavigation> activeNavigations
    ) {
        public Data(World world) {
            this(
                    new Long2ReferenceOpenHashMap<>(),
                    Raid.getOminousBanner(world.getRegistryManager().getWrapperOrThrow(RegistryKeys.BANNER_PATTERN)),
                    new ReferenceOpenHashSet<>()
            );
        }
    }

    LithiumData.Data lithium$getData();
}
