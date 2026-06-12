package university.management.library.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String isbn;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(name = "published_year", nullable = false)
    private int publishedYear;

    @Column(nullable = false, length = 20)
    private String status = "AVAILABLE";

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public Book() {}

    public Long getId() { return id; }
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public int getPublishedYear() { return publishedYear; }
    public String getStatus() { return status; }

    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setCategory(String category) { this.category = category; }
    public void setPublishedYear(int publishedYear) { this.publishedYear = publishedYear; }
    public void setStatus(String status) { this.status = status; }
}
