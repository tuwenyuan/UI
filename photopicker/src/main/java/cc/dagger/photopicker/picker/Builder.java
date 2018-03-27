package cc.dagger.photopicker.picker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * Created by wzfu on 16/5/21.
 */
public abstract class Builder {

    public void start(Fragment fragment){
        start(fragment, 0, 0);
    }

    public void start(Activity activity) {
        start(activity, 0, 0);
    }

    public abstract void start(Activity aty, int enterAnim, int exitAnim);

    public abstract void start(Fragment fragment, int enterAnim, int exitAnim);

    protected void overridePendingTransition(Activity aty, int enterAnim, int exitAnim){
        if(enterAnim != 0 || exitAnim != 0){
            aty.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    protected boolean hasPermission(Context mContext) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            // Permission was added in API Level 16
            return ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    protected abstract Intent createIntent(Activity aty);
}