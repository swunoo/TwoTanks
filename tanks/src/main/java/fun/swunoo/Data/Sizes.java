package fun.swunoo.Data;

/*
 * Layout data that can be expressed in px.
 */
public enum Sizes {

    WINDOW_WIDTH(600),
    WINDOW_HEIGHT(550),
    HEADER_HEIGHT(80),
    SIDE_WIDTH(130),
    PADDING(10),
    HEADER_PADDING(30),
    GAP(5),
    STARTING_BOTTOM(100), // starting y-position (from bottom) of p1Tank.
    FULL(1000); // to set width/height to 100%.

    private int sizeValue;

    private Sizes (int value){
        this.sizeValue = value;
    }

    public int getSize(){
        return sizeValue;
    }
}
