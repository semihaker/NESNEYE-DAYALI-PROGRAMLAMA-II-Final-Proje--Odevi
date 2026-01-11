package com.smartlibraryplus.app;

import com.smartlibraryplus.dao.BookDao;
import com.smartlibraryplus.dao.LoanDao;
import com.smartlibraryplus.dao.StudentDao;
import com.smartlibraryplus.entity.Book;
import com.smartlibraryplus.entity.Loan;
import com.smartlibraryplus.entity.Student;
import com.smartlibraryplus.util.HibernateUtil;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    private static final BookDao bookDao = new BookDao();
    private static final StudentDao studentDao = new StudentDao();
    private static final LoanDao loanDao = new LoanDao();

    public static void main(String[] args) {
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
            System.setErr(new java.io.PrintStream(System.err, true, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException e) {
            // fallback to platform default
        }
        // Debug info for encoding issues
        System.out.println("[Encoding] file.encoding=" + System.getProperty("file.encoding") + ", defaultCharset=" + java.nio.charset.Charset.defaultCharset());
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Seçiminiz: ");
            String option = scanner.nextLine().trim();

            try {
                switch (option) {
                    case "1" -> addBook(scanner);
                    case "2" -> listBooks();
                    case "3" -> addStudent(scanner);
                    case "4" -> listStudents();
                    case "5" -> borrowBook(scanner);
                    case "6" -> listLoans();
                    case "7" -> returnBook(scanner);
                    case "0" -> running = false;
                    default -> System.out.println("Geçersiz seçenek");
                }
            } catch (Exception e) {
                System.out.println("Hata: " + e.getMessage());
            }
            System.out.println();
        }

        System.out.println("Sistem kapatılıyor...");
        HibernateUtil.shutdown();
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("=== SmartLibraryPlus ===");
        System.out.println("1) Kitap Ekle");
        System.out.println("2) Kitapları Listele");
        System.out.println("3) Öğrenci Ekle");
        System.out.println("4) Öğrencileri Listele");
        System.out.println("5) Kitap Ödünç Ver");
        System.out.println("6) Ödünç Listesini Görüntüle");
        System.out.println("7) Kitap Geri Teslim Al");
        System.out.println("0) Çıkış");
    }

    private static void addBook(Scanner scanner) {
        System.out.print("Başlık: ");
        String title = scanner.nextLine().trim();
        System.out.print("Yazar: ");
        String author = scanner.nextLine().trim();
        System.out.print("Yıl: ");
        int year = Integer.parseInt(scanner.nextLine().trim());

        Book book = new Book(title, author, year);
        bookDao.save(book);
        System.out.println("Kaydedildi: " + book);
    }

    private static void listBooks() {
        List<Book> books = bookDao.getAll();
        if (books.isEmpty()) {
            System.out.println("Kayıtlı kitap bulunamadı.");
            return;
        }
        books.forEach(System.out::println);
    }

    private static void addStudent(Scanner scanner) {
        System.out.print("İsim: ");
        String name = scanner.nextLine().trim();
        System.out.print("Bölüm: ");
        String dept = scanner.nextLine().trim();

        Student student = new Student(name, dept);
        studentDao.save(student);
        System.out.println("Kaydedildi: " + student);
    }

    private static void listStudents() {
        List<Student> students = studentDao.getAll();
        if (students.isEmpty()) {
            System.out.println("Kayıtlı öğrenci bulunamadı.");
            return;
        }
        students.forEach(System.out::println);
    }

    private static void borrowBook(Scanner scanner) {
        System.out.print("Öğrenci ID: ");
        Long studentId = Long.parseLong(scanner.nextLine().trim());
        System.out.print("Kitap ID: ");
        Long bookId = Long.parseLong(scanner.nextLine().trim());

        Loan loan = loanDao.createLoan(studentId, bookId);
        System.out.println("Ödünç kaydı oluşturuldu: " + loan);
    }

    private static void returnBook(Scanner scanner) {
        System.out.print("Ödünç ID: ");
        Long loanId = Long.parseLong(scanner.nextLine().trim());

        Loan loan = loanDao.returnLoan(loanId);
        System.out.println("İade kaydı güncellendi: " + loan);
    }

    private static void listLoans() {
        List<Loan> loans = loanDao.getAll();
        if (loans.isEmpty()) {
            System.out.println("Kayıtlı ödünç bulunamadı.");
            return;
        }
        loans.forEach(System.out::println);
    }
}
