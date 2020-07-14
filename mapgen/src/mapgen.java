import javafx.event.ActionEvent;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

import java.util.Random;

public class mapgen {
    public Slider roughSlider;
    public Slider waterSlider;
    public Slider expSlider;
    public Slider O1Slider;
    public Slider O2Slider;
    public Slider O3Slider;
    public AnchorPane map;
    public AnchorPane container;

    private double ROUGH;
    private double LEVEL;
    private double EXP;
    private double O1;
    private double O2;
    private double O3;
    private int numShow = 11;
    private tile central;
    private static int sideLength = 21;
    private static tile[][] tiles = new tile[sideLength][sideLength];
    OpenSimplexNoise noise; OpenSimplexNoise moist; OpenSimplexNoise dry;

    public void initialize(){
        map.setOnScroll(event -> {
            int initialShow = numShow;
            numShow -= event.getDeltaY()/30;
            if(numShow<=4) numShow=4;
            if(numShow>=sideLength/2) numShow=sideLength/2;
            if(initialShow != numShow){
                map.getChildren().clear();
                central = tiles[initialShow-2][initialShow-2];
                int a = 0, b = 0;
                for (int h = central.getY() - numShow - 1; h < central.getY() + numShow; h++) {
                    for (int w = central.getX() - numShow - 1; w < central.getX() + numShow; w++) {
                        int x = w, y = h;
                        if (w < 0) x = sideLength + w;
                        if (w > sideLength - 1) x = w + (sideLength - w - 1);
                        if (h < 0) y = sideLength + h;
                        if (h > sideLength - 1) y = h + (sideLength - h - 1);
                        tile t = tiles[x][y];

                        Rectangle r = new Rectangle(b * (400 / (numShow * 2)) * 1.05, a * (400 / (numShow * 2)) * 1.05, (400 / (numShow * 2)) - 1, (400 / (numShow * 2)) - 1);
                        r.setFill(t.getColor());
                        map.getChildren().add(r);
                        t.setRect(r);
                        b++;
                    }
                    b=0;
                    a++;
                }
            }
            System.out.println(numShow);
        });
        container.setOnKeyPressed(event -> {
//            int c=0, d=0;
//            switch (event.getCode().getName()){
//                case "W":
//                    c=central.getX(); d=central.getY()-1;
//                    if(d<0) d++;
//                    break;
//                case "S":
//                    c=central.getX(); d=central.getY()+1;
//                    if(d>sideLength-1) d--;
//                    break;
//                case "A":
//                    c=central.getX()-1; d=central.getY();
//                    if(c<0)c=sideLength-1;
//                    break;
//                case "D":
//                    c=central.getX()+1; d=central.getY();
//                    if(c>sideLength-1)c=0;
//                    break;
//            }
//            central = tiles[c][d];
//
//            map.getChildren().clear();
//            int a = 0, b = 0;
//            for (int h = central.getY() - numShow; h < central.getY() + numShow; h++) {
//                for (int w = central.getX() - numShow; w < central.getX() + numShow; w++) {
//                    int x = w, y = h;
//                    if (w < 0) x = Math.abs(sideLength + w);
//                    if (w > sideLength - 1) x = Math.abs(sideLength - w);
//                    if (h < 0) y = Math.abs(sideLength + h);
//                    if (h > sideLength - 1) y = Math.abs(sideLength - h);
//                    tile t = tiles[x][y];
//
//                    Rectangle r = new Rectangle(b * (400 / (numShow * 2)) * 1.05, a * (400 / (numShow * 2)) * 1.05, (400 / (numShow * 2)) - 1, (400 / (numShow * 2)) - 1);
//                    r.setFill(t.getColor());
//                    map.getChildren().add(r);
//                    t.setRect(r);
//                    b++;
//                }
//                a++;
//                b=0;
//            }
            int c=0, d=0;
            switch (event.getCode().getName()){
                case "W":
                    c=central.getX(); d=central.getY()-1;
                    if(d<0) d++;
                    break;
                case "S":
                    c=central.getX(); d=central.getY()+1;
                    if(d>sideLength-1) d--; break;
                case "A":
                    c=central.getX()+1; d=central.getY();
                    if(c>sideLength-1)c=0;
                    central = tiles[c][d];
                    for (int h = central.getY() - numShow - 1; h < central.getY() + numShow; h++) {
                        for (int w = central.getX() - numShow - 1; w < central.getX() + numShow; w++) {
                            int x = wrap(w), y = wrap(h);
                            tile t = tiles[x][y];
                            if(t.getRect().getX() == 0) map.getChildren().remove(t.getRect());
                            if(!map.getChildren().contains(t.getRect())){
                                t.getRect().setX( (numShow*2) * ((400 / (numShow * 2)) * 1.05) );    //-1 tick from x value
                                t.getRect().setY( y *  ((400 / (numShow * 2)) * 1.05) );
                                t.getRect().setHeight(400/(numShow*2)-1);
                                t.getRect().setWidth(400/(numShow*2)-1);
                                map.getChildren().add(t.getRect());
                                System.out.println("xd");
                            }
                            else {
                                t.getRect().setX(t.getRect().getX() - (400 / (numShow * 2)) * 1.05);    //-1 tick from x value
                            }
                        }
                    }
                    break;
                case "D":
                    c=central.getX()-1; d=central.getY();
                    if(c>sideLength-1)c=0;
                    central = tiles[c][d];
                    for (int h = central.getY() - numShow - 1; h < central.getY() + numShow; h++) {
                        for (int w = central.getX() - numShow - 1; w < central.getX() + numShow; w++) {
                            int x = wrap(w), y = wrap(h);
                            tile t = tiles[x][y];
                            if(w== central.getX()-numShow-1) map.getChildren().remove(t.getRect());
                            if(!map.getChildren().contains(t.getRect())){
                                t.getRect().setX( (numShow*2) * ((400 / (numShow * 2)) * 1.05) );
                                t.getRect().setY(y * ((400 / (numShow * 2)) * 1.05));
                                t.getRect().setHeight(400/(numShow*2)-1);
                                t.getRect().setWidth(400/(numShow*2)-1);
                                map.getChildren().add(t.getRect());
                                System.out.println("xd");
                            }
                            else {
                                t.getRect().setX(t.getRect().getX() + (400 / (numShow * 2)) * 1.05);    //+1 tick to     x value
                            }
                        }
                    }
                    break;
            }
        });
    }

    public int wrap(int original){
        if (original < 0) original = Math.abs(sideLength + original);
        if (original > sideLength - 1) original = Math.abs(sideLength - original);
        return original;
    }

    public void generate(ActionEvent actionEvent) {
        numShow=sideLength/2;
        noise = new OpenSimplexNoise(new Random().nextLong());
        moist = new OpenSimplexNoise(new Random().nextLong());
        dry = new OpenSimplexNoise(new Random().nextLong());

        ROUGH = roughSlider.getValue();
        LEVEL = waterSlider.getValue();
        EXP   = expSlider.getValue();
        O1    = O1Slider.getValue();
        O2    = O2Slider.getValue();
        O3    = O3Slider.getValue();
        map.getChildren().clear();

        for (int h = 0; h < sideLength; h++) {
            for (int w = 0; w < sideLength; w++) {
                double elev =   O1*noise.cylinderNoise(w, h, ROUGH, sideLength) +
                                O2*noise.cylinderNoise(2*w, 2*h, ROUGH, sideLength) +
                                O3*noise.cylinderNoise(4*w, 4*h, ROUGH, sideLength);

                elev /= O1+O2+O3;
                elev = Math.pow(Math.abs(elev), EXP);

                tiles[w][h] = new tile(w, h, elev);
                if(elev < LEVEL) tiles[w][h].setTrn(0);
                else if(elev < LEVEL+.02) tiles[w][h].setTrn(2);
                else if(elev < .45){
                    double moistLevel = O1*moist.cylinderNoise(w, h, ROUGH, sideLength) +
                                        O2*moist.cylinderNoise(2*w, 2*h, ROUGH, sideLength) +
                                        O3*moist.cylinderNoise(4*w, 4*h, ROUGH, sideLength);
                    moistLevel /= O1+O2+O3;
                    moistLevel = Math.pow(Math.abs(moistLevel), EXP);
                    moistLevel *= 10;
                    double dryLevel = O1*dry.cylinderNoise(w, h, ROUGH, sideLength) +
                                      O2*dry.cylinderNoise(2*w, 2*h, ROUGH, sideLength) +
                                      O3*dry.cylinderNoise(4*w, 4*h, ROUGH, sideLength);
                    dryLevel /= O1+O2+O3;
                    dryLevel  = Math.pow(Math.abs(dryLevel), EXP);
                    dryLevel *= 10;

                    if(moistLevel > 1.2) tiles[w][h].setTrn(4); //forest
                    else if(dryLevel > 1.7) tiles[w][h].setTrn(3);  //dessert
                    else tiles[w][h].setTrn(1);  //grassland
                }
                if(elev > .6) tiles[w][h].setTrn(9);
            }
        }


        for(tile[] a:tiles) {
            for (tile t : a) {
                Rectangle r = new Rectangle(t.getX() * (400 / (numShow * 2)) * 1.05, t.getY() * (400 / (numShow * 2)) * 1.05, (400 / (numShow * 2)) - 1, (400 / (numShow * 2)) - 1);
                if(t.getTrn() == 0) t.setColor(Color.BLUE);
                else if(t.getTrn() == 1) t.setColor(Color.rgb(140, 252, 0  ));
                else if(t.getTrn() == 2) t.setColor(Color.rgb(237, 201, 175));
                else if(t.getTrn() == 3) t.setColor(Color.rgb(255, 247, 0  ));
                else if(t.getTrn() == 4) t.setColor(Color.rgb(0,   110, 0  ));
                else if(t.getTrn() == 9) t.setColor(Color.GRAY);

                r.setFill(t.getColor());
                map.getChildren().add(r);
                t.setRect(r);
            }
        }
        central = tiles[sideLength/2][sideLength/2];
        System.out.println("ROUGH: " + ROUGH);
        System.out.println("LEVEL: " + LEVEL);
        System.out.println("EXP  : " + EXP);
        System.out.println("O1   : " + O1);
        System.out.println("O2:    " + O2);
        System.out.println("O3:    " + O3);
        System.out.println("-");

    }
}