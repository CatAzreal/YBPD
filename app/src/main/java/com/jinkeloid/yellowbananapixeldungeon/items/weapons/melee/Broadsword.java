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
package com.jinkeloid.yellowbananapixeldungeon.items.weapons.melee;

import com.jinkeloid.yellowbananapixeldungeon.visuals.sprites.ItemSpriteSheet;

public class Broadsword extends MeleeWeaponHeavyOH {
	
	{
		name = "broadsword";
		image = ItemSpriteSheet.SWORD;
	}
	
	public Broadsword() {
		super( 3 );
	}
	
	@Override
	public String desc() {
		return "The razor-sharp length of steel blade shines reassuringly.";
	}

	@Override
	public Type weaponType() {
		return Type.M_SWORD;
	}
}
