package net.esindo.crmapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by riyan on 04/12/2018.
 */

public class Penjualan {

   // @SerializedName("id")
   // private String id;
   // @SerializedName("nopo")
    private String nopo;
   // @SerializedName("pt")
    private String pt;
   // @SerializedName("produk")
    private  String produk;
   // @SerializedName("total")
    private  String total;

    //public String getId() {
      //  return id;
    //}

    public String getNopo() {
        return nopo;
    }

    public String getPt() {
        return pt;
    }

    public String getProduk() {
        return produk;
    }

    public String getTotal() {
        return total;
    }

    /*
    public Penjualan() {
    }

    public Penjualan(String id, String pt, String produk, int total) {
        this.id = id;
        this.pt = pt;
        this.produk = produk;
        this.total = total;
    }

    public String getNopo() {
        return nopo;
    }

    public void setNopo(String nopo) {
        this.nopo = nopo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public String getProduk() {
        return produk;
    }

    public void setProduk(String produk) {
        this.produk = produk;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    */
}
