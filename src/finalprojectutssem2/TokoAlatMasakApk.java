/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalprojectutssem2;

import com.mysql.jdbc.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
/**
 *
 * @author hafiz ilham ardana
 */
public class TokoAlatMasakApk extends javax.swing.JFrame {

    /**
     * Creates new form TokoAlatMasakApk
     */
   
  int idex =0;

     Connection con = null;
    Statement st = null;
    ResultSet res =null;
    PreparedStatement pst = null;
    
    cPeralatanmasak alatmsk[];cPeralatanmasak tomas[];
    cKonvensional alatkn[];
    cElektronik alatelk[];
    cTransaksi tr;
    Cpembeli pbl[] ;
    cPelanggan plg[];
    pendapatan pn[];
    cDiskon cd [];
    
    String[] nampro = new String[100];
     String[] totpro = new String[100];
    int jumtr=0;
    int jpn = 0;
    int jumbl=0;
    int jtr =0;
    int jplg =0;
    double total;
    int jpbl=0;
     private JFrame frame;
    public TokoAlatMasakApk() {
        cd = new cDiskon[100];
        pn = new pendapatan[100];
        tomas = new cPeralatanmasak[100];
        pbl = new Cpembeli[100];
        plg = new cPelanggan[100];
        alatmsk = new cPeralatanmasak[100];
        alatkn = new cKonvensional[100];
        alatelk = new cElektronik[100];
        tr = null;
        initComponents();
        koneksidb();
        showmenu();
        time();
       
       
        
    }
     public  void koneksidb(){
         try{
             Class.forName("com.mysql.jdbc.Driver");
               con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/apkalatmasak","root","");
               st = con.createStatement();
             st =(Statement) con.createStatement();
             JOptionPane.showMessageDialog(null,"terkoneksi");
         }catch(Exception e){
             
         }
            
           
            

     }
     public void showmenu(){
         DefaultTableModel tb = new DefaultTableModel();
         tb.addColumn("Nama Produk");
         tb.addColumn("Kode");
         tb.addColumn("Kategori");
         tb.addColumn("Harga");
         tabmenu.setModel(tb);
         
        try{
            res = st.executeQuery("Select * from tbproduk");
             while (res.next()){
             tb.addRow(new Object[]{
             res.getString("nama"),
             res.getString("kode"),
             res.getString("kategori"),
             res.getString("harga"),
             });
             
             }
        }catch (Exception e){
           JOptionPane.showMessageDialog(null, "Gagal Menampilkan,,"+e.getMessage());
        }
     }
    public void totalin(){
        
        
        double sum = 0;
        
        for (int i = 0; i < tabtr.getRowCount(); i++) {
            sum = sum + Double.parseDouble(tabtr.getValueAt(i,5).toString());
            
            
        }
        totals.setText(Double.toString(sum));
        brcd.setText("Total is "+totals.getText());
        
            //String brc = String.format("total is %.2f", ctotal);
            //jtextbarcode.setText(brc);
    }
    
    public void trdb() {
        
         
        for (int i = 0; i <tabtr.getRowCount(); i++) {
          
            String kodtr = kodetr.getText();
              String pmbli= pmbl.getText();
              String tod = totals.getText();
                String prodt = tabtr.getValueAt(i,0).toString();
                String kodet = tabtr.getValueAt(i,1).toString();
                String kat = tabtr.getValueAt(i,2).toString();
                String hargat = tabtr.getValueAt(i,3).toString();
                String qu = tabtr.getValueAt(i,4).toString();
                String su = tabtr.getValueAt(i,5).toString();
         
                try {
                
                PreparedStatement pStatement = con.prepareStatement("INSERT tbtransaksi(kodetr,nama,produk,kodebrg,kategori,harga,jumlah,subtotal,total)" + "VALUES (?,?,?,?,?,?,?,?,?)");
                pStatement.setString(1, kodtr);
                pStatement.setString(2, pmbli);

                pStatement.setString(3, prodt);
                pStatement.setString(4, kodet);
                pStatement.setString(5, kat);
                pStatement.setString(6, hargat);
                pStatement.setString(7, qu);
                pStatement.setString(8, su);
                pStatement.setString(9, tod);
                
                if (pStatement.executeUpdate()>0)
                JOptionPane.showMessageDialog(this,"Penambahan sukses", "Informasi",JOptionPane.INFORMATION_MESSAGE);

                else
                JOptionPane.showMessageDialog(this,"Penambahan gagal", "Informasi",JOptionPane.INFORMATION_MESSAGE);

                
                 

                
                }
                catch (SQLException e){
                System.out.println("koneksi gagal " + e.toString());
                }
                
                
                
        }
        
        }
    
    
    public void kembalian(){
       
        
        
        double sum = 0;
        double cash = Double.parseDouble(uangd.getText());
        
         for (int i = 0; i < tabtr.getRowCount(); i++) {
            sum = sum + Double.parseDouble(tabtr.getValueAt(i,5).toString());
            
            
        }
         
         double change = (cash - sum);
         
        kembalian.setText(Double.toString(sum));
        
    }
    
    
    
    public void income(){
       
        for (int i = 0; i < tabtr.getRowCount(); i++) {
             
            
           
            
            String n = tabtr.getValueAt(i, 0).toString();
            String x = tabtr.getValueAt(i, 5).toString();
            double t = Double.parseDouble(x);
            
           pn[jpn]= new pendapatan(n,t);
                    jpn++;   
                
        }
    
        
        
    }
    
    public void time(){
            Timer updateTimer;
            int delay = 100;
            
            updateTimer =new Timer (delay, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               
                         Date currentTime = new Date();
                String formattimestr = "hh:mm:ss";
                DateFormat formattime = new SimpleDateFormat(formattimestr);
                String formattedtimestr = formattime.format(currentTime);
               
                
                jm.setText(formattedtimestr);
                }
                });
                updateTimer.start();
        }
    
     
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabmenu = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabtr = new javax.swing.JTable();
        brcd = new javax.swing.JTextField();
        pmlk = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        totals = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        uangd = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        kembalian = new javax.swing.JTextField();
        tambahbl = new javax.swing.JButton();
        Batalbl = new javax.swing.JButton();
        hapusbl = new javax.swing.JButton();
        bayar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        pendapatan = new javax.swing.JButton();
        trplg = new javax.swing.JButton();
        tambahmnalat = new javax.swing.JButton();
        hapusmenu = new javax.swing.JButton();
        ubahhargamenu = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        cekkode = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        kodetr = new javax.swing.JTextField();
        pmbl = new javax.swing.JTextField();
        tambahbl1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        plg1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 250, 205));

        jPanel1.setBackground(new java.awt.Color(255, 250, 205));

        jPanel2.setBackground(new java.awt.Color(245, 222, 179));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        tabmenu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabmenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Produk", "Kode Barang", "Kategori", "Harga"
            }
        ));
        jScrollPane1.setViewportView(tabmenu);

        jScrollPane3.setViewportView(jScrollPane1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(245, 222, 179));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        tabtr.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tabtr.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Produk", "Kode barang", "Kategori", "Harga", "Jumlah", "Subtotal"
            }
        ));
        jScrollPane2.setViewportView(tabtr);

        brcd.setFont(new java.awt.Font("C39HrP24DhTt", 1, 66)); // NOI18N
        brcd.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        brcd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brcdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(brcd)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 672, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(brcd, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
        );

        pmlk.setBackground(new java.awt.Color(245, 222, 179));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setText("Nama Pengguna :");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel8.setText("Toko                 :");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel9.setText("Alamat Toko      :");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel10.setText("Tanggal             :");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel11.setText("Jam                  :");

        pgn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        toko.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        tgl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        jm.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        almat.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        javax.swing.GroupLayout pmlkLayout = new javax.swing.GroupLayout(pmlk);
        pmlk.setLayout(pmlkLayout);
        pmlkLayout.setHorizontalGroup(
            pmlkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pmlkLayout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addGroup(pmlkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pmlkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pmlkLayout.createSequentialGroup()
                        .addGroup(pmlkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(pgn, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                            .addComponent(toko, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(almat, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tgl, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(9, 9, 9)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jm, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        pmlkLayout.setVerticalGroup(
            pmlkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pmlkLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pmlkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel12)
                    .addComponent(pgn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pmlkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(toko, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(pmlkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(almat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pmlkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pmlkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Total :");

        totals.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        totals.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalsActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Uang :");

        uangd.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        uangd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uangdActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Kembalian :");

        kembalian.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        kembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kembalianActionPerformed(evt);
            }
        });

        tambahbl.setText("Tambah ");
        tambahbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahblActionPerformed(evt);
            }
        });

        Batalbl.setText("Clear");
        Batalbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BatalblActionPerformed(evt);
            }
        });

        hapusbl.setText("Hapus");
        hapusbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusblActionPerformed(evt);
            }
        });

        bayar.setText("Bayar");
        bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarActionPerformed(evt);
            }
        });

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        pendapatan.setText("Laporan pendapatan");
        pendapatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pendapatanActionPerformed(evt);
            }
        });

        trplg.setText("Transaksi Pelanggan");
        trplg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trplgActionPerformed(evt);
            }
        });

        tambahmnalat.setText("tambah");
        tambahmnalat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahmnalatActionPerformed(evt);
            }
        });

        hapusmenu.setText("hapus");
        hapusmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusmenuActionPerformed(evt);
            }
        });

        ubahhargamenu.setText("Ubah harga");
        ubahhargamenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ubahhargamenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(tambahmnalat, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hapusmenu, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ubahhargamenu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(pendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(trplg, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trplg)
                    .addComponent(tambahmnalat)
                    .addComponent(pendapatan)
                    .addComponent(hapusmenu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ubahhargamenu)
                .addContainerGap())
        );

        jButton11.setText("Regist Pelangan");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        cekkode.setText("Cek Kode");
        cekkode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cekkodeActionPerformed(evt);
            }
        });

        jButton16.setText("Log Out");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel4.setText("Kode transaksi   :");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel5.setText("Pembeli              : ");

        kodetr.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        kodetr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kodetrActionPerformed(evt);
            }
        });

        pmbl.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        pmbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pmblActionPerformed(evt);
            }
        });

        tambahbl1.setText("Tambah Kode Transaksi");
        tambahbl1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahbl1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel6.setText("Kode Pelanggan :");

        plg1.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        plg1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plg1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pmlk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(Batalbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tambahbl))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(hapusbl, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(tambahbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(plg1))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pmbl, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGap(21, 21, 21)
                                    .addComponent(kodetr, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(totals)
                                .addComponent(kembalian)
                                .addComponent(uangd))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cekkode, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton11)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totals, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(uangd, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(kodetr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(pmbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(plg1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cekkode)
                            .addComponent(jButton11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton16)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pmlk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(tambahbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tambahbl, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hapusbl, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Batalbl, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hapusblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusblActionPerformed
      
           int mks = jumtr;
           boolean ketemu=false;
          
           int idx=0;
           String id = JOptionPane.showInputDialog(this,"Masukan Nama Produk");
          
           for (int i = 0; i < tabtr.getRowCount(); i++) {
               idx=i;
               if (tabtr.getValueAt(i, 0).toString().equalsIgnoreCase(id)) {
                   ketemu=true;
                   
                   break;
                   
               } else {
                   JOptionPane.showMessageDialog(this, "....");
               }
           }
           if (ketemu==true) {
               int j =JOptionPane.showConfirmDialog(this,"Yakin Dihapus?");
               if (j==0) {
                    
                   
                   
                   DefaultTableModel model = (DefaultTableModel)tabtr.getModel();
                   model.removeRow(idx);
                   JOptionPane.showMessageDialog(this, "berhasil dihapus...");
                   totalin();
                   
               }
           }
       
    }//GEN-LAST:event_hapusblActionPerformed

    private void kembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kembalianActionPerformed

    private void brcdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brcdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_brcdActionPerformed

    private void tambahmnalatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahmnalatActionPerformed
        String m = JOptionPane.showInputDialog(this, "Masukan Nama Alat");
        String k = JOptionPane.showInputDialog(this, "Masukan kode");
        String kat = JOptionPane.showInputDialog(this, "Masukan kategori");

  
        double h = Double.parseDouble(JOptionPane.showInputDialog(this, "Masukan Harga"));
        boolean ada = false;
        for (int i = 0; i < jumtr; i++) {
            if(alatmsk[i].getNama().equalsIgnoreCase(m)){
                ada = true;
                break;
            }
        }
        if(ada==true){
            JOptionPane.showMessageDialog(this, "Barang Sudah Ada");
        }
        else{
            if("konvensional".equals(kat)){
                alatkn[jumtr]= new cKonvensional(m,k,kat,h);
                
            }else if("elektronik".equals(kat)){
                 alatelk[jumtr]= new cElektronik(m,k,kat,h);
                 
            }
           
            

           
            alatmsk[jumtr]= new cPeralatanmasak(m,k,kat,h);
           
            
             String row[][]= new String[1][4];
            row [0][0] = alatmsk[jumtr].getNama();
            row [0][1] = alatmsk[jumtr].getKode();
            row [0][2] = String.valueOf(alatmsk[jumtr].getKategori());
            row [0][3] = String.valueOf(alatmsk[jumtr].getHarga());
            DefaultTableModel model = (DefaultTableModel)tabmenu.getModel();  
            model.addRow(row[0]);
            jumtr++;
            
             try{
            
               Class.forName("com.mysql.jdbc.Driver");
               con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/apkalatmasak","root","");
               st = con.createStatement();
               String save = "insert into tbproduk values( '" +m+"','" +k+"','"+kat+"','"+h+"')";
               st = con.createStatement();
               int SA = st.executeUpdate(save);
               JOptionPane.showMessageDialog(this, "update Berhasil");
              
            
       }catch (Exception e){
            JOptionPane.showMessageDialog(this,"Sudah ada","Pesan",JOptionPane.WARNING_MESSAGE);
        }
            
            
          
    }
            
           
            
        
    }//GEN-LAST:event_tambahmnalatActionPerformed

    private void ubahhargamenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ubahhargamenuActionPerformed
       String id = JOptionPane.showInputDialog(this, "Masukan Nama Produk");
       String  hrg = JOptionPane.showInputDialog(this, "Masukan harga baru");

      
      int idx = 0;
          boolean ketemu = false;
           for (int i = 0; i < tabmenu.getRowCount(); i++) {
               idx=i;
               if (tabmenu.getValueAt(i, 0).toString().equalsIgnoreCase(id)) {
                   ketemu=true;
                   break;
                   
               } else {
                   JOptionPane.showMessageDialog(this, ".....");
               
               }
           
           }
           if (ketemu==true) { 
               
               int j =JOptionPane.showConfirmDialog(this,"Yakin Diganti?");
           
               if (j==0) {
                     
            String usrID = tabmenu.getValueAt(idx,3).toString();
                   System.out.println(usrID);
        String sql ="update tbproduk set harga='"+hrg+
                "' where nama='"+id+"'";
        try {
            st = con.createStatement();
            st.executeUpdate(sql);
            showmenu();
            DefaultTableModel model = (DefaultTableModel)tabmenu.getModel();
            model.setValueAt(hrg, idx, 3);
            
            JOptionPane.showMessageDialog(null, "Data telah terupdate");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error "+e.getMessage());
        }
           
                    
       
                    
                  
                   
     
                   }
               }
          
        
    }//GEN-LAST:event_ubahhargamenuActionPerformed

    private void hapusmenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusmenuActionPerformed
           
         int idx = 0;
          boolean ketemu = false;
          String id = JOptionPane.showInputDialog(this,"Masukan Nama Produk");
          
                  
           for (int i = 0; i < tabmenu.getRowCount(); i++) {
               idx=i;
               if (tabmenu.getValueAt(i, 0).toString().equalsIgnoreCase(id)) {
                   ketemu=true;
                   
                   break;
                   
               } else {
                   JOptionPane.showMessageDialog(this, ".....");
               
           }
           
           }
           if (ketemu==true) {
               int j =JOptionPane.showConfirmDialog(this,"Yakin Dihapus?");
               if (j==0) {
                    
                    String usrID = tabmenu.getValueAt(idx,0).toString();
      try {
             PreparedStatement pStatement = null;
                      String sql ="DELETE from tbproduk WHERE nama=? ";
                      pStatement = con.prepareStatement(sql);
                      pStatement.setString(1, usrID);
                      int intTambah= pStatement.executeUpdate();
                      DefaultTableModel model = (DefaultTableModel)tabmenu.getModel();
                    model.removeRow(idx);
               if (intTambah>0)
                    JOptionPane.showMessageDialog(this,
                    "Hapus data sukses", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
                    

                else
                    JOptionPane.showMessageDialog(this,
                    "Hapus data gagal", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
                    pStatement.close();
                    showmenu();
                    }
                    catch (SQLException e){
                    System.out.println("koneksi gagal " + e.toString());
                }
                   
                   
                  
                   
               }
           }
        
       
    }//GEN-LAST:event_hapusmenuActionPerformed

    private void tambahblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahblActionPerformed
String id = JOptionPane.showInputDialog(this, "Masukan Nama Produk");
      String qty = JOptionPane.showInputDialog(this, "Masukan jumlah");
      double qty2 = Double.parseDouble(qty);
      int subt =0;
     
        try {
     
     Statement sta = con.createStatement();
     ResultSet rs = sta.executeQuery
    ("Select * from tbproduk" + " where nama like('%" +
             id + "%')");
             if (rs.next()) {
                  String namap =rs.getString("nama");
                   String kodep =rs.getString("kode");
                    String kat =rs.getString("kategori");
                     String harp =rs.getString("harga");
                 double hargap = Double.parseDouble(harp);
                 Double subt1 = hargap*qty2;
                 String subt2 = String.valueOf(subt1);
                 
                 
                 
                  DefaultTableModel model = (DefaultTableModel) tabtr.getModel();
                  model.addRow(new Object[]{namap,kodep,kat,harp,qty,subt2});
                  totalin();
                
              } else {
              JOptionPane.showMessageDialog(this ,"tidak ada");
              
                
                
                          
}
}
catch (SQLException e){
System.out.println("koneksi gagal " + e.toString());
}

    }//GEN-LAST:event_tambahblActionPerformed

    private void BatalblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BatalblActionPerformed
        tr = null;
        total=0; jumbl=0;
        kodetr.setText("");
        pmbl.setText("");
        uangd.setText("");
        totals.setText("");
        kembalian.setText("");       
        totals.setText("");
        plg1.setText("");
        brcd.setText("");
        DefaultTableModel model = (DefaultTableModel)tabtr.getModel();
        model.setRowCount(0);
        
        
    }//GEN-LAST:event_BatalblActionPerformed

    private void totalsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalsActionPerformed

    private void tambahbl1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahbl1ActionPerformed
        String kd = JOptionPane.showInputDialog(this,"Masukan Kode");
        String pl = JOptionPane.showInputDialog(this,"Masukan Nama Pelanggan");
        int jum = 100;
        tr =new cTransaksi(kd,jum);
        kodetr.setText(kd);
        pmbl.setText(pl);
        
        DefaultTableModel model =(DefaultTableModel)tabtr.getModel();
        model.setRowCount(0);
    }//GEN-LAST:event_tambahbl1ActionPerformed

    private void uangdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uangdActionPerformed
      
    }//GEN-LAST:event_uangdActionPerformed

    private void bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarActionPerformed
        String uang = uangd.getText();
        int uang2 = Integer.parseInt(uang);
        double totalis =  uang2 -total;
        double totalisx =  Double.parseDouble(totals.getText());
        double u =  Double.parseDouble(uangd.getText());
        double tot = uang2-totalisx;
         
        if (u>totalisx) {
            
            kembalian.setText(Double.toString(tot));
          
            income();
            trdb();
            String cek = plg1.getText();
            
            if ("".equals(cek)) {
                for (int i = 0; i < tabtr.getRowCount(); i++) {
             
            
           
            
            String n = tabtr.getValueAt(i, 0).toString();
            String x = tabtr.getValueAt(i, 5).toString();
            double t = Double.parseDouble(x);
            
             
                pbl[jpbl] = new Cpembeli(n,t);      
                jpbl++;
        }

            }
            
            
        }else{
            JOptionPane.showMessageDialog(this,"Uang Tidak Cukup");
        }
            
       
       
       
    }//GEN-LAST:event_bayarActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
         regPelanggan rgf = new regPelanggan(); //memanggil form lain
       rgf.setVisible(true);//memperlihatkan form
       rgf.pack();//set size frame
       rgf.setLocationRelativeTo(null);//mengatur lokasi frame
      
       
    }//GEN-LAST:event_jButton11ActionPerformed

    private void pmblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pmblActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pmblActionPerformed

    private void cekkodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cekkodeActionPerformed
     try{
            if(plg1.getText().equals("")){
                JOptionPane.showMessageDialog(rootPane, "Data Tidak Boleh Kosong", "Pesan", JOptionPane.ERROR_MESSAGE);
                plg1.requestFocus();
               
                
            }else{
                st= con.createStatement();
                String sql= ("SELECT * FROM kodeplg WHERE kodeplg ='"+plg1.getText()+"'");
                ResultSet rs = st.executeQuery(sql);
                  
                if(rs.next()){
                    JOptionPane.showMessageDialog(null, "Anda Adalah Pelanggan, anda mendapat diskon 10%");                 
                   pmbl.setText(rs.getString("nama"));
                   
                    double total = Double.parseDouble(totals.getText());
                    double diskon = total*0.1;
                    double totalfin = total-diskon;
                    totals.setText(Double.toString(totalfin));
                    
                    String nampbl = pmbl.getText();
                    String namplg = plg1.getText();
                    double totalplg = totalfin;
                      
                     Double tofin = diskon;
          
                     cd[jtr] = new cDiskon(tofin);
                     jtr++;
                     
                    
                     plg[jplg] = new cPelanggan(nampbl,namplg,totalplg);
                     jplg++;
                        
                    
                }else{
                    JOptionPane.showMessageDialog(rootPane,"Pelanggan tidak terdaftar","pesan", JOptionPane.ERROR_MESSAGE);
                    pmbl.setText("");
                }
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }
                         
    }//GEN-LAST:event_cekkodeActionPerformed

    private void kodetrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kodetrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kodetrActionPerformed

    private void pendapatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pendapatanActionPerformed
 
        TableModel model1 = tabtr.getModel();
        int indexs[] = tabtr.getSelectedRows();
        
        
        
        
       Object[] row = new Object[2];
       
        framep frm2 = new framep();
        DefaultTableModel model2 = (DefaultTableModel)frm2.tabp.getModel();
        
        double sum =0;
                 
        for(int i = 0; i < jpn; i++)
        {   
          
                       row [0]= pn[i].getNama();
                       row [1]=String.valueOf(pn[i].getTotal());
            
            
            
            model2.addRow(row);
        }
        
        for (int i = 0; i < jpn; i++) {
            sum = sum + Double.parseDouble(frm2.tabp.getValueAt(i,1).toString());
        }
        
        double disk = 0;
        for (int i = 0; i < jtr; i++) {
            disk = disk + cd[i].getTotaldis();
        
        }
        double sum2 = sum- disk;
        frm2.jlabelpen.setText("Rp."+sum);
        frm2.jlabeldisk.setText("Rp."+sum2);
        
        frm2.setVisible(true);
    }//GEN-LAST:event_pendapatanActionPerformed

    private void plg1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plg1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_plg1ActionPerformed

    private void trplgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trplgActionPerformed
        TableModel model1 = tabtr.getModel();
        int indexs[] = tabtr.getSelectedRows();
        
        
        
        
       Object[] row = new Object[3];
       
        Keranjang_Pelanggan frm3 = new Keranjang_Pelanggan();
        DefaultTableModel model3 = (DefaultTableModel)frm3.tabplg.getModel();
        
        double sum1 =0;
                 
        for(int i = 0; i < jplg; i++)
        {   
          
                       row [0]= plg[i].getNama();
                       row [1]= plg[i].getKode();
                       row [2]=String.valueOf(plg[i].getTotal());
            
            
            
            model3.addRow(row);
        }
        
        for (int i = 0; i < jplg; i++) {
            sum1 = sum1 + Double.parseDouble(frm3.tabplg.getValueAt(i,2).toString());//total belanja pelanggan
        }
        
        double nonp = 0;
        for (int i = 0; i < jpbl ;i++) {
            nonp = nonp +pbl[i].getTotal();
        
        }
        
        frm3.jlp.setText("Rp."+sum1);
        frm3.jlnp.setText("Rp."+nonp);
        
        frm3.setVisible(true);        
    }//GEN-LAST:event_trplgActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
       
        frame = new JFrame("Exit");
        if(JOptionPane.showConfirmDialog(frame,"Yakin Untuk Keluar","SmartCash",
                JOptionPane.YES_NO_OPTION)== JOptionPane.YES_NO_OPTION){
            
        
    }
       
        
        System.exit(0);
    }//GEN-LAST:event_jButton16ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TokoAlatMasakApk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TokoAlatMasakApk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TokoAlatMasakApk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TokoAlatMasakApk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TokoAlatMasakApk().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Batalbl;
    public static final javax.swing.JTextField almat = new javax.swing.JTextField();
    private javax.swing.JButton bayar;
    private javax.swing.JTextField brcd;
    private javax.swing.JButton cekkode;
    private javax.swing.JButton hapusbl;
    private javax.swing.JButton hapusmenu;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton16;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public static final javax.swing.JTextField jm = new javax.swing.JTextField();
    private javax.swing.JTextField kembalian;
    private javax.swing.JTextField kodetr;
    private javax.swing.JButton pendapatan;
    public static final javax.swing.JTextField pgn = new javax.swing.JTextField();
    private javax.swing.JTextField plg1;
    private javax.swing.JTextField pmbl;
    private javax.swing.JPanel pmlk;
    private javax.swing.JTable tabmenu;
    public javax.swing.JTable tabtr;
    private javax.swing.JButton tambahbl;
    private javax.swing.JButton tambahbl1;
    private javax.swing.JButton tambahmnalat;
    public static final javax.swing.JTextField tgl = new javax.swing.JTextField();
    public static final javax.swing.JTextField toko = new javax.swing.JTextField();
    private javax.swing.JTextField totals;
    private javax.swing.JButton trplg;
    private javax.swing.JTextField uangd;
    private javax.swing.JButton ubahhargamenu;
    // End of variables declaration//GEN-END:variables

private Object[][] getData(){
Object[][] data1= null;
try {
   Statement st = con.createStatement();
    ResultSet rs = st.executeQuery("Select * from tbproduk");
       rs.last();
       int rowCount= rs.getRow();
       rs.beforeFirst();
       data1= new Object[rowCount][4];
       int no=-1;
       while (rs.next()) {
       no=no+1;
       data1[no][0]=rs.getString("nama");
       data1[no][1]=rs.getString("kode");
       data1[no][2]=rs.getString("kategori");
       data1[no][3]=rs.getString("harga");
     }
     st.close();
  }
      catch (SQLException e){
      System.out.println("koneksi gagal " +
     e.toString());
}
       return data1;
}
     private void tampilTabel() {
String[] columnNames = {"nama", "kode", "kategori",
"harga"};
 tabmenu = new JTable(getData(), columnNames);
jScrollPane1.setViewportView(tabmenu);
}
}



