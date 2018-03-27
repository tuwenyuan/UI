package cc.dagger.photopicker.picker;

/**
 * Created by wzfu on 16/5/21.
 */
public class Load {

    private PickerParams params;

    public Load() {
        this.params = new PickerParams();
    }

    public Load filter(PhotoFilter filter) {
        this.params.filter = filter;
        return this;
    }

    public Load showCamera(boolean showCamera){
        this.params.showCamera = showCamera;
        return this;
    }

    public Load gridColumns(int columns){
        if(columns > 0) {
            this.params.gridColumns = columns;
        }
        return this;
    }

    public SingleSelect single() {
        return new SingleSelect(params);
    }

    public MultiSelect multi(){
        return new MultiSelect(params);
    }
}
