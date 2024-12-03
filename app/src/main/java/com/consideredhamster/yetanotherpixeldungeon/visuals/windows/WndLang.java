/*
 * Pixel Dungeon
 * Adapted for Language Switching Window
 */
package com.consideredhamster.yetanotherpixeldungeon.visuals.windows;

import com.consideredhamster.yetanotherpixeldungeon.multilang.Ml;
import com.consideredhamster.yetanotherpixeldungeon.scenes.GameScene;
import com.consideredhamster.yetanotherpixeldungeon.scenes.PixelScene;
import com.consideredhamster.yetanotherpixeldungeon.visuals.ui.RedButton;
import com.consideredhamster.yetanotherpixeldungeon.visuals.ui.Window;
import com.watabou.noosa.BitmapTextMultiline;
import com.watabou.noosa.Game;
import com.watabou.noosa.tweeners.Tweener;

public class WndLang extends Window {

    private static final String TITLE = Ml.g("visuals.windows.wndlang.title");
    private static final String[] LANGUAGES = {
            "English",
            "Chinese",
            "Русский",
            "Español",
//            "Français",
//            "Deutsch",
//            "Italiano",
//            "Português",
//            "Japanese",
//            "Korean"
    };
    private static final int WIDTH = 112;
    private static final int BTN_HEIGHT = 20;
    private static final int GAP = 2;

    private static final String EXTRA_TEXT = "All languages except English and Chinese are machine-translated, incorrect translations may occur. If you would like to contribute to the translation, please visit the GitHub page or take a look at the Paratranz project.";

    // Configurable delay in seconds
    private static final float RESTART_DELAY = 3f; // Set your desired delay here

    public WndLang() {
        super();

        float posY = 0;

        // Add title at the top
        BitmapTextMultiline txtTitle = PixelScene.createMultiline(TITLE, 8);
        txtTitle.hardlight(TITLE_COLOR);
        txtTitle.measure();
        txtTitle.x = (WIDTH - txtTitle.width()) / 2;
        txtTitle.y = posY;
        add(txtTitle);

        posY += txtTitle.height() + GAP;

        for (final String language : LANGUAGES) {
            RedButton btnLanguage = new RedButton(language) {
                @Override
                protected void onClick() {
                    switch (language) {
                        case "English":
                            Ml.saveLanguageSetting("en");
                            break;
                        case "Chinese":
                            Ml.saveLanguageSetting("cn");
                            break;
                        case "Русский":
                            Ml.saveLanguageSetting("ru");
                            break;
                        case "Español":
                            Ml.saveLanguageSetting("es");
                            break;
                        case "Français":
                            Ml.saveLanguageSetting("fr");
                            break;
                        case "Deutsch":
                            Ml.saveLanguageSetting("de");
                            break;
                        case "Italiano":
                            Ml.saveLanguageSetting("it");
                            break;
                        case "Português":
                            Ml.saveLanguageSetting("pt");
                            break;
                        case "Japanese":
                            Ml.saveLanguageSetting("ja");
                            break;
                        case "Korean":
                            Ml.saveLanguageSetting("ko");
                            break;
                    }
                    parent.add(new WndMessage("YAPD will quit in 3 seconds to apply the language changes, if the game fails to automatically quit, please manually quit the application instead."));
                    Game.scene().add(new RestartTweener(RESTART_DELAY));
                }
            };
            btnLanguage.setRect(0, posY, WIDTH, BTN_HEIGHT);
            add(btnLanguage);

            posY += BTN_HEIGHT + GAP;
        }

        // Leave some space below for extra text
        posY += 10;

        BitmapTextMultiline txtExtra = PixelScene.createMultiline(EXTRA_TEXT, 6);
        txtExtra.maxWidth(WIDTH);
        txtExtra.setPos(0, posY);
        add(txtExtra);

        resize(WIDTH, (int) (posY + txtExtra.height()));
    }

    private class RestartTweener extends Tweener {

        public RestartTweener(float interval) {
            super(null, interval);
        }

        @Override
        protected void updateValues(float progress) {
            // No values to update during the tween
        }

        @Override
        protected void onComplete() {
            super.onComplete();
            Game.instance.finish();
            System.exit(0);

            //todo: restartapplication fries the app, not only the languages failed to be changed, touch controls are also failed
//            if (!restartApplication()) {
//                Game.instance.finish();
//                System.exit(0);
//            }
        }
    }

    /**
     * Attempts to restart the application.
     * Returns true if the restart was initiated successfully, false otherwise.
     */
    private boolean restartApplication() {
        try {
            android.content.Context context = Game.instance.getApplicationContext();

            android.content.Intent intent = context.getPackageManager()
                    .getLaunchIntentForPackage(context.getPackageName());
            if (intent != null) {
                intent.addFlags(android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
