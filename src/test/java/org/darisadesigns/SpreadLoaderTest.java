/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package org.darisadesigns;

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
public class SpreadLoaderTest {
    
    public SpreadLoaderTest() {
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
     * Test of Load method, of class SpreadLoader.
     */
    @Test
    public void LoadTest()
    {
        try {
            var spreadLoader = new SpreadLoader("src/test/resources/TestSpread.xml");
            var spread = spreadLoader.Load();
            
            assertEquals(0, spreadLoader.getLoadProblems().size());

            assertEquals(12, spread.getPositions().size());
            assertEquals("Draque Thompson", spread.getAuthor());
            assertEquals(7.0, spread.getCardSizeHorizontal(), 0);
            assertEquals(12.0, spread.getCardSizeVertical(), 0);
            assertEquals("January 1st 2021", spread.getCopyrightDate());
            assertEquals("The Celtic Cross is the spread most associated with modern Tarot reaidings and cartomancy generally. There are many, many variations on it. The order of positions read, specific meanings, and even number of positions may vary between different readers with different styles.", spread.getDescription());
            assertEquals("eng", spread.getLanguageId());
            assertEquals("Celtic Cross", spread.getName());
            assertEquals("CELTIC_CROSS_DRAQUE_ENG", spread.getSpreadId());

            assert(spread.IsValid());

            var position = spread.getPositions().get(0);

            assertEquals("Significator", position.getName());
            assertEquals("This position is the significator. It represents you, the querent through this reading. Its suit and its position within the suit's court may add context to cards drawn.", position.getDescription());
            assertFalse(position.isFaceDown());
            assertEquals(35, position.getHorizontalPosition(), 0);
            assertEquals("Significator", position.getName());
            assertEquals(0, position.getOrder());
            assertEquals(0, position.getRotation(), 0);
            assertEquals(50, position.getVerticalPosition(), 0);
            assert(position.isSignificator());

            position = spread.getPositions().get(1);
            assertFalse(position.isSignificator());
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getLocalizedMessage());
        }
    }
    
}
