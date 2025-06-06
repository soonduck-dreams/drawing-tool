package cose457.drawingtool.util;

import cose457.drawingtool.viewmodel.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ShapeRenderer implements ShapeViewModelVisitor {

    private final GraphicsContext gc;
    private static final Color SELECTION_COLOR = Color.DODGERBLUE;
    private static final double HANDLE_SIZE = 6;

    public ShapeRenderer(GraphicsContext gc) {
        this.gc = gc;
    }

    private void drawSelectionBox(double x, double y, double width, double height) {
        gc.setStroke(SELECTION_COLOR);
        gc.setLineWidth(2);
        gc.setLineDashes(6);
        gc.strokeRect(x, y, width, height);
        gc.setLineDashes(0);

        double half = HANDLE_SIZE / 2;
        gc.setFill(SELECTION_COLOR);
        double[] xs = {x, x + width, x, x + width};
        double[] ys = {y, y, y + height, y + height};
        for (int i = 0; i < xs.length; i++) {
            gc.fillRect(xs[i] - half, ys[i] - half, HANDLE_SIZE, HANDLE_SIZE);
        }
    }

    private void drawSelectionLine(double x1, double y1, double x2, double y2) {
        gc.setStroke(SELECTION_COLOR);
        gc.setLineWidth(2);
        gc.setLineDashes(6);
        gc.strokeLine(x1, y1, x2, y2);
        gc.setLineDashes(0);

        double half = HANDLE_SIZE / 2;
        gc.setFill(SELECTION_COLOR);
        gc.fillRect(x1 - half, y1 - half, HANDLE_SIZE, HANDLE_SIZE);
        gc.fillRect(x2 - half, y2 - half, HANDLE_SIZE, HANDLE_SIZE);
    }

    @Override
    public void visit(RectangleViewModel viewModel) {
        gc.setFill(viewModel.getFillColor());
        gc.fillRect(
                viewModel.getX(), viewModel.getY(),
                viewModel.getWidth(), viewModel.getHeight()
        );
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(
                viewModel.getX(), viewModel.getY(),
                viewModel.getWidth(), viewModel.getHeight()
        );
        if (viewModel.isSelected()) {
            drawSelectionBox(
                    viewModel.getX(),
                    viewModel.getY(),
                    viewModel.getWidth(),
                    viewModel.getHeight());
        }
    }

    @Override
    public void visit(EllipseViewModel viewModel) {
        gc.setFill(viewModel.getFillColor());
        gc.fillOval(
                viewModel.getX(), viewModel.getY(),
                viewModel.getWidth(), viewModel.getHeight()
        );
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeOval(
                viewModel.getX(), viewModel.getY(),
                viewModel.getWidth(), viewModel.getHeight()
        );
        if (viewModel.isSelected()) {
            drawSelectionBox(
                    viewModel.getX(),
                    viewModel.getY(),
                    viewModel.getWidth(),
                    viewModel.getHeight());
        }
    }

    @Override
    public void visit(LineViewModel viewModel) {
        gc.setStroke(viewModel.getFillColor());
        gc.setLineWidth(2);
        gc.strokeLine(
                viewModel.getX(), viewModel.getY(),
                viewModel.getX() + viewModel.getWidth(),
                viewModel.getY() + viewModel.getHeight()
        );
        if (viewModel.isSelected()) {
            drawSelectionLine(
                    viewModel.getX(),
                    viewModel.getY(),
                    viewModel.getX() + viewModel.getWidth(),
                    viewModel.getY() + viewModel.getHeight());
        }
    }

    @Override
    public void visit(TextViewModel viewModel) {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.1);
        gc.strokeRect(
                viewModel.getX(), viewModel.getY(),
                viewModel.getWidth(), viewModel.getHeight()
        );
        gc.setFill(viewModel.getFillColor());
        String text = viewModel.getText();
        if (text != null) {
            gc.setFont(Font.font("System", FontWeight.BOLD, 30));
            gc.fillText(
                    text,
                    viewModel.getX() + 5, viewModel.getY() + viewModel.getHeight() / 2
            );
        }
        if (viewModel.isSelected()) {
            drawSelectionBox(
                    viewModel.getX(),
                    viewModel.getY(),
                    viewModel.getWidth(),
                    viewModel.getHeight());
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
        if (viewModel.isSelected()) {
            drawSelectionBox(
                    viewModel.getX(),
                    viewModel.getY(),
                    viewModel.getWidth(),
                    viewModel.getHeight());
        }
    }
}