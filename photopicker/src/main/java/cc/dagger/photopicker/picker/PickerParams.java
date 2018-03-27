package cc.dagger.photopicker.picker;

import java.util.ArrayList;

/**
 * 图片选择器参数
 * Created by wzfu on 16/5/21.
 */
public class PickerParams implements java.io.Serializable{

    public PhotoFilter filter;

    public boolean showCamera;

    // 选择模式
    public SelectMode mode;

    // 默认最大选择照片数量
    public int maxPickSize = 9;

    // 选择图片列表默认列数
    public int gridColumns = 3;

    public ArrayList<String> selectedPaths;

}
