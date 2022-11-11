/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package org.darisadesigns;

import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author draqu
 */
public class CardTest {

    private final int suit = 1;
    private final int cardNum = 4;
    private final boolean courtCard = true;
    private final String name = "The Well Hung Man";
    private final String longDescription = "LongDescription";
    private final String shortDescription = "ShortDescription";
    private final String readingText = "BaseReading";
    private final String invertedReadingText = "InvertedReading";
    private final String keywords = "  soopa, doopa    ,d'luxx,or gans";
    private final String significatorText = "this card is not significant";
    private final String[] keywordsExpected = {"soopa", "doopa", "d'luxx", "or gans"};

    private final Card card;
    private final Card cardNoShortDescription;
    private final Card cardNoInvertedReading;
    private final Card cardNoKeywords;

    public CardTest() {
        card = new Card(
                suit,
                cardNum,
                courtCard,
                name,
                longDescription,
                shortDescription,
                readingText,
                invertedReadingText,
                significatorText);

        card.SetKeywords(keywords);

        cardNoShortDescription = new Card(
                suit,
                cardNum,
                courtCard,
                name,
                longDescription,
                "",
                readingText,
                invertedReadingText,
                significatorText);

        card.SetKeywords(keywords);

        cardNoInvertedReading = new Card(
                suit,
                cardNum,
                courtCard,
                name,
                longDescription,
                shortDescription,
                readingText,
                "",
                significatorText);

        card.SetKeywords(keywords);

        cardNoKeywords = new Card(
                suit,
                cardNum,
                courtCard,
                name,
                longDescription,
                shortDescription,
                readingText,
                invertedReadingText,
                significatorText);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of SetKeywords method, of class Card.
     */
    @Test
    public void TestCardBasic() {
        assertEquals(suit, card.getSuit());
        assertEquals(cardNum, card.getNumber());
        assertEquals(courtCard, card.isCourt());
        assertEquals(name, card.getName());
        assertEquals(longDescription, card.getLongDescription());
        assertEquals(shortDescription, card.getShortDescription());
        assertEquals(readingText, card.getReadingText());
        assertEquals(invertedReadingText, card.getInvertedReadingText());
        assert(Arrays.equals(keywordsExpected, card.getKeywords()));
    }

    @Test
    public void testCardNoShortDescription()
    {
        assertEquals(longDescription, cardNoShortDescription.getShortDescription());
    }

    @Test
    public void TestCardNoInvertedReading()
    {
        assertEquals(readingText, cardNoInvertedReading.getInvertedReadingText());
    }

    @Test
    public void testCardNoKeywords()
    {
        assertEquals(0, cardNoKeywords.getKeywords().length);
    }
    
    @Test
    public void testCardLongTextTags() {
        var initial = "blah[showChance=0]REMOVE[/showChance]";
        var expected = "blah";
        
        var cardTags = new Card(
                suit,
                cardNum,
                courtCard,
                name,
                initial,
                shortDescription,
                readingText,
                invertedReadingText,
                significatorText);
        
        assertEquals(expected, cardTags.getLongDescription());
    }
    
    @Test
    public void testCardShortTextTags() {
        var initial = "blah[showChance=0]REMOVE[/showChance]";
        var expected = "blah";
        
        var cardTags = new Card(
                suit,
                cardNum,
                courtCard,
                name,
                longDescription,
                initial,
                readingText,
                invertedReadingText,
                significatorText);
        
        assertEquals(expected, cardTags.getShortDescription());
    }
    
    @Test
    public void testCardReadingTextTags() {
        var initial = "blah[showChance=0]REMOVE[/showChance]";
        var expected = "blah";
        
        var cardTags = new Card(
                suit,
                cardNum,
                courtCard,
                name,
                longDescription,
                shortDescription,
                initial,
                invertedReadingText,
                significatorText);
        
        assertEquals(expected, cardTags.getReadingText());
    }
    
    @Test
    public void testCardInvertedTextTags() {
        var initial = "blah[showChance=0]REMOVE[/showChance]";
        var expected = "blah";
        
        var cardTags = new Card(
                suit,
                cardNum,
                courtCard,
                name,
                longDescription,
                shortDescription,
                readingText,
                initial,
                significatorText);
        
        assertEquals(expected, cardTags.getInvertedReadingText());
    }
    
    @Test
    public void testCardSignificatorTextTags() {
        var initial = "blah[showChance=0]REMOVE[/showChance]";
        var expected = "blah";
        
        var cardTags = new Card(
                suit,
                cardNum,
                courtCard,
                name,
                longDescription,
                shortDescription,
                readingText,
                invertedReadingText,
                initial);
        
        assertEquals(expected, cardTags.getSignificatorText());
    }
}
