package cc.dagger.photopicker.picker;

import java.util.ArrayList;

/**
 * Created by wzfu on 16/5/21.
 */
public class MultiSelect extends PhotoSelectBuilder {

    public MultiSelect(PickerParams params) {
        super(params);
        super.params.mode = SelectMode.MULTI;
    }

    public MultiSelect selectedPaths(ArrayList<String> paths) {
        super.params.selectedPaths = paths;
        return this;
    }

    public MultiSelect maxPickSize(int maxPickSize) {
        if(maxPickSize > 0) {
            super.params.maxPickSize = maxPickSize;
        }
        return this;
    }
}
