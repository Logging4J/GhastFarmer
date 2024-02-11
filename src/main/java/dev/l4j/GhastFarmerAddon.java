package dev.l4j;

import dev.l4j.modules.GhastFarmer;

import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;

import org.slf4j.Logger;

public class GhastFarmerAddon extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("GhastFarmer");

    @Override
    public void onInitialize() {
        LOG.info("Initializing Meteor Addon Template");
        Modules.get().add(new GhastFarmer());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "dev.l4j";
    }
}
