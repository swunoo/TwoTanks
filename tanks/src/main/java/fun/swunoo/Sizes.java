package fun.swunoo;

public enum Sizes {

    WINDOW_WIDTH(600),
    WINDOW_HEIGHT(550),
    HEADER_HEIGHT(80),
    SIDE_WIDTH(130),
    PADDING(10),
    HEADER_PADDING(30),
    GAP(5);

    private int sizeValue;

    private Sizes (int value){
        this.sizeValue = value;
    }

    public int getSize(){
        return sizeValue;
    }
}
