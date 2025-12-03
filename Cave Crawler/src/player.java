import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.paint.Paint;
import javafx.scene.image.*;


public class player extends tile {
    static int TILE_UNIT = 128;
    String tile_type;
    Image up = new Image("up.png");
    Image down = new Image("down.png");
    Image left = new Image("left.png");
    Image right = new Image("right.png");
    Image test = new Image("player.png");
    boolean lantern = true;
    int current_tile = 67;
    int x = 7;
    int y = 4;



    boolean t = true;

    public player(String tile_type) {
        super(7*TILE_UNIT, 4*TILE_UNIT, 4);
    }

    public void update_image(int state){
        if(lantern) {
            switch (state) {
                case 1:
                    tile_image.setImage(up);
                    break;
                case 2:
                    tile_image.setImage(right);
                    break;
                case 3:
                    tile_image.setImage(down);
                    break;
                case 4:
                    tile_image.setImage(left);
                    break;
            }
        } else{
            tile_image.setImage(test);
        }

    }

    public boolean collision(int x, int y, tile[][] tiles) {
        return !tiles[this.x + x][this.y + y].traversable;
    }

}
