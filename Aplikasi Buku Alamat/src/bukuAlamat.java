import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Pongo
 */
public class bukuAlamat extends javax.swing.JFrame {

    /**
     * Creates new form bukuAlamat
     */
    public bukuAlamat() {
        initComponents();
    }
     private List<Object[]> originalData = new ArrayList<>(); // Menyimpan data asli sebelum pencarian
    // Metode untuk membatalkan input dan menyegarkan tabel ke keadaan awal
    private void batal() {
        txtNama.setText(""); // Kosongkan field teks untuk nama
        buttonGroup2.clearSelection(); // Hapus pilihan pada grup tombol radio (jenis kelamin)
        tanggalLahir.setDate(null); // Reset tanggal lahir di JDateChooser
        cmbAgama.setSelectedIndex(0); // Reset combo box agama ke pilihan pertama
        txtAreaAlamat.setText(""); // Kosongkan area teks untuk alamat
        txtTlp.setText(""); // Kosongkan field teks untuk telepon
        txtEmail.setText(""); // Kosongkan field teks untuk email
        txtAreaCatatan.setText(""); // Kosongkan area teks untuk catatan
        txtCariData.setText(""); // Kosongkan field teks pencarian

        tabelAlamat.clearSelection(); // Hapus seleksi pada tabel

        // Segarkan tabel dengan data dari `originalData`
        DefaultTableModel model = (DefaultTableModel) tabelAlamat.getModel();
        model.setRowCount(0); // Kosongkan tabel
        for (Object[] row : originalData) {
            model.addRow(row); // Tambahkan kembali data dari `originalData` ke tabel
        }
}

   private void simpanAlamat() {
    DefaultTableModel model = (DefaultTableModel) tabelAlamat.getModel(); // Mendapatkan model tabel
    int selectedRow = tabelAlamat.getSelectedRow(); // Mendapatkan indeks baris yang dipilih

    // Validasi input: pastikan semua field wajib diisi
    if (txtNama.getText().trim().equals("") || buttonGroup2.getSelection() == null || cmbAgama.getSelectedItem() == null
            || cmbAgama.getSelectedItem().toString().equals("-- Pilih --") || txtAreaAlamat.getText().trim().equals("")
            || txtTlp.getText().trim().equals("") || txtEmail.getText().trim().equals("")) {
        JOptionPane.showMessageDialog(null, "Data Yang Anda Masukkan Belum Lengkap! Silahkan Ulangi Lagi!");
    } else {
        // Menentukan jenis kelamin berdasarkan radio button yang dipilih
        String gender = rbLaki.isSelected() ? "Laki-laki" : "Perempuan";

        // Mendapatkan nilai tanggal lahir dan memformatnya
        java.util.Date date = tanggalLahir.getDate(); // Ambil nilai tanggal dari JDateChooser
        String tanggalLahirStr = ""; // Variabel untuk menyimpan tanggal dalam bentuk string
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Format tanggal
            tanggalLahirStr = sdf.format(date); // Format tanggal menjadi string
        }

        // Jika baris dipilih (edit mode)
        if (selectedRow != -1) {
            // Update data pada baris yang dipilih
            model.setValueAt(txtNama.getText(), selectedRow, 0); // Nama
            model.setValueAt(gender, selectedRow, 1); // Jenis Kelamin
            model.setValueAt(tanggalLahirStr, selectedRow, 2); // Tanggal Lahir
            model.setValueAt(cmbAgama.getSelectedItem().toString(), selectedRow, 3); // Agama
            model.setValueAt(txtAreaAlamat.getText(), selectedRow, 4); // Alamat
            model.setValueAt(txtTlp.getText(), selectedRow, 5); // Telepon
            model.setValueAt(txtEmail.getText(), selectedRow, 6); // Email
            model.setValueAt(txtAreaCatatan.getText(), selectedRow, 7); // Catatan

            // Perbarui data di originalData
            originalData.set(selectedRow, new Object[]{
                txtNama.getText(),
                gender,
                tanggalLahirStr,
                cmbAgama.getSelectedItem().toString(),
                txtAreaAlamat.getText(),
                txtTlp.getText(),
                txtEmail.getText(),
                txtAreaCatatan.getText()
            });

            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
        } else {
            // Jika tidak ada baris dipilih (tambah mode), tambahkan baris baru
            Object[] newData = new Object[]{
                txtNama.getText(),
                gender,
                tanggalLahirStr,
                cmbAgama.getSelectedItem().toString(),
                txtAreaAlamat.getText(),
                txtTlp.getText(),
                txtEmail.getText(),
                txtAreaCatatan.getText()
            };
            model.addRow(newData);
            originalData.add(newData); // Tambahkan data ke originalData
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
        }

        batal(); // Reset form setelah data berhasil disimpan/diubah
    }
}


   private void hapusAlamat() {
        // Mendapatkan model dari tabelAlamat (yang berisi data tabel)
        DefaultTableModel model = (DefaultTableModel) tabelAlamat.getModel();

        // Mendapatkan indeks baris yang dipilih pada tabel
        int selectedRow = tabelAlamat.getSelectedRow();

        // Mengecek apakah ada baris yang dipilih
        if (selectedRow != -1) {
            // Jika ada baris yang dipilih, hapus baris tersebut berdasarkan indeksnya
            model.removeRow(selectedRow);

            // Menampilkan pesan bahwa data berhasil dihapus
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        } else {
            // Jika tidak ada baris yang dipilih, tampilkan pesan peringatan
            JOptionPane.showMessageDialog(null, "Pilih data yang ingin dihapus.");
        }
}
   private void editAlamat() {
    DefaultTableModel model = (DefaultTableModel) tabelAlamat.getModel(); // Mendapatkan model data dari tabel
    int selectedRow = tabelAlamat.getSelectedRow(); // Mendapatkan baris yang dipilih oleh pengguna di tabel

    if (selectedRow != -1) { // Mengecek apakah ada baris yang dipilih
        // Mengisi data dari tabel ke field input
        txtNama.setText(model.getValueAt(selectedRow, 0).toString()); // Mengambil nama dari kolom 0 dan menampilkannya di txtNama
        
        // Mengambil jenis kelamin dari kolom 1
        String gender = model.getValueAt(selectedRow, 1).toString(); 
        if (gender.equals("Laki-laki")) {
            rbLaki.setSelected(true); // Jika jenis kelamin "Laki-laki", pilih radio button rbLaki
        } else {
            rbPerempuan.setSelected(true); // Jika jenis kelamin bukan "Laki-laki", pilih radio button rbPerempuan
        }

        // Mengambil tanggal lahir dari kolom 2 dan mengaturnya ke JDateChooser
        try {
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(model.getValueAt(selectedRow, 2).toString());
            tanggalLahir.setDate(date); // Mengatur tanggal lahir pada komponen JDateChooser
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Format tanggal tidak valid!"); // Tampilkan pesan jika terjadi kesalahan parsing
        }

        // Mengambil agama dari kolom 3 dan menampilkan pilihan yang sesuai di combo box
        cmbAgama.setSelectedItem(model.getValueAt(selectedRow, 3).toString());

        // Mengambil alamat dari kolom 4 dan menampilkannya di txtAreaAlamat
        txtAreaAlamat.setText(model.getValueAt(selectedRow, 4).toString());

        // Mengambil nomor telepon dari kolom 5 dan menampilkannya di txtTlp
        txtTlp.setText(model.getValueAt(selectedRow, 5).toString());

        // Mengambil email dari kolom 6 dan menampilkannya di txtEmail
        txtEmail.setText(model.getValueAt(selectedRow, 6).toString());

        // Mengambil catatan dari kolom 7 dan menampilkannya di txtAreaCatatan
        txtAreaCatatan.setText(model.getValueAt(selectedRow, 7).toString());

        JOptionPane.showMessageDialog(null, "Silakan ubah data dan klik Simpan."); // Pesan konfirmasi bahwa data siap diubah
    } else {
        JOptionPane.showMessageDialog(null, "Pilih data yang ingin diedit."); // Pesan jika tidak ada data yang dipilih
    }
}
 private void imporData() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Buka File Alamat");
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files", "txt"));
    int result = fileChooser.showOpenDialog(null);

    if (result == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            DefaultTableModel model = (DefaultTableModel) tabelAlamat.getModel();
            String line;

            // Tidak mengosongkan `originalData` agar data sebelumnya tetap ada
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // Tambahkan data baru ke tabel
                model.addRow(data);

                // Tambahkan data baru ke `originalData`
                originalData.add(data);
            }
            JOptionPane.showMessageDialog(null, "Data Alamat Berhasil Diimpor dari File TXT!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan: " + ex.getMessage());
        }
    }
}



   private void eksporData() {
    // Membuka dialog untuk memilih lokasi file teks
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Simpan File Alamat"); // Set judul dialog
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files", "txt")); // Filter hanya file .txt
    int result = fileChooser.showSaveDialog(null); // Menampilkan dialog simpan file
    if (result == JFileChooser.APPROVE_OPTION) { // Jika pengguna memilih file
        File file = fileChooser.getSelectedFile(); // Dapatkan file yang dipilih
        // Tambahkan ekstensi .txt jika belum ada
        if (!file.getName().endsWith(".txt")) {
            file = new File(file.getAbsolutePath() + ".txt");
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) { // Membuka file untuk ditulis
            DefaultTableModel model = (DefaultTableModel) tabelAlamat.getModel(); // Mendapatkan model tabel
            for (int i = 0; i < model.getRowCount(); i++) { // Looping setiap baris pada tabel
                StringBuilder sb = new StringBuilder(); // StringBuilder untuk membangun baris teks
                for (int j = 0; j < model.getColumnCount(); j++) { // Looping setiap kolom pada baris
                    sb.append(model.getValueAt(i, j).toString()); // Tambahkan nilai kolom
                    if (j < model.getColumnCount() - 1) sb.append(","); // Tambahkan koma sebagai pemisah kecuali kolom terakhir
                }
                bw.write(sb.toString()); // Tulis baris ke file
                bw.newLine(); // Tambahkan baris baru
            }
            JOptionPane.showMessageDialog(null, "Data Alamat Berhasil Diekspor ke File TXT!"); // Tampilkan pesan sukses
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan: " + ex.getMessage()); // Tampilkan pesan error
        }
    }
}

  private void cariData() {
    String searchText = txtCariData.getText().trim().toLowerCase(); // Ambil teks pencarian dan ubah menjadi lowercase
    DefaultTableModel model = (DefaultTableModel) tabelAlamat.getModel();
    
    // Kosongkan tabel sebelum menambahkan hasil pencarian
    model.setRowCount(0);

    boolean found = false; // Flag untuk memeriksa apakah data ditemukan
    
    // Jika ada teks pencarian
    if (!searchText.isEmpty()) {
        // Loop untuk memeriksa setiap baris dalam `originalData`
        for (Object[] row : originalData) {
            String nama = row[0].toString().toLowerCase(); // Ambil nama (kolom pertama)
            // Jika nama mengandung teks pencarian
            if (nama.contains(searchText)) {
                model.addRow(row); // Menambahkan data yang ditemukan ke tabel
                found = true; // Tandai bahwa data ditemukan
            }
        }

        // Jika data tidak ditemukan, tampilkan pesan
        if (!found) {
            JOptionPane.showMessageDialog(null, "Nama tidak ditemukan");
        }
    } else {
        // Jika teks pencarian kosong, tampilkan semua data dari `originalData`
        for (Object[] row : originalData) {
            model.addRow(row);
        }
    }
}
private void tampilkanOriginalData() {
    // Menampilkan pesan untuk menunjukkan bahwa data originalData sedang ditampilkan
    System.out.println("Isi originalData:");
    
    // Melakukan iterasi pada setiap elemen dalam originalData (yang berupa array of Object)
    for (Object[] row : originalData) {
        // Menampilkan isi setiap baris (row) yang ada dalam originalData
        System.out.println(Arrays.toString(row));
    }
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        rbLaki = new javax.swing.JRadioButton();
        rbPerempuan = new javax.swing.JRadioButton();
        colon1 = new javax.swing.JLabel();
        txtTlp = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        cmbAgama = new javax.swing.JComboBox<>();
        tanggalLahir = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaAlamat = new javax.swing.JTextArea();
        txtNama = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaCatatan = new javax.swing.JTextArea();
        colon2 = new javax.swing.JLabel();
        colon3 = new javax.swing.JLabel();
        colon4 = new javax.swing.JLabel();
        colon5 = new javax.swing.JLabel();
        colon6 = new javax.swing.JLabel();
        colon7 = new javax.swing.JLabel();
        colon8 = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelAlamat = new javax.swing.JTable();
        btnHapus = new javax.swing.JButton();
        btnEkspor = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnImpor = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtCariData = new javax.swing.JTextField();
        btnCariData = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        jPanel2.setBackground(new java.awt.Color(51, 102, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("APLIKASI BUKU ALAMAT");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(453, 453, 453))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(51, 102, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nama Lengkap");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Jenis Kelamin");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Agama");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Tanggal Lahir");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Alamat");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Telepon");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Catatan");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Alamt Email");

        buttonGroup2.add(rbLaki);
        rbLaki.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rbLaki.setForeground(new java.awt.Color(255, 255, 255));
        rbLaki.setText("Laki-laki");

        buttonGroup2.add(rbPerempuan);
        rbPerempuan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rbPerempuan.setForeground(new java.awt.Color(255, 255, 255));
        rbPerempuan.setText("Perempuan");

        colon1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        colon1.setForeground(new java.awt.Color(255, 255, 255));
        colon1.setText(":");

        txtTlp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTlp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTlpKeyTyped(evt);
            }
        });

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        cmbAgama.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cmbAgama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih --", "Islam", "Kristen Protestan", "Kristen Katolik", "Hindu", "Buddha", "Konghucu" }));
        cmbAgama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAgamaActionPerformed(evt);
            }
        });

        txtAreaAlamat.setColumns(20);
        txtAreaAlamat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtAreaAlamat.setRows(5);
        jScrollPane2.setViewportView(txtAreaAlamat);

        txtNama.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtAreaCatatan.setColumns(20);
        txtAreaCatatan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtAreaCatatan.setRows(5);
        jScrollPane3.setViewportView(txtAreaCatatan);

        colon2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        colon2.setForeground(new java.awt.Color(255, 255, 255));
        colon2.setText(":");

        colon3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        colon3.setForeground(new java.awt.Color(255, 255, 255));
        colon3.setText(":");

        colon4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        colon4.setForeground(new java.awt.Color(255, 255, 255));
        colon4.setText(":");

        colon5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        colon5.setForeground(new java.awt.Color(255, 255, 255));
        colon5.setText(":");

        colon6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        colon6.setForeground(new java.awt.Color(255, 255, 255));
        colon6.setText(":");

        colon7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        colon7.setForeground(new java.awt.Color(255, 255, 255));
        colon7.setText(":");

        colon8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        colon8.setForeground(new java.awt.Color(255, 255, 255));
        colon8.setText(":");

        btnSimpan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSimpan.setText("Simpan Data");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 516, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colon5, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colon6, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colon4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(8, 8, 8)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane2)
                                .addComponent(txtTlp)
                                .addComponent(cmbAgama, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colon8, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colon7, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(8, 8, 8)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtEmail)
                                .addComponent(jScrollPane3)))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(colon2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colon3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(colon1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtNama))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(tanggalLahir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(rbLaki, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(rbPerempuan, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(109, 109, 109)))))))
                    .addContainerGap()))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(colon1))
                        .addComponent(txtNama, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(colon2))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbLaki)
                            .addComponent(rbPerempuan)))
                    .addGap(11, 11, 11)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(colon3))
                        .addComponent(tanggalLahir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(10, 10, 10)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(colon4))
                        .addComponent(cmbAgama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(colon5))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTlp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(colon6))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(colon7))
                    .addGap(10, 10, 10)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(colon8))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnSimpan)
                        .addComponent(btnBatal))
                    .addContainerGap()))
        );

        jPanel3.setBackground(new java.awt.Color(51, 102, 255));

        tabelAlamat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama ", "Jenis Kelamin", "Tanggal Lahir", "Agama", "Alamat", "Telepon", "Email", "Catatan"
            }
        ));
        jScrollPane1.setViewportView(tabelAlamat);

        btnHapus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHapus.setText("Hapus Data");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnEkspor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnEkspor.setText("Ekspor Data");
        btnEkspor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEksporActionPerformed(evt);
            }
        });

        btnUbah.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnUbah.setText("Ubah Data");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnImpor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnImpor.setText("Impor Data");
        btnImpor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImporActionPerformed(evt);
            }
        });

        btnKeluar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Cari Nama :");

        txtCariData.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnCariData.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCariData.setText("Cari");
        btnCariData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnEkspor, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnImpor, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCariData, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(btnCariData, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 66, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(7, 7, 7)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(259, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtCariData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariData))
                .addGap(70, 70, 70)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapus)
                    .addComponent(btnUbah)
                    .addComponent(btnKeluar))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEkspor)
                    .addComponent(btnImpor))
                .addGap(70, 70, 70))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(259, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void cmbAgamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAgamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbAgamaActionPerformed

    private void btnImporActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImporActionPerformed
        imporData(); //Memanggil Method
        tampilkanOriginalData(); //Memanggil Method
        // TODO add your handling code here:
    }//GEN-LAST:event_btnImporActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        simpanAlamat();//Memanggil Method
        tampilkanOriginalData();//Memanggil Method
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        batal();     //Memanggil Method  
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapusAlamat();  //Memanggil Method
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        editAlamat();   //Memanggil Method
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUbahActionPerformed

    private void txtTlpKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTlpKeyTyped
            char c = evt.getKeyChar();
                if (!Character.isDigit(c) && c != '.') {
                    evt.consume(); // Hanya izinkan angka dan titik desimal
                }        // TODO add your handling code here:
    }//GEN-LAST:event_txtTlpKeyTyped

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
             System.exit(0); // Menutup aplikasi        // TODO add your handling code here:
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void btnEksporActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEksporActionPerformed
        eksporData();   //Memanggil Method
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEksporActionPerformed

    private void btnCariDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariDataActionPerformed
            cariData(); //Memanggil Method
            // TODO add your handling code here:
    }//GEN-LAST:event_btnCariDataActionPerformed

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
            java.util.logging.Logger.getLogger(bukuAlamat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(bukuAlamat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(bukuAlamat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(bukuAlamat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new bukuAlamat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCariData;
    private javax.swing.JButton btnEkspor;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnImpor;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUbah;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cmbAgama;
    private javax.swing.JLabel colon1;
    private javax.swing.JLabel colon2;
    private javax.swing.JLabel colon3;
    private javax.swing.JLabel colon4;
    private javax.swing.JLabel colon5;
    private javax.swing.JLabel colon6;
    private javax.swing.JLabel colon7;
    private javax.swing.JLabel colon8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JRadioButton rbLaki;
    private javax.swing.JRadioButton rbPerempuan;
    private javax.swing.JTable tabelAlamat;
    private com.toedter.calendar.JDateChooser tanggalLahir;
    private javax.swing.JTextArea txtAreaAlamat;
    private javax.swing.JTextArea txtAreaCatatan;
    private javax.swing.JTextField txtCariData;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtTlp;
    // End of variables declaration//GEN-END:variables
}
