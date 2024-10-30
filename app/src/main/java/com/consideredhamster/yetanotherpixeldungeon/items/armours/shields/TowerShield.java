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
package com.consideredhamster.yetanotherpixeldungeon.items.armours.shields;

import com.consideredhamster.yetanotherpixeldungeon.visuals.sprites.ItemSpriteSheet;


public class TowerShield extends Shield {

    {
        name = "tower shield";
        image = ItemSpriteSheet.SHIELD_TOWER;
    }

    public TowerShield() {
        super(3);
    }

    @Override
    public String desc() {
        return "Size of this shield allows to cover your whole body behind it.";
    }
}
