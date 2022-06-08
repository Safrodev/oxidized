package safro.oxidized.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import safro.oxidized.block.screen.CopperKilnScreenHandler;
import safro.oxidized.registry.BlockRegistry;

public class CopperKilnBlockEntity extends AbstractFurnaceBlockEntity {

    public CopperKilnBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.COPPER_KILN_BLOCK_ENTITY, pos, state, BlockRegistry.COPPER_KILN_RECIPE_TYPE);
    }

    protected Text getContainerName() {
        return Text.translatable("container.oxidized.copper_kiln");
    }

    protected int getFuelTime(ItemStack fuel) {
        return super.getFuelTime(fuel) / 2;
    }

    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new CopperKilnScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
}
