/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author draqu
 */
public class Card {

    private final int suit;
    private final int number;
    private final boolean court;
    private final String name;
    private final String longDescription;
    private final String readingText;
    private String[] keywords;
    private int[] relatedSuits = new int[0];
    private boolean inverted = false;
    private boolean faceUp = false;
    private CardRelation[] cardRelations = new CardRelation[0];
    private SpreadPositionRelation[] positionRelations = new SpreadPositionRelation[0];
    private String shortDescription;
    private String invertedReadingText;
    private final String significatorText;

    public Card(
            int _suit,
            int _number,
            boolean _court,
            String _name,
            String _longDescription,
            String _shortDescription,
            String _readingText,
            String _invertedReadingText,
            String _significatorText
    ) {
        suit = _suit;
        number = _number;
        court = _court;
        name = _name;
        longDescription = _longDescription;
        shortDescription = _shortDescription;
        readingText = _readingText;
        invertedReadingText = _invertedReadingText;
        keywords = new String[]{};
        significatorText = _significatorText;
    }

    /**
     * Takes comma delimited list of keywords and parses to set keyword array
     *
     * @param keywordsRaw
     */
    public void SetKeywords(String keywordsRaw) {
        setKeywords(GetCleanArrayFromCommaDelimit(keywordsRaw));
    }

    private String[] GetCleanArrayFromCommaDelimit(String input) {
        var cleanedVals = new ArrayList<String>();

        for (var value : input.split(",")) {
            String cleanedVal = value.trim();

            if (!cleanedVal.isBlank()) {
                cleanedVals.add(cleanedVal);
            }
        }

        return cleanedVals.toArray(new String[0]);
    }

    /**
     * Gets ID unique to card within deck (FOR GLOBAL, APPEND DECK ID)
     *
     * @return
     */
    public String GetId() {
        return getSuit() + "_" + getNumber();
    }

    /**
     * Returns true if card is valid
     *
     * @return
     */
    public boolean IsValid() {
        return Validate().isEmpty();
    }

    /**
     * Returns a human readable list of any problems with this card. Empty list
     * if no problems.
     *
     * @return
     */
    public List<String> Validate() {
        var problems = new ArrayList<String>();
        var problemPrefix = "Card " + this.GetId() + ": ";

        if (getSuit() < 0) {
            problems.add("Suit id cannot be less than 0.");
        }

        if (getNumber() < 0) {
            problems.add(problemPrefix + "Card number cannot be less than 0.");
        }

        if (getName().trim().equals("")) {
            problems.add(problemPrefix + "Cards must have name.");
        }

        if (getReadingText().trim().equals("")) {
            problems.add(problemPrefix + "Cards must have a reading text.");
        }
        
        if (isCourt() && significatorText.isBlank()) {
            problems.add(problemPrefix + "Court cards must have significator text");
        }
        
        var tagRegex = "(\\n|.)*\\[.*\\](\\n|.)*";
        if (getInvertedReadingText().matches(tagRegex) ||
                getLongDescription().matches(tagRegex) ||
                getReadingText().matches(tagRegex) ||
                getShortDescription().matches(tagRegex) ||
                getSignificatorText().matches(tagRegex)) {
            problems.add(problemPrefix + "Card text cannot contain unresolved tags");
        }

        for (var relation : getCardRelations()) {
            problems.addAll(relation.Validate());
        }

        for (var relation : getPositionRelations()) {
            problems.addAll(relation.Validate());
        }

        // TODO: Validate that appropriate asset exists for card and make appropriate error if not
        return problems;
    }

    @Override
    public boolean equals(Object obj) {
        boolean ret = true;

        if ((obj != null) && obj instanceof Card card) {
            if (ret) {
                ret = getSuit() == card.getSuit()
                        && getNumber() == card.getNumber()
                        && isCourt() == card.isCourt()
                        && getName().equals(card.getName())
                        && getLongDescription().equals(card.getLongDescription())
                        && getReadingText().equals(card.getReadingText())
                        && Arrays.equals(getKeywords(), card.getKeywords())
                        && Arrays.equals(getRelatedSuits(), card.getRelatedSuits())
                        && isInverted() == card.isInverted()
                        && isFaceUp() == card.isFaceUp()
                        && getShortDescription().equals(card.getShortDescription())
                        && getInvertedReadingText().equals(card.getInvertedReadingText())
                        && getCardRelations().length == card.getCardRelations().length
                        && getPositionRelations().length == card.getPositionRelations().length;
            }

            if (ret) {
                for (int i = 0; i < getCardRelations().length; i++) {
                    if (getCardRelations()[i].equals(card.getCardRelations()[i])) {
                        continue;
                    }
                    ret = false;
                    break;
                }
            }

            if (ret) {
                for (int i = 0; i < getPositionRelations().length; i++) {
                    if (getPositionRelations()[i].equals(card.getPositionRelations()[i])) {
                        continue;
                    }
                    ret = false;
                    break;
                }
            }
        }

        return ret;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.getSuit();
        hash = 67 * hash + this.getNumber();
        return hash;
    }
    
    /**
     * If turned down flips over or vice versa
     */
    public void flip() {
        faceUp = !isFaceUp();
    }
    
    /**
     * If right side up, inverts, or vice versa
     */
    public void rotate() {
        setInverted(!isInverted());
    }

    // TODO: THIS
//    public XmlNode GetXML(XmlDocument doc) {
//        var card = ReaderUtils.MakeXmlNode(
//                doc,
//                ReaderUtils.XMLNodeCard,
//                "",
//                ReaderUtils.XMLNodeCardAttributeCourt,
//                Court ? ReaderUtils.trueString : ReaderUtils.falseString);
//
//        // write basic card attributes
//        card.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeCardNumber, Number.ToString()));
//        card.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeCardSuitId, Suit.ToString()));
//        card.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeCardName, Name));
//        card.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeShortCardDescription, shortDescription));
//        card.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeLongCardDescription, LongDescription));
//        card.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeReadingText, ReadingText));
//        card.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeInvertedReadingText, invertedReadingText));
//
//        // write Relations Node
//        var relationsNode = ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeCardReadingRelations);
//        relationsNode.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeCardKeywords, String.Join(",", Keywords)));
//        var suitsRelatedNode = ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeRelatedSuits);
//
//        // related suits (arcana cards may have relations independant of suit and multiple in nature)
//        foreach(
//                
//        var value in RelatedSuits
//        
//        
//            )
//        {
//            suitsRelatedNode.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeRelatedSuit, value.ToString()));
//        }
//        relationsNode.AppendChild(suitsRelatedNode);
//
//        // card relations
//        var relatedCardsNode = ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeRelatedCards);
//        foreach(
//                
//        var relation in CardRelations
//        
//        
//            )
//        {
//            relatedCardsNode.AppendChild(relation.GetXML(doc));
//        }
//        relationsNode.AppendChild(relatedCardsNode);
//
//        // position relations
//        var relatedPositionsNode = ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodePositionRelations);
//        foreach(
//                
//        var position in PositionRelations
//        
//        
//            )
//        {
//            relatedPositionsNode.AppendChild(position.GetXML(doc));
//        }
//        relationsNode.AppendChild(relatedPositionsNode);
//
//        card.AppendChild(relationsNode);
//
//        return card;
//    }

    public void setShortDescription(String _shortDescription) {
        shortDescription = _shortDescription;
    }

    public String getShortDescription() {
        return ReaderUtils.processTextTags(shortDescription.isBlank() ? getLongDescription() : shortDescription);
    }

    public void setShortInvertedReadingText(String _invertedReadingText) {
        invertedReadingText = _invertedReadingText;
    }

    public String getInvertedReadingText() {
        return ReaderUtils.processTextTags(invertedReadingText.isBlank() ? getReadingText() : invertedReadingText);
    }
    
    /**
     * @return the suit
     */
    public int getSuit() {
        return suit;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @return the court
     */
    public boolean isCourt() {
        return court;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the longDescription
     */
    public String getLongDescription() {
        return ReaderUtils.processTextTags(longDescription);
    }

    /**
     * @return the readingText
     */
    public String getReadingText() {
        return ReaderUtils.processTextTags(readingText);
    }

    /**
     * @return the keywords
     */
    public String[] getKeywords() {
        return keywords;
    }

    /**
     * @param keywords the keywords to set
     */
    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    /**
     * @return the relatedSuits
     */
    public int[] getRelatedSuits() {
        return relatedSuits;
    }

    /**
     * @return the inverted
     */
    public boolean isInverted() {
        return inverted;
    }

    /**
     * @param inverted the inverted to set
     */
    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    /**
     * @return the flipped
     */
    public boolean isFaceUp() {
        return faceUp;
    }

    /**
     * @return the cardRelations
     */
    public CardRelation[] getCardRelations() {
        return cardRelations;
    }

    /**
     * @return the positionRelations
     */
    public SpreadPositionRelation[] getPositionRelations() {
        return positionRelations;
    }
    
    /**
     * @param positionRelations the positionRelations to set
     */
    public void setPositionRelations(SpreadPositionRelation[] positionRelations) {
        this.positionRelations = positionRelations;
    }
    
    /**
     * @param cardRelations the cardRelations to set
     */
    public void setCardRelations(CardRelation[] cardRelations) {
        this.cardRelations = cardRelations;
    }
    
    /**
     * @param relatedSuits the relatedSuits to set
     */
    public void setRelatedSuits(int[] relatedSuits) {
        this.relatedSuits = relatedSuits;
    }
    
    public void setFlipped(boolean _faceUp) {
        faceUp = _faceUp;
    }
    
    public String getSignificatorText() {
        return ReaderUtils.processTextTags(significatorText);
    }
    
    /**
     * Returns Segments of text to be read one at a time.
     * @param tableState
     * @param position
     * @return 
     */
    public String[] getReading(TableState tableState, SpreadPosition position) {
        var constructedReading = new ArrayList<String>();
        
        // TODO: Rethink the strategy for non-significator readings. Consider that the short text might be better to give in cases such as having a specific relation to the spread position
        // Currently it simply defaults to the full text to start with no matter what.
        if (position.isSignificator()) {
            constructedReading.add(significatorText);
        } else if (inverted) {
            constructedReading.add(getInvertedReadingText());
        } else {
            constructedReading.add(readingText);
        }
        
        // TODO: The first time a suit is encountered, its description should be read.
        
        for (var posRelation : positionRelations) {
            if (posRelation.getSpreadId().equals(tableState.spread.getSpreadId()) && posRelation.getPositionId() == position.getOrder()) {
                if (inverted) {
                    constructedReading.add(posRelation.getInvertedReadingText());
                } else {
                    constructedReading.add(posRelation.getReadingText());
                }
            }
        }

        var priorRelatedCards = new ArrayList<CardRelationValue>();
        for (var priorCard : tableState.getFaceUpCards()) {
            priorRelatedCards.add(getCardRelationValue(priorCard, tableState));
        }
        
        Collections.sort(priorRelatedCards);
        
        // add relation readings so long as there are cards left and 
        // a) the card relations are over the max threshhold, or 
        // b) the are over the min threshhold and we haven't gone over the small relation limit yet
        int i = 0;
        for (;i < priorRelatedCards.size() && 
                (priorRelatedCards.get(i).score > ReaderUtils.relationValueUpperThreshhold ||
                (priorRelatedCards.get(i).score > ReaderUtils.relationValueLowerThreshhold && i < ReaderUtils.maxLowValueRelationRead));
                i++) {
            constructedReading.addAll(priorRelatedCards.get(i).text);
        }
        
        if (i == 0) {
            if (priorRelatedCards.size() > 0) {
                // if no prior relations at all, at least add something nominal if it exists
                constructedReading.addAll(priorRelatedCards.get(i).text);
            } else if (!position.isSignificator() && position.getOrder() > 1) {
                // If this card is neither the significator nor one of the first two cards, display this message
                // TODO: Put this into the deck.xml
                constructedReading.add("There don't seem to be any related cards showing to this one yet.");
            }
        }
        
        return constructedReading.toArray(new String[0]);
    }
    
    /**
     * Gets the relationship value to a given card based on:
     * - defined relation
     * - shared keywords
     * - shared suit
     * - if cards are sequential
     * @param card
     * @return 
     */
    private CardRelationValue getCardRelationValue(TableState.TablePosition tablePosition, TableState tableState) {
        int relValue = 0;
        boolean directRelation = false;
        List<String> text = new ArrayList<>();
        
        for (var relation : tablePosition.card.cardRelations) {
            if (relation.relationMatches(this)) {
                relValue += ReaderUtils.cardRelationValue;
                directRelation = true;
                text.add(tablePosition.card.inverted ? relation.getInvertedReadingText() : relation.getReadingText());
                break;
            }
        }
        
        var compKeywords = Arrays.asList(tablePosition.card.keywords);
        
        var matchedKeywords = new ArrayList<WeightedKeyword>();
        
        for (var keyword : keywords) {
            tableState.keywordHit(keyword);
            
            if (compKeywords.contains(keyword)) {
                var weight = tableState.getKeywordHitCount(keyword);
                
                // add keyword value TIMES multiplier based on num times keyword encountered
                relValue += (ReaderUtils.keywordRelationValue * DeckKeyword.getRecurranceMultiplier(weight));
                matchedKeywords.add(new WeightedKeyword(tableState.getDeck().getDeckKeywords().get(keyword), weight));
            }
        }
        
        if (!directRelation && matchedKeywords.size() > 0) {
            Collections.sort(matchedKeywords);
            var rand = new Random();
            
            var highWeight = matchedKeywords.get(0).weight;
            var keywordsAdded = 0;
            
            for (var i = 0; i < matchedKeywords.size(); i++) {
                var weightedKeyword = matchedKeywords.get(i);
                
                // for keywords with lesser weighted values, the chances that they will be explained is reduced (the chance is 1/diff of weight to max weight)
                // number of keywords added cannot exceed max set
                if (rand.nextInt(highWeight - weightedKeyword.weight) == 1
                        || keywordsAdded >= ReaderUtils.maxKeywordsPerRelatedCard) {
                    break;
                }
                
                keywordsAdded++;
                
                // TODO: This text should be moved to the deck.xml file
                // TODO: This phrase should have multiple possilbe ways to be said.
                text.add(tablePosition.card.theCard(true) + " and " + theCard(false) + " both depict " + weightedKeyword.keyword.name + ", relating them to one another");
                
                // TODO: Depending on how many times the keyword has been seen, this should be commented on, something like "We're seeing the X symbol again... it seems significant."
                
                if (!tableState.isKeywordMentioned(weightedKeyword.keyword.name)) {
                    text.add(weightedKeyword.keyword.keywordText);
                }
            }
        }
        
        // arcana can have suit relations other than their own
        if (tableState.getDeck().GetSuit(tablePosition.card.suit).isArcana()) {
            var compSuits = new ArrayList<Integer>();
            
            for (var relSuit : tablePosition.card.getRelatedSuits()) {
                compSuits.add(relSuit);
            }
            
            // with two arcana, check related suits of both
            if (tableState.getDeck().GetSuit(getSuit()).isArcana()) {
                for (var relSuit : getRelatedSuits()) {
                    if (compSuits.contains(relSuit)) {
                        relValue += ReaderUtils.arcanaSuitRelationValue;
                    }
                }
            } else if (compSuits.contains(getSuit())) {
                relValue += ReaderUtils.arcanaSuitRelationValue;
                
                if (!directRelation) {
                    // non arcana cards will have additional relational text added when matching suit
                    // TODO: This should not be hardcoded. Move this to the deck.xml and update the template deck to reflect this
                    text.add("The " + tablePosition.card.getName() + " care of the " + tableState.getDeck().GetSuit(tablePosition.card.getSuit()).getName() + 
                            " is tied to the suit of " + tableState.getDeck().GetSuit(getSuit()).getName() + " matching this card.");
                }
            }
        } else if (getSuit() == tablePosition.card.getSuit()) {
            relValue += ReaderUtils.suitRelationValue;
        }
        
        if (isSequential(tablePosition.card)) {
            relValue += ReaderUtils.sequentialCardRelationValue;
            if (!directRelation) {
                // TODO: move to deck.xml
                text.add(tablePosition.card.theCard(true) + " and " + theCard(false) + 
                        " appear sequentially, implying that events surrounding them have causation in some regard.");
            }
        }

        return new CardRelationValue(tablePosition.card, relValue, text);
    }
    
    /**
     * Returns a card the the word "the" prepended only if the card's name itself does not
     * @param capitalize
     * @return 
     */
    public String theCard(boolean capitalize) {
        var ret = this.getName();
        
        if (!this.getName().toLowerCase().startsWith("the")) {
            ret = (capitalize ? "The " : "the ") + ret;
        }
        
        return ret;
    }
    
    /**
     * Returns true if the passed card is sequential
     * (same suit, and one number off, but NOT across court cards)
     * @param card
     * @return 
     */
    
    private boolean isSequential(Card card) {
        return getSuit() == card.getSuit() &&
                Math.abs(getNumber() - card.getNumber()) == 1 &&
                !isCourt() && !card.isCourt();
    }
    
    private class WeightedKeyword implements Comparable<WeightedKeyword> {
        public final DeckKeyword keyword;
        public final int weight;
        
        public WeightedKeyword(DeckKeyword _keyword, int _weight) {
            keyword = _keyword;
            weight = _weight;
        }

        @Override
        public int compareTo(WeightedKeyword o) {
            return weight == o.weight ? 0 : (weight < o.weight ? -1 : 1);
        }
    }
    
    private class CardRelationValue implements Comparable<CardRelationValue> {
        public final int score;
        public final Card card;
        public final List<String> text;
        
        public CardRelationValue(Card _card, int _score, List<String> _text) {
            card = _card;
            score = _score;
            text = _text;
        }
        
        @Override
        public int compareTo(CardRelationValue o) {
            return score == o.score ? 0 : (score < o.score ? -1 : 1);
        }
        
    }
}


// TODO: The below
/*

Reading text:

1) Give base reading text.

2) Give text specific to card's positional relation (if any)

2) Find most related card which was previously drawn based on suit, keyword, and direct relations (if any).
    a) If direct card relation, add text
    b) If not direct card relation, add text explaining keyword relations (only explain significance of keywords first time this happens)
    c) If not keyword relation, add text denoting significance
    d) If prior card was itself related to a card before it, draw significance to that.
    e) Record that these two cards are related to one another
 
2.5) If there is another strongly related card (how strongly?), repeat step 2
    - Should cards with explicit relations ALWAYS be pointed out? Maybe.

3) If a significant number of cards of this number (non arcana), suit, or with a particular keyword show up, make note of it. Past this point, make note each time the significant feature appears in the reading.
    - name each card that has had this suit/symbol on it

4) Always mention if the card has a suit affinity to the significator

5) Add tags to reading/inverted reading/relation text which give things like % chance that text within the tags will be seen (otherwise absent)

6) Add option to relations as to whether to replace text entirely (if both a position and a prior card relation, consider which gets priority)

7) If card relation calculation results in two cards of the same suit, randomly decide whether to point this out within the relation text
 
Thoughts: Randomize some language phrases? Different word choices that would be randomly selected but have the same meaining, simply to give a less mechanical feel?
 
Relation scale:
Give each card a relation rating. Things like keyword matches, explicit relation

 */
