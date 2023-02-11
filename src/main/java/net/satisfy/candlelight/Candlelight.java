package net.satisfy.candlelight;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab;
import net.moddingplayground.frame.api.tabbeditemgroups.v0.TabbedItemGroup;
import net.moddingplayground.frame.api.util.GUIIcon;
import net.satisfy.candlelight.registry.CRecipeTypes;
import net.satisfy.candlelight.registry.ObjectRegistry;
import net.satisfy.candlelight.registry.StorageTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfyu.vinery.VineryIdentifier;

import static net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab.Predicate.ALWAYS;
import static net.moddingplayground.frame.api.tabbeditemgroups.v0.Tab.Predicate.items;

public class Candlelight implements ModInitializer {
	public static final String MOD_ID = "candlelight";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static final ItemGroup ITEM_GROUP_TABBED_ICON_TEXTURES =
			TabbedItemGroup.builder()
					.defaultPredicate(ALWAYS)
					.tab(Tab.builder().predicate(items(
							ObjectRegistry.TOMATO_SEEDS,
							ObjectRegistry.TOMATO,
							ObjectRegistry.TOMATO_CRATE,
							ObjectRegistry.BROCCOLI_SEEDS,
							ObjectRegistry.BROCCOLI,
							ObjectRegistry.BROCCOLI_CRATE,
							ObjectRegistry.STRAWBERRY_SEEDS,
							ObjectRegistry.STRAWBERRY,
							ObjectRegistry.STRAWBERRY_CRATE,
							ObjectRegistry.SOFA,
							ObjectRegistry.CHAIR,
							ObjectRegistry.TABLE,
							ObjectRegistry.LAMP,
							ObjectRegistry.WOOD_FIRED_OVEN,
							ObjectRegistry.STOVE,
							ObjectRegistry.SIDEBOARD,
							ObjectRegistry.DRAWER,
							ObjectRegistry.KITCHEN_SINK,
							ObjectRegistry.FLOORBOARD,
							ObjectRegistry.CAKE_STAND,
							ObjectRegistry.COOKING_POT,
							//ObjectRegistry.COOKING_PAN,
							//ObjectRegistry.TRAY,
							ObjectRegistry.TABLE_SET,
							ObjectRegistry.BUTTER,
							ObjectRegistry.CAKE_STAND,
							ObjectRegistry.COOKING_POT,
							ObjectRegistry.MOZZARELLA,
							ObjectRegistry.TOMATO_SOUP,
							ObjectRegistry.BEETROOT_SALAD,
							ObjectRegistry.COOKED_BEEF,
							ObjectRegistry.PASTA,
							ObjectRegistry.BEEF_TARTARE,
							//ObjectRegistry.TOMATO_MOZZARELLA_SALAT,
							ObjectRegistry.BROCCOLI_BEEF,
							ObjectRegistry.BROCCOLI_TOMATO,
							//ObjectRegistry.ROASTBEEF_CARROTS,
							ObjectRegistry.SALMON,
							ObjectRegistry.VEGGIE_PLATE,
							ObjectRegistry.PORK_RIB,
							//ObjectRegistry.LASAGNA,
							//ObjectRegistry.BEEF_WELLINGTON,
							ObjectRegistry.MASHED_POTATOES,
							ObjectRegistry.FRICASSE,
							ObjectRegistry.FRIED_EGG,
							ObjectRegistry.STRAWBERRY_JAM,
							ObjectRegistry.VINEGAR,
							ObjectRegistry.RED_WINE,
							ObjectRegistry.PRAETORIAN_WINE,
							ObjectRegistry.PANCAKE,
							ObjectRegistry.WAFFLE,
							ObjectRegistry.STRAWBERRY_GLAZED_COOKIE,
							ObjectRegistry.CROISSANT,
							ObjectRegistry.BUNDT_CAKE,
							ObjectRegistry.SESAM_BREAD,
							ObjectRegistry.STRAWBERRY_CHOCOLATE,
							ObjectRegistry.STRAWBERRY_CAKE_SLICE,
							ObjectRegistry.STRAWBERRY_CAKE,
							ObjectRegistry.STRAWBERRY_MILKSHAKE,
							ObjectRegistry.STRAWBERRY_ICECREAM,
							ObjectRegistry.SWEETBERRY_CAKE_SLICE,
							ObjectRegistry.SWEETBERRY_CAKE,
							ObjectRegistry.SWEETBERRY_MILKSHAKE,
							ObjectRegistry.SWEETBERRY_ICECREAM,
							ObjectRegistry.CHOCOLATE_CAKE_SLICE,
							ObjectRegistry.CHOCOLATE_CAKE,
							ObjectRegistry.CHOCOLATE_MILKSHAKE,
							ObjectRegistry.CHOCOLATE_ICECREAM,
							ObjectRegistry.TABLE_SIGN,
							ObjectRegistry.STREET_SIGN,
							ObjectRegistry.PAINTING,
							ObjectRegistry.HEARTH,
							ObjectRegistry.ROSE,
							ObjectRegistry.CANDLE,
							ObjectRegistry.JEWELRY_BOX,
							ObjectRegistry.CHOCOLATE_BOX,
							ObjectRegistry.COOKING_HAT,
							ObjectRegistry.CHEFS_JACKET,
							ObjectRegistry.CHEFS_PANTS,
							ObjectRegistry.CHEFS_BOOTS,
							ObjectRegistry.GLASS,
							ObjectRegistry.NAPKIN,
							ObjectRegistry.BOOK,
							ObjectRegistry.NOTE_PAPER,
							ObjectRegistry.NOTE_PAPER_WRITEABLE,
							ObjectRegistry.NOTE_PAPER_WRITTEN,
							//ObjectRegistry.TYPEWRITTER_IRON,
							//ObjectRegistry.TYPEWRITTER_COPPER,
							ObjectRegistry.LETTER_OPEN,
							ObjectRegistry.LETTER_CLOSED,
							ObjectRegistry.LOVE_LETTER
					)).build("candlelight", GUIIcon.of(() -> new ItemStack(ObjectRegistry.HEARTH))))

					.build(new VineryIdentifier("vinery_tab"), g -> GUIIcon.of(() -> new ItemStack(satisfyu.vinery.registry.ObjectRegistry.RED_GRAPE)));
	@Override
	public void onInitialize() {
		CRecipeTypes.init();
		ObjectRegistry.init();
		StorageTypes.init();
	}
}
