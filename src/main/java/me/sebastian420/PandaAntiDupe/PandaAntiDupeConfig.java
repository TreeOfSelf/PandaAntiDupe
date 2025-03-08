package me.sebastian420.PandaAntiDupe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PandaAntiDupeConfig {
    private static final File CONFIG_FILE = new File("./config/PandaAntiDupe.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final Map<String, Boolean> DUPE_TYPES = new HashMap<>() {{
        put("RailDupe", true);
        put("BeeDupe", true);
        put("BookDupe", true);
        put("GravityBlockDupe", true);
        put("HopperDupe", true);
        put("HorseQuickMoveDupe", true);
        put("LecternDupe", true);
        put("EntityDimensionDupe", true);
        put("PistonDupe", true);
        put("StackSplitDupe", true);
        put("ShulkerBoxDupe", true);
        put("NBTListDupe", true);
        put("TridentDupe", true);
        put("TripwireDupe", true);
        put("TripwireHookDupe", true);
    }};

    public static void loadDupeConfig() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                JsonObject json = GSON.fromJson(reader, JsonObject.class);

                JsonObject updatedJson = new JsonObject();

                for (String dupeType : DUPE_TYPES.keySet()) {
                    boolean value;
                    if (json.has(dupeType)) {
                        value = json.get(dupeType).getAsBoolean();
                        DUPE_TYPES.put(dupeType, value);
                    } else {
                        value = DUPE_TYPES.get(dupeType);
                    }
                    updatedJson.addProperty(dupeType, value);
                }

                saveDupeConfigToFile(updatedJson);
            } catch (IOException ignored) {
            }
        } else {
            createDefaultConfig();
        }
    }

    private static void createDefaultConfig() {
        JsonObject json = new JsonObject();
        DUPE_TYPES.forEach((dupeType, defaultValue) -> json.addProperty(dupeType, defaultValue));
        saveDupeConfigToFile(json);
    }

    private static void saveDupeConfigToFile(JsonObject json) {
        try {
            if (!CONFIG_FILE.getParentFile().exists()) {
                CONFIG_FILE.getParentFile().mkdirs();
            }
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(json, writer);
            }
        } catch (IOException ignored) {
        }
    }

    public static boolean getDupeStatus(String dupeType) {
        return DUPE_TYPES.getOrDefault(dupeType, false);
    }
}