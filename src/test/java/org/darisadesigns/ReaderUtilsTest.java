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
public class ReaderUtilsTest {
    /**
     * Test of consumeText method, of class ReaderUtils.
     */
    @Test
    public void test_processTextTaxShowChance() {
        var expectedResult = "blahf'shoblahblah";
        var originText = """
                         blah[showChance=100]f'sho[/showChance]blah[showChance=0]TE
                         XT[/showChance]blah""";
        
        assertEquals(expectedResult, ReaderUtils.processTextTaxShowChance(originText));
    }
        
    @Test
    public void test_processTextWeightedMultiPossibility_first() {
       String expected = "I am the first";
       String test = "[multiposs]100=I am the first|0=I am the second|0=I am the third[/multiposs]";
       
       assertEquals(expected, ReaderUtils.processTextWeightedMultiPossibility(test));
    }
    
    @Test
    public void test_processTextWeightedMultiPossibility_second() {
       String expected = "I am the second";
       String test = "[multiposs]0=I am the first|100=I am the second|0=I am the third[/multiposs]";
       
       assertEquals(expected, ReaderUtils.processTextWeightedMultiPossibility(test));
    }
    
    @Test
    public void test_processTextWeightedMultiPossibility_third() {
       String expected = "I am the third";
       String test = "[multiposs]0=I am the first|0=I am the second|100=I am the third[/multiposs]";
       
       assertEquals(expected, ReaderUtils.processTextWeightedMultiPossibility(test));
    }
}
