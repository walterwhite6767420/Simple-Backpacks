package simplebackpacks.modi;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record OpenEquippedBackpackPayload()
        implements CustomPacketPayload {

    public static final Type<OpenEquippedBackpackPayload> TYPE =
            new Type<>(SimpleBackpacks.id("open_equipped_backpack"));

    public static final StreamCodec<
            RegistryFriendlyByteBuf,
            OpenEquippedBackpackPayload
            > CODEC = StreamCodec.unit(
            new OpenEquippedBackpackPayload()
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}