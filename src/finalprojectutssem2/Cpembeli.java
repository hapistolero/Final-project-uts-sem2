/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalprojectutssem2;


public class Cpembeli {
    private String nama;
    
    private double totalnp;
    Cpembeli(String n, double t){
        nama=n; totalnp =t;
        System.out.println("Objek "+nama+" Dibuat..");
    }
    public void setNama(String n){
        nama=n;
    }
   
    public String getNama(){
        return nama;
    }
    public double getTotal(){
        return totalnp;
    }
   
    
}
