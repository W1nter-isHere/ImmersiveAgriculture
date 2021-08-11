package wintersteve25.immersiveagriculture.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fictioncraft.wintersteve25.fclib.common.interfaces.IJsonConfigBase;
import org.apache.logging.log4j.Level;
import wintersteve25.immersiveagriculture.ImmersiveAgriculture;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Config implements IJsonConfigBase {
    public static ConfigObject globalConfig;

    public static void createConfig() {
        PrintWriter writer;
        File file = new File(directory.getPath() + File.separator + "immersiveagriculture.json");

        if (!directory.exists()) {
            directory.mkdir();
        }

        if (!file.exists()) {
            try {
                writer = new PrintWriter(file);
            } catch (Exception e) {
                ImmersiveAgriculture.LOGGER.log(Level.ERROR, "Exception when trying to create config");
                e.printStackTrace();
                return;
            }

            ArrayList<ConfigObject.CropConfiguration> config = new ArrayList<>();

            ConfigObject exampleBuild = new ConfigObject(config);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.print(gson.toJson(exampleBuild));
            writer.close();

            ImmersiveAgriculture.LOGGER.log(Level.INFO, "Written ece_crop_config.json");
        }
    }

    public static void printExample() {
        PrintWriter writer;

        if (!directory.exists()) {
            directory.mkdir();
        }

        try {
            writer = new PrintWriter(directory.getPath() + File.separator + "immersiveagriculture_example.json");
        } catch (Exception e) {
            ImmersiveAgriculture.LOGGER.log(Level.ERROR, "Exception when trying to create example config");
            e.printStackTrace();
            return;
        }

        ArrayList<ConfigObject.CropConfiguration> config = new ArrayList<>();
        config.add(new ConfigObject.CropConfiguration("minecraft:wheat", "minecraft:wheat_seeds", 1, true, "minecraft:water", 200, true));

        ConfigObject exampleBuild = new ConfigObject(config);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        writer.print(gson.toJson(exampleBuild));
        writer.close();

        ImmersiveAgriculture.LOGGER.log(Level.INFO, "Written ece_crop_config_example.json");
    }

    public static void read() {
        File file = new File(directory.getPath() + File.separator + "immersiveagriculture.json");
        if (!file.exists()) {
            ImmersiveAgriculture.LOGGER.info("Immersive Agriculture config not found! Creating a new one..");
            createConfig();
        } else {
            try {
                ImmersiveAgriculture.LOGGER.info("Attempting to read IA Config file...");
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                ConfigObject builder = gson.fromJson(new FileReader(file), ConfigObject.class);
                if (builder != null) {
                    if (!builder.getConfigurations().isEmpty()) {
                        globalConfig = builder;
                        CropRecipe.recipes.clear();
                        for (ConfigObject.CropConfiguration configs : globalConfig.getConfigurations()) {
                            if (CropRecipe.getRecipeFromConfig(configs) != null) {
                                CropRecipe.addRecipe(CropRecipe.getRecipeFromConfig(configs));
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                ImmersiveAgriculture.LOGGER.info("IA Config json file not found! Creating a new one..");
                e.printStackTrace();
                createConfig();
            }
        }
    }
}
