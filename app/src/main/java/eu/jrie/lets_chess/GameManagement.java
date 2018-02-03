package eu.jrie.lets_chess;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

interface GameManagement {
    Game getGame();

    void initGame(int beginningTime, int addingTime);
    void resetGame();
    void leaveGame();
    void endGame();
    void pauseGame();
    void unpauseGame();

    void closeFragment(Fragment fragmentToClose);

    void showBoard();
    void shareBoard();

    void manageDraw(final byte color);
    void proceedFailure(final byte color);

    static byte switchColor(final byte c){
        if(c == Const.WHITE) return Const.BLACK;
        return Const.WHITE;
    }

    static void makeToast(final int stringId, final int color, Activity activity){
        Toast toast = new Toast(activity.getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        View toastLayout = activity.getLayoutInflater().inflate(R.layout.toast_draw, activity.findViewById(R.id.toast_layout));
        TextView toastText = toastLayout.findViewById(R.id.toast_text);
        toastText.setText(activity.getResources().getText(stringId));
        int pxY = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, activity.getResources().getDisplayMetrics());
        if(color == Const.WHITE){
            toastLayout.setRotation(180);
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, pxY);
        } else toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, pxY);
        toast.setView(toastLayout);
        toast.show();
    }

}
