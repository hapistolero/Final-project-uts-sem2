/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalprojectutssem2;

/**
 *
 * @author hafiz ilham ardana
 */
public class cPeralatanmasak {
    private String nama;
    private double harga;
    private String kode;
    private String kategori;
    cPeralatanmasak(String n,  String k, String kat,double h){
        nama =n; harga=h; kode=k; kategori=kat;
        System.out.println("Objek "+nama+" dibuat..");
    }
    public void setHarga(double h){
        harga=h;
    }
    public String getNama(){
        return nama;
    }
     public double getHarga(){
        return harga;
     }
     public String getKode(){
         return kode;
     }
     public String getKategori(){
         return kategori;
     }
      
   
}
