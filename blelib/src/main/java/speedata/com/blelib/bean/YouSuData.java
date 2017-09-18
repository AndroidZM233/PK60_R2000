package speedata.com.blelib.bean;

/**
 * Created by 张明_ on 2017/8/7.
 */

public class YouSuData {
    private final static String[] data1 = {
            "FF1001B1008060F807101010FF10F01116101000",
            "FF1002B1404042CC0004F49494FF9494F4040000",
            "FF1003B100E000FF1020080808FF0808F8000000",
            "FF1004B1404042CC0000E42526FC26253C000000"
    };
    private final static String[] data2 = {
            "B200010000FF0080601C03003F40404078000000",
            "B2000040201F20484442415F4142444840004000",
            "B200010000FF008141310D030D31418181000000",
            "B2000040201F20504945437F4149514F40004000"
    };

    public static String getData1(int i) {
        return data1[i];
    }

    public static String getData2(int i) {
        return data2[i];
    }
}
