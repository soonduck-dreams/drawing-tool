package cose457.drawingtool.viewmodel;

import cose457.drawingtool.model.ShapeModel;
import cose457.drawingtool.util.Observable;
import cose457.drawingtool.util.ObservableValue;
import cose457.drawingtool.util.ShapeViewModelVisitor;
import lombok.Getter;

public abstract class ShapeViewModel {

    @Getter
    protected final ShapeModel model;

    private boolean selected = false;

    public ShapeViewModel(ShapeModel model) {
        this.model = model;
    }

    public double getX() { return model.getX(); }
    public double getY() { return model.getY(); }
    public double getWidth() { return model.getWidth(); }
    public double getHeight() { return model.getHeight(); }
    public int getZOrder() { return model.getZOrder(); }

    // 선택 상태 getter/setter
    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }

    public abstract void accept(ShapeViewModelVisitor visitor);
}
