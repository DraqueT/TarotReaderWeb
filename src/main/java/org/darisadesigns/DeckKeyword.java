/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author draqu
 */
public class DeckKeyword {
    public final String name;
    public final String keywordText;

    public DeckKeyword(String keyword, String keywordText)
    {
        this.name = keyword;
        this.keywordText = keywordText;
    }

    public boolean IsValid()
    {
        return !name.isBlank() && keywordText.isBlank();
    }

    public List<String> Validate()
    {
        var problems = new ArrayList<String>();

        if (name.isBlank())
        {
            problems.add("Blank deck level keyword");
        }

        if (keywordText.isBlank())
        {
            problems.add("Blank deck level keyword text");
        }

        return problems;
    }
    
    /**
     * Calculates the relative value of a keyword's significance based on the number of times it has appeared.
     * 
     * @param occurances
     * @return 
     */
    public static double getRecurranceMultiplier(int occurances) {
        return Math.pow(ReaderUtils.keywordReccuranceGrowthValue, (double)occurances); 
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj != null && obj instanceof DeckKeyword deckKeyword) {
            return name.equals(deckKeyword.name) && keywordText.equals(deckKeyword.keywordText);
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.name);
        return hash;
    }
}
