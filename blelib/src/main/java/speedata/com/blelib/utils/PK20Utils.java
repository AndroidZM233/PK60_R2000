package speedata.com.blelib.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 张明_ on 2017/9/4.
 */

public class PK20Utils {

    /**
     * 获取设置时间的发送数据
     *
     * @param currentTimeMillis 时间
     * @return 设置时间的发送数据
     */
    public static String getSetTimeData(long currentTimeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
        Date date = new Date(currentTimeMillis);
        String format = simpleDateFormat.format(date);
        String[] split = format.split(" ");
        String[] spHMS = split[1].split(":");
        String[] spYMD = split[0].split("-");
        String week = DataManageUtils.backWeek(split[2]);
        String year = spYMD[0].substring(2, 4);
        String jiaoYan = DataManageUtils.getJiaoYan(spHMS[2], year);
        return "FF0C08" + spHMS[2] + spHMS[1] + spHMS[0] + week + spYMD[2]
                + spYMD[1] + year + "0000000000000000" + jiaoYan + "00";
    }

    /**
     * 检测设置时间返回的数据是否正确
     *
     * @param data 设置时间返回的数据
     * @return 0为正确 -1数据格式不正确 -2重发指令 -3部分重发
     */
    public static int checkSetTimeBackData(String data) {
        return DataManageUtils.jiaoYanData(data, "FF", "0C");
    }

    /**
     * 获取设置比例的发送数据
     * @param ratio int
     * @return 设置比例的发送数据
     */
    public static String getSetRatioData(int ratio) {
        if (ratio > 60000) {
            return null;
        }
        int high = ratio >> 8 & 0xff;
        int low = ratio >> 0 & 0xff;
        String highStr = DataManageUtils.toHexString(high);
        String lowStr = DataManageUtils.toHexString(low);
        String jiaoYan = DataManageUtils.getJiaoYan(highStr, lowStr);
        return "FF0D03" + highStr + lowStr +
                "00000000000000000000000000" + jiaoYan + "00";
    }

    /**
     * 检测设置比例返回的数据是否正确
     *
     * @param data 设置比例返回的数据
     * @return 0为正确 -1数据格式不正确 -2重发指令 -3部分重发
     */
    public static int checkSetRatioBackData(String data) {
        return DataManageUtils.jiaoYanData(data, "FF", "0D");
    }

    /**
     * 检测设置字库返回的数据是否正确
     *
     * @param data 设置字库返回的数据
     * @return 0为正确 -1数据格式不正确 -2重发指令 -4重发B1部分 -5重发B2部分
     */
    public static int checkSetZiKuBackData(String data){
        int jiaoYanData = DataManageUtils.jiaoYanData(data, "FF", "0B");
        if (jiaoYanData == -1 || jiaoYanData == -2) {
            return jiaoYanData;
        } else if (jiaoYanData == -3) {
            String[] splitData = data.split(" ");
            if (splitData[3].equals("B1")) {
                return -4;
            } else if (splitData[3].equals("B2")) {
                return -5;
            }
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 获取清除流程发送数据
     * @return String
     */
    public static String getCleanData(){
        return "FF0E010000000000000000000000000000000000";
    }

    /**
     * 检测清除流程返回的数据是否正确
     *
     * @param data 清除流程返回的数据
     * @return 0为正确 -1数据格式不正确 -2重发指令 -3部分重发
     */
    public static int checkCleanBackData(String data) {
        return DataManageUtils.jiaoYanData(data, "FF", "0E");
    }


    /**
     * 检测设置公司名返回的数据是否正确
     *
     * @param data 设置公司名返回的数据
     * @return 0为正确 -1数据格式不正确 -2重发指令 -4重发B1部分 -5重发B2部分
     */
    public static int checkSetNameBackData(String data){
        int jiaoYanData = DataManageUtils.jiaoYanData(data, "FF", "10");
        if (jiaoYanData == -1 || jiaoYanData == -2) {
            return jiaoYanData;
        } else if (jiaoYanData == -3) {
            String[] splitData = data.split(" ");
            if (splitData[3].equals("B1")) {
                return -4;
            } else if (splitData[3].equals("B2")) {
                return -5;
            }
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 获取清除FLASH发送数据
     * @return String
     */
    public static String getCleanFlashData(){
        return "FF11010000000000000000000000000000000000";
    }

    /**
     * 检测清除FLASH返回的数据是否正确
     *
     * @param data 清除FLASH返回的数据
     * @return 0为正确 -1数据格式不正确 -2重发指令 -3部分重发
     */
    public static int checkCleanFlashBackData(String data) {
        return DataManageUtils.jiaoYanData(data, "FF", "11");
    }


    /**
     * 获取设置最小比例的发送数据
     * @param ratio int
     * @return 设置最小比例的发送数据
     */
    public static String getSetLeastRatioData(int ratio) {
        if (ratio > 100) {
            return null;
        }
        int high = ratio >> 8 & 0xff;
        int low = ratio >> 0 & 0xff;
        String highStr = DataManageUtils.toHexString(high);
        String lowStr = DataManageUtils.toHexString(low);
        String jiaoYan = DataManageUtils.getJiaoYan(highStr, lowStr);
        return "FF1203" + highStr + lowStr +
                "00000000000000000000000000" + jiaoYan + "00";
    }

    /**
     * 检测设置最小比例返回的数据是否正确
     *
     * @param data 设置最小比例返回的数据
     * @return 0为正确 -1数据格式不正确 -2重发指令 -3部分重发
     */
    public static int checkSetLeastRatioBackData(String data) {
        return DataManageUtils.jiaoYanData(data, "FF", "12");
    }
}
