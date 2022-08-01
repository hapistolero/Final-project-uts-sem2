/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalprojectutssem2;



public class pendapatan {
    private String namaproduk;
    private double total;
    private double totalfin;
    pendapatan(String n, double t){
        namaproduk = n;
        total = t;
        
    }
     
    public void setTotal(double t){
        total=t;
    }
    public double getTotal(){
        return total;
    }
     public String getNama(){
        return namaproduk;
     }
     
      public double getTotalfin(){
        return totalfin;
     }
    
    
}

