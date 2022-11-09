/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author draqu
 */
public class TableState {
    private final List<TablePosition> positions;
    public Spread spread;
    private int deckPosition;
    private final List<Card> cardStack;
    private final Card[] significators;
    private final Map<String, Integer> keywordHits;
    private final List<String> keywordsMentioned;

    /**
     * 
     * @param _deck
     * @param _spread
     * @param _significators Significators to pre-set with selected values (applied in order)
     * @throws Exception 
     */
    public TableState(Deck _deck, Spread _spread) throws Exception
    {
        positions = new ArrayList<>();
        deckPosition = 0;
        cardStack = _deck.GetShuffledCards();
        spread = _spread;
        significators = _deck.GetCourtCards();
        cardStack.removeAll(Arrays.asList(significators));
        keywordHits = new HashMap<>();
        keywordsMentioned = new ArrayList<>();
        
        if (spread.significatorCount() != significators.length) {
            throw new Exception("Number of significators populated at deal time must match those of the spread.");
        } else if (cardStack.size() < spread.getPositions().size()) {
            throw new Exception("Spread size is larger than deck size: please choose a larger deck or smaller spread.");
        }
        
        Deal();
    }
    
    /**
     * Returns array of all cards in reading which are face up so far
     * @return 
     */
    public TablePosition[] getFaceUpCards() {
        var faceUp = new ArrayList<TablePosition>();
        
        for (var position : positions) {
            if (!position.card.isFaceUp()) {
                break;
            }
            
            faceUp.add(position);
        }
        
        return faceUp.toArray(new TablePosition[0]);
    }

    // TODO: Just for testing purposes. Ultimately delete this
    private void Deal() throws Exception
    {
        int significatorPosition = 0;
        
        for (var spreadPosition : spread.getPositions())
        {
            TablePosition tablePosition = new TablePosition();

            // Significator positions are not random, but chosen
            if (spreadPosition.isSignificator()) {
                tablePosition.card = significators[significatorPosition];
                significatorPosition++;
            } else {
                tablePosition.card = cardStack.get(deckPosition);
                deckPosition++;
            }
            
            tablePosition.spreadPositon = spreadPosition;
            positions.add(tablePosition);
        }
    }

    public int GetLength()
    {
        return positions.size();
    }

    public TablePosition GetTablePosition (int i) throws Exception
    {
        if (i >= 0 && i < positions.size())
        {
            return positions.get(i);
        }

        throw new Exception("Out of bounds position requested.");
    }
    
    /**
     * Indicates that a keyword has been encountered, meaning that it should either
     * be added to the hit count or have its count incremented
     * @param keyword 
     */
    public void keywordHit(String keyword) {
        if (keywordHits.containsKey(keyword)) {
            keywordHits.replace(keyword, keywordHits.get(keyword) + 1);
        } else {
            keywordHits.put(keyword, 1);
        }
    }
    
    /**
     * Fetches number of times keyword has been hit
     * @param keyword
     * @return 
     */
    public int getKeywordHitCount(String keyword) {
        return keywordHits.containsKey(keyword) ? keywordHits.get(keyword) : 0;
    }
    
    /**
     * Call when mentioning keyword in reading so that it is not fully explained again
     * @param keyword 
     */
    public void keywordMentioned(String keyword) {
        if (!keywordsMentioned.contains(keyword)) {
            keywordsMentioned.add(keyword);
        }
    }
    
    /**
     * Returns true if keyword has already been mentioned
     * @param keyword
     * @return 
     */
    public boolean isKeywordMentioned(String keyword) {
        return keywordsMentioned.contains(keyword);
    }
    
    public class TablePosition
    {
        public Card card;
        public SpreadPosition spreadPositon;
    }
}
