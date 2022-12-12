/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.RandomAccess;
import java.util.regex.Pattern;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author draqu
 */
public class ReaderUtils {

    // TODO: THIS
//    public static boolean IsValidISO3CountryCode(String code)
//    {
//        return getISO3Culture(code) != null;
//    }
    // TODO: THIS
//    /**
//     * Gets culture info based on ISO3 code
//     * @param code
//     * @return 
//     */
//    public static CultureInfo getISO3Culture(string code)
//    {
//        CultureInfo foundCuture = null;
//
//        if (code.Length == 3)
//        {
//            CultureInfo[] cultures = CultureInfo.GetCultures(CultureTypes.SpecificCultures);
//            foreach (CultureInfo culture in cultures)
//            {
//                if (culture.ThreeLetterISOLanguageName == code)
//                {
//                    CultureInfo topLevelCulture = culture;
//
//                    while (topLevelCulture.Parent != null
//                        && topLevelCulture.Parent.Name != ""
//                        && !topLevelCulture.Parent.Equals(topLevelCulture))
//                    {
//                        topLevelCulture = topLevelCulture.Parent;
//                    }
//
//                    foundCuture = topLevelCulture;
//                }
//            }
//        }
//
//        return foundCuture;
//    }
    // TODO: DELETE?
//    /// <summary>
//    /// Returns string with all directory path characters set appropriately
//    /// </summary>
//    /// <param name="path"></param>
//    public static string CleanFilePath(string path)
//    {
//        return Regex.Replace(path, "\\\\|\\/", Char.ToString(Path.DirectorySeparatorChar));
//    }
//
//    public static string GetDirectoryWithoutFile(string path)
//    {
//        return Regex.Replace(path, "(\\\\|\\/)[^\\\\\\/]*$", "");
//    }
    // CONSTANTS
    // deck attributes
    public static final String XMLNodeDeck = "Deck";
    public static final String XMLNodeSalt = "Salt";
    public static final String XMLNodeDeckAttributes = "DeckAttributes";
    public static final String XMLNodeSuits = "Suits";
    public static final String XMLNodeCards = "Cards";
    public static final String XMLNodeDeckName = "DeckName";
    public static final String XMLNodeDeckId = "DeckId";
    public static final String XMLNodeDeckDescription = "DeckDescription";
    public static final String XMLNodeLanguage = "Language";
    public static final String XMLNodeFileAuthor = "FileAuthor";
    public static final String XMLNodeFileCopyrightDate = "FileCopyrightDate";
    public static final String XMLNodeDeckAuthor = "DeckAuthor";
    public static final String XMLNodeDeckCopyrightDate = "DeckCopyrightDate";

    // suit attributes
    public static final String XMLNodeSuit = "Suit";
    public static final String XMLNodeSuitName = "SuitName";
    public static final String XMLNodeSuitId = "SuitId";
    public static final String XMLNodeShortSuitDescription = "ShortDescription";
    public static final String XMLNodeSuitDescription = "SuitDescription";
    public static final String XMLNodeSuitAttributeArcana = "arcana";

    // card attributes
    public static final String XMLNodeCard = "Card";
    public static final String XMLNodeCardNumber = "CardNumber";
    public static final String XMLNodeCardSuitId = "CardSuitId";
    public static final String XMLNodeCardName = "CardName";
    public static final String XMLNodeShortCardDescription = "ShortDescription";
    public static final String XMLNodeLongCardDescription = "LongDescription";
    public static final String XMLNodeReadingText = "ReadingText";
    public static final String XMLNodeCardReadingRelations = "CardReadingRelations";
    public static final String XMLNodeInvertedReadingText = "InvertedReadingText";
    public static final String XMLNodeCardAttributeCourt = "court";
    public static final String XMLNodeSignificatorText = "SignificatorText";

    // deck keywords
    public static final String XMLNodeDeckKeywordDescriptions = "DeckKeywordDescriptions";
    public static final String XMLNodeDeckKeywordDescription = "DeckKeywordDescription";
    public static final String XMLNodeDeckKeyword = "DeckKeyword";
    public static final String XMLNodeDeckKeywordText = "DeckKeywordText";

    // card keywords
    public static final String XMLNodeCardKeywords = "CardKeywords";
    public static final String XMLNodeRelatedSuits = "RelatedSuits";
    public static final String XMLNodeRelatedCards = "RelatedCards";

    // card relations
    public static final String XMLNodeCardRelation = "CardRelation";
    public static final String XMLNodeCardNumberRelation = "CardNumberRelation";
    public static final String XMLNodeCardSuitRelation = "CardSuitRelation";
    public static final String XMLNodeRelationReadingText = "ReadingText";
    public static final String XMLNodeRelationInvertedReadingText = "InvertedReadingText";
    public static final String XMLNodeRelatedSuit = "Suit";

    // position relations
    public static final String XMLNodePositionRelations = "PositionRelations";
    public static final String XMLNodePositionRelation = "PositionRelation";
    public static final String XMLNodePositionSpreadIdRelation = "SpreadIdRelation";
    public static final String XMLNodePositionIdRelation = "PositionIdRelation";
    public static final String XMLNodePositionRelationReadingText = "ReadingText";
    public static final String XMLNodePositionRelationInvertedReadingText = "InvertedReadingText";

    // general static values
    public static final String trueString = "true";
    public static final String falseString = "false";
    public static final String deckXmlFile = "deckXML";
    public static final String deckCardBack = "deckCardBack";
    public static final String textConstant = "#text";
    public static final String firstLine = "Well, I suppose you found this. It'll only get you a bit, but have fun!";
    
    // TODO: Consider putting these values into the deck
    // relation values
    public static final int cardRelationValue = 15;
    public static final int suitRelationValue = 3;
    public static final int keywordRelationValue = 2;
    public static final int arcanaSuitRelationValue = 5;
    public static final int sequentialCardRelationValue = 3;
    
    // relations above this are guaranteed to be read
    public static final int relationValueUpperThreshhold = cardRelationValue;
    
    // relations above this will be read only if too many prior relations have not already been read (below this, they are never read)
    public static final int relationValueLowerThreshhold = cardRelationValue;
    
    // cutoff, after which lower value relations will not be read
    public static final int maxLowValueRelationRead = 2;
    
    // TODO: Consider putting this into the deck xml
    // Max keyword relations that can be described per card relation
    public static final int maxKeywordsPerRelatedCard = 3;
    
    public static final double keywordReccuranceGrowthValue = 1.3;

    public static List<Node> asList(NodeList n) {
        return n.getLength() == 0 ? Collections.<Node>emptyList() : new NodeListWrapper(n);
    }

    static final class NodeListWrapper extends AbstractList<Node> implements RandomAccess {
        private final NodeList list;

        NodeListWrapper(NodeList l) {
            list = l;
        }

        public Node get(int index) {
            return list.item(index);
        }

        @Override
        public int size() {
            return list.getLength();
        }
    }
    
    final static Pattern numberPattern = Pattern.compile("\\d+");
    
    /**
     * Parses text and replaces/parses relevant elements
     * 
     * @param originText
     * @return 
     */
    public static String processTextTags(String originText) {
        var finalText = originText;
        
        finalText = processTextTaxShowChance(finalText);
        finalText = processTextWeightedMultiPossibility(finalText);
        
        return finalText;
    }
    
    /**
     * [multiposs]X=<TEXT1>|Y=<TEXT2>|Z=<TEXT3>[/multiposs]
     * X, Y and Z are the weighs that any given text will be shown, and can be any 
     * integer values. There can be as many texts as you like.
     * @param originText
     * @return 
     */
    public static String processTextWeightedMultiPossibility(String originText) {
        var finalText = originText;
        
        var rand = new Random();
        final var tagOpen = "\\[multiposs\\]";
        final var tagClose = "\\[/multiposs\\]";
        final var fullTag = tagOpen + "(.|\\s)*?" + tagClose;
        final var tagPattern = Pattern.compile(fullTag);
        
        for (var tagMatch = tagPattern.matcher(finalText); tagMatch.find(); tagMatch = tagPattern.matcher(finalText)) {
            try {
                var textMatch = tagMatch.group();
                var phrases = textMatch.split("\\|");

                var totalWeight = 0;
                var weightList = new ArrayList<Integer>();
                var textList = new ArrayList<String>();

                for (var phrase : phrases) {
                    var split = phrase.split("=");
                    var weight = Integer.parseInt(split[0].replaceFirst(tagOpen, ""));
                    var text = split[1].replaceFirst(tagClose, "");

                    totalWeight += weight;
                    weightList.add(totalWeight);
                    textList.add(text);
                }

                var randomWeight = rand.nextInt(totalWeight);

                for (var i = 0; i < weightList.size(); i++) {
                    if (randomWeight < weightList.get(i)) {
                        finalText = finalText.replaceFirst(fullTag, textList.get(i));
                        break;
                    }
                }
            } catch (Exception e) {
                // malformed cases should be ignored - the will be found and reporte at time of validation, as their tags are not replaced
            }
        }

        return finalText;
    }
    
    /**
     * [showChance=X]TEXT[/showChance] <- Random chance (X%) to show or not show given text
     * @param originText
     * @return 
     */
    public static String processTextTaxShowChance(String originText) {
        var rand = new Random();
        var finalText = originText;
        
        // handle showchance
        final var showChanceTagOpen = "\\[showChance=\\d*\\]";
        final var showChanceTagClose = "\\[/showChance\\]";
        final var showChancePattern = Pattern.compile(showChanceTagOpen + "(.|\\s)*?" + showChanceTagClose, Pattern.CASE_INSENSITIVE);
        
        // loop until no instances of tags exist
        for (var tagMatch = showChancePattern.matcher(finalText); tagMatch.find(); tagMatch = showChancePattern.matcher(finalText)) {
            var textMatch = tagMatch.group();
            var numMatch = numberPattern.matcher(textMatch);
            numMatch.find();
            var pctShow = Integer.parseInt(numMatch.group());
            var chance = rand.nextInt(99);
            
            if (pctShow > chance) {
                // remove surrounding tags if test passes
                finalText = finalText.replaceFirst(showChanceTagOpen, "");
                finalText = finalText.replaceFirst(showChanceTagClose, "");
            } else {
                // remove entirely otherwise
                finalText = finalText.replace(textMatch, "");
            }
        }
               
        return finalText;
    }
    
    /**
     * Tests whether text has tags remaining in it
     * @param text
     * @return 
     */
    public static boolean containsTags(String text) {
        var tagRegex = "(\\n|.)*\\[.*\\](\\n|.)*";
        return text.matches(tagRegex);
    }

    private ReaderUtils() {
    }
}
