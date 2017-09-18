package speedata.com.blelib.bean;

/**
 * Created by 张明_ on 2017/8/7.
 */

public class SpeedataData {
    private final static String[] data1 = {
            "FF1001B1000000FE929292FE92929292FE000000",
            "FF1002B10000C000F011020C04C0304E84000000",
            "FF1003B1080808FF48280A02E29E828282820200",
            "FF1004B100000000000000000000000000000000"
    };
    private final static String[] data2 = {
            "B200203800003C4040424C404070041830000000",
            "B200404621103F28242221202020380702000000",
            "B2000242817F000804037F202020207F00000800",
            "B200000000000000000000000000000000000000"
    };

    public static String getData1(int i) {
        return data1[i];
    }

    public static String getData2(int i) {
        return data2[i];
    }
}
