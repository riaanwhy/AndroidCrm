package net.esindo.crmapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Esindo on 08/02/2018.
 */

public class Event {

    @SerializedName("nama")
    private String nama;
    @SerializedName("harga")
    private String harga;
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("tempat_id")
    private  String tempat_id;

    public Event(String nama, String harga, String tanggal, String tempat_id) {
        this.nama = nama;
        this.harga = harga;
        this.tanggal = tanggal;
        this.tempat_id = tempat_id;
    }

    public Event() {
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getTanggal() {
        return tanggal.toString();
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTempat_id() {
        return tempat_id;
    }

    public void setTempat_id(String tempat_id) {
        this.tempat_id = tempat_id;
    }

    @Override
    public String toString() {
        return nama ;
    }

}
