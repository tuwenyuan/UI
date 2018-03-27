package cc.dagger.photopicker.picker;

/**
 * Created by wzfu on 16/5/21.
 */
public class SingleSelect extends PhotoSelectBuilder {

    public SingleSelect(PickerParams params) {
        super(params);
        super.params.mode = SelectMode.SINGLE;
    }
}
