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

import com.consideredhamster.yetanotherpixeldungeon.YetAnotherPixelDungeon;
import com.consideredhamster.yetanotherpixeldungeon.items.Heap;
import com.consideredhamster.yetanotherpixeldungeon.items.Heap.Type;
import com.consideredhamster.yetanotherpixeldungeon.items.Item;
import com.consideredhamster.yetanotherpixeldungeon.misc.utils.Utils;
import com.consideredhamster.yetanotherpixeldungeon.multilang.Ml;
import com.consideredhamster.yetanotherpixeldungeon.scenes.PixelScene;
import com.consideredhamster.yetanotherpixeldungeon.visuals.sprites.ItemSprite;
import com.consideredhamster.yetanotherpixeldungeon.visuals.ui.ItemSlot;
import com.consideredhamster.yetanotherpixeldungeon.visuals.ui.Window;
import com.watabou.noosa.BitmapTextMultiline;

public class WndInfoItem extends Window {

    private static final String TXT_CHEST = Ml.g("visuals.windows.wndinfoitem.txt_chest");
    private static final String TXT_LOCKED_CHEST = Ml.g("visuals.windows.wndinfoitem.txt_locked_chest");
    private static final String TXT_CRYSTAL_CHEST = Ml.g("visuals.windows.wndinfoitem.txt_crystal_chest");
    private static final String TXT_TOMB = Ml.g("visuals.windows.wndinfoitem.txt_tomb");
    private static final String TXT_SKELETON = Ml.g("visuals.windows.wndinfoitem.txt_skeleton");
    private static final String TXT_WONT_KNOW = Ml.g("visuals.windows.wndinfoitem.txt_wont_know");
    private static final String TXT_NEED_KEY = Ml.g("visuals.windows.wndinfoitem.txt_need_key", TXT_WONT_KNOW);
    private static final String TXT_INSIDE = Ml.g("visuals.windows.wndinfoitem.txt_inside");
    private static final String TXT_OWNER = Ml.g("visuals.windows.wndinfoitem.txt_owner");
    private static final String TXT_REMAINS = Ml.g("visuals.windows.wndinfoitem.txt_remains");

    private static final float GAP = 2;

    private static final int WIDTH_P = 120;
    private static final int WIDTH_L = 240;

    private BitmapTextMultiline normal;
    private BitmapTextMultiline highlighted;

    public WndInfoItem(Heap heap) {

        super();

        if (heap.type == Heap.Type.HEAP || heap.type == Heap.Type.FOR_SALE) {

            Item item = heap.peek();

            int color = TITLE_COLOR;
            if (item.isIdentified() && item.bonus > 0) {
                color = ItemSlot.UPGRADED;
            } else if (item.isIdentified() && item.bonus < 0) {
                color = ItemSlot.DEGRADED;
            }
            fillFields(item.image(), item.glowing(), color, item.toString(), item.info());

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
                info = Utils.format(TXT_INSIDE, Utils.indefinite(heap.peek().name()));
            } else {
                title = TXT_LOCKED_CHEST;
                info = TXT_NEED_KEY;
            }

            fillFields(heap.image(), heap.glowing(), TITLE_COLOR, title, info);

        }
    }

    public WndInfoItem(Item item) {

        super();

        int color = TITLE_COLOR;
        if (item.isIdentified() && item.bonus > 0) {
            color = ItemSlot.UPGRADED;
        } else if (item.isIdentified() && item.bonus < 0) {
            color = ItemSlot.DEGRADED;
        }

        fillFields(item.image(), item.glowing(), color, item.toString(), item.info());
    }

    private void fillFields(int image, ItemSprite.Glowing glowing, int titleColor, String title, String info) {

        int width = YetAnotherPixelDungeon.landscape() ? WIDTH_L : WIDTH_P;

        IconTitle titlebar = new IconTitle();
        titlebar.icon(new ItemSprite(image, glowing));
        titlebar.label(Utils.capitalize(title), titleColor);
        titlebar.setRect(0, 0, width, 0);
        add(titlebar);

//        Highlighter hl = new Highlighter(info);

        normal = PixelScene.createMultiline(info, 6);
        normal.maxWidth = width;
        normal.measure();
        normal.x = titlebar.left();
        normal.y = titlebar.bottom() + GAP;
        add(normal);

//        if (hl.isHighlighted()) {
//            normal.mask = hl.inverted();
//
//            highlighted = PixelScene.createMultiline(hl.text, 6);
//            highlighted.maxWidth = normal.maxWidth;
//            highlighted.measure();
//            highlighted.x = normal.x;
//            highlighted.y = normal.y;
//            add(highlighted);
//
//            highlighted.mask = hl.mask;
//            highlighted.hardlight(TITLE_COLOR);
//        }

//		BitmapTextMultiline txtInfo = PixelScene.createMultiline( info, 6 );
//		txtInfo.maxWidth = WIDTH;
//		txtInfo.measure();
//		txtInfo.x = titlebar.left();
//		txtInfo.y = titlebar.bottom() + GAP;
//		add( txtInfo );

//		resize( WIDTH, (int)(txtInfo.y + txtInfo.height()) );
        resize(width, (int) (normal.y + normal.height()));
    }
}
