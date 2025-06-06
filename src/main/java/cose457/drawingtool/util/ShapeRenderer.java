package cose457.drawingtool.util;

import cose457.drawingtool.viewmodel.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShapeRenderer implements ShapeViewModelVisitor {

    private final GraphicsContext gc;

    public ShapeRenderer(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public void visit(RectangleViewModel viewModel) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(
                viewModel.getX(), viewModel.getY(),
                viewModel.getWidth(), viewModel.getHeight()
        );
        // 선택 표시
        if (viewModel.isSelected()) {
            gc.setStroke(Color.BLUE);
            gc.setLineDashes(6);
            gc.strokeRect(
                    viewModel.getX(), viewModel.getY(),
                    viewModel.getWidth(), viewModel.getHeight()
            );
            gc.setLineDashes(0);
        }
    }

    @Override
    public void visit(EllipseViewModel viewModel) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeOval(
                viewModel.getX(), viewModel.getY(),
                viewModel.getWidth(), viewModel.getHeight()
        );
        if (viewModel.isSelected()) {
            gc.setStroke(Color.BLUE);
            gc.setLineDashes(6);
            gc.strokeOval(
                    viewModel.getX(), viewModel.getY(),
                    viewModel.getWidth(), viewModel.getHeight()
            );
            gc.setLineDashes(0);
        }
    }

    @Override
    public void visit(LineViewModel viewModel) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(
                viewModel.getX(), viewModel.getY(),
                viewModel.getX() + viewModel.getWidth(),
                viewModel.getY() + viewModel.getHeight()
        );
        if (viewModel.isSelected()) {
            gc.setStroke(Color.BLUE);
            gc.setLineDashes(6);
            gc.strokeLine(
                    viewModel.getX(), viewModel.getY(),
                    viewModel.getX() + viewModel.getWidth(),
                    viewModel.getY() + viewModel.getHeight()
            );
            gc.setLineDashes(0);
        }
    }

    @Override
    public void visit(TextViewModel viewModel) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeRect(
                viewModel.getX(), viewModel.getY(),
                viewModel.getWidth(), viewModel.getHeight()
        );
        gc.setFill(Color.BLACK);
        String text = viewModel.text.get();
        if (text != null) {
            gc.fillText(
                    text,
                    viewModel.getX() + 5, viewModel.getY() + viewModel.getHeight() / 2
            );
        }
    }

    @Override
    public void visit(ImageViewModel viewModel) {
        // 간단 예시: 실제 Image 객체 로딩 등은 별도 처리 필요
        String path = viewModel.imagePath.get();
        // gc.drawImage(...) 등으로 실제 이미지를 그림
        // 생략, 필요시 자세히 구현
        gc.setStroke(Color.GRAY);
        gc.strokeRect(
                viewModel.getX(), viewModel.getY(),
                viewModel.getWidth(), viewModel.getHeight()
        );
        gc.strokeText(
                "IMAGE",
                viewModel.getX() + 5, viewModel.getY() + 20
        );
    }
}