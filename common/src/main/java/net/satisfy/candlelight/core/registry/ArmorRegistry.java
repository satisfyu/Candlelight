package net.satisfy.candlelight.core.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.item.Item;
import net.satisfy.candlelight.client.model.*;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class ArmorRegistry {
    private static final Map<Item, TieModel<?>> TieModels = new HashMap<>();
    private static final Map<Item, FlowerCrownModel<?>> crownModels = new HashMap<>();
    private static final Map<Item, CookingHatModel<?>> hatModels = new HashMap<>();
    private static final Map<Item, CookingChestplateModel<?>> chestplateModels = new HashMap<>();
    private static final Map<Item, CookingLeggingsModel<?>> leggingsModels = new HashMap<>();
    private static final Map<Item, CookingBootsModel<?>> bootsModels = new HashMap<>();
    private static final Map<Item, DressChestplateModel<?>> dressModels = new HashMap<>();
    private static final Map<Item, SuitLeggingsModel<?>> suitModels = new HashMap<>();

    public static Model getHatModel(Item item, ModelPart baseHead) {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        CookingHatModel<?> model = hatModels.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.COOKING_HAT.get()) {
                return new CookingHatModel<>(modelSet.bakeLayer(CookingHatModel.LAYER_LOCATION));
            } else {
                return null;
            }
        });

        if (model != null) {
            model.copyHead(baseHead);
        }

        return model;
    }

    public static Model getCrownModel(Item item, ModelPart baseHead) {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        FlowerCrownModel<?> model = crownModels.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.FLOWER_CROWN.get()) {
                return new FlowerCrownModel<>(modelSet.bakeLayer(FlowerCrownModel.LAYER_LOCATION));
            } else {
                return null;
            }
        });

        if (model != null) {
            model.copyHead(baseHead);
        }

        return model;
    }

    public static Model getTieModel(Item item, ModelPart baseHead, ModelPart baseBody) {
        EntityModelSet modelSet = Minecraft.getInstance().getEntityModels();
        TieModel<?> model = TieModels.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.NECKTIE.get()) {
                return new TieModel<>(modelSet.bakeLayer(TieModel.LAYER_LOCATION));
            } else {
                return null;
            }
        });

        if (model != null) {
            model.copyHead(baseHead, baseBody);
        }

        return model;
    }

    public static Model getChestplateModel(Item item, ModelPart body, ModelPart leftArm, ModelPart rightArm, ModelPart leftLeg, ModelPart rightLeg) {
        CookingChestplateModel<?> model = chestplateModels.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.CHEFS_JACKET.get() ||
                    key == ObjectRegistry.FORMAL_SHIRT.get() ||
                    key == ObjectRegistry.SHIRT.get()) {
                return new CookingChestplateModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(CookingChestplateModel.LAYER_LOCATION));
            } else {
                return null;
            }
        });

        if (model != null) {
            model.copyBody(body, leftArm, rightArm, leftLeg, rightLeg);
        }

        return model;
    }

    public static Model getDressModel(Item item, ModelPart body, ModelPart leftArm, ModelPart rightArm, ModelPart leftLeg, ModelPart rightLeg) {
        DressChestplateModel<?> model = dressModels.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.DRESS.get()) {
                return new DressChestplateModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(DressChestplateModel.LAYER_LOCATION));
            } else {
                return null;
            }
        });

        if (model != null) {
            model.copyBody(body, leftArm, rightArm, leftLeg, rightLeg);
        }

        return model;
    }

    public static Model getLeggingsModel(Item item, ModelPart rightLeg, ModelPart leftLeg) {
        CookingLeggingsModel<?> model = leggingsModels.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.CHEFS_PANTS.get() ||
                    key == ObjectRegistry.TROUSERS_AND_VEST.get()) {
                return new CookingLeggingsModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(CookingLeggingsModel.LAYER_LOCATION));
            } else {
                return null;
            }
        });

        if (model != null) {
            model.copyLegs(rightLeg, leftLeg);
        }

        return model;
    }

    public static Model getSuitModel(Item item, ModelPart rightLeg, ModelPart leftLeg) {
        SuitLeggingsModel<?> model = suitModels.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.CHEFS_PANTS.get() ||
                    key == ObjectRegistry.TROUSERS_AND_VEST.get()) {
                return new SuitLeggingsModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(SuitLeggingsModel.LAYER_LOCATION));
            } else {
                return null;
            }
        });

        if (model != null) {
            model.copyLegs(rightLeg, leftLeg);
        }

        return model;
    }

    public static Model getBootsModel(Item item, ModelPart rightLeg, ModelPart leftLeg) {
        CookingBootsModel<?> model = bootsModels.computeIfAbsent(item, key -> {
            if (key == ObjectRegistry.CHEFS_BOOTS.get()) {
                return new CookingBootsModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(CookingBootsModel.LAYER_LOCATION));
            } else {
                return null;
            }
        });

        if (model != null) {
            model.copyLegs(rightLeg, leftLeg);
        }

        return model;
    }
}
