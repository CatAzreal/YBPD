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
package com.jinkeloid.yellowbananapixeldungeon.visuals.sprites;

import com.watabou.noosa.TextureFilm;
import com.jinkeloid.yellowbananapixeldungeon.visuals.Assets;
import com.jinkeloid.yellowbananapixeldungeon.actors.Char;

public class ElementalSprite extends MobSprite {

//    private int cellToAttack;

	public ElementalSprite() {
		super();
		
		texture( Assets.ELEMENTAL );
		
		TextureFilm frames = new TextureFilm( texture, 12, 14 );
		
		idle = new Animation( 10, true );
		idle.frames( frames, 0, 1, 2 );
		
		run = new Animation( 12, true );
		run.frames( frames, 0, 1, 3 );
		
		attack = new Animation( 15, false );
		attack.frames( frames, 4, 5, 6 );
		
		die = new Animation( 15, false );
		die.frames( frames, 7, 8, 9, 10, 11, 12, 13, 12 );

        cast = attack.clone();
		
		play( idle );
	}
	
	@Override
	public void link( Char ch ) {
		super.link( ch );
		add( State.BURNING );
	}
	
	@Override
	public void die() {
		super.die();
		remove( State.BURNING );
	}

//    public void cast(int cell) {
//
//        turnTo( ch.pos , cell );
//        play(cast);
//
//        MagicMissile.fire( parent, ch.pos, cell,
//                new Callback() {
//                    @Override
//                    public void call() {
//                        ((Elemental)ch).onZapComplete();
//                    }
//                } );
//        Sample.INSTANCE.play( Assets.SND_ZAP );
//    }
//
//    @Override
//    public void onComplete( Animation anim ) {
//        if (anim == cast) {
//            idle();
//        }
//        super.onComplete( anim );
//    }
	
	@Override
	public int blood() {
		return 0xFFFF7D13;
	}
}
