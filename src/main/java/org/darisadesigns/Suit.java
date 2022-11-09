/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author draqu
 */
public class Suit {

    private final int id;
    private final String name;
    private final String description;
    private final String shortDescription;
    private final boolean arcana;

    public Suit(int _id, String _name, String _desription, String _shortDescription, boolean _isArcana) {
        id = _id;
        name = _name;
        description = _desription;
        shortDescription = _shortDescription;
        arcana = _isArcana;
    }

    public boolean IsValid() {
        return !Validate().isEmpty();
    }

    public List<String> Validate() {
        List<String> problems = new ArrayList<>();

        if (getId() < 0) {
            problems.add("Suit cannot have an id below 0");
        }

        if (getName().isBlank()) {
            problems.add("Suit must have a name");
        }

        return problems;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Suit suit) {
            return getId() == suit.getId()
                    && getName().equals(suit.getName())
                    && getDescription().equals(suit.getDescription())
                    && getShortDescription().equals(suit.getShortDescription())
                    && isArcana() == suit.isArcana();
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.getId();
        return hash;
    }

    /**
     * @return the Id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the Name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the ShortDescription
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * @return the IsArcana
     */
    public boolean isArcana() {
        return arcana;
    }
}
