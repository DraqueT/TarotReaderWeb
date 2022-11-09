/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

        if (getSuit() < 0) {
            problems.add("Suit id cannot be less than 0.");
        }

        if (getNumber() < 0) {
            problems.add("Card number cannot be less than 0.");
        }

        if (getName().trim().equals("")) {
            problems.add("Cards must have name.");
        }

        if (getReadingText().trim().equals("")) {
            problems.add("Cards must have a reading text.");
        }
        
        if (isCourt() && significatorText.isBlank()) {
            problems.add("Court cards must have significator text");
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

    // TODO: Figure out whether this should be an array return or just a string
    public String[] GetCardReading(Spread spread) {
        var reading = new ArrayList<String>();

        if (isInverted() && !invertedReadingText.equals("")) {
            reading.add(getInvertedReadingText());
        } else {
            reading.add(getReadingText());
        }

        // TODO: This is where logic for building readings from relations goes
        // is it in a position with special text?
        // any prior card of the same suit? - remember to refer to court cards based on pronooun field (only for court cards)
        return reading.toArray(new String[0]);
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
        return shortDescription.isBlank() ? getLongDescription() : shortDescription;
    }

    public void setShortInvertedReadingText(String _invertedReadingText) {
        invertedReadingText = _invertedReadingText;
    }

    public String getInvertedReadingText() {
        return invertedReadingText.isBlank() ? getReadingText() : invertedReadingText;
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
        return longDescription;
    }

    /**
     * @return the readingText
     */
    public String getReadingText() {
        return readingText;
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
    
    /**
     * Returns pairs of 
     * @param tableState
     * @param position
     * @return 
     */
    public String[] getReading(TableState tableState, SpreadPosition position) {
        var constructedReading = new ArrayList<String>();
        
        constructedReading.add(ReaderUtils.ReadingBase);
        
        if (position.isSignificator()) {
            constructedReading.add(significatorText);
        } else if (inverted) {
            constructedReading.add(getInvertedReadingText());
        } else {
            constructedReading.add(readingText);
        }
        
        for (var posRelation : positionRelations) {
            if (posRelation.getSpreadId().equals(tableState.spread.getSpreadId()) && posRelation.getPositionId() == position.getOrder()) {
                constructedReading.add(ReaderUtils.ReadingPositionRelation);
                if (inverted) {
                    constructedReading.add(posRelation.getInvertedReadingText());
                } else {
                    constructedReading.add(posRelation.getReadingText());
                }
            }
        }
        
        var cardsShown = Arrays.asList(tableState.getFaceUpCards());
        
        // TODO: Add additional language in template file for various messages... including things like "Whithin the position of...." etc.
//        for (var relation : cardRelations) {
//            if (relation.getSuit() == ) { // check whether each card shown is a relation
//                constructedReading.add(ReaderUtils.ReadingCardRelation);
//                if (inverted) {
//                    constructedReading.add(relation.getInvertedReadingText());
//                } else {
//                    constructedReading.add(relation.getReadingText());
//                }
//            }
//        }

        var cardRelations = new ArrayList<CardRelationValue>();
        for (var priorCard : tableState.getFaceUpCards()) {
            cardRelations.add(new CardRelationValue(priorCard.card, getCardRelationValue(priorCard, tableState)));
        }
        
        Collections.sort(cardRelations);
        
        // Add relation readings
        // all relations with certain score are added as significant (in order of highest significance)
        // cards with explicit relation have ONLY the explicit relation text shown
        // while all direct relatios are read (maybe make max?), beyond a certain point, cards with a relation value too low are ignored no matter what
        // so long as there are any cards with relations at least one is added to readings
        // each time a key appears, its significance in later cards has significantly increased value, meaning that streaks become more significant (store keyword bonus scores in tableState passed in)
        
        // TODO: Add to encountered keywords every time they are seen on a card, NOT just every time they are read
        
        return constructedReading.toArray(new String[0]);
    }
    
    /**
     * Gets the relationship value to a given card based on:
     * - defined relation
     * - shared keywords
     * - shared suit
     * @param card
     * @return 
     */
    private int getCardRelationValue(TableState.TablePosition tablePosition, TableState tableState) {
        // TODO: Move the values below to a more logical place once they've been tweaked appropriately
        // TODO: Tweak values
        final int CARD_RELATION_VALUE = 15;
                
        int relValue = 0;
        
        for (var relation : tablePosition.card.cardRelations) {
            if (relation.relationMatches(this)) {
                relValue += CARD_RELATION_VALUE;
                break;
            }
        }
        
        // TODO: Add keyword values (remember to check prior occurances and feed this to the getRecurranceMultiplier function in DeckKeyword
        
        // TODO: Add matching suit significance
        
        return relValue;
    }
    
    private class CardRelationValue implements Comparable<CardRelationValue> {
        public final int score;
        public final Card card;
        
        public CardRelationValue(Card _card, int _score) {
            card = _card;
            score = _score;
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

4) Always mention if the card has a suit affinity to the significator

5) Add tags to reading/inverted reading/relation text which give things like % chance that text within the tags will be seen (otherwise absent)

6) Add option to relations as to whether to replace text entirely (if both a position and a prior card relation, consider which gets priority)

7) If card relation calculation results in two cards of the same suit, randomly decide whether to point this out within the relation text
 
Thoughts: Randomize some language phrases? Different word choices that would be randomly selected but have the same meaining, simply to give a less mechanical feel?
 
Relation scale:
Give each card a relation rating. Things like keyword matches, explicit relation

 */
