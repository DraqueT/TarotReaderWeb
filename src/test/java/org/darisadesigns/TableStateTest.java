/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package org.darisadesigns;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author draqu
 */
public class TableStateTest {
    TableState tableState;
    Spread spread;
    Deck deck;
    
    public TableStateTest() {
        try {
            spread = new SpreadLoader("src/test/resources/TestSpread.xml").Load();
            deck = new DeckLoader("src/test/resources/TestLomisht.xml").LoadDeck();
            tableState = new TableState(deck, spread, new Card[]{deck.GetCourtCards()[0]});
        } catch (Exception e) {
            fail(e.getLocalizedMessage());
        }
    }

    /**
     * Test of getFaceUpCards method, of class TableState.
     */
    @Test
    public void testGetFaceUpCards() {
    }

    /**
     * Test of GetLength method, of class TableState.
     */
    @Test
    public void testGetLength() {
    }

    /**
     * Test of GetTablePosition method, of class TableState.
     */
    @Test
    public void testGetTablePosition() {
    }

    @Test
    public void testKeywordHit_notHit() {
        var keyword = "TEST0";
        var expected = 0;
        assertEquals(expected, tableState.getKeywordHitCount(keyword));
    }
    
    @Test
    public void testKeywordHit_isHitOnce() {
        var keyword = "TEST0";
        var expected = 1;
        tableState.keywordHit(keyword);
        assertEquals(expected, tableState.getKeywordHitCount(keyword));
    }
    
    @Test
    public void testKeywordHit_isHitMulti() {
        var keyword = "TEST0";
        var expected = 4;
        tableState.keywordHit(keyword);
        tableState.keywordHit(keyword);
        tableState.keywordHit(keyword);
        tableState.keywordHit(keyword);
        assertEquals(expected, tableState.getKeywordHitCount(keyword));
    }
    
    @Test
    public void testKeywordHit_otherHit() {
        var keyword = "TEST0";
        var fakeout = "BEST0";
        var expected = 0;
        tableState.keywordHit(fakeout);
        assertEquals(expected, tableState.getKeywordHitCount(keyword));
    }

    @Test
    public void testIsKeywordMentioned_isNot() {
        var keyword = "TEST0";
        assertFalse(tableState.isKeywordMentioned(keyword));
    }
    
    @Test
    public void testIsKeywordMentioned_is() {
        var keyword = "TEST0";
        tableState.keywordMentioned(keyword);
        assertTrue(tableState.isKeywordMentioned(keyword));
    }
    
    @Test
    public void testIsKeywordMentioned_isMulti() {
        var keyword = "TEST0";
        tableState.keywordMentioned(keyword);
        tableState.keywordMentioned(keyword);
        assertTrue(tableState.isKeywordMentioned(keyword));
    }
    
    @Test
    public void testIsKeywordMentioned_otherIs() {
        var keyword = "TEST0";
        var fakeout = "BEST0";
        tableState.keywordMentioned(fakeout);
        assertFalse(tableState.isKeywordMentioned(keyword));
    }
    
}
