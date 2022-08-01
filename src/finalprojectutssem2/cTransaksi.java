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
import java.util.Scanner;
public class cTransaksi {
    private String kode;
    private Cpembeli[] pembeli;
    private cPeralatanmasak[] alatmsk,totald;
    private int jpr,maks,total,kr;
    private String kembalian;
    private String totalx[];
    private Scanner sc = new Scanner (System.in);
    
    cTransaksi(String k, int n){
        jpr=0;
        kode=k;
        maks= 100;
        
        totalx = new String[maks];
        pembeli = new Cpembeli[maks];
        alatmsk = new cPeralatanmasak[maks];
        totald = new cPeralatanmasak[maks];
        System.out.println("Objek pembelian dibuat...");
    }
    public void tambahalat(cPeralatanmasak p){ 
            alatmsk[jpr]=p;
            jpr++;
                 
    }
    
     public  void total(cPeralatanmasak t){
             totald[jpr]=t;
            jpr++;
        }
        public void hapusalat(String n){
         if(jpr>0){
             boolean ada=false;
             
             for (int i = 0; i < jpr; i++) {
                 
                 if(alatmsk[i].getNama().equalsIgnoreCase(n)){
                     
                     ada=true;
                     for (int j = i; j < jpr; j++) {
                         if(j==jpr-1){
                             alatmsk[j]=null;
                             
                         }else
                             alatmsk[j]=alatmsk[j+1];
                         break;
                     }
                     jpr--;
                     System.out.println("delete done");
                 
                 }
                 if (ada==false){
                     System.out.println("Not found..");
                 
             }
                     
                     }
             
         }else {
             System.out.println("Empty..");
         }
        }
        public void showAlat(){
            System.out.println("Keranjang");
            if(jpr==0){
                System.out.println("kosong");
            }else{
                for (int i = 0; i < jpr; i++) {
                    System.out.println(alatmsk[i].getNama());
                    System.out.println(" [Rp."+alatmsk[i].getHarga()+"] ");
                    
                }
            }
            System.out.println("");
        }
        public String getKode(){
            return kode;
        }
        public String getTotal(){
            return totalx[jpr];
        }
        public cPeralatanmasak[] getAlatmasak(){
            return alatmsk;
        }
        
      
        }
            
             
 

