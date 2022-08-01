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
public class cRegPelanggan {
     private String nama ;
    private String kode ;
    
    private String alamat;
    cRegPelanggan(String n , String a, String k){
     nama=n; kode=k; alamat=a;
     
     
    }
    
    public String getNama(){
        return nama;
    }
    public String getKode(){
        return kode;
    }
    public String getAlamat(){
        return alamat;
    }
}
