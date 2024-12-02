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
package com.consideredhamster.yetanotherpixeldungeon.visuals.windows;

import com.consideredhamster.yetanotherpixeldungeon.Dungeon;
import com.consideredhamster.yetanotherpixeldungeon.actors.hero.Hero;
import com.consideredhamster.yetanotherpixeldungeon.actors.mobs.npcs.Ghost;
import com.consideredhamster.yetanotherpixeldungeon.items.Item;
import com.consideredhamster.yetanotherpixeldungeon.items.quest.DriedRose;
import com.consideredhamster.yetanotherpixeldungeon.misc.utils.GLog;
import com.consideredhamster.yetanotherpixeldungeon.misc.utils.Utils;
import com.consideredhamster.yetanotherpixeldungeon.multilang.Ml;
import com.consideredhamster.yetanotherpixeldungeon.scenes.PixelScene;
import com.consideredhamster.yetanotherpixeldungeon.visuals.sprites.ItemSprite;
import com.consideredhamster.yetanotherpixeldungeon.visuals.ui.RedButton;
import com.consideredhamster.yetanotherpixeldungeon.visuals.ui.Window;
import com.watabou.noosa.BitmapTextMultiline;

public class WndSadGhost extends Window {

    private static final String TXT_ROSE = Ml.g("visuals.windows.wndsadghost.txt_rose");
    private static final String TXT_RAT = Ml.g("visuals.windows.wndsadghost.txt_rat");
    private static final String TXT_WEAPON = Ml.g("visuals.windows.wndsadghost.txt_weapon");
    private static final String TXT_ARMOR = Ml.g("visuals.windows.wndsadghost.txt_armor");

    private static final int WIDTH = 120;
    private static final int BTN_HEIGHT = 20;
    private static final float GAP = 2;

    public WndSadGhost(final Ghost ghost, final Item item) {

        super();

        IconTitle titlebar = new IconTitle();
        titlebar.icon(new ItemSprite(item.image(), null));
        titlebar.label(Utils.capitalize(item.name()));
        titlebar.setRect(0, 0, WIDTH, 0);
        add(titlebar);

        BitmapTextMultiline message = PixelScene.createMultiline(item instanceof DriedRose ? TXT_ROSE : TXT_RAT, 6);
        message.maxWidth = WIDTH;
        message.measure();
        message.y = titlebar.bottom() + GAP;
        add(message);

        RedButton btnWeapon = new RedButton(Ghost.Quest.weapon.toString()) {
            @Override
            protected void onClick() {
                selectReward(ghost, item, Ghost.Quest.weapon);
            }
        };
        btnWeapon.setRect(0, message.y + message.height() + GAP, WIDTH, BTN_HEIGHT);
        add(btnWeapon);

        RedButton btnArmor = new RedButton(Ghost.Quest.armor.toString()) {
            @Override
            protected void onClick() {
                selectReward(ghost, item, Ghost.Quest.armor);
            }
        };
        btnArmor.setRect(0, btnWeapon.bottom() + GAP, WIDTH, BTN_HEIGHT);
        add(btnArmor);

        resize(WIDTH, (int) btnArmor.bottom());
    }

    private void selectReward(Ghost ghost, Item item, Item reward) {

        hide();

        item.detach(Dungeon.hero.belongings.backpack);

        if (reward.doPickUp(Dungeon.hero)) {
            GLog.i(Hero.TXT_YOU_NOW_HAVE, reward.name());
        } else {
            Dungeon.level.drop(reward, ghost.pos).sprite.drop();
        }

        ghost.yell(Ml.g("visuals.windows.wndsadghost.yell"));
        ghost.die(null);

        Ghost.Quest.complete();
    }
}
