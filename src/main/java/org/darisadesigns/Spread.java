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
public class Spread {

    private final String name;
    private final String description;
    private final String spreadId;
    private final String languageId;
    private final String author;
    private final String copyrightDate;
    private final float cardSizeVertical;
    private final float cardSizeHorizontal;
    private final List<SpreadPosition> positions;
    private int numSignificators = -1;

    public Spread(
            String _name,
            String _description,
            String _spreadId,
            String _languageId,
            String _author,
            String _copyrightDate,
            float _cardSizeVertical,
            float _cardSizeHorizontal) {
        name = _name;
        description = _description;
        spreadId = _spreadId;
        languageId = _languageId;
        author = _author;
        copyrightDate = _copyrightDate;
        cardSizeVertical = _cardSizeVertical;
        cardSizeHorizontal = _cardSizeHorizontal;

        positions = new ArrayList<>();
    }

    public boolean IsValid() {
        return Validate().isEmpty();
    }

    public void AddPosition(SpreadPosition position) {
        getPositions().add(position);
    }
    
    /**
     * Returns count of number of significators in spread
     * @return 
     */
    public int significatorCount() {
        if (numSignificators == -1) {
            numSignificators = 0;
            
            for (var position : positions) {
                if (position.isSignificator()) {
                    numSignificators++;
                }
            }
        }
        
        return numSignificators;
    }

    public boolean HasSignificator() {
        for (var position : getPositions()) {
            if (position.isSignificator()) {
                return true;
            }
        }

        return false;
    }

    public List<String> Validate() {
        var problems = new ArrayList<String>();

        if (getName().isBlank()) {
            problems.add("Spread must have name");
        }

        if (getSpreadId().isBlank()) {
            problems.add("Spread must have id");
        }

        // TODO: Implement this if you're going to go public
//        if (!ReaderUtils.IsValidISO3CountryCode(LanguageId))
//        {
//            problems.Add("Spread language must be ISO3 compatible, 3 character signifier");
//        }
        if (getCardSizeVertical() < 0.0 || getCardSizeHorizontal() < 0.0) {
            problems.add("Card size must be greater than 0");
        }

        for (int i = 0; i < getPositions().size(); i++) {
            if (getPositions().get(i).getOrder() != i) {
                problems.add("Spread order must be sequential. Spread: " + getName() + " is nonsequential at position " + i);
                break;
            }
        }
        
        var tagRegex = "(\\n|.)*\\[.*\\](\\n|.)*";
        if (getDescription().matches(tagRegex)) {
            problems.add("Spread text cannot contain unresolved tags");
        }

        return problems;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return ReaderUtils.processTextTags(description);
    }

    /**
     * @return the spreadId
     */
    public String getSpreadId() {
        return spreadId;
    }

    /**
     * @return the languageId
     */
    public String getLanguageId() {
        return languageId;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the copyrightDate
     */
    public String getCopyrightDate() {
        return copyrightDate;
    }

    /**
     * @return the cardSizeVertical
     */
    public float getCardSizeVertical() {
        return cardSizeVertical;
    }

    /**
     * @return the cardSizeHorizontal
     */
    public float getCardSizeHorizontal() {
        return cardSizeHorizontal;
    }

    /**
     * @return the positions
     */
    public List<SpreadPosition> getPositions() {
        return positions;
    }
}
