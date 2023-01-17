package safro.oxidized.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.level.ServerWorldProperties;
import safro.oxidized.mixin.ServerWorldAccessor;

public class BarometerItem extends Item {

    public BarometerItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient) {
            ServerWorld serverWorld = (ServerWorld) world;
            ServerWorldProperties properties = ((ServerWorldAccessor) serverWorld).getWorldProperties();
            Biome biome = serverWorld.getBiome(user.getBlockPos()).value();

            if (serverWorld.isThundering()) {
                user.sendMessage(getTime(properties, false, false), true);
            } else if (serverWorld.isRaining()) {
                user.sendMessage(getTime(properties, true, biome.isCold(user.getBlockPos())), true);
            } else {
                user.sendMessage(Text.translatable("text.oxidized.clearweather", properties.getClearWeatherTime() / 20).formatted(Formatting.GOLD), true);
            }
        }
        return TypedActionResult.success(stack, world.isClient());
    }

    private static Text getTime(ServerWorldProperties properties, boolean rain, boolean snow) {
        int time = (rain ? properties.getRainTime() : properties.getThunderTime()) / 20;
        String key = rain ? "text.oxidized.rain" : "text.oxidized.thunderstorm";
        if (snow) {
            key = "text.oxidized.snow";
        }
        return Text.translatable(key, time).formatted(Formatting.GOLD);
    }
}
