
/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Yet Another Pixel Dungeon
 * Copyright (C) 2015-2016 Considered Hamster
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.consideredhamster.yetanotherpixeldungeon.items.potions;

import com.consideredhamster.yetanotherpixeldungeon.Badges;
import com.consideredhamster.yetanotherpixeldungeon.Dungeon;
import com.consideredhamster.yetanotherpixeldungeon.actors.hero.Hero;
import com.consideredhamster.yetanotherpixeldungeon.items.Item;
import com.consideredhamster.yetanotherpixeldungeon.items.ItemStatusHandler;
import com.consideredhamster.yetanotherpixeldungeon.levels.Level;
import com.consideredhamster.yetanotherpixeldungeon.misc.mechanics.Ballistica;
import com.consideredhamster.yetanotherpixeldungeon.misc.utils.GLog;
import com.consideredhamster.yetanotherpixeldungeon.multilang.Ml;
import com.consideredhamster.yetanotherpixeldungeon.scenes.GameScene;
import com.consideredhamster.yetanotherpixeldungeon.visuals.Assets;
import com.consideredhamster.yetanotherpixeldungeon.visuals.effects.Splash;
import com.consideredhamster.yetanotherpixeldungeon.visuals.sprites.ItemSprite;
import com.consideredhamster.yetanotherpixeldungeon.visuals.sprites.ItemSpriteSheet;
import com.consideredhamster.yetanotherpixeldungeon.visuals.windows.WndOptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class Potion extends Item {

    public static final String AC_DRINK = Ml.g("items.potions.potion.ac_drink");

    private static final String TXT_HARMFUL = Ml.g("items.potions.potion.txt_harmful");
    private static final String TXT_BENEFICIAL = Ml.g("items.potions.potion.txt_beneficial");
    private static final String TXT_YES = Ml.g("items.potions.potion.txt_yes");
    private static final String TXT_NO = Ml.g("items.potions.potion.txt_no");
    private static final String TXT_R_U_SURE_DRINK = Ml.g("items.potions.potion.txt_r_u_sure_drink");
    private static final String TXT_R_U_SURE_THROW = Ml.g("items.potions.potion.txt_r_u_sure_throw");

    private static final String TXT_IDENTIFIED = Ml.g("items.potions.potion.txt_identified");

    private static final float TIME_TO_DRINK = 1f;

    private static final Class<?>[] potions = {
            PotionOfMending.class,
            PotionOfWisdom.class,
            PotionOfToxicGas.class,
            PotionOfLiquidFlame.class,
            PotionOfStrength.class,
            PotionOfThunderstorm.class,
            PotionOfLevitation.class,
            PotionOfMindVision.class,
            PotionOfBlessing.class,
            PotionOfInvisibility.class,
            PotionOfWebbing.class,
            PotionOfFrigidVapours.class,
            PotionOfConfusionGas.class,
            PotionOfRage.class,
            PotionOfShield.class,
            PotionOfCausticOoze.class
    };
    private static final String[] colors = {
            Ml.g("items.potions.potion.color_turquoise"),
            Ml.g("items.potions.potion.color_crimson"),
            Ml.g("items.potions.potion.color_azure"),
            Ml.g("items.potions.potion.color_emerald"),
            Ml.g("items.potions.potion.color_golden"),
            Ml.g("items.potions.potion.color_magenta"),
            Ml.g("items.potions.potion.color_charcoal"),
            Ml.g("items.potions.potion.color_ivory"),
            Ml.g("items.potions.potion.color_amber"),
            Ml.g("items.potions.potion.color_bistre"),
            Ml.g("items.potions.potion.color_indigo"),
            Ml.g("items.potions.potion.color_silver"),
            Ml.g("items.potions.potion.color_chartreuse"),
            Ml.g("items.potions.potion.color_lavender"),
            Ml.g("items.potions.potion.color_bordeaux"),
            Ml.g("items.potions.potion.color_jade")
    };

    private static final Integer[] images = {
            ItemSpriteSheet.POTION_TURQUOISE,
            ItemSpriteSheet.POTION_CRIMSON,
            ItemSpriteSheet.POTION_AZURE,
            ItemSpriteSheet.POTION_EMERALD,

            ItemSpriteSheet.POTION_GOLDEN,
            ItemSpriteSheet.POTION_MAGENTA,
            ItemSpriteSheet.POTION_CHARCOAL,
            ItemSpriteSheet.POTION_IVORY,

            ItemSpriteSheet.POTION_AMBER,
            ItemSpriteSheet.POTION_BISTRE,
            ItemSpriteSheet.POTION_INDIGO,
            ItemSpriteSheet.POTION_SILVER,

            ItemSpriteSheet.POTION_CHARTREUSE,
            ItemSpriteSheet.POTION_LAVENDER,
            ItemSpriteSheet.POTION_BORDEAUX,
            ItemSpriteSheet.POTION_JADE,
    };


    private static ItemStatusHandler<Potion> handler;

    protected String color;

    // hides potion color in the alchemy window and
    // disables identification by using unstable potions
    // tbh it's a kind of hack and probably should be fixed later
    public boolean dud = false;

    protected boolean harmful;

    {
        stackable = true;
        harmful = false;
        shortName = "??";

    }

    @SuppressWarnings("unchecked")
    public static void initColors() {
        handler = new ItemStatusHandler<Potion>((Class<? extends Potion>[]) potions, colors, images);
    }

    public static void save(Bundle bundle) {
        handler.save(bundle);
    }

    @SuppressWarnings("unchecked")
    public static void restore(Bundle bundle) {
        handler = new ItemStatusHandler<Potion>((Class<? extends Potion>[]) potions, colors, images, bundle);
    }

    public Potion() {
        super();

        if (!(this instanceof EmptyBottle || this instanceof UnstablePotion)) {
            image = handler.image(this);
            color = handler.label(this);
        }
    }

    public int image() {
        if (dud) {
            if (this instanceof UnstablePotion || handler.isKnown(this))
                return super.image();
            else
                return ItemSpriteSheet.POTION_UNKNOWN;
        } else {
            return super.image();
        }
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_DRINK);
        return actions;
    }

    @Override
    public void execute(final Hero hero, String action) {
        if (action.equals(AC_DRINK)) {

            if (isTypeKnown() && harmful && !(this instanceof UnstablePotion)) {

                GameScene.show(
                        new WndOptions(TXT_HARMFUL, TXT_R_U_SURE_DRINK, TXT_YES, TXT_NO) {
                            @Override
                            protected void onSelect(int index) {
                                if (index == 0) {
                                    drink(hero);
                                }
                            }

                            ;
                        }
                );

            } else {
                drink(hero);
            }

        } else {

            super.execute(hero, action);

        }
    }

    @Override
    public void doThrow(final Hero hero) {

        if (isTypeKnown() && !harmful && !(this instanceof UnstablePotion)) {

            GameScene.show(
                    new WndOptions(TXT_BENEFICIAL, TXT_R_U_SURE_THROW, TXT_YES, TXT_NO) {
                        @Override
                        protected void onSelect(int index) {
                            if (index == 0) {
                                Potion.super.doThrow(hero);
                            }
                        }

                        ;
                    }
            );

        } else {
            super.doThrow(hero);
        }
    }

    protected void drink(Hero hero) {

        detach(hero.belongings.backpack);

        hero.spend(TIME_TO_DRINK);
        hero.busy();
        apply(hero);

        Sample.INSTANCE.play(Assets.SND_DRINK);

        hero.sprite.operate(hero.pos);
    }

    @Override
    protected void onThrow(int cell) {

        if (Level.chasm[cell]) {

            super.onThrow(cell);

        } else {

            detach(curUser.belongings.backpack);
            shatter(Level.solid[cell] ? Ballistica.trace[Ballistica.distance - 1] : cell);

            Sample.INSTANCE.play(Assets.SND_SHATTER);
            splash(cell);

        }
    }

    protected void apply(Hero hero) {
        shatter(hero.pos);
    }

    public void shatter(int cell) {

        if (Dungeon.visible[cell]) {

            if (harmful) {
                setKnown();
            } else {
                GLog.i(Ml.g("items.potions.potion.txt_shatter", color()));
            }
        }
    }

    @Override
    public boolean isTypeKnown() {
        return handler.isKnown(this) || dud;
    }

    public void setKnown() {
        if (!isTypeKnown() && !dud) {
            handler.know(this);

            if (Dungeon.hero.isAlive()) {
                GLog.i(TXT_IDENTIFIED, color, name());
            }
        }

        Badges.validateAllPotionsIdentified();
    }

    @Override
    public Item identify() {
        setKnown();
        return this;
    }

    @Override
    public String quickAction() {

        if (!isTypeKnown())
            return null;

        return harmful ? AC_THROW : AC_DRINK;
    }

    protected String color() {
        return color;
    }

    @Override
    public String name() {
        return Ml.g("items.potions.potion.name", (isTypeKnown() ? name : color + Ml.g("items.potions.potion.nameappend")));
    }

    @Override
    public String info() {
        return Ml.g("items.potions.potion.info", (isTypeKnown() ? desc() : Ml.g("items.potions.potion.infoappend", color)));
    }


    @Override
    public boolean isIdentified() {
        return isTypeKnown();
    }

    @Override
    public boolean isUpgradeable() {
        return false;
    }

    public static HashSet<Class<? extends Potion>> getKnown() {
        return handler.known();
    }

    public static HashSet<Class<? extends Potion>> getUnknown() {
        return handler.unknown();
    }

    public static boolean allKnown() {
        return handler.known().size() == potions.length;
    }

    protected void splash(int cell) {
        Splash.at(cell, ItemSprite.pick(image, 8, 10), 10);
    }

    @Override
    public int price() {
        return 30 * quantity;
    }

    public float brewingChance() {
        return 0f;
    }
}
