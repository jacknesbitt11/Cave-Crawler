import javafx.scene.image.*;


public class player extends Tile {
    static int TILE_UNIT = 128;
    Image up = new Image("character-up.png");
    Image down = new Image("character-down.png");
    Image left = new Image("character-left.png");
    Image right = new Image("character-right.png");
    Tile next_level = new Tile(-1920, -1080);
    Tile lose_level = new Tile(-1920, -1080);
    Image lose_level_image = new Image("level-lose.png");
    Tile pickaxe_icon_tile = new Tile(0,0);
    Tile shovel_icon_tile = new Tile(TILE_UNIT, 0);
    Image next_level_image = new Image("level_win.png");
    boolean pickaxe = false;
    boolean shovel = false;
    boolean win = false;
    boolean lose = false;
    boolean pickaxe_use = true;
    boolean shovel_use = true;
    int current_level = 1;
    int x = 7;
    int y = 4;
    int direction = 1;


    public player(String tile_type) {super(0, 0);
        lose_level.generateTile();
        lose_level.tile_image.setImage(lose_level_image);
        pickaxe_icon_tile.generateTile();
        pickaxe_icon_tile.tile_image.setImage(pickaxe_icon);
        shovel_icon_tile.generateTile();
        shovel_icon_tile.tile_image.setImage(shovel_icon);
        next_level.generateTile();
        next_level.tile_image.setImage(next_level_image);

    }

    public void update_image(int state){
        //Called when the player moves, updates the character image to face the correct direction
            switch (state) {
                case 1:
                    tile_image.setImage(up);
                    direction = 1;
                    break;
                case 2:
                    tile_image.setImage(right);
                    direction = 2;
                    break;
                case 3:
                    tile_image.setImage(down);
                    direction = 3;
                    break;
                case 4:
                    tile_image.setImage(left);
                    direction = 4;
                    break;
            }
    }
    public void player_move(int x, int y, Tile[][] tiles){
        //Moves all the tiles in the given direction
        for (Tile[] tile_row : tiles) {
            for (Tile individual_tile : tile_row) {
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
        sounds.walking_sound();


        //Various checks preformed on every tile move
        item_found(tiles);
        damage_taken(tiles);

        if(pickaxe && shovel){
            tiles[Tile.exit_x][Tile.exit_y].assign_tile_type(7);
        }
        if(pickaxe && shovel && tiles[this.x][this.y].tile_type == 7){
            game_win();
        }
    }




    public boolean collision(int x, int y, Tile[][] tiles) {return !tiles[this.x + x][this.y + y].traversable;}
    //Checks if the tile that the player is moving towards is traversable

    public void game_win(){
        //Displays the win menu
        next_level.tile_image.setX(0);
        next_level.tile_image.setY(0);
        next_level.tile_image.toFront();
        win = true;
        sounds.win();
    }

    public void item_found(Tile[][] tiles){
        //Checks if the player is standing on the pickaxe or shovel
        if(tiles[this.x][this.y].tile_type == 4){
            pickaxe = true;
            tiles[this.x][this.y].assign_tile_type(2);
            pickaxe_icon_tile.tile_image.toFront();
            sounds.pickup();
        } else if(tiles[this.x][this.y].tile_type == 5) {
            shovel = true;
            tiles[this.x][this.y].assign_tile_type(2);
            shovel_icon_tile.tile_image.toFront();
            sounds.pickup();
        }
    }

    public void damage_taken(Tile[][] tiles){
        //Checks if the player is standing on a hole, and updates to the game lost menu if true
        if(tiles[this.x][this.y].damaging){
            lose = true;
            lose_level.tile_image.setX(0);
            lose_level.tile_image.setY(0);
            lose_level.tile_image.toFront();
            sounds.lose();

        }
    }

    public void break_boulder_or_fill_hole(Tile[][] tiles){
        //Breaks a boulder or fills hole in the direction the player is facing
        switch(direction){
            case 1:
                if (tiles[x][y - 1].tile_type == 1 && pickaxe_use && pickaxe) {
                    tiles[x][y - 1].assign_tile_type(2);
                    sounds.rock_break();
                    pickaxe_use = false;
                } else if (tiles[x][y - 1].tile_type == 8 && shovel_use && shovel){
                    tiles[x][y - 1].assign_tile_type(2);
                    sounds.rock_break();
                    shovel_use = false;
                }
            case 2:
                if (tiles[x + 1][y].tile_type == 1 && pickaxe_use && pickaxe) {
                    tiles[x + 1][y].assign_tile_type(2);
                    sounds.rock_break();
                    pickaxe_use = false;
                }else if (tiles[x + 1][y].tile_type == 8 && shovel_use && shovel){
                    tiles[x + 1][y].assign_tile_type(2);
                    sounds.rock_break();
                    shovel_use = false;
                }
            case 3:
                if (tiles[x][y + 1].tile_type == 1 && pickaxe_use && pickaxe) {
                    tiles[x][y + 1].assign_tile_type(2);
                    sounds.rock_break();
                    pickaxe_use = false;
                }else if (tiles[x][y + 1].tile_type == 8 && shovel_use && shovel){
                    tiles[x][y + 1].assign_tile_type(2);
                    sounds.rock_break();
                    shovel_use = false;
                }
            case 4:
                if (tiles[x - 1][y].tile_type == 1 && pickaxe_use && pickaxe) {
                    tiles[x - 1][y].assign_tile_type(2);
                    sounds.rock_break();
                    pickaxe_use = false;
                }else if (tiles[x - 1][y].tile_type == 8 && shovel_use && shovel){
                    tiles[x - 1][y].assign_tile_type(2);
                    sounds.rock_break();
                    shovel_use = false;
                }
        }
    }


    public void reset(Tile[][] tiles){
        //Resets all the player variables back to their defaults
        lose_level.tile_image.toBack();
        pickaxe_icon_tile.tile_image.toBack();
        shovel_icon_tile.tile_image.toBack();
        this.next_level.tile_image.toBack();
        next_level.move(-1920, -1080);
        lose_level.move(-1920, -1080);
        tile_image.toFront();
        pickaxe = false;
        shovel = false;
        x = 7;
        y = 4;
        player_move(0,0, tiles);
        pickaxe_use = true;
        shovel_use = true;
    }

}
