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
package com.jinkeloid.yellowbananapixeldungeon.items.armours.body;

import java.util.ArrayList;

import com.jinkeloid.yellowbananapixeldungeon.actors.hero.Hero;
import com.jinkeloid.yellowbananapixeldungeon.misc.utils.GLog;
import com.watabou.utils.Bundle;

abstract public class ClassArmor extends BodyArmor {
	
	private static final String TXT_LOW_HEALTH		= "Your health is too low!";
	private static final String TXT_NOT_EQUIPPED	= "You need to be wearing this armor to use its special power!";
	
	{
//		bonusKnown = true;
//		cursedKnown = true;
//		defaultAction = special();
	}
	
	public ClassArmor() {
		super( 6 );
	}
	
	public static ClassArmor upgrade ( Hero owner, BodyArmor armor ) {
		
		ClassArmor classArmor = null;
		
//		switch (owner.heroClass) {
//		case WARRIOR:
//			classArmor = new WarriorArmor();
//			break;
//		case BRIGAND:
//			classArmor = new RogueArmor();
//			break;
//		case SCHOLAR:
//			classArmor = new MageArmor();
//			break;
//		case ACOLYTE:
//			classArmor = new HuntressArmor();
//			break;
//		}
		
//		classArmor.STR = armor.STR;
//		classArmor.DR = armor.DR;
		
		classArmor.inscribe( armor.glyph );
		
		return classArmor;
	}
	
//	private static final String ARMOR_STR	= "STR";
//	private static final String ARMOR_DR	= "DR";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
//		bundle.put( ARMOR_STR, STR );
//		bundle.put( ARMOR_DR, DR );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
//		STR = bundle.getInt( ARMOR_STR );
//		DR = bundle.getInt( ARMOR_DR );
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (hero.HP >= 3 && isEquipped( hero )) {
			actions.add( special() );
		}
		return actions;
	}
	
	@Override
	public void execute( Hero hero, String action ) {
		if (action == special()) {
			
			if (hero.HP < 3) {
				GLog.w( TXT_LOW_HEALTH );
			} else if (!isEquipped( hero )) {
				GLog.w( TXT_NOT_EQUIPPED );
			} else {
				curUser = hero;
				doSpecial();
			}
			
		} else {	
			super.execute( hero, action );		
		}
	}
	
	abstract public String special();
	abstract public void doSpecial();
	
	@Override
	public int price() {
		return 0;
	}
	
	@Override
	public String desc() {
		return "The thing looks awesome!";
	}
}
