/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns;

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;
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
    
    // reading Segments
    public static final String ReadingBase = "ReadingBase";
    public static final String ReadingCardRelation = "ReadingCardRelation";
    public static final String ReadingPositionRelation = "ReadingPositionRelation";

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

    private ReaderUtils() {
    }
}
