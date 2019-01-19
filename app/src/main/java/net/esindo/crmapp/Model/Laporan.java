package net.esindo.crmapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 *
 * Created by riyan on 05/12/2018.
 *
 */

public class Laporan {

    private  String pt;
    private  String tgl;
    private  String nopo;
    private  String produk;
    private  String harga;
    private  String qty;
    private  String total;

    public String getPt() {
        return pt;
    }

    public String getTgl() {
        return tgl;
    }

    public String getNopo() {
        return nopo;
    }

    public String getProduk() {
        return produk;
    }

    public String getHarga() {
        return harga;
    }

    public String getQty() {
        return qty;
    }

    public String getTotal() {
        return total;
    }

    /* @SerializedName("pt")
    private  String pt;
    @SerializedName("tgl")
    private  String tgl;
    @SerializedName("nopo")
    private  String nopo;
    @SerializedName("produk")
    private  String produk;
    @SerializedName("harga")
    private  String harga;
    @SerializedName("qty")
    private  String qty;
    @SerializedName("total")
    private  String total;

    public Laporan() {
    }


    public Laporan(String tgl, String nopo, String produk, String harga, String qty, String total) {
        this.tgl = tgl;
        this.nopo = nopo;
        this.produk = produk;
        this.harga = harga;
        this.qty = qty;
        this.total = total;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getNopo() {
        return nopo;
    }

    public void setNopo(String nopo) {
        this.nopo = nopo;
    }

    public String getProduk() {
        return produk;
    }

    public void setProduk(String produk) {
        this.produk = produk;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    */
}
