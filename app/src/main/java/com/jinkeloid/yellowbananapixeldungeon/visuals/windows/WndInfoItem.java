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
package com.jinkeloid.yellowbananapixeldungeon.visuals.windows;

import com.jinkeloid.yellowbananapixeldungeon.YellowBananaPixelDungeon;
import com.watabou.noosa.BitmapTextMultiline;
import com.jinkeloid.yellowbananapixeldungeon.items.Heap;
import com.jinkeloid.yellowbananapixeldungeon.items.Heap.Type;
import com.jinkeloid.yellowbananapixeldungeon.items.Item;
import com.jinkeloid.yellowbananapixeldungeon.scenes.PixelScene;
import com.jinkeloid.yellowbananapixeldungeon.visuals.sprites.ItemSprite;
import com.jinkeloid.yellowbananapixeldungeon.visuals.ui.ItemSlot;
import com.jinkeloid.yellowbananapixeldungeon.visuals.ui.Window;
import com.jinkeloid.yellowbananapixeldungeon.misc.utils.Utils;

public class WndInfoItem extends Window {
	
	private static final String TXT_CHEST			= "Chest";
	private static final String TXT_LOCKED_CHEST	= "Locked chest";
	private static final String TXT_CRYSTAL_CHEST	= "Crystal chest";
	private static final String TXT_TOMB			= "Tomb";
	private static final String TXT_SKELETON		= "Skeletal remains";
	private static final String TXT_WONT_KNOW		= "You won't know what's inside until you open it!";
	private static final String TXT_NEED_KEY		= TXT_WONT_KNOW + " But to open it you need a golden key.";
	private static final String TXT_INSIDE			= "You can see %s inside, but to open the chest you need a golden key.";
	private static final String TXT_OWNER	= 
		"This ancient tomb may contain something useful, " +
		"but its owner will most certainly object to checking.";
	private static final String TXT_REMAINS	= 
		"This is all that's left from one of your predecessors. " +
		"Maybe it's worth checking for any valuables.";
	
	private static final float GAP	= 2;

    private static final int WIDTH_P = 120;
    private static final int WIDTH_L = 240;

    private BitmapTextMultiline normal;
    private BitmapTextMultiline highlighted;
	
	public WndInfoItem( Heap heap ) {
		
		super();
		
		if (heap.type == Heap.Type.HEAP || heap.type == Heap.Type.FOR_SALE) {
			
			Item item = heap.peek();
			
			int color = TITLE_COLOR;
			if (item.isIdentified() && item.bonus > 0) {
				color = ItemSlot.UPGRADED;				
			} else if (item.isIdentified() && item.bonus < 0) {
				color = ItemSlot.DEGRADED;				
			}
			fillFields( item.image(), item.glowing(), color, item.toString(), item.info() );
			
		} else {
			
			String title;
			String info;
			
			if (heap.type == Type.CHEST || heap.type == Type.CHEST_MIMIC) {
				title = TXT_CHEST;
				info = TXT_WONT_KNOW;
			} else if (heap.type == Type.TOMB) {
				title = TXT_TOMB;
				info = TXT_OWNER;
			} else if (heap.type == Type.BONES || heap.type == Type.BONES_CURSED) {
				title = TXT_SKELETON;
				info = TXT_REMAINS;
			} else if (heap.type == Type.CRYSTAL_CHEST) {
				title = TXT_CRYSTAL_CHEST;
				info = Utils.format( TXT_INSIDE, Utils.indefinite( heap.peek().name() ) );
			} else {
				title = TXT_LOCKED_CHEST;
				info = TXT_NEED_KEY;
			}
			
			fillFields( heap.image(), heap.glowing(), TITLE_COLOR, title, info );
			
		}
	}
	
	public WndInfoItem( Item item ) {
		
		super();
		
		int color = TITLE_COLOR;
		if (item.isIdentified() && item.bonus > 0) {
			color = ItemSlot.UPGRADED;				
		} else if (item.isIdentified() && item.bonus < 0) {
			color = ItemSlot.DEGRADED;				
		}
		
		fillFields( item.image(), item.glowing(), color, item.toString(), item.info() );
	}
	
	private void fillFields( int image, ItemSprite.Glowing glowing, int titleColor, String title, String info ) {

        int width = YellowBananaPixelDungeon.landscape() ? WIDTH_L : WIDTH_P ;

		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(image, glowing));
		titlebar.label(Utils.capitalize(title), titleColor);
		titlebar.setRect( 0, 0, width, 0 );
		add( titlebar );

        Highlighter hl = new Highlighter( info );

        normal = PixelScene.createMultiline( hl.text, 6 );
        normal.maxWidth = width;
        normal.measure();
        normal.x = titlebar.left();
        normal.y = titlebar.bottom() + GAP;
        add( normal );

        if (hl.isHighlighted()) {
            normal.mask = hl.inverted();

            highlighted = PixelScene.createMultiline( hl.text, 6 );
            highlighted.maxWidth = normal.maxWidth;
            highlighted.measure();
            highlighted.x = normal.x;
            highlighted.y = normal.y;
            add( highlighted );

            highlighted.mask = hl.mask;
            highlighted.hardlight( TITLE_COLOR );
        }
		
//		BitmapTextMultiline txtInfo = PixelScene.createMultiline( info, 6 );
//		txtInfo.maxWidth = WIDTH;
//		txtInfo.measure();
//		txtInfo.x = titlebar.left();
//		txtInfo.y = titlebar.bottom() + GAP;
//		add( txtInfo );
		
//		resize( WIDTH, (int)(txtInfo.y + txtInfo.height()) );
        resize( width, (int)(normal.y + normal.height()) );
	}
}
