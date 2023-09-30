package catto.uwu.event;

/**
 * Created by Hexeption on 18/12/2016.
 */
public class Priority {

    public static final byte VERY_LOW = 0, LOW = 1, MEDIUM = 2, HIGH = 3, VERY_HIGH = 4;

    public static final byte[] VALUE_ARRAY;

    static {
        VALUE_ARRAY = new byte[]{0, 1, 2, 3, 4};
    }

}
