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
package com.consideredhamster.yetanotherpixeldungeon.items.misc;

import com.consideredhamster.yetanotherpixeldungeon.Dungeon;
import com.consideredhamster.yetanotherpixeldungeon.actors.hero.Hero;
import com.consideredhamster.yetanotherpixeldungeon.items.Item;
import com.consideredhamster.yetanotherpixeldungeon.items.armours.Armour;
import com.consideredhamster.yetanotherpixeldungeon.items.rings.RingOfDurability;
import com.consideredhamster.yetanotherpixeldungeon.misc.utils.GLog;
import com.consideredhamster.yetanotherpixeldungeon.misc.utils.Utils;
import com.consideredhamster.yetanotherpixeldungeon.multilang.Ml;
import com.consideredhamster.yetanotherpixeldungeon.scenes.GameScene;
import com.consideredhamster.yetanotherpixeldungeon.visuals.Assets;
import com.consideredhamster.yetanotherpixeldungeon.visuals.effects.Speck;
import com.consideredhamster.yetanotherpixeldungeon.visuals.sprites.ItemSpriteSheet;
import com.consideredhamster.yetanotherpixeldungeon.visuals.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ArmorerKit extends Item {

    private static final String TXT_SELECT_ARMOUR = Ml.g("items.misc.armorerkit.txt_select_armour");
    private static final String TXT_REPAIR_ARMOUR = Ml.g("items.misc.armorerkit.txt_repair_armour");
    private static final String TXT_CHARGE_KEEPED = Ml.g("items.misc.armorerkit.txt_charge_keeped");
    private static final String TXT_CHARGE_WASTED = Ml.g("items.misc.armorerkit.txt_charge_wasted");

    private static final float TIME_TO_APPLY = 3f;

    private static final String AC_APPLY = Ml.g("items.misc.armorerkit.ac_apply");

    private static final String TXT_STATUS = Ml.g("items.misc.armorerkit.txt_status");

    {
        name = Ml.g("items.misc.armorerkit.name");
        image = ItemSpriteSheet.ARMORER_KIT;
    }

    private static final String VALUE = "value";

    private int value = 3;
    private final int limit = 3;

    @Override
    public String quickAction() {
        return AC_APPLY;
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(VALUE, value);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        value = bundle.getInt(VALUE);
    }

    @Override
    public Item random() {
        value = Random.IntRange(1, 3);
        return this;
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_APPLY);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        if (action == AC_APPLY) {

            curUser = hero;
            curItem = this;
            GameScene.selectItem(itemSelector, WndBag.Mode.ARMORERS_KIT, TXT_SELECT_ARMOUR);

        } else {

            super.execute(hero, action);

        }
    }

    @Override
    public int price() {
        return 20 + 10 * value;
    }

    private void repair(Armour armor) {

        float bonus = Dungeon.hero.ringBuffsBaseZero(RingOfDurability.Durability.class) * 0.5f;

        if (bonus > 0.0f && Random.Float() < bonus) {
            GLog.p(TXT_CHARGE_KEEPED);
        } else {
            if (--value <= 0) {
                detach(curUser.belongings.backpack);
            }
        }

        if (bonus < 0.0f && Random.Float() < -bonus) {
            GLog.n(TXT_CHARGE_WASTED);
        } else {
            armor.repair(1);
            GLog.i(TXT_REPAIR_ARMOUR, armor.name());
        }

        curUser.sprite.operate(curUser.pos);
        Sample.INSTANCE.play(Assets.SND_EVOKE);

        curUser.sprite.centerEmitter().start(Speck.factory(Speck.KIT), 0.05f, 10);
        curUser.spend(TIME_TO_APPLY);
        curUser.busy();
    }

    @Override
    public String info() {
        return Ml.g("items.misc.armorerkit.info", value);
    }

    @Override
    public String status() {
        return Utils.format(TXT_STATUS, value, limit);
    }

    @Override
    public String toString() {
        return super.toString() + " (" + status() + ")";
    }

    private final WndBag.Listener itemSelector = new WndBag.Listener() {
        @Override
        public void onSelect(Item item) {
            if (item != null) {
                ArmorerKit.this.repair((Armour) item);
            }
        }
    };
}
