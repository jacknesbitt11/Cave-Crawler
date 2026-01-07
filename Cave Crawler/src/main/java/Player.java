import javafx.scene.image.*;


public class Player {
    private final Image up = new Image("character-up.png");
    private final Image down = new Image("character-down.png");
    private final Image left = new Image("character-left.png");
    private final Image right = new Image("character-right.png");
    private final static  Image pickaxe_icon = new Image("pickaxe-icon.png");
    private final static  Image shovel_icon = new Image("shovel-icon.png");
    private final Tile next_level = new Tile(-1920, -1080);
    private final Tile lose_level = new Tile(-1920, -1080);
    private final Tile pickaxe_icon_tile = new Tile(0,0);
    private final Tile shovel_icon_tile = new Tile(Constants.TILE_UNIT, 0);
    private final ImageView playerImage = new ImageView(new Image("character-up.png"));

    public int x = Constants.player_default_x;
    public int y = Constants.player_default_y;
    public boolean win = false;
    public boolean lose = false;
    private boolean hasPickaxe = false;
    private boolean hasShovel = false;
    private boolean pickaxeUse = true;
    private boolean shovelUse = true;


    Direction direction = Direction.up;

    public Player() {
        Image lose_level_image = new Image("level-lose.png");
        Image next_level_image = new Image("level_win.png");
        lose_level.generateTile();
        lose_level.tile_image.setImage(lose_level_image);
        pickaxe_icon_tile.generateTile();
        pickaxe_icon_tile.tile_image.setImage(pickaxe_icon);
        shovel_icon_tile.generateTile();
        shovel_icon_tile.tile_image.setImage(shovel_icon);
        next_level.generateTile();
        next_level.tile_image.setImage(next_level_image);

    }

    public void updateImage(Direction direction){
        //Called when the player moves, updates the character image to face the correct direction
            switch (direction) {
                case up:
                    playerImage.setImage(up);
                    this.direction = Direction.up;
                    break;
                case right:
                    playerImage.setImage(right);
                    this.direction = Direction.right;
                    break;
                case down:
                    playerImage.setImage(down);
                    this.direction = Direction.down;
                    break;
                case left:
                    playerImage.setImage(left);
                    this.direction = Direction.left;

                    break;
            }
    }
    public void playerMove(int x, int y, Tile[][] tiles){
        //Moves all the tiles in the given direction
        for (Tile[] tile_row : tiles) {
            for (Tile individual_tile : tile_row) {
                individual_tile.move(x, y);
            }
        }
        if(x == 0 && y == Constants.TILE_UNIT){
            this.y--;
            this.updateImage(Direction.up);
        } else if(x == Constants.TILE_UNIT && y == 0){
            this.x--;
            this.updateImage(Direction.left);
        } else if (x == 0 && y == -Constants.TILE_UNIT){
            this.y++;
            this.updateImage(Direction.down);
        } else if (x == -Constants.TILE_UNIT && y == 0){
            this.x++;
            this.updateImage(Direction.right);
        }

        Sounds.walkingSound();


        //Various checks preformed on every tile move
        itemFound(tiles);
        damageTaken(tiles);

        if(hasPickaxe && hasShovel){
            tiles[Tile.exit_x][Tile.exit_y].assignTileType(7);
        }
        if(hasPickaxe && hasShovel && tiles[this.x][this.y].type == TileType.EXIT_OPEN){
            gameWin();
        }
    }




    public boolean collision(int x, int y, Tile[][] tiles) {return !tiles[this.x + x][this.y + y].isTraversable;}
    //Checks if the tile that the player is moving towards is traversable

    public void gameWin(){
        //Displays the win menu
        next_level.tile_image.setX(0);
        next_level.tile_image.setY(0);
        next_level.tile_image.toFront();
        win = true;
        Sounds.win();
    }

    public void itemFound(Tile[][] tiles){
        //Checks if the player is standing on the pickaxe or shovel
        if(tiles[this.x][this.y].type == TileType.PICKAXE){
            hasPickaxe = true;
            tiles[this.x][this.y].assignTileType(TileType.FLAT_GROUND.getId());
            pickaxe_icon_tile.tile_image.toFront();
            Sounds.pickup();
        } else if(tiles[this.x][this.y].type == TileType.SHOVEL) {
            hasShovel = true;
            tiles[this.x][this.y].assignTileType(TileType.FLAT_GROUND.getId());
            shovel_icon_tile.tile_image.toFront();
            Sounds.pickup();
        }
    }

    public void damageTaken(Tile[][] tiles){
        //Checks if the player is standing on a hole, and updates to the game lost menu if true
        if(tiles[this.x][this.y].isDamaging){
            lose = true;
            lose_level.tile_image.setX(0);
            lose_level.tile_image.setY(0);
            lose_level.tile_image.toFront();
            Sounds.lose();

        }
    }

    public void breakBoulderOrFillHole(Tile[][] tiles){
        //Breaks a boulder or fills hole in the direction the player is facing
        switch(direction){
            case up:
                if (tiles[x][y - 1].type == TileType.ROCK && pickaxeUse && hasPickaxe) {
                    tiles[x][y - 1].assignTileType(TileType.FLAT_GROUND.getId());
                    Sounds.rockBreak();
                    pickaxeUse = false;
                } else if (tiles[x][y - 1].type == TileType.HOLE && shovelUse && hasShovel){
                    tiles[x][y - 1].assignTileType(TileType.FLAT_GROUND.getId());
                    Sounds.rockBreak();
                    shovelUse = false;
                }
            case right:
                if (tiles[x + 1][y].type == TileType.ROCK && pickaxeUse && hasPickaxe) {
                    tiles[x + 1][y].assignTileType(TileType.FLAT_GROUND.getId());
                    Sounds.rockBreak();
                    pickaxeUse = false;
                }else if (tiles[x + 1][y].type == TileType.HOLE && shovelUse && hasShovel){
                    tiles[x + 1][y].assignTileType(TileType.FLAT_GROUND.getId());
                    Sounds.rockBreak();
                    shovelUse = false;
                }
            case down:
                if (tiles[x][y + 1].type == TileType.ROCK && pickaxeUse && hasPickaxe) {
                    tiles[x][y + 1].assignTileType(TileType.FLAT_GROUND.getId());
                    Sounds.rockBreak();
                    pickaxeUse = false;
                }else if (tiles[x][y + 1].type == TileType.HOLE && shovelUse && hasShovel){
                    tiles[x][y + 1].assignTileType(TileType.FLAT_GROUND.getId());
                    Sounds.rockBreak();
                    shovelUse = false;
                }
            case left:
                if (tiles[x - 1][y].type == TileType.ROCK && pickaxeUse && hasPickaxe) {
                    tiles[x - 1][y].assignTileType(TileType.FLAT_GROUND.getId());
                    Sounds.rockBreak();
                    pickaxeUse = false;
                }else if (tiles[x - 1][y].type == TileType.HOLE && shovelUse && hasShovel){
                    tiles[x - 1][y].assignTileType(TileType.FLAT_GROUND.getId());
                    Sounds.rockBreak();
                    shovelUse = false;
                }
        }
    }

    public void generatePlayer(){
        playerImage.setX(0);
        playerImage.setY(0);
        Tile.root.getChildren().add(playerImage);
    }




    public void reset(Tile[][] tiles){
        //Resets all the player variables back to their defaults
        lose_level.tile_image.toBack();
        pickaxe_icon_tile.tile_image.toBack();
        shovel_icon_tile.tile_image.toBack();
        this.next_level.tile_image.toBack();
        next_level.move(-1920, -1080);
        lose_level.move(-1920, -1080);
        playerImage.toFront();
        hasPickaxe = false;
        hasShovel = false;
        x = Constants.player_default_x;
        y = Constants.player_default_y;
        playerMove(0,0, tiles);
        pickaxeUse = true;
        shovelUse = true;
    }

}
