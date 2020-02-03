/**
 * Created by Migue-GR. on 13/04/2018.
 * @MigueNightcore
 */

public class Tooltips {

    public static void showToolTip(View v, int gravity, String message, final int code, final boolean[] TOOLTIPS_ON_SCREEN, Tooltip[] TOOLTIP){
        if(!TOOLTIPS_ON_SCREEN[code]) {
            closeTooltips(code, TOOLTIPS_ON_SCREEN, TOOLTIP);
            TOOLTIP[code] = new Tooltip.Builder(v)
                    .setText(message + "   ×")
                    .setTextColor(Color.WHITE)
                    .setBackgroundColor(Color.BLACK)
                    .setGravity(gravity)
                    .setTextStyle(1)
                    .setCornerRadius(8f)
                    .setDismissOnClick(true)
                    .setOnDismissListener(
                            new OnDismissListener() {
                                @Override
                                public void onDismiss() {
                                    TOOLTIPS_ON_SCREEN[code] = false;
                                }
                            })
                    .show();
            TOOLTIPS_ON_SCREEN[code] = true;
            dismissTooltips(code, TOOLTIP);
        }
    }

    //Close tooltip when the time ends
    private static void dismissTooltips(final int cod, final Tooltip[] TOOLTIP) {
        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                //code
            }

            public void onFinish() {
                TOOLTIP[cod].dismiss();
            }

        }.start();
    }

    //Close Tooltips if is necessary
    private static void closeTooltips(int cod, final boolean[] TOOLTIPS_ON_SCREEN, Tooltip[] TOOLTIP) {
        for(int i = 0; i < TOOLTIPS_ON_SCREEN.length; i++){
            if(TOOLTIPS_ON_SCREEN[i] && i == cod){
                TOOLTIP[i].dismiss();
            }
        }
    }
}
