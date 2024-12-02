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
package com.consideredhamster.yetanotherpixeldungeon.items.scrolls;

import com.consideredhamster.yetanotherpixeldungeon.Dungeon;
import com.consideredhamster.yetanotherpixeldungeon.Element;
import com.consideredhamster.yetanotherpixeldungeon.actors.buffs.BuffActive;
import com.consideredhamster.yetanotherpixeldungeon.actors.buffs.debuffs.Tormented;
import com.consideredhamster.yetanotherpixeldungeon.actors.mobs.Mob;
import com.consideredhamster.yetanotherpixeldungeon.levels.Level;
import com.consideredhamster.yetanotherpixeldungeon.misc.utils.GLog;
import com.consideredhamster.yetanotherpixeldungeon.multilang.Ml;
import com.consideredhamster.yetanotherpixeldungeon.scenes.GameScene;
import com.consideredhamster.yetanotherpixeldungeon.visuals.Assets;
import com.consideredhamster.yetanotherpixeldungeon.visuals.effects.Flare;
import com.consideredhamster.yetanotherpixeldungeon.visuals.effects.SpellSprite;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;

public class ScrollOfTorment extends Scroll {

    {
        name = Ml.g("items.scrolls.scrolloftorment.name");
        shortName = "To";

        spellSprite = SpellSprite.SCROLL_MASSHARM;
        spellColour = SpellSprite.COLOUR_DARK;
    }

    @Override
    protected void doRead() {

        int count = 0;

        Mob affected = null;

        for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {

            if (Level.fieldOfView[mob.pos]) {

                new Flare(6, 32).color(SpellSprite.COLOUR_DARK, true).show(mob.sprite, 2f);

                int dmg = 10 + curUser.magicPower();

                mob.damage(mob.currentHealthValue() * dmg / 100, curUser, Element.MIND);
                BuffActive.addFromDamage(mob, Tormented.class, dmg);

                affected = mob;
                count++;

            }
        }

        int dmg = Math.min(curUser.HP - 1, curUser.HP * (190 - curUser.magicPower()) / 400);

        curUser.damage(dmg, curUser, Element.MIND);

        GameScene.flash(SpellSprite.COLOUR_DARK - 0x660000);
        Sample.INSTANCE.play(Assets.SND_FALLING);
        Camera.main.shake(4, 0.3f);

        switch (count) {
            case 0:
                GLog.i(Ml.g("items.scrolls.scrolloftorment.nothing"));
                break;
            case 1:
                GLog.i(Ml.g("items.scrolls.scrolloftorment.single", affected.name));
                break;
            default:
                GLog.i(Ml.g("items.scrolls.scrolloftorment.multiple"));
        }


        super.doRead();
    }

    @Override
    public String desc() {
        return Ml.g("items.scrolls.scrolloftorment.desc");
    }

    @Override
    public int price() {
        return isTypeKnown() ? 95 * quantity : super.price();
    }
}
