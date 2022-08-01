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
public class cToko {
    private String namatoko;
    private String alamattoko;
    
    cToko (){
        System.out.println("Object Toko dibuat.. ");
    }
    cToko (String n, String a){
        namatoko = n; alamattoko=a;
        System.out.println("Objek "+namatoko+" dibuat");
    }
    public void setAlamat(String a){
        alamattoko=a;
    }
    public String ToString (){
        return namatoko+" ["+alamattoko+"] ";
    }
    
}
