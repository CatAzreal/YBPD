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
package com.consideredhamster.yetanotherpixeldungeon.items.weapons.throwing;

import com.consideredhamster.yetanotherpixeldungeon.Dungeon;
import com.consideredhamster.yetanotherpixeldungeon.actors.Actor;
import com.consideredhamster.yetanotherpixeldungeon.actors.Char;
import com.consideredhamster.yetanotherpixeldungeon.actors.buffs.bonuses.Invisibility;
import com.consideredhamster.yetanotherpixeldungeon.actors.buffs.debuffs.Vertigo;
import com.consideredhamster.yetanotherpixeldungeon.actors.buffs.special.Satiety;
import com.consideredhamster.yetanotherpixeldungeon.actors.hero.Hero;
import com.consideredhamster.yetanotherpixeldungeon.actors.mobs.Mob;
import com.consideredhamster.yetanotherpixeldungeon.items.Item;
import com.consideredhamster.yetanotherpixeldungeon.items.rings.RingOfDurability;
import com.consideredhamster.yetanotherpixeldungeon.items.weapons.Weapon;
import com.consideredhamster.yetanotherpixeldungeon.levels.Level;
import com.consideredhamster.yetanotherpixeldungeon.misc.mechanics.Ballistica;
import com.consideredhamster.yetanotherpixeldungeon.misc.utils.GLog;
import com.consideredhamster.yetanotherpixeldungeon.multilang.Ml;
import com.consideredhamster.yetanotherpixeldungeon.scenes.CellSelector;
import com.consideredhamster.yetanotherpixeldungeon.scenes.GameScene;
import com.consideredhamster.yetanotherpixeldungeon.visuals.Assets;
import com.consideredhamster.yetanotherpixeldungeon.visuals.effects.Chains;
import com.consideredhamster.yetanotherpixeldungeon.visuals.sprites.CharSprite;
import com.consideredhamster.yetanotherpixeldungeon.visuals.sprites.ItemSpriteSheet;
import com.consideredhamster.yetanotherpixeldungeon.visuals.sprites.MissileSprite;
import com.consideredhamster.yetanotherpixeldungeon.visuals.ui.QuickSlot;
import com.consideredhamster.yetanotherpixeldungeon.visuals.ui.TagAttack;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;

public abstract class ThrowingWeapon extends Weapon {

//	private static final String TXT_MISSILES	= "Missile weapon";

//	private static final String TXT_YES			= "Yes, I know what I'm doing";
//	private static final String TXT_NO			= "No, I changed my mind";
//	private static final String TXT_R_U_SURE	=
//		"Do you really want to equip it as a melee weapon?";

    private static final String TXT_TARGET_CHARMED = Ml.g("items.weapons.throwing.throwingweapon.txt_target_charmed");

    private static final String AC_SHOOT = Ml.g("items.weapons.throwing.throwingweapon.ac_shoot");

    public ThrowingWeapon(int tier) {
        super();

        this.tier = tier;

        stackable = true;
    }

    public boolean returnsWhenThrown() {
        return false;
    }

    @Override
    public String equipAction() {
        return AC_SHOOT;
    }

    @Override
    public String quickAction() {
        return isEquipped(Dungeon.hero) ? AC_UNEQUIP : AC_EQUIP;
    }

//    @Override
//    public boolean isEquipped( Hero hero ) {
//        return hero.belongings.weap2 != null && getClass().equals(hero.belongings.weap2.getClass());
//    }

    @Override
    public int penaltyBase(Hero hero, int str) {

        return super.penaltyBase(hero, str) + tier * 4 - 4;

    }

    @Override
    public float stealingDifficulty() {
        return 0.75f;
    }

    @Override
    public int lootChapter() {
        return tier;
    }

    @Override
    public int lootLevel() {
        return (lootChapter() - 1) * 6 + 6 * quantity / baseAmount();
    }

    public int baseAmount() {
        return 1;
    }

    @Override
    public int priceModifier() {
        return 2;
    }

    @Override
    public Item random() {

        quantity = Random.Int(baseAmount(), baseAmount() * 2);
        quantity = quantity * (4 + Dungeon.chapter() - lootChapter()) / 4;
        quantity = Math.max(1, quantity);

        return this;
    }

    @Override
    public float breakingRateWhenShot() {
        return 0.05f / Dungeon.hero.ringBuffs(RingOfDurability.Durability.class);
    }

//	@Override
//	public ArrayList<String> actions( Hero hero ) {
//		ArrayList<String> actions = super.actions( hero );

//        actions.remove( AC_EQUIP );
//        actions.remove( AC_UNEQUIP );

//		return actions;
//	}

//	@Override
//	protected void onThrow( int cell ) {
//		Char enemy = Actor.findChar( cell );

//		if (enemy == null || enemy == curUser) {
//			super.onThrow( cell );
//		} else {
//			if (!curUser.shoot( enemy, this )) {
//				miss( cell );
//			}
//		}

//        if (enemy != null && enemy != curUser) {
//            curUser.shoot( enemy, this );
//		}
//
//        super.onThrow( cell );
//	}

//	protected void miss( int cell ) {
//		super.onThrow( cell );
//	}

//	@Override
//	public void proc( Char attacker, Char defender, int damage ) {
//
//		super.proc( attacker, defender, damage );
//
//		Hero hero = (Hero)attacker;
//		if (hero.rangedWeapon == null && stackable) {
//			if (quantity == 1) {
//				doUnequip( hero, false, false );
//			} else {
//				detach( null );
//			}
//		}
//	}

    @Override
    public boolean doEquip(final Hero hero) {

        if (!this.isEquipped(hero)) {

            detachAll(hero.belongings.backpack);

            if (QuickSlot.quickslot1.value == getClass() && (hero.belongings.weap2 == null || hero.belongings.weap2.bonus >= 0))
                QuickSlot.quickslot1.value = hero.belongings.weap2 != null && hero.belongings.weap2.stackable ? hero.belongings.weap2.getClass() : hero.belongings.weap2;

            if (QuickSlot.quickslot2.value == getClass() && (hero.belongings.weap2 == null || hero.belongings.weap2.bonus >= 0))
                QuickSlot.quickslot2.value = hero.belongings.weap2 != null && hero.belongings.weap2.stackable ? hero.belongings.weap2.getClass() : hero.belongings.weap2;

            if (QuickSlot.quickslot3.value == getClass() && (hero.belongings.weap2 == null || hero.belongings.weap2.bonus >= 0))
                QuickSlot.quickslot3.value = hero.belongings.weap2 != null && hero.belongings.weap2.stackable ? hero.belongings.weap2.getClass() : hero.belongings.weap2;

            if (hero.belongings.weap2 == null || hero.belongings.weap2.doUnequip(hero, true, false)) {

                hero.belongings.weap2 = this;
                activate(hero);

                QuickSlot.refresh();
                GLog.i(TXT_EQUIP, name());
                hero.spendAndNext(time2equip(hero));
                return true;

            } else {

                collect(hero.belongings.backpack);
                return false;

            }
        } else {

            GLog.w(TXT_ISEQUIPPED, name());
            return false;

        }
    }

    @Override
    public boolean doPickUp(Hero hero) {

        Class<?> c = getClass();

        if (hero.belongings.weap2 != null && hero.belongings.weap2.getClass() == c) {

            hero.belongings.weap2.quantity += quantity;

            GameScene.pickUp(this);
            Sample.INSTANCE.play(Assets.SND_ITEM);

//            hero.spendAndNext(TIME_TO_PICK_UP);

            QuickSlot.refresh();

            return true;

        } else {

            return super.doPickUp(hero);

        }

    }

    @Override
    public String info() {

        final String p = "\n\n";

        int heroStr = Dungeon.hero.STR();
        int itemStr = strShown(isIdentified());
        float penalty = GameMath.gate(0, penaltyBase(Dungeon.hero, strShown(isIdentified())), 20) * 2.5f;

        StringBuilder info = new StringBuilder(desc());

        info.append(p);

        info.append(Ml.g("items.weapons.throwing.throwingweapon.info_3", tier, !descType().isEmpty() ? descType() : "", itemStr,
                isRepairable() ? Ml.g("items.weapons.throwing.throwingweapon.info_repairable", stateToString(state)) : "",
                min(), max()));

        info.append(p);

        if (itemStr > heroStr) {
            info.append(Ml.g("items.weapons.throwing.throwingweapon.info_4", penalty, (int) (100 - 10000 / (100 + penalty))));
        } else if (itemStr < heroStr) {
            String decrease = penalty > 0 ? Ml.g("items.weapons.throwing.throwingweapon.info_decrease_only", penalty) : Ml.g("items.weapons.throwing.throwingweapon.info_not_decreased");
            info.append(Ml.g("items.weapons.throwing.throwingweapon.info_5", decrease, ((float) (heroStr - itemStr) / 2)));
        } else {
            String decreased = penalty > 0 ? Ml.g("items.weapons.throwing.throwingweapon.info_decreased_by", penalty) : Ml.g("items.weapons.throwing.throwingweapon.info_not_decreased");
            info.append(Ml.g("items.weapons.throwing.throwingweapon.info_6", decreased));
        }

        info.append(p);

        if (isEquipped(Dungeon.hero)) {
            info.append(Ml.g("items.weapons.throwing.throwingweapon.info_7", name));
        } else if (Dungeon.hero.belongings.backpack.contains(this)) {
            info.append(Ml.g("items.weapons.throwing.throwingweapon.info_8", name));
        } else {
            info.append(Ml.g("items.weapons.throwing.throwingweapon.info_9", name));
        }

        info.append(Ml.g("items.weapons.throwing.throwingweapon.info_10", lootChapterAsString()));

        return info.toString();
    }

    @Override
    public void execute(Hero hero, String action) {

        if (action == AC_SHOOT) {

            curUser = hero;
            curItem = this;

            if (!isEquipped(hero)) {

                super.execute(hero, AC_THROW);

            } else {

                GameScene.selectCell(shooter);

            }


        } else {

            super.execute(hero, action);

        }
    }

    public static CellSelector.Listener shooter = new CellSelector.Listener() {

        @Override
        public void onSelect(Integer target) {

            if (target != null) {

                final ThrowingWeapon curWeap = (ThrowingWeapon) ThrowingWeapon.curItem;

//                int tmp_cell = target;

                if (curUser.buff(Vertigo.class) != null) {
                    target += Level.NEIGHBOURS8[Random.Int(8)];
                }

                final int cell = Ballistica.cast(curUser.pos, target, false, true);

                final Char ch = Actor.findChar(cell);

                if (ch != null && curUser != ch && Dungeon.visible[cell]) {
                    QuickSlot.target(curItem, ch);
                    TagAttack.target((Mob) ch);
                }

                curUser.sprite.cast(cell, new Callback() {
                    @Override
                    public void call() {

                        curUser.busy();

                        if (curWeap instanceof Harpoons) {
//                        curUser.sprite.parent.add(new Chains(curUser.pos, cell, ch != null && ch.isHeavy()));
                            // draw chains going from the user to the target
                            curUser.sprite.parent.add(new Chains(curUser.pos, cell, false));
                        }

                        ((MissileSprite) curUser.sprite.parent.recycle(MissileSprite.class)).
                                reset(curUser.pos, cell, curUser.belongings.weap2, new Callback() {
                                    @Override
                                    public void call() {
                                        ((ThrowingWeapon) curUser.belongings.weap2).onShoot(cell, curWeap);
                                    }
                                });

                        curUser.buff(Satiety.class).decrease(Satiety.POINT * curWeap.str() / curUser.STR());

                    }
                });

                Invisibility.dispel();
                Sample.INSTANCE.play(Assets.SND_MISS, 0.6f, 0.6f, 1.5f);

            }
        }

        @Override
        public String prompt() {
            return Ml.g("items.weapons.throwing.throwingweapon.prompt");
        }
    };

    public void onShoot(int cell, Weapon weapon) {
        Char enemy = Actor.findChar(cell);

        // FIXME

        if (enemy == curUser) {

            super.onThrow(cell);

        } else if (enemy == null || !curUser.shoot(enemy, weapon)) {

            if (returnsWhenThrown()) {

                if (this instanceof Harpoons) {

                    curUser.sprite.parent.add(new Chains(curUser.pos, cell, true));
                    ((MissileSprite) curUser.sprite.parent.recycle(MissileSprite.class)).
                            reset(cell, curUser.pos, ItemSpriteSheet.HARPOON_RETURN, null);

                } else {

                    ((MissileSprite) curUser.sprite.parent.recycle(MissileSprite.class)).
                            reset(cell, curUser.pos, curItem.imageAlt(), null);

                }

                curUser.belongings.weap2 = this;

            } else {
                super.onThrow(cell);
            }

        } else if (Random.Float() > weapon.breakingRateWhenShot()) {

            if (returnsWhenThrown()) {

                curUser.belongings.weap2 = this;
                if (this instanceof Chakrams && ((Chakrams) this).bounce(cell)) {
                    return;
                }

                if (this instanceof Harpoons) {
                    curUser.sprite.parent.add(new Chains(curUser.pos, cell, true));
                } else {
                    ((MissileSprite) curUser.sprite.parent.recycle(MissileSprite.class)).
                            reset(cell, curUser.pos, curItem.imageAlt(), null);
                }

            } else {
                super.onThrow(cell);
            }

        } else {

            enemy.sprite.showStatus(CharSprite.DEFAULT, Ml.g("items.weapons.throwing.throwingweapon.status_broken"));

            if (this instanceof Harpoons) {
                curUser.sprite.parent.add(new Chains(curUser.pos, cell, true));
            }

            if (quantity == 1) {

                doUnequip(curUser, false, false);

            } else {

                detach(null);

            }
        }

        curUser.spendAndNext(1 / weapon.speedFactor(curUser));
        QuickSlot.refresh();
    }
}