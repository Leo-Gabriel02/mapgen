import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class tile {
    private int x;
    private int y;
    private int terrain;
    private double elevation;
    private Rectangle rect;
    Paint color;
    tile(int x, int y, double elev){
        this.x=x;
        this.y=y;
        this.elevation = elev;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public int getTrn() { return terrain;}
    public void setTrn(int newTrn) {
        terrain = newTrn;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }
    public Rectangle getRect() {
        return rect;
    }

    public void setColor(Paint color) { this.color = color; }
    public Paint getColor() { return color; }


    public double getElevation() {
        return elevation;
    }


}
