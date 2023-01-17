package safro.oxidized.block;

import net.minecraft.block.ButtonBlock;
import net.minecraft.sound.SoundEvents;

public class CopperButtonBlock extends ButtonBlock {

    public CopperButtonBlock(Settings settings) {
        super(settings, 20, false, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON);
    }
}
