import java.util.Scanner;
import java.io.*;
import java.util.HashMap;

class MenuUtama {
    public void tampilkanMenu() {
        System.out.println("--------------------------");
        System.out.println("Selamat Datang di BinarFud");
        System.out.println("--------------------------\n");
        System.out.println("=== Menu Utama ===");
        System.out.println("1. Nasi Goreng (Rp 15.000)");
        System.out.println("2. Mie Goreng (Rp 13.000)");
        System.out.println("3. Nasi dan Ayam (Rp 18.000)");
        System.out.println("4. Es Teh Manis (Rp 3.000)");
        System.out.println("5. Es Jeruk (Rp 5.000)");
        System.out.println("6. Pesan dan Bayar");
        System.out.println("7. Keluar dari Aplikasi");
    }
}

class KonfirmasiPesanan {
    public void tampilkanKonfirmasi(int kodeMakanan, int jumlah) {
        String[] makanan = {"Nasi Goreng", "Mie Goreng", "Nasi dan Ayam", "Es Teh Manis", "Es Jeruk"};
        int[] harga = {15000, 13000, 18000, 3000, 5000};

        System.out.println("=== Konfirmasi Pesanan ===");
        System.out.println("Makanan: " + makanan[kodeMakanan - 1]);
        System.out.println("Harga: Rp " + harga[kodeMakanan - 1]);
        System.out.println("Jumlah: " + jumlah);
        System.out.println("Total Harga: Rp " + harga[kodeMakanan - 1] * jumlah);
    }
}

class KonfirmasiPembayaran {
    public void tampilkanPembayaran(int totalHarga, HashMap<Integer, Integer> pesanan) {
        System.out.println("=== Konfirmasi dan Pembayaran ===");
        System.out.println("Detail Pesanan:");

        String[] makanan = {"Nasi Goreng", "Mie Goreng", "Nasi dan Ayam", "Es Teh Manis", "Es Jeruk"};
        int[] harga = {15000, 13000, 18000, 3000, 5000};
        int totalMakananKeseluruhan = 0;

        for (int kodeMakanan : pesanan.keySet()) {
            int jumlah = pesanan.get(kodeMakanan);
            System.out.println(makanan[kodeMakanan - 1] + " - " + jumlah + " porsi - Rp " + (harga[kodeMakanan - 1] * jumlah));
            totalMakananKeseluruhan += jumlah;
        }

        System.out.println("Total Makanan Keseluruhan: " + totalMakananKeseluruhan + " porsi");
        System.out.println("Total Harga Makanan Keseluruhan: Rp " + totalHarga);
        System.out.println("1. Konfirmasi dan Bayar");
        System.out.println("2. Keluar dari Aplikasi");
    }
}

class StrukPembayaran {
    public static void cetakStruk(int kodeMakanan, int jumlah, int totalHarga, HashMap<Integer, Integer> pesanan) {
        String[] makanan = {"Nasi Goreng", "Mie Goreng", "Nasi dan Ayam", "Es Teh Manis", "Es Jeruk"};
        try {
            FileWriter writer = new FileWriter("struk_pembayaran.txt");
            writer.write("========================\n");
            writer.write("    Struk Pembayaran    \n");
            writer.write("    BinarFud    \n");
            writer.write("========================\n");
            for (int kode : pesanan.keySet()) {
                int jumlahMakanan = pesanan.get(kode);
                writer.write("Makanan: " + makanan[kode - 1] + "\n");
                writer.write("Jumlah: " + jumlahMakanan + "\n");
                writer.write("Total Harga: Rp " + (getHargaMakanan(kode) * jumlahMakanan) + "\n");
                writer.write("\n");
            }
            writer.write("Total Makanan Keseluruhan: " + getTotalMakanan(pesanan) + " porsi\n");
            writer.write("Total Harga Makanan Keseluruhan: Rp " + totalHarga + "\n");
            writer.write("Pembayaran : BinarCash\n\n");
            writer.write("======================================\n");
            writer.write("Terima kasih Sudah Memesan di BinarFud\n");
            writer.write("======================================\n\n");
            writer.write("*simpan struk ini sebagai bukti pembayaran*\n");
            writer.close();
            System.out.println("Struk pembayaran telah disimpan sebagai struk_pembayaran.txt");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan dalam menyimpan struk pembayaran.");
            e.printStackTrace();
        }
    }

    public static int getHargaMakanan(int kodeMakanan) {
        int[] harga = {15000, 13000, 18000, 3000, 5000};
        return harga[kodeMakanan - 1];
    }

    private static int getTotalMakanan(HashMap<Integer, Integer> pesanan) {
        int total = 0;
        for (int jumlah : pesanan.values()) {
            total += jumlah;
        }
        return total;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        MenuUtama menu = new MenuUtama();
        KonfirmasiPesanan konfirmasi = new KonfirmasiPesanan();
        KonfirmasiPembayaran pembayaran = new KonfirmasiPembayaran();
        StrukPembayaran struk = new StrukPembayaran();

        HashMap<Integer, Integer> pesanan = new HashMap<>();
        int totalHarga = 0;

        int kodeMakanan = 0, jumlah = 0;
        int kodeMakananTerakhir = 0, jumlahTerakhir = 0; // Menyimpan pilihan terakhir sebelum keluar dari loop pesanan
        boolean selesaiPesan = false;
        boolean selesaiBayar = false;

        while (!selesaiPesan) {
            menu.tampilkanMenu();
            System.out.print("Pilih Opsi (1-7): ");
            kodeMakanan = input.nextInt();
            if (kodeMakanan < 1 || kodeMakanan > 7) {
                System.out.println("Opsi yang Anda pilih tidak valid. Silakan pilih opsi yang ada.");
                continue;
            }
            if (kodeMakanan == 6) {
                selesaiPesan = true;
                continue;
            } else if (kodeMakanan == 7) {
                System.out.println("Terima kasih telah menggunakan aplikasi BinarFud.");
                return;
            }
            System.out.print("Masukkan jumlah pesanan: ");
            jumlah = input.nextInt();
            pesanan.put(kodeMakanan, jumlah);
            konfirmasi.tampilkanKonfirmasi(kodeMakanan, jumlah);
            totalHarga += jumlah * StrukPembayaran.getHargaMakanan(kodeMakanan);

            // Simpan pilihan terakhir
            kodeMakananTerakhir = kodeMakanan;
            jumlahTerakhir = jumlah;
        }

        while (!selesaiBayar) {
            pembayaran.tampilkanPembayaran(totalHarga, pesanan);
            System.out.print("Pilihan Anda: ");
            int pilihan = input.nextInt();
            if (pilihan == 1) {
                struk.cetakStruk(kodeMakananTerakhir, jumlahTerakhir, totalHarga, pesanan); // Menggunakan pilihan makanan terakhir
                selesaiBayar = true;
            } else if (pilihan == 2) {
                System.out.println("Terima kasih telah menggunakan aplikasi BinarFud :).");
                return;
            }
        }
    }
}