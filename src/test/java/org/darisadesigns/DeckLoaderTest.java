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
public class DeckLoaderTest {

    public DeckLoaderTest() {
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
     * Test of LoadDeck method, of class DeckLoader.
     */
    @Test
    public void TestLoadNormalDeck() {
        try {
            DeckLoader loader = new DeckLoader("src/test/resources/TestDeck.xml");
            Deck deck = loader.LoadDeck();
            
            var problems = deck.Validate();
            
            if (!problems.isEmpty()) {
                fail(problems.get(0));
            }

            assertEquals("Poopo M'goo", deck.getDeckAuthor());
            assertEquals("Tomorrow", deck.getDeckCopyright());
            assertEquals("THIS IS A TEST DECK. HOORAY.", deck.getDescription());
            assertEquals("ZIM ZAM M'BAM", deck.getFileAuthor());
            assertEquals("6/6/1966", deck.getFileCopyright());
            assertEquals("TESTO_MCBESTO", deck.getId());
            assertEquals("eng", deck.getLangId());
            assertEquals("TestDeck", deck.getName());
            assertEquals(6, deck.getDeckKeywords().size());
            assertEquals("cleans your woerm of sin wash it away", deck.getDeckKeywords().get("woerm").keywordText);
            assertEquals("big ol' sweatybutt", deck.getDeckKeywords().get("butt").keywordText);

            var cards = deck.GetCards();

            assertEquals(2, cards.size());

            Card card = cards.get(0);

            assertEquals("0_0", card.GetId());
            assertEquals(false, card.isCourt());
            assertEquals("normal inverted text", card.getInvertedReadingText());
            assertEquals("normal long description", card.getLongDescription());
            assertEquals("normal card", card.getName());
            assertEquals(0, card.getNumber());
            assertEquals("READ DIS", card.getReadingText());
            assertEquals("normal short description", card.getShortDescription());
            assertEquals(0, card.getSuit());
            assertArrayEquals(new String[]{"Zim", "Zam", "m'bam  and", "thank you ma'am"}, card.getKeywords());
            assertEquals(2, card.getRelatedSuits().length);
            assertEquals(0, card.getRelatedSuits()[0]);
            assertEquals(1, card.getRelatedSuits()[1]);
            assertEquals(2, card.getCardRelations().length);

            var cardRel0 = card.getCardRelations()[0];
            var cardRel1 = card.getCardRelations()[1];

            assertEquals(1, cardRel0.getNumber());
            assertEquals(0, cardRel0.getSuit());
            assertEquals("READ MY RELATED TEXT?!", cardRel0.getReadingText());
            assertEquals("YOUR FATE IS SEALED.", cardRel0.getInvertedReadingText());

            assertEquals(6, cardRel1.getNumber());
            assertEquals(7, cardRel1.getSuit());
            assertEquals("ZOOBLE", cardRel1.getReadingText());
            assertEquals("BOOBLE", cardRel1.getInvertedReadingText());

            assertEquals(2, card.getPositionRelations().length);

            var posRelation0 = card.getPositionRelations()[0];
            var posRelation1 = card.getPositionRelations()[1];

            assertEquals("Mystic_Butt", posRelation0.getSpreadId());
            assertEquals(4, posRelation0.getPositionId());
            assertEquals("READ MY MYSTIC BUTT TEXT", posRelation0.getReadingText());
            assertEquals("INVERTED BUTT BABYBEE", posRelation0.getInvertedReadingText());

            assertEquals("Mystic_Butt", posRelation1.getSpreadId());
            assertEquals(5, posRelation1.getPositionId());
            assertEquals("THE POSITION OF POOPING", posRelation1.getReadingText());
            assertEquals("I SEE A BIG JUICY DICK IN UR FUTURE (MUCH LUCK!)", posRelation1.getInvertedReadingText());

            Card[] court = deck.GetCourtCards();

            assertEquals(1, court.length);
            assertEquals("DO THIS CARD ONCE RELATIONS ENGINE COMPLETE", court[0].getName());

            assert (deck.GetSuit(0).isArcana());

            assert (loader.LoadProblems.isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getLocalizedMessage());
        }
    }

}
