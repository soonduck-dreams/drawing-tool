package cose457.drawingtool.factory;

import cose457.drawingtool.model.ShapeModel;
import cose457.drawingtool.model.ShapeType;
import cose457.drawingtool.model.shape.*;

public class ShapeModelFactory {

    public static RectangleBuilder rectangle() {
        return new RectangleBuilder();
    }

    public static EllipseBuilder ellipse() {
        return new EllipseBuilder();
    }

    public static LineBuilder line() {
        return new LineBuilder();
    }

    public static TextBuilder text() {
        return new TextBuilder();
    }

    public static ImageBuilder image() {
        return new ImageBuilder();
    }

    // Rectangle
    public static class RectangleBuilder {
        private double x, y, width, height;
        private int zOrder;

        public RectangleBuilder x(double x) { this.x = x; return this; }
        public RectangleBuilder y(double y) { this.y = y; return this; }
        public RectangleBuilder width(double width) { this.width = width; return this; }
        public RectangleBuilder height(double height) { this.height = height; return this; }
        public RectangleBuilder zOrder(int zOrder) { this.zOrder = zOrder; return this; }

        public RectangleShape build() {
            return new RectangleShape(x, y, width, height, zOrder);
        }
    }

    // Ellipse
    public static class EllipseBuilder {
        private double x, y, width, height;
        private int zOrder;

        public EllipseBuilder x(double x) { this.x = x; return this; }
        public EllipseBuilder y(double y) { this.y = y; return this; }
        public EllipseBuilder width(double width) { this.width = width; return this; }
        public EllipseBuilder height(double height) { this.height = height; return this; }
        public EllipseBuilder zOrder(int zOrder) { this.zOrder = zOrder; return this; }

        public EllipseShape build() {
            return new EllipseShape(x, y, width, height, zOrder);
        }
    }

    // Line
    public static class LineBuilder {
        private double x, y, width, height;
        private int zOrder;

        public LineBuilder x(double x) { this.x = x; return this; }
        public LineBuilder y(double y) { this.y = y; return this; }
        public LineBuilder width(double width) { this.width = width; return this; }
        public LineBuilder height(double height) { this.height = height; return this; }
        public LineBuilder zOrder(int zOrder) { this.zOrder = zOrder; return this; }

        public LineShape build() {
            return new LineShape(x, y, width, height, zOrder);
        }
    }

    // Text
    public static class TextBuilder {
        private double x, y, width, height;
        private int zOrder;
        private String text = "";

        public TextBuilder x(double x) { this.x = x; return this; }
        public TextBuilder y(double y) { this.y = y; return this; }
        public TextBuilder width(double width) { this.width = width; return this; }
        public TextBuilder height(double height) { this.height = height; return this; }
        public TextBuilder zOrder(int zOrder) { this.zOrder = zOrder; return this; }
        public TextBuilder text(String text) { this.text = text; return this; }

        public TextShape build() {
            return new TextShape(x, y, width, height, zOrder, text);
        }
    }

    // Image
    public static class ImageBuilder {
        private double x, y, width, height;
        private int zOrder;
        private String imagePath = "";

        public ImageBuilder x(double x) { this.x = x; return this; }
        public ImageBuilder y(double y) { this.y = y; return this; }
        public ImageBuilder width(double width) { this.width = width; return this; }
        public ImageBuilder height(double height) { this.height = height; return this; }
        public ImageBuilder zOrder(int zOrder) { this.zOrder = zOrder; return this; }
        public ImageBuilder imagePath(String path) { this.imagePath = path; return this; }

        public ImageShape build() {
            return new ImageShape(x, y, width, height, zOrder, imagePath);
        }
    }
}
