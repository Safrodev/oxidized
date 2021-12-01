package safro.oxidized.config;

import draylar.omegaconfig.api.Config;

public class OxidizedConfig implements Config {

    @Override
    public String getName() {
        return "oxidized-config";
    }

    public String rail_info = "Set this number to how far a redstone signal can power copper rails.";

    public int copper_rail_range = 50;

    public String pulsar_info = "Set this number to the range at which the pulsar can pick up items/xp orbs";

    public int pulsar_reach = 10;

    public String pan_info = "The values below determine the chance for items to be sifted in the copper pan. Each value must be in 0.#F form and it's recommended that all values add up to less than or equal to 1.0";

    public float iron_nugget_chance = 0.15F;
    public float gold_nugget_chance = 0.15F;
    public float sand_chance = 0.24F;
    public float gravel_chance = 0.12F;
    public float emerald_chance = 0.02F;

    public String copper_golem_info = "The below value can be set to a number from 3 to 2,147,483,647. The higher the value, the less often the golem will push buttons. Default is 30";

    public int button_chance = 30;
}
