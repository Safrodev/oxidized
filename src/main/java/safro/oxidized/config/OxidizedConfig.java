package safro.oxidized.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class OxidizedConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public int copper_rail_range = 50;
    public int pulsar_reach = 10;
    public float iron_nugget_chance = 0.15F;
    public float gold_nugget_chance = 0.15F;
    public float sand_chance = 0.24F;
    public float gravel_chance = 0.12F;
    public float emerald_chance = 0.02F;
    public int copper_golem_button_chance = 30;

    public static OxidizedConfig loadConfig(File file) {
        OxidizedConfig config;

        if (file.exists() && file.isFile()) {
            try (
                    FileInputStream fileInputStream = new FileInputStream(file);
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            ) {
                config = GSON.fromJson(bufferedReader, OxidizedConfig.class);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load config", e);
            }
        } else {
            config = new OxidizedConfig();
        }

        config.saveConfig(file);

        return config;
    }

    public void saveConfig(File config) {
        try (
                FileOutputStream stream = new FileOutputStream(config);
                Writer writer = new OutputStreamWriter(stream, StandardCharsets.UTF_8);
        ) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config", e);
        }
    }
}
