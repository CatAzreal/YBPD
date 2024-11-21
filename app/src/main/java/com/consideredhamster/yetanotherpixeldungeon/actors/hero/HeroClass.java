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
package com.consideredhamster.yetanotherpixeldungeon.actors.hero;

import com.consideredhamster.yetanotherpixeldungeon.Badges;
import com.consideredhamster.yetanotherpixeldungeon.items.armours.body.HuntressArmor;
import com.consideredhamster.yetanotherpixeldungeon.items.armours.body.MageArmor;
import com.consideredhamster.yetanotherpixeldungeon.items.armours.body.RogueArmor;
import com.consideredhamster.yetanotherpixeldungeon.items.armours.body.StuddedArmor;
import com.consideredhamster.yetanotherpixeldungeon.items.armours.shields.RoundShield;
import com.consideredhamster.yetanotherpixeldungeon.items.bags.Keyring;
import com.consideredhamster.yetanotherpixeldungeon.items.food.RationMedium;
import com.consideredhamster.yetanotherpixeldungeon.items.misc.ArmorerKit;
import com.consideredhamster.yetanotherpixeldungeon.items.misc.Battery;
import com.consideredhamster.yetanotherpixeldungeon.items.misc.CraftingKit;
import com.consideredhamster.yetanotherpixeldungeon.items.misc.OilLantern;
import com.consideredhamster.yetanotherpixeldungeon.items.misc.Waterskin;
import com.consideredhamster.yetanotherpixeldungeon.items.misc.Whetstone;
import com.consideredhamster.yetanotherpixeldungeon.items.potions.EmptyBottle;
import com.consideredhamster.yetanotherpixeldungeon.items.potions.PotionOfStrength;
import com.consideredhamster.yetanotherpixeldungeon.items.rings.RingOfAccuracy;
import com.consideredhamster.yetanotherpixeldungeon.items.rings.RingOfShadows;
import com.consideredhamster.yetanotherpixeldungeon.items.scrolls.ScrollOfClairvoyance;
import com.consideredhamster.yetanotherpixeldungeon.items.scrolls.ScrollOfRaiseDead;
import com.consideredhamster.yetanotherpixeldungeon.items.wands.Wand;
import com.consideredhamster.yetanotherpixeldungeon.items.wands.WandOfMagicMissile;
import com.consideredhamster.yetanotherpixeldungeon.items.weapons.melee.Dagger;
import com.consideredhamster.yetanotherpixeldungeon.items.weapons.melee.Quarterstaff;
import com.consideredhamster.yetanotherpixeldungeon.items.weapons.melee.Shortsword;
import com.consideredhamster.yetanotherpixeldungeon.items.weapons.ranged.Sling;
import com.consideredhamster.yetanotherpixeldungeon.items.weapons.throwing.Bullets;
import com.consideredhamster.yetanotherpixeldungeon.items.weapons.throwing.Knives;
import com.consideredhamster.yetanotherpixeldungeon.multilang.Ml;
import com.consideredhamster.yetanotherpixeldungeon.visuals.Assets;
import com.watabou.utils.Bundle;

public enum HeroClass {

    WARRIOR(Ml.g("actors.hero.heroclass.warrior")), BRIGAND(Ml.g("actors.hero.heroclass.brigand")), SCHOLAR(Ml.g("actors.hero.heroclass.scholar")), ACOLYTE(Ml.g("actors.hero.heroclass.acolyte"));

    private String title;

    private HeroClass(String title) {
        this.title = title;
    }

    public static final String[] WAR_ABOUT = {
            Ml.g("actors.hero.heroclass.war_about_1"),
            Ml.g("actors.hero.heroclass.war_about_2"),
            Ml.g("actors.hero.heroclass.war_about_3"),
    };

    public static final String[] ROG_ABOUT = {
            Ml.g("actors.hero.heroclass.rog_about_1"),
            Ml.g("actors.hero.heroclass.rog_about_2"),
            Ml.g("actors.hero.heroclass.rog_about_3"),
    };

    public static final String[] MAG_ABOUT = {
            Ml.g("actors.hero.heroclass.mag_about_1"),
            Ml.g("actors.hero.heroclass.mag_about_2"),
            Ml.g("actors.hero.heroclass.mag_about_3"),
    };

    public static final String[] HUN_ABOUT = {
            Ml.g("actors.hero.heroclass.hun_about_1"),
            Ml.g("actors.hero.heroclass.hun_about_2"),
            Ml.g("actors.hero.heroclass.hun_about_3"),
    };

    public static final String[] WAR_DETAILS = {
            Ml.g("actors.hero.heroclass.war_details_1"),
            Ml.g("actors.hero.heroclass.war_details_2"),
            Ml.g("actors.hero.heroclass.war_details_3"),
            Ml.g("actors.hero.heroclass.war_details_4"),
            Ml.g("actors.hero.heroclass.war_details_5"),
            Ml.g("actors.hero.heroclass.war_details_6"),
            Ml.g("actors.hero.heroclass.war_details_7"),
            Ml.g("actors.hero.heroclass.war_details_8"),
    };

    public static final String[] ROG_DETAILS = {
            Ml.g("actors.hero.heroclass.rog_details_1"),
            Ml.g("actors.hero.heroclass.rog_details_2"),
            Ml.g("actors.hero.heroclass.rog_details_3"),
            Ml.g("actors.hero.heroclass.rog_details_4"),
            Ml.g("actors.hero.heroclass.rog_details_5"),
            Ml.g("actors.hero.heroclass.rog_details_6"),
            Ml.g("actors.hero.heroclass.rog_details_7"),
            Ml.g("actors.hero.heroclass.rog_details_8"),
            Ml.g("actors.hero.heroclass.rog_details_9"),
    };

    public static final String[] MAG_DETAILS = {
            Ml.g("actors.hero.heroclass.mag_details_1"),
            Ml.g("actors.hero.heroclass.mag_details_2"),
            Ml.g("actors.hero.heroclass.mag_details_3"),
            Ml.g("actors.hero.heroclass.mag_details_4"),
            Ml.g("actors.hero.heroclass.mag_details_5"),
            Ml.g("actors.hero.heroclass.mag_details_6"),
            Ml.g("actors.hero.heroclass.mag_details_7"),
            Ml.g("actors.hero.heroclass.mag_details_8"),
            Ml.g("actors.hero.heroclass.mag_details_9"),
    };

    public static final String[] HUN_DETAILS = {
            Ml.g("actors.hero.heroclass.hun_details_1"),
            Ml.g("actors.hero.heroclass.hun_details_2"),
            Ml.g("actors.hero.heroclass.hun_details_3"),
            Ml.g("actors.hero.heroclass.hun_details_4"),
            Ml.g("actors.hero.heroclass.hun_details_5"),
            Ml.g("actors.hero.heroclass.hun_details_6"),
            Ml.g("actors.hero.heroclass.hun_details_7"),
            Ml.g("actors.hero.heroclass.hun_details_8"),
            Ml.g("actors.hero.heroclass.hun_details_9"),
    };

    public void initHero(Hero hero) {

        hero.heroClass = this;

        initCommon(hero);

        switch (this) {
            case WARRIOR:
                initWarrior(hero);
                break;

            case BRIGAND:
                initRogue(hero);
                break;

            case SCHOLAR:
                initMage(hero);
                break;

            case ACOLYTE:
                initHuntress(hero);
                break;
        }
    }

    private static void initCommon(Hero hero) {

        new Keyring().collect();
        new RationMedium().collect();

//        new ScrollOfClairvoyance().quantity(30).identify().collect();
//        new ScrollOfPhaseWarp().quantity(30).identify().collect();
//        new ScrollOfDetectMagic().quantity(30).identify().collect();
//        new ScrollOfTransmutation().quantity(30).identify().collect();
//        new Explosives.BombBundle().quantity(30).identify().collect();
//        new Explosives.BombStick().quantity(30).identify().collect();
//        new PotionOfInvisibility().quantity(30).identify().collect();
//        new PotionOfMindVision().quantity(30).identify().collect();
//        new PotionOfWisdom().quantity(12).identify().collect();
        new PotionOfStrength().quantity(4).collect();
//        new ScrollOfUpgrade().quantity(4).identify().collect();

        new Waterskin().setLimit(5).fill().collect();
        new OilLantern().collect();

    }

    public Badges.Badge masteryBadge() {
        switch (this) {
            case WARRIOR:
                return Badges.Badge.MASTERY_WARRIOR;
            case BRIGAND:
                return Badges.Badge.MASTERY_BRIGAND;
            case SCHOLAR:
                return Badges.Badge.MASTERY_SCHOLAR;
            case ACOLYTE:
                return Badges.Badge.MASTERY_ACOLYTE;
        }
        return null;
    }


    public Badges.Badge victoryBadge() {
        switch (this) {
            case WARRIOR:
                return Badges.Badge.VICTORY_WARRIOR;
            case BRIGAND:
                return Badges.Badge.VICTORY_BRIGAND;
            case SCHOLAR:
                return Badges.Badge.VICTORY_SCHOLAR;
            case ACOLYTE:
                return Badges.Badge.VICTORY_ACOLYTE;

        }
        return null;
    }

    private static void initWarrior(Hero hero) {

        hero.STR++;

        hero.HP = (hero.HT += 5);
        hero.defenseSkill -= 5;

//        hero.HP = (hero.HT += 50);
//        hero.STR = 50;
//
//        new ScrollOfClairvoyance().quantity(30).identify().collect();

        (hero.belongings.weap1 = new Shortsword()).identify().repair().fix();
        (hero.belongings.weap2 = new RoundShield()).identify().repair().fix();
        (hero.belongings.armor = new StuddedArmor()).identify().repair().fix();

        new ArmorerKit().collect();

    }

    private static void initRogue(Hero hero) {

        hero.defenseSkill += 5;
        hero.magicPower -= 5;

        (hero.belongings.weap1 = new Dagger()).identify().repair().fix();
        (hero.belongings.weap2 = new Knives()).quantity(10);
        (hero.belongings.armor = new RogueArmor()).identify().repair().fix();
        (hero.belongings.ring1 = new RingOfShadows()).identify();

        hero.belongings.ring1.activate(hero);

        new Whetstone().collect();
    }

    private static void initMage(Hero hero) {

        hero.magicPower += 5;
        hero.attackSkill -= 5;

        (hero.belongings.weap1 = new Quarterstaff()).identify().repair().fix();
        (hero.belongings.weap2 = new WandOfMagicMissile()).identify().repair().fix();
        (hero.belongings.armor = new MageArmor()).identify().repair().fix();

        ((Wand) hero.belongings.weap2).recharge();

        new ScrollOfRaiseDead().identify().collect();
        new Battery().collect();

    }

    private static void initHuntress(Hero hero) {

        hero.STR--;

        hero.HP = (hero.HT -= 5);
        hero.attackSkill += 5;

        (hero.belongings.weap1 = new Sling()).repair().identify().fix();
        (hero.belongings.weap2 = new Bullets()).quantity(30);
        (hero.belongings.armor = new HuntressArmor()).identify().repair().fix();

        new EmptyBottle().quantity(3).collect();
        new CraftingKit().collect();
    }

    public String title() {
        return title;
    }

    public String spritesheet() {

        switch (this) {
            case WARRIOR:
                return Assets.WARRIOR;
            case BRIGAND:
                return Assets.BRIGAND;
            case SCHOLAR:
                return Assets.SCHOLAR;
            case ACOLYTE:
                return Assets.ACOLYTE;
        }

        return null;
    }

    public String[] history() {

        switch (this) {
            case WARRIOR:
                return WAR_ABOUT;
            case BRIGAND:
                return ROG_ABOUT;
            case SCHOLAR:
                return MAG_ABOUT;
            case ACOLYTE:
                return HUN_ABOUT;
        }

        return null;
    }

    public String[] details() {

        switch (this) {
            case WARRIOR:
                return WAR_DETAILS;
            case BRIGAND:
                return ROG_DETAILS;
            case SCHOLAR:
                return MAG_DETAILS;
            case ACOLYTE:
                return HUN_DETAILS;
        }

        return null;
    }

    private static final String CLASS = "class";

    public void storeInBundle(Bundle bundle) {
        bundle.put(CLASS, toString());
    }

    public static HeroClass restoreInBundle(Bundle bundle) {
        String value = bundle.getString(CLASS);
        return value.length() > 0 ? valueOf(value) : WARRIOR;
    }
}
