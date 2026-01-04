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
    tile next_level = new tile(-1920, -1080);
    Image next_level_image = new Image("level_win.png");
    boolean lantern = true;
    boolean pickaxe = false;
    boolean shovel = false;
    boolean win = false;
    int current_level = 1;
    int x = 7;
    int y = 4;

    boolean t = true;

    public player(String tile_type) {
        super(0, 0);
        next_level.generate_tile();
        next_level.tile_image.setImage(next_level_image);

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
        } else if (x == -TILE_UNIT && y == 0){
            this.x++;
            this.update_image(2);
        }
        this.item_found(tiles);

        if(pickaxe && shovel){
            assign_tile_type(10, tiles[tile.exit_x][tile.exit_y]);
        }
        if(pickaxe && shovel && tiles[this.x][this.y].tile_type == 10){
            game_win();
        }
    }




    public boolean collision(int x, int y, tile[][] tiles) {return !tiles[this.x + x][this.y + y].traversable;}


    public void game_win(){
        next_level.tile_image.setX(0);
        next_level.tile_image.setY(0);
        next_level.tile_image.toFront();
        win = true;
    }

    public void item_found(tile[][] tiles){
        if(tiles[this.x][this.y].tile_type == 7){
            pickaxe = true;
            assign_tile_type(2, tiles[this.x][this.y]);
        } else if(tiles[this.x][this.y].tile_type == 8) {
            shovel = true;
            assign_tile_type(2, tiles[this.x][this.y]);
        }
    }


    public void reset(tile[][] tiles){
        this.next_level.tile_image.toBack();
        this.tile_image.toFront();
        this.pickaxe = false;
        this.shovel = false;
        this.x = 7;
        this.y = 4;
        this.player_move(0,0, tiles);
    }

}
