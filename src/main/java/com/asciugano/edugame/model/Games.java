package com.asciugano.edugame.model;

import jakarta.persistence.*;

@Entity
public class Games {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    @Column(unique = true)
    private String image;
    @Column(unique = true)
    private String container;
    @Column(nullable = false)
    private String creator;

    public String getCreator() { return creator; }

    public void setCreator(String creator) { this.creator = creator; }

    public String getContainer() { return container; }

    public void setContainer(String container) { this.container = container; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    @Column(unique = true, nullable = false)
    private String url;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    @Override
    public String toString() {
        return "Games{id=" + id + ", nome='" + name + "'}";
    }
}
