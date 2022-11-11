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
public class ReaderUtilsTest {
    /**
     * Test of consumeText method, of class ReaderUtils.
     */
    @Test
    public void testprocessTextTags_showChanceTag() {
        var expectedResult = "blahf'shoblahblah";
        var originText = """
                         blah[showChance=100]f'sho[/showChance]blah[showChance=0]TE
                         XT[/showChance]blah""";
        
        assertEquals(expectedResult, ReaderUtils.processTextTags(originText));
    }
    
}
