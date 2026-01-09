package com.nesbot.cavecrawler;

import javafx.scene.image.*;

public class Player {
    private final Image up = new Image("character-up.png");
    private final Image down = new Image("character-down.png");
    private final Image left = new Image("character-left.png");
    private final Image right = new Image("character-right.png");
    private final Tile nextMap = new Tile(-Constants.FRAME_WIDTH, -Constants.FRAME_HEIGHT);
    private final Tile playerLose = new Tile(-Constants.FRAME_WIDTH, -Constants.FRAME_HEIGHT);
    private final Tile pickAxeIconTile = new Tile(0,0);
    private final Tile shovelIconTile = new Tile(Constants.TILE_UNIT, 0);
    private final ImageView playerImage = new ImageView(new Image("character-up.png"));

    private int x = Constants.PLAYER_DEFAULT_X;
    private int y = Constants.PLAYER_DEFAULT_Y;
    private boolean isWin = false;
    private boolean isLose = false;
    private boolean hasPickaxe = false;
    private boolean hasShovel = false;
    private boolean hasPickaxeUse = true;
    private boolean hasShovelUse = true;

    private Direction direction = Direction.UP;

    public Player() {
        playerLose.generateTile();
        playerLose.setImage(new Image("level-lose.png"));
        pickAxeIconTile.generateTile();
        pickAxeIconTile.setImage(new Image("pickaxe-icon.png"));
        shovelIconTile.generateTile();
        shovelIconTile.setImage(new Image("shovel-icon.png"));
        nextMap.generateTile();
        nextMap.setImage(new Image("level_win.png"));
        playerImage.setX(0);
        playerImage.setY(0);
        Tile.getRoot().getChildren().add(playerImage);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean isWin(){
        return isWin;
    }

    public boolean isLose(){
        return isLose;
    }

    public void updateImage(Direction direction){
        //Called when the player moves, updates the character image to face the correct direction
        switch (direction) {
            case UP:
                playerImage.setImage(up);
                this.direction = Direction.UP;
                break;
            case RIGHT:
                playerImage.setImage(right);
                this.direction = Direction.RIGHT;
                break;
            case DOWN:
                playerImage.setImage(down);
                this.direction = Direction.DOWN;
                break;
            case LEFT:
                playerImage.setImage(left);
                this.direction = Direction.LEFT;
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
        if (x == 0 && y == Constants.TILE_UNIT) {
            this.y--;
            updateImage(Direction.UP);
        } else if (x == Constants.TILE_UNIT && y == 0) {
            this.x--;
            updateImage(Direction.LEFT);
        } else if (x == 0 && y == -Constants.TILE_UNIT) {
            this.y++;
            updateImage(Direction.DOWN);
        } else if (x == -Constants.TILE_UNIT && y == 0) {
            this.x++;
            updateImage(Direction.RIGHT);
        }

        Sounds.walkingSound();


        //Various checks preformed on every tile move
        isItemFound(tiles);
        isDamageTaken(tiles);

        if (hasPickaxe && hasShovel) {
            tiles[Tile.exitX][Tile.exitY].assignTileType(TileType.EXIT_OPEN.getId());
        }
        if (hasPickaxe && hasShovel && tiles[this.x][this.y].type == TileType.EXIT_OPEN) {
            gameWin();
        }
    }

    public boolean isCollision(int x, int y, Tile[][] tiles) {return !tiles[this.x + x][this.y + y].isTraversable;}
    //Checks if the tile that the player is moving towards is traversable

    public void gameWin(){
        //Displays the win menu
        nextMap.show();
        isWin = true;
        Sounds.win();
    }

    public void isItemFound(Tile[][] tiles){
        //Checks if the player is standing on the pickaxe or shovel
        if(tiles[this.x][this.y].type == TileType.PICKAXE){
            hasPickaxe = true;
            tiles[this.x][this.y].assignTileType(TileType.FLAT_GROUND.getId());
            pickAxeIconTile.show(false);
            Sounds.pickup();
        } else if(tiles[this.x][this.y].type == TileType.SHOVEL) {
            hasShovel = true;
            tiles[this.x][this.y].assignTileType(TileType.FLAT_GROUND.getId());
            shovelIconTile.show(false);
            Sounds.pickup();
        }
    }

    public void isDamageTaken(Tile[][] tiles){
        //Checks if the player is standing on a hole, and updates to the game lost menu if true
        if(tiles[this.x][this.y].isDamaging){
            isLose = true;
            playerLose.show();
            Sounds.lose();

        }
    }

    public void breakBoulderOrFillHole(Tile[][] tiles){
        //Breaks a boulder or fills hole in the direction the player is facing
        switch(direction){
            case UP:
                if (tiles[x][y - 1].type == TileType.ROCK && hasPickaxeUse && hasPickaxe) {
                    tiles[x][y - 1].assignTileType(TileType.FLAT_GROUND.getId());
                    Sounds.rockBreak();
                    hasPickaxeUse = false;
                } else if (tiles[x][y - 1].type == TileType.HOLE && hasShovelUse && hasShovel) {
                    tiles[x][y - 1].assignTileType(TileType.FLAT_GROUND.getId());
                    Sounds.rockBreak();
                    hasShovelUse = false;
                }
            case RIGHT:
                if (tiles[x + 1][y].type == TileType.ROCK && hasPickaxeUse && hasPickaxe) {
                    tiles[x + 1][y].assignTileType(TileType.FLAT_GROUND.getId());
                    Sounds.rockBreak();
                    hasPickaxeUse = false;
                }else if (tiles[x + 1][y].type == TileType.HOLE && hasShovelUse && hasShovel) {
                    tiles[x + 1][y].assignTileType(TileType.FLAT_GROUND.getId());
                    Sounds.rockBreak();
                    hasShovelUse = false;
                }
            case DOWN:
                if (tiles[x][y + 1].type == TileType.ROCK && hasPickaxeUse && hasPickaxe) {
                    tiles[x][y + 1].assignTileType(TileType.FLAT_GROUND.getId());
                    Sounds.rockBreak();
                    hasPickaxeUse = false;
                }else if (tiles[x][y + 1].type == TileType.HOLE && hasShovelUse && hasShovel) {
                    tiles[x][y + 1].assignTileType(TileType.FLAT_GROUND.getId());
                    Sounds.rockBreak();
                    hasShovelUse = false;
                }
            case LEFT:
                if (tiles[x - 1][y].type == TileType.ROCK && hasPickaxeUse && hasPickaxe) {
                    tiles[x - 1][y].assignTileType(TileType.FLAT_GROUND.getId());
                    Sounds.rockBreak();
                    hasPickaxeUse = false;
                }else if (tiles[x - 1][y].type == TileType.HOLE && hasShovelUse && hasShovel) {
                    tiles[x - 1][y].assignTileType(TileType.FLAT_GROUND.getId());
                    Sounds.rockBreak();
                    hasShovelUse = false;
                }
        }
    }



    public void reset(Tile[][] tiles){
        //Resets all the player variables back to their defaults
        playerLose.hide();
        pickAxeIconTile.hide();
        shovelIconTile.hide();
        nextMap.hide();
        nextMap.move(-Constants.FRAME_WIDTH, -Constants.FRAME_HEIGHT);
        playerLose.move(-Constants.FRAME_WIDTH, -Constants.FRAME_HEIGHT);
        playerImage.toFront();
        x = Constants.PLAYER_DEFAULT_X;
        y = Constants.PLAYER_DEFAULT_Y;
        playerMove(0,0, tiles);
        hasPickaxe = false;
        hasShovel = false;
        hasPickaxeUse = true;
        hasShovelUse = true;
    }
}
