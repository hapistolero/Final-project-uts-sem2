/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalprojectutssem2;



public class cPelanggan  {
    private String nama ;
    private String kode ;
    private double total ;
    private String alamat;
    cPelanggan(String n , String k, double t){
     nama=n; kode=k; total=t;
     
     
    }
    
    public String getNama(){
        return nama;
    }
    public String getKode(){
        return kode;
    }
    public double getTotal(){
        return total;
    }
    
}
