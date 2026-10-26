package com.rearis.catalog;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String nameEn;

    @Column(nullable = false, unique = true, length = 100)
    private String slug;      // URL-friendly name: "running-shoes"

    protected Category() {}   // JPA needs an empty constructor to build objects from rows

    public Category(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getNameEn() { return nameEn; }
    public String getSlug() { return slug; }
}
