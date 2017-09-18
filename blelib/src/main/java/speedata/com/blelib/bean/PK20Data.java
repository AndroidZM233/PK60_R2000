package speedata.com.blelib.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 张明_ on 2017/9/5.
 */

public class PK20Data implements Parcelable{
    public String barCode;

    public String wangDian;
    public String center;
    public String muDi;
    public String liuCheng;
    public String L;
    public String W;
    public String H;
    public String V;
    public String G;
    public String time;
    public String zhu;
    public String zi;
    public String biaoJi;


    protected PK20Data(Parcel in) {
        barCode = in.readString();
        wangDian = in.readString();
        center = in.readString();
        muDi = in.readString();
        liuCheng = in.readString();
        L = in.readString();
        W = in.readString();
        H = in.readString();
        V = in.readString();
        G = in.readString();
        time = in.readString();
        zhu = in.readString();
        zi = in.readString();
        biaoJi = in.readString();
    }

    public static final Creator<PK20Data> CREATOR = new Creator<PK20Data>() {
        @Override
        public PK20Data createFromParcel(Parcel in) {
            return new PK20Data(in);
        }

        @Override
        public PK20Data[] newArray(int size) {
            return new PK20Data[size];
        }
    };

    public PK20Data(String barCode, String wangDian, String center, String muDi, String liuCheng, String l, String w, String h, String v, String g, String time, String zhu, String zi, String biaoJi) {
        this.barCode = barCode;
        this.wangDian = wangDian;
        this.center = center;
        this.muDi = muDi;
        this.liuCheng = liuCheng;
        L = l;
        W = w;
        H = h;
        V = v;
        G = g;
        this.time = time;
        this.zhu = zhu;
        this.zi = zi;
        this.biaoJi = biaoJi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(barCode);
        parcel.writeString(wangDian);
        parcel.writeString(center);
        parcel.writeString(muDi);
        parcel.writeString(liuCheng);
        parcel.writeString(L);
        parcel.writeString(W);
        parcel.writeString(H);
        parcel.writeString(V);
        parcel.writeString(G);
        parcel.writeString(time);
        parcel.writeString(zhu);
        parcel.writeString(zi);
        parcel.writeString(biaoJi);
    }
}
