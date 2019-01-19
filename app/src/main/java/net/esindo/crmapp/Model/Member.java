package net.esindo.crmapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by riyan on 04/12/2018.
 */

public class Member {

    @SerializedName("id")
    private  String id;
    @SerializedName("no")
    private  String no;
    @SerializedName("awal")
    private  String  awal;
    @SerializedName("akhir")
    private  String akhir;
    @SerializedName("pt")
    private  String pt;


    public Member() {
    }

    public Member(String id, String no, String awal, String akhir, String pt) {
        this.id = id;
        this.no = no;
        this.awal = awal;
        this.akhir = akhir;
        this.pt = pt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getAwal() {
        return awal;
    }

    public void setAwal(String awal) {
        this.awal = awal;
    }

    public String getAkhir() {
        return akhir;
    }

    public void setAkhir(String akhir) {
        this.akhir = akhir;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }
}
