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
package com.consideredhamster.yetanotherpixeldungeon.items.weapons.enchantments;

import com.consideredhamster.yetanotherpixeldungeon.Element;
import com.consideredhamster.yetanotherpixeldungeon.actors.Char;
import com.consideredhamster.yetanotherpixeldungeon.actors.blobs.Thunderstorm;
import com.consideredhamster.yetanotherpixeldungeon.items.wands.Wand;
import com.consideredhamster.yetanotherpixeldungeon.items.wands.WandOfLightning;
import com.consideredhamster.yetanotherpixeldungeon.items.weapons.Weapon;
import com.consideredhamster.yetanotherpixeldungeon.multilang.Ml;
import com.consideredhamster.yetanotherpixeldungeon.visuals.effects.Lightning;
import com.consideredhamster.yetanotherpixeldungeon.visuals.sprites.ItemSprite;
import com.watabou.utils.Random;

import java.util.HashSet;

public class Shocking extends Weapon.Enchantment {

    @Override
    public ItemSprite.Glowing glowing() {
        return WHITE;
    }

    @Override
    public Class<? extends Wand> wandBonus() {
        return WandOfLightning.class;
    }

    @Override
    protected String name_p() {
        return Ml.g("items.weapons.enchantments.shocking.name_p");
    }

    @Override
    protected String name_n() {
        return Ml.g("items.weapons.enchantments.shocking.name_n");
    }

    @Override
    protected String desc_p() {
        return Ml.g("items.weapons.enchantments.shocking.desc_p");
    }

    @Override
    protected String desc_n() {
        return Ml.g("items.weapons.enchantments.shocking.desc_n");
    }

    @Override
    protected boolean proc_p(Char attacker, Char defender, int damage) {

        HashSet<Char> affected = Thunderstorm.spreadFrom(defender.pos);

        if (affected != null && !affected.isEmpty()) {
            for (Char ch : affected) {

                int power = Random.IntRange(damage / 3, damage / 2);

                ch.damage(ch == defender ? power : power / 2, this, Element.SHOCK);

            }
        }

        defender.sprite.parent.add(new Lightning(defender.pos, defender.pos));

        return true;
    }

    @Override
    protected boolean proc_n(Char attacker, Char defender, int damage) {

        HashSet<Char> affected = Thunderstorm.spreadFrom(attacker.pos);

        if (affected != null && !affected.isEmpty()) {
            for (Char ch : affected) {
                ch.damage(ch == attacker ? damage : damage / 2, this, Element.SHOCK);
            }
        }

        attacker.sprite.parent.add(new Lightning(attacker.pos, attacker.pos));

        return true;
    }
}
