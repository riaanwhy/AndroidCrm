package net.esindo.crmapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by riyan on 17/08/2018.
 */

public class Customer {

    @SerializedName("id")
    private  String id;

    @SerializedName("nama")
    private  String nama;

    @SerializedName("alamat")
    private  String alamat;

    @SerializedName("telpon")
    private  String telpon;

    public Customer() {

    }

/*
    public Customer() {

    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getTelpon() {
        return telpon;
    }
    */
public Customer(String id,String nama,String alamat,String telpon) {
        this.id = id ;
        this.nama = nama;
        this.alamat = alamat;
        this.telpon = telpon;

    }

    public String getTelpon() {
        return telpon;
    }

    public void setTelpon(String telpon) {
        this.telpon = telpon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

}
