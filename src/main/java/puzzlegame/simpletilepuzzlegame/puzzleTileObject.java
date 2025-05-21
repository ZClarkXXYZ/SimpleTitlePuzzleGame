package puzzlegame.simpletilepuzzlegame;

import javafx.scene.image.Image;

// [SCRAPPED] ideas are left in code in case I do go back and implement them.
//Basic (swaps itself and 8 surrounding square between blue/black)
//Row swapper (swaps itself and the row)
//Column swapper (swaps itself and the column)
//Plus swapper (row and column swap)
//[SCRAPPED]Tri color [modifier] (swaps black -> blue -> red -> black for itself and whatever additional type it is)
//star (swaps itself and the 4 corners)
//[SCRAPPED] Timer [X] [modifier] (swaps, then swaps back after X turns)
//Prime swaps (the tiles 2, 3, 5, 7 ...
//Not [modifier] (does not swap itself)
//[SCRAPPED] Mirror    (when selected, copies the effect of the last tile)

public class puzzleTileObject {

    /*
     * Initialized class variables
     */
    enum TileType {   //Too hard to use with current knowledge.

        BASIC,  //0
        ROW,    //1
        COLUMN, //2
        PLUS,   //3
        STAR,   //4
        PRIME,  //5
        MIRROR //Scrapped Idea  //6
    }

    private int tileColor; //0 = Black, 1 = Blue, 2 = Red
    private puzzleTileObject.TileType TileType;
    private boolean triModifier = false; //[SCRAPPED]
    private boolean notModifier = false; //When true, does not flip itself when selected
    private int timerModifer = 0; //[SCRAPPED] Idea
    private String tileID; //5-length string like 12000 that relates to images like 12000.png
    private int tileLocationID;



    /*
     * Constructors
     */

    //Default Constructor
    public puzzleTileObject() {
        this.TileType = TileType.BASIC;
        this.triModifier = false; //Scrapped
        this.notModifier = false;
        this.timerModifer = 0; //Scrapped
        this.tileColor = 0;
        this.tileID = this.makeID();
    }

    //Not Default Constructor
    public puzzleTileObject(int tileNum, int tileColor, boolean notModifier) {
        this.TileType = this.getTileTypeFromNum(tileNum);
        this.triModifier = false; //Scrapped
        this.notModifier = notModifier;
        this.timerModifer = 0; //Scrapped
        this.tileColor = tileColor;
        this.tileID = this.makeID();
    }

    //Not Default Constructor 2 //scrapped idea included
    public puzzleTileObject(int tileNum, int tileColor, boolean notModifier, boolean triColor) {
        this.TileType = this.getTileTypeFromNum(tileNum);
        this.triModifier = triColor; //Scrapped
        this.notModifier = notModifier;
        this.timerModifer = 0; //Scrapped
        this.tileColor = tileColor;
        this.tileID = this.makeID();
    }

    //If there is timer added later, would need one more constructor



    /*
     * PUBLIC METHODS
     */
    public String getID() {
        return(this.tileID);
    }

    public boolean getNotModifier() {
        return(this.notModifier);
    }

    public int getTitleType() {
        return(getNumFromTileType(this.TileType));
    }

    public Image getImage() {
        String imagePath = "images/" + this.tileID +".png";
        return(new Image(puzzleTileObject.class.getResourceAsStream(imagePath)));
    }

    public void flipTile() {
            if (!this.triModifier) {
                if (this.tileColor == 0) {
                    this.tileColor = 1;
             }
                else {this.tileColor = 0;}
            }
            else { //If the triModifier is true, then tile can switch to 3 different colors instead of just 2 //scrapped idea
                if (this.tileColor == 0) {
                    this.tileColor = 1;
                } else if (this.tileColor == 1) {
                    this.tileColor = 2;
                } else {
                    this.tileColor = 0;
                }
            }
        this.tileID = this.makeID();
    }


    public static puzzleTileObject getTileObjectFromID(String ID) {
        //returns a tile object based on the ID it would have.
        if (ID.equals("00000")) { //the simple default
            return(new puzzleTileObject());
        }

        else {
            int color = Character.getNumericValue(ID.charAt(0));
            int type = Character.getNumericValue(ID.charAt(1));
            boolean triModifer = (Character.getNumericValue(ID.charAt(2)) == 1);
            boolean notModifier = (Character.getNumericValue(ID.charAt(3)) == 1);
            int timerModifier = Character.getNumericValue(ID.charAt(4));
            if ((!triModifer) && (timerModifier == 0)) {
                return(new puzzleTileObject(type, color, notModifier));
            }
            else {
                return(new puzzleTileObject(type, color, notModifier, triModifer));
            }
        }
    }

    /*
     * PRIVATE METHODS
     */
    private TileType getTileTypeFromNum(int tileNum) {

        if (tileNum == 1) {return(TileType.ROW);}
        if (tileNum == 2) {return(TileType.COLUMN);}
        if (tileNum == 3) {return(TileType.PLUS);}
        if (tileNum == 4) {return(TileType.STAR);}
        if (tileNum == 5) {return(TileType.PRIME);}
        if (tileNum == 6) {return(TileType.MIRROR);} //Scrapped

        return(TileType.BASIC);
    }
    private int getNumFromTileType(TileType typeType) {
        switch (typeType) {
            case BASIC:
                return (0);
            case ROW:
                return (1);
            case COLUMN:
                return (2);
            case PLUS:
                return (3);
            case STAR:
                return (4);
            case PRIME:
                return (5);
            case MIRROR: //Scrapped Idea
                return (6);
            default: //Unnecessary Failsafe just in case
                return (0);
        }
    }



    private String makeID() {
        String tileID = "";

        //First Number: Color
        if (this.tileColor == 0) {
            tileID = tileID + "0";
        }
        else if (this.tileColor == 1) {
            tileID = tileID + "1";
        }
        else if (this.tileColor == 2){
            tileID = tileID + "2";
        }

        //Second Number : Tile Type
        switch (this.TileType) {
            case BASIC:
                tileID = tileID + "0";
                break;
            case ROW:
                tileID = tileID + "1";
                break;
            case COLUMN:
                tileID = tileID + "2";
                break;
            case PLUS:
                tileID = tileID + "3";
                break;
            case STAR:
                tileID = tileID + "4";
                break;
            case PRIME:
                tileID = tileID + "5";
                break;
            case MIRROR: //Scrapped Idea
                tileID = tileID + "6";
                break;
            default: //Unnecessary Failsafe just in case
                tileID = tileID + "0";
                break;
        }

        //3rd Number
        if (this.triModifier) {tileID = tileID + "1";}
        else {tileID = tileID + "0";}

        //4th Number
        if (this.notModifier) {tileID = tileID + "1";}
        else {tileID = tileID + "0";}

        //5th Number, but is always 0 unless Timer is added in later
        tileID = tileID + "0";


        return(tileID);
        }

    }







