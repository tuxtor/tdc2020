package com.nabenik.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "phrase")
public class Phrase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phrase_id")
    Long phraseId;

    @Column
    String description;

    @Column
    String author;

    public Long getPhraseId() {
        return phraseId;
    }

    public void setPhraseId(Long phraseId) {
        this.phraseId = phraseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
