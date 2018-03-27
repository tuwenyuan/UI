package cc.dagger.photopicker.picker;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import cc.dagger.photopicker.MultiImageSelectorActivity;
import cc.dagger.photopicker.PhotoPicker;
import cc.dagger.photopicker.R;

/**
 * Created by wzfu on 16/5/21.
 */
public class PhotoSelectBuilder extends Builder{

    protected PickerParams params;

    public PhotoSelectBuilder(PickerParams params) {
        this.params = params;
        if(params.filter == null){
            params.filter = new PhotoFilter();
        }
    }

    @Override
    protected Intent createIntent(Activity aty) {
        Intent intent = new Intent(aty, MultiImageSelectorActivity.class);
        intent.putExtra(PhotoPicker.PARAMS_PICKER, params);
        return intent;
    }

    @Override
    public void start(Activity aty, int enterAnim, int exitAnim) {

        if(!hasPermission(aty)) {
            Toast.makeText(aty, R.string.error_no_permission, Toast.LENGTH_SHORT).show();
            return;
        }

        aty.startActivityForResult(createIntent(aty), PhotoPicker.REQUEST_SELECTED);
        overridePendingTransition(aty, enterAnim, exitAnim);
    }

    @Override
    public void start(Fragment fragment, int enterAnim, int exitAnim) {
        if(!hasPermission(fragment.getActivity())) {
            Toast.makeText(fragment.getActivity(), R.string.error_no_permission, Toast.LENGTH_SHORT).show();
            return;
        }

        fragment.startActivityForResult(createIntent(fragment.getActivity()), PhotoPicker.REQUEST_SELECTED);
        overridePendingTransition(fragment.getActivity(), enterAnim, exitAnim);
    }
}
