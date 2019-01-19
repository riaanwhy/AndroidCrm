package net.esindo.crmapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by riyan on 15/12/2018.
 */

public class Produk {
    
    @SerializedName("Name")
    private  String name;
    @SerializedName("harga")
    private  String harga;

    public Produk() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
