import java.sql.*;
import java.util.*;

// ---------- Book Class ----------
class Book {
    private String isbn;
    private String title;
    private String author;
    private boolean isIssued;

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isIssued() { return isIssued; }

    public void issue() { this.isIssued = true; }
    public void returnBook() { this.isIssued = false; }

    @Override
    public String toString() {
        return title + " by " + author + " (ISBN: " + isbn + ") - " +
                (isIssued ? "Issued" : "Available");
    }
}

// ---------- Member Class ----------
class Member {
    private int memberId;
    private String name;

    public Member(int memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    public int getMemberId() { return memberId; }
    public String getName() { return name; }

    @Override
    public String toString() {
        return "Member: " + name + " (ID: " + memberId + ")";
    }
}

// ---------- Custom Exceptions ----------
class BookNotFoundException extends Exception {
    public BookNotFoundException(String message) { super(message); }
}

class MemberNotFoundException extends Exception {
    public MemberNotFoundException(String message) { super(message); }
}

class BookAlreadyIssuedException extends Exception {
    public BookAlreadyIssuedException(String message) { super(message); }
}

// ---------- Library Class ----------
class Library {
    private Map<String, Book> books = new HashMap<>();
    private Map<Integer, Member> members = new HashMap<>();

    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    public void addMember(Member member) {
        members.put(member.getMemberId(), member);
    }

    public void issueBook(String isbn, int memberId)
            throws BookNotFoundException, MemberNotFoundException, BookAlreadyIssuedException {
        Book book = books.get(isbn);
        if (book == null) throw new BookNotFoundException("Book not found: " + isbn);

        Member member = members.get(memberId);
        if (member == null) throw new MemberNotFoundException("Member not found: " + memberId);

        if (book.isIssued()) throw new BookAlreadyIssuedException("Book already issued: " + isbn);

        book.issue();
        System.out.println("Book issued: " + book + " to " + member);
    }

    public void returnBook(String isbn) throws BookNotFoundException {
        Book book = books.get(isbn);
        if (book == null) throw new BookNotFoundException("Book not found: " + isbn);

        book.returnBook();
        System.out.println("Book returned: " + book);
    }

    public void showBooks() {
        books.values().forEach(System.out::println);
    }

    public void showMembers() {
        members.values().forEach(System.out::println);
    }
}

// ---------- Database Manager ----------
class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/librarydb";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void saveBook(Book book) {
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO books (isbn, title, author, isIssued) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, book.getIsbn());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setBoolean(4, book.isIssued());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveMember(Member member) {
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO members (id, name) VALUES (?, ?)")) {
            stmt.setInt(1, member.getMemberId());
            stmt.setString(2, member.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// ---------- Main Class ----------
public class LibraryManagement {
    public static void main(String[] args) {
        Library library = new Library();
        DatabaseManager db = new DatabaseManager();

        Book b1 = new Book("123", "Effective Java", "Joshua Bloch");
        Book b2 = new Book("456", "Clean Code", "Robert Martin");
        Member m1 = new Member(266, "Lokanya");

        library.addBook(b1);
        library.addBook(b2);
        library.addMember(m1);

        db.saveBook(b1);
        db.saveBook(b2);
        db.saveMember(m1);

        library.showBooks();
        library.showMembers();

        try {
            library.issueBook("123", 266);
            library.returnBook("123");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

