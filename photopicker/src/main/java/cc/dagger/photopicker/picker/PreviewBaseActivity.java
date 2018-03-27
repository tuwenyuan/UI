package cc.dagger.photopicker.picker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import cc.dagger.photopicker.PhotoPicker;
import cc.dagger.photopicker.R;

/**
 * Created by wzfu on 16/5/29.
 */
public abstract class PreviewBaseActivity extends AppCompatActivity {

    public static final String CURRENT_ITEM = "photo_preview_current_item";
    public static final String PHOTO_PATHS = "photo_preview_paths";

    public int currentItem;
    public ArrayList<String> paths;
    public List<String> tmpPaths = new ArrayList<>();

    private MenuItem menuItemDelete;
    private static final int menuItemDeleteId = Menu.FIRST + 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentViewId());

        currentItem = getIntent().getIntExtra(CURRENT_ITEM, 0);
        paths = getIntent().getStringArrayListExtra(PHOTO_PATHS);

        if(paths == null) {
            paths = new ArrayList<>();
        }
        tmpPaths.addAll(paths);

        initWidget();
    }

    protected abstract void initWidget();

    // 布局文件Id
    protected abstract int getContentViewId();

    protected void back(){
        int resultCode = tmpPaths.size() == paths.size()
                ? RESULT_CANCELED : RESULT_OK;
        Intent intent = new Intent();
        intent.putStringArrayListExtra(PhotoPicker.PATHS, paths);
        setResult(resultCode, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                back();
                return true;

            case menuItemDeleteId:
                deleteImage();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // 返回键处理
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuItemDelete = menu.add(Menu.NONE, menuItemDeleteId, 0, getString(R.string.delete));
        menuItemDelete.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    public abstract void updateTitle();

    public abstract void deleteImage();
}
