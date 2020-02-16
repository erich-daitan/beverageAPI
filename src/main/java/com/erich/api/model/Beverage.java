package com.erich.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "beverage")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Beverage {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    @JsonProperty
    private String name;

    @JsonProperty
    @Column(name = "timestamp")
    private Long timestamp;

    @JsonProperty
    @Column(name = "alcoholic")
    private boolean alcoholic;

    @JsonProperty
    @Column(name = "volumeInLiters")
    private Integer volumeInLiters;

    @JsonProperty
    @Column(name = "section")
    private Integer section;

    @JsonProperty
    @Column(name = "responsibleForStorage")
    private String responsibleForStorage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isAlcoholic() {
        return alcoholic;
    }

    public void setAlcoholic(boolean alcoholic) {
        this.alcoholic = alcoholic;
    }

    public Integer getVolumeInLiters() {
        return volumeInLiters;
    }

    public void setVolumeInLiters(Integer volumeInLiters) {
        this.volumeInLiters = volumeInLiters;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public String getResponsibleForStorage() {
        return responsibleForStorage;
    }

    public void setResponsibleForStorage(String responsableForStorage) {
        this.responsibleForStorage = responsableForStorage;
    }

}
