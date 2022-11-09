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
public class CardRelation {
    private final int suit;
    private final int number;
    private final String readingText;
    private final String invertedReadingText;
    
// TODO: THIS
//    public XmlNode GetXML(XmlDocument doc)
//    {
//        XmlNode relation = ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeCardRelation);
//        relation.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeCardNumberRelation, Number.ToString()));
//        relation.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeCardSuitRelation, Suit.ToString()));
//        relation.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeRelationReadingText, ReadingText));
//        relation.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeRelationInvertedReadingText, invertedReadingText));
//
//        return relation;
//    }

    public CardRelation(int _suit, int _number, String _readingText, String _invertedReadingText)
    {
        suit = _suit;
        number = _number;
        readingText = _readingText;
        invertedReadingText = _invertedReadingText;
    }

    public boolean IsValid()
    {
        return Validate().isEmpty();
    }
    
    /**
     * returns true if relation matches passed card
     * presumes same deck
     * @param card
     * @return 
     */
    public boolean relationMatches(Card card) {
        return suit == card.getSuit() && number == card.getNumber();
    }

    public List<String> Validate()
    {
        var problems = new ArrayList<String>();

        if (suit < 0)
        {
            problems.add("Suit id cannot be less than 0.");
        }

        if (number < 0)
        {
            problems.add("Card number cannot be less than 0.");
        }

        if (readingText.isBlank())
        {
            problems.add("Relations must have a reading text.");
        }

        return problems;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj != null && obj instanceof CardRelation cardRelation)
        {
            return suit == cardRelation.suit
                && number == cardRelation.number
                && readingText.equals(cardRelation.readingText)
                && invertedReadingText.equals(cardRelation.invertedReadingText);
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.suit;
        hash = 41 * hash + this.number;
        return hash;
    }
    
    public int getSuit() {
        return suit;
    }
    
    public int getNumber() {
        return number;
    }
    
    public String getReadingText() {
        return readingText;
    }

    public String getInvertedReadingText()
    {
        return invertedReadingText.isBlank() ? readingText : invertedReadingText; 
    }
}
