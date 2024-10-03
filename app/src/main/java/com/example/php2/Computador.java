package com.example.php2;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Computador {
    String ref, cpu, gpu, ram, ssd, psu;

    public Computador(JSONObject objetoJSON) throws JSONException {
        this.ref = objetoJSON.getString("ref");
        this.cpu = objetoJSON.getString("cpu");
        this.gpu = objetoJSON.getString("gpu");
        this.ram = objetoJSON.getString("ram");
        this.ssd = objetoJSON.getString("ssd");
        this.psu = objetoJSON.getString("psu");
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getSsd() {
        return ssd;
    }

    public void setSsd(String ssd) {
        this.ssd = ssd;
    }

    public String getPsu() {
        return psu;
    }

    public void setPsu(String psu) {
        this.psu = psu;
    }

    @NonNull
    @Override
    public String toString() {
        return "Ref: " + ref + "\n" +
                "CPU: " + cpu + "\n" +
                "GPU: " + gpu + "\n" +
                "RAM: " + ram + "\n" +
                "SSD: " + ssd + "\n" +
                "PSU: " + psu + "\n";
    }
}