package src;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MineSweeper {//Değerlendirme formu 5.MineSweeper sınıfı içerisinde tasarlandı.Değişkenler isimlendirildi.
    int rowNumber = 0;
    int columnNumber = 0;
    int successCount = 0;
    int size = 0;
    int sizeWithoutMine = 0;
    boolean isGameOver = false;
    String[][] mineMap;
    String[][] userMap;
    Random setRandom = new Random();
    Scanner input = new Scanner(System.in);

    MineSweeper() {
        do {//Değerlendirme formu 7. Matris boyutu kullanıcıdan istendi.
            System.out.print("Satır sayısını giriniz: ");
            rowNumber = input.nextInt();
            System.out.print("Sütun sayısını giriniz: ");
            columnNumber = input.nextInt();
            if (rowNumber < 2 || columnNumber < 2) {
                System.out.println("Minimum 2 değeri kabul edilmektedir. Lütfen tekrar satır ve sütun değeri giriniz!");
            } else {
                this.size = rowNumber * columnNumber;// dizi boyutu hesaplanıyor
                this.sizeWithoutMine = size - (size / 4);// kazanma durumu için mayın olmayan alanın boyutu hesaplanıyor
                this.mineMap = new String[rowNumber][columnNumber];//mayın haritası olusturuluyor
                this.userMap = new String[rowNumber][columnNumber];// kullanıcının görecegi harita olusturuluyor
                for (int i = 0; i < rowNumber; i++) {
                    for (int k = 0; k < columnNumber; k++) {
                        this.userMap[i][k] = "-";// ilk değer olarak - atanıyor
                        this.mineMap[i][k] = "-";// ilk değer olarak - atanıyor
                    }
                }
            }
        } while (rowNumber < 2 && columnNumber < 2); //en az 2x2 matris kontrolü yapılıyor
    }

    public void setMine() { //Değerlendirme formu 8. Rastgele mayın olusturma metodu
        int randomRow, randomColumn, counter = 0;
        while (counter != (size / 4)) { // Matrisin çeyregi kadar mayın atandı
            randomRow = setRandom.nextInt(rowNumber);
            randomColumn = setRandom.nextInt(columnNumber);
            if (mineMap[randomRow][randomColumn] != "*") {// mayınların aynı noktaya gelmesinin kontrolu
                this.mineMap[randomRow][randomColumn] = "*";
                counter++;
            }
        }
    }

    public void start() {// Oyunu baslatma methodu
        setMine();
        print(userMap);
        while (!isGameOver) {// Oyunu kaybetmediği sürece koordina almaya devam edecek
            System.out.print("Açmak istediğiniz koordinatın satır değerini giriniz: ");//Değerlendirme formu 9. Kullanıcıdan koordinat alınıyor.
            int selectedRowNumber = input.nextInt();
            System.out.print("Açmak istediğiniz koordinatın sütun değerini giriniz: ");//Değerlendirme formu 9. Kullanıcıdan koordinat alınıyor.
            int selectedColumnNumber = input.nextInt();
            validate(selectedRowNumber, selectedColumnNumber);
            if (sizeWithoutMine == successCount) {//Değerlendirme formu 14. Mayının olmadığı bütün koordinatlar açıldıysa oyunu kazanmıştır
                System.out.println("Tebrikler.Oyunu Kazandınız!!");//Değerlendirme formu 15.
                break; // Koordinat alma döngüsü bitmesi için
            }
        }
    }

    public void print(String[][] array) { // Verilen arrayi matris olarak yazdırma methodu
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col < array[0].length; col++) {
                System.out.print(array[row][col] + " ");
            }
            System.out.println();
        }
    }

    public int countMein(int selectedRow, int selectedColumn) {
        // Seçilen nokta etrafındaki mayın sayısını bulan method
        // string tutulan arrayi direk artıramayacağımız için önce etraftaki mayın sayını bulup sonra string e çevirip atayacağız
        int count = 0;

        if ((selectedColumn < columnNumber - 1) && (mineMap[selectedRow][selectedColumn + 1] == "*")) { // Sağ Komşu Kontrol
            count++;
        }
        if ((selectedRow < rowNumber - 1) && (mineMap[selectedRow + 1][selectedColumn] == "*")) {//Alt Komşu Kontrol
            count++;
        }
        if ((selectedRow > 0) && (mineMap[selectedRow - 1][selectedColumn] == "*")) {//Üst Komşu Kontrol
            count++;
        }
        if ((selectedColumn > 0) && (mineMap[selectedRow][selectedColumn - 1] == "*")) {//Sol Komşu Kontrol
            count++;
        }
        if ((selectedRow > 0 && selectedColumn > 0) && (mineMap[selectedRow - 1][selectedColumn - 1] == "*")) {//Sol Üst Çapraz Komşu
            count++;
        }
        if ((selectedRow > 0 && selectedColumn < columnNumber - 1) && (mineMap[selectedRow - 1][selectedColumn + 1] == "*")) {//Sağ Üst Çapraz Komşu
            count++;
        }
        if ((selectedRow < rowNumber - 1 && selectedColumn > 0) && (mineMap[selectedRow + 1][selectedColumn - 1] == "*")) {//Sol Alt Çapraz Komşu
            count++;
        }
        if ((selectedRow < rowNumber - 1 && selectedColumn < columnNumber - 1) && (mineMap[selectedRow + 1][selectedColumn + 1] == "*")) {//Sağ AltKomşu Çapraz
            count++;
        }
        return count;
    }

    public void validate(int selectedRowNumber, int selectedColumnNumber) { // Girilen koordinatı kontrol eden method

        if (selectedRowNumber < rowNumber && selectedColumnNumber < columnNumber && selectedRowNumber >= 0 && selectedColumnNumber >= 0) { // Arrayin uzunlungundan büyük ya da 0 dan küçük değer girilmemesi kontrolu
            if (userMap[selectedRowNumber][selectedColumnNumber] == "-") { // Seçilen koordinat açılmamışsa
                if (mineMap[selectedRowNumber][selectedColumnNumber] != "*") { // Seçilen nokta da mayın kontrolü
                    int meinCount = countMein(selectedRowNumber, selectedColumnNumber); // Eğer nokta temiz ise etrafındakı mayınları bulunur
                    userMap[selectedRowNumber][selectedColumnNumber] = String.valueOf(meinCount); // methoddan dönen değer int tipinde olduğu için string e çeviriypruz
                    print(userMap);//Değerlendirme formu 12.Etraftaki mayınların sayısının yazılı oldugu haritayı yazdırıyoruz
                    successCount++;// Değerlendirme formu 12.Girilen nokta da mayın olmadıgında açılan nokta sayısı artırılır
                } else {
                    System.out.println("Mayına Bastınız Oyun Bitti!!");//Değerlendirme formu 15.
                    System.out.println("Mayın harıtası!!");
                    print(mineMap);// Kullanıcı girmiş olduğu noktada mayın olduğunu görsün diye mayın haritası yazdırılır.
                    isGameOver = true;// Oyunun bıttıgını kontrol ettiğimiz degişken true atanır.
                }
            } else System.out.println("Başka Koordinat Giriniz!!");
        } else {
            System.out.println("Oyun sınırları içinde bir nokta seçiniz!!");//Değerlendirme formu 10.
        }
    }
}
