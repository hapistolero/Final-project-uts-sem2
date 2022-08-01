/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalprojectutssem2;
//owner menjual alat alat masak kepada pembeli

public class cPemilik {
    private String namapem;
    private String alamatpem;
    private String notelppem;
    private cToko toko;
    cPemilik (String n, String a ,String no){
        namapem = n; alamatpem = a;
        System.out.println("User "+namapem+" login");
    }
    public void setAlamat(String a){
        alamatpem=a;
    }
    public void setNama (String n){
        namapem=n;
    }
    public String ToString(){
        return namapem+" ["+alamatpem+"]"+" ["+notelppem+"] ";
    }
    public void lihatalamattoko(){
        System.out.println("  "+namapem+" Pemilik Toko "+toko.ToString());
    }
}

