package cose457.drawingtool.viewmodel;

import cose457.drawingtool.model.shape.TextShape;
import cose457.drawingtool.util.ShapeViewModelVisitor;

public class TextViewModel extends ShapeViewModel {

    public TextViewModel(TextShape model) {
        super(model);
    }

    public String getText() {
        return ((TextShape) model).getText();
    }

    @Override
    public void accept(ShapeViewModelVisitor visitor) {
        visitor.visit(this);
    }
}
