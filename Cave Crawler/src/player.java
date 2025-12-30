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
    Image up = new Image("lantern-up.png");
    Image down = new Image("lantern-down.png");
    Image left = new Image("lantern-left.png");
    Image right = new Image("lantern-right.png");
    Image test = new Image("player.png");
    Image default_tile = new Image("flatground.png");
    boolean lantern = true;
    boolean pickaxe = false;
    boolean shovel = false;
    int current_tile = 67;
    int x = 7;
    int y = 4;


    boolean t = true;

    public player(String tile_type) {
        super(0, 0);
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
    public void player_move(int x, int y, tile[][] tiles){
        for (tile[] tile_row : tiles) {
            for (tile individual_tile : tile_row) {
                individual_tile.move(x, y);
            }
        }
        if(x == 0 && y == TILE_UNIT){
            this.y--;
            this.update_image(1);
        } else if(x == TILE_UNIT && y == 0){
            this.x--;
            this.update_image(4);
        } else if (x == 0 && y == -TILE_UNIT){
            this.y++;
            this.update_image(3);
        } else{
            this.x++;
            this.update_image(2);
        }
        this.item_found(tiles);
        System.out.println(tile.exit_x);

        if(pickaxe && shovel){
            assign_tile_type(10, tiles[tile.exit_x][tile.exit_y]);
        }
    }




    public boolean collision(int x, int y, tile[][] tiles) {return !tiles[this.x + x][this.y + y].traversable;}

    public void item_found(tile[][] tiles){
        if(tiles[this.x][this.y].tile_type == 7){
            pickaxe = true;
            assign_tile_type(2, tiles[this.x][this.y]);
            System.out.println("true");

        } else if(tiles[this.x][this.y].tile_type == 8) {
            shovel = true;
            assign_tile_type(2, tiles[this.x][this.y]);
            System.out.println("true");

        }
    }

}
