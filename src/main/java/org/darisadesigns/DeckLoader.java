/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author draqu
 */
public class DeckLoader {

    private final Document document;
    private Deck deck;
    private Map<String, byte[]> files = new HashMap<>();
    public List<String> LoadProblems = new ArrayList<>();

    /**
     * Opens files.Put in try catch.
     *
     * @param _files
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws java.io.IOException
     */
    public DeckLoader(Map<String, byte[]> _files) throws ParserConfigurationException, SAXException, IOException, Exception {
        files = _files;
        byte[] xmlFileBytes = GetFile(ReaderUtils.deckXmlFile);

        var dbf = DocumentBuilderFactory.newInstance();
        var db = dbf.newDocumentBuilder();
        document = db.parse(new ByteArrayInputStream(xmlFileBytes));
    }

    /**
     * Loads raw XML docuemnt and pulls linked files manually. Used early in dev
     * before archive system implemented
     *
     * @param filePath
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public DeckLoader(String filePath) throws SAXException, IOException, ParserConfigurationException {
        var dbf = DocumentBuilderFactory.newInstance();
        var db = dbf.newDocumentBuilder();
        document = db.parse(filePath);
    }

    /**
     *
     * @return @throws Exception On problem loading file
     */
    public Deck LoadDeck() throws Exception {
        var deckNode = document.getElementsByTagName(ReaderUtils.XMLNodeDeck);

        if (deckNode.getLength() != 1) {
            throw new Exception("Malformed Deck XML");
        }

        for (var node : ReaderUtils.asList(deckNode.item(0).getChildNodes())) {
            if (node instanceof Comment || node == null) {
                continue;
            }

            switch (node.getNodeName()) {
                case ReaderUtils.XMLNodeDeckAttributes ->
                    PopulateAttributes(node);
                case ReaderUtils.XMLNodeSuits ->
                    PopulateSuits(node);
                case ReaderUtils.XMLNodeCards ->
                    PopulateCards(node);
                case ReaderUtils.XMLNodeDeckKeywordDescriptions ->
                    PopulateDeckKeywords(node);
                case ReaderUtils.textConstant -> {
                }
                default ->
                    LoadProblems.add("Unrecognized node: '"
                            + node.getNodeName()
                            + "' in Deck");
            }
        }

        LoadProblems.addAll(deck.Validate());

        return deck;
    }

    private void PopulateDeckKeywords(Node keywordDescriptions) {
        var deckKeywords = new ArrayList<DeckKeyword>();

        for (var node : ReaderUtils.asList(keywordDescriptions.getChildNodes())) {
            if (node instanceof Comment || node == null) {
                continue;
            }

            switch (node.getNodeName()) {
                case ReaderUtils.XMLNodeDeckKeywordDescription ->
                    deckKeywords.add(PopulateDeckKeyword(node));
                case ReaderUtils.textConstant -> {
                }
                default ->
                    LoadProblems.add("Unrecognized node: '"
                            + node.getNodeName()
                            + "' in Deck");
            }
        }

        for (var keyword : deckKeywords) {
            deck.getDeckKeywords().put(keyword.name, keyword);
        }
    }

    private DeckKeyword PopulateDeckKeyword(Node keywordDescription) {
        String keyword = "";
        String keywordText = "";

        for (var node : ReaderUtils.asList(keywordDescription.getChildNodes())) {
            if (node instanceof Comment || node == null) {
                continue;
            }

            switch (node.getNodeName()) {
                case ReaderUtils.XMLNodeDeckKeyword ->
                    keyword = node.getTextContent().trim();
                case ReaderUtils.XMLNodeDeckKeywordText ->
                    keywordText = node.getTextContent().trim();
                case ReaderUtils.textConstant -> {
                }
                default ->
                    LoadProblems.add("Unrecognized node: '"
                            + node.getNodeName()
                            + "' in Deck");
            }
        }

        return new DeckKeyword(keyword, keywordText);
    }

    private void PopulateAttributes(Node attributes) {
        String deckName = "";
        String deckId = "";
        String deckDescription = "";
        String language = "";
        String fileAuthor = "";
        String fileCopyrightDate = "";
        String deckAuthor = "";
        String deckCopyrightDate = "";

        for (Node node : ReaderUtils.asList(attributes.getChildNodes())) {
            if (node instanceof Comment || node == null) {
                continue;
            }

            switch (node.getNodeName()) {
                case ReaderUtils.XMLNodeDeckName ->
                    deckName = node.getTextContent().trim();
                case ReaderUtils.XMLNodeDeckId ->
                    deckId = node.getTextContent().trim();
                case ReaderUtils.XMLNodeDeckDescription ->
                    deckDescription = node.getTextContent().trim();
                case ReaderUtils.XMLNodeLanguage ->
                    language = node.getTextContent().trim();
                case ReaderUtils.XMLNodeFileAuthor ->
                    fileAuthor = node.getTextContent().trim();
                case ReaderUtils.XMLNodeFileCopyrightDate ->
                    fileCopyrightDate = node.getTextContent().trim();
                case ReaderUtils.XMLNodeDeckAuthor ->
                    deckAuthor = node.getTextContent().trim();
                case ReaderUtils.XMLNodeDeckCopyrightDate ->
                    deckCopyrightDate = node.getTextContent().trim();
                case "DeckBackAsset" -> {
                } // legacy relic - ignore
                case ReaderUtils.textConstant -> {
                }
                default ->
                    LoadProblems.add("Unrecognized node: '"
                            + node.getNodeName()
                            + "' in DeckAttributes");
            }
        }

        deck = new Deck(
                deckName,
                deckId,
                deckDescription,
                language,
                fileAuthor,
                fileCopyrightDate,
                deckAuthor,
                deckCopyrightDate
        );
    }

    private void PopulateSuits(Node suits) {
        for (Node node : ReaderUtils.asList(suits.getChildNodes())) {
            if (node instanceof Comment || node == null) {
                continue;
            }

            if (!node.getNodeName().equals(ReaderUtils.XMLNodeSuit)) {
                if (!node.getNodeName().equals(ReaderUtils.textConstant)) { // ignore text constants
                    LoadProblems.add("Unrecognized node: '"
                            + node.getNodeName()
                            + "' in Suits");
                }
                continue;
            }

            PopulateSuit(node);
        }
    }

    private void PopulateSuit(Node suit) {
        String suitName = "";
        int suitId = -1;
        String shortDescription = "";
        String suitDescription = "";

        for (Node node : ReaderUtils.asList(suit.getChildNodes())) {
            if (node instanceof Comment || node == null) {
                continue;
            }

            switch (node.getNodeName()) {
                case ReaderUtils.XMLNodeSuitName ->
                    suitName = node.getTextContent().trim();
                case ReaderUtils.XMLNodeSuitId -> {
                    try {
                        suitId = Integer.parseInt(node.getTextContent().trim());
                    } catch (NumberFormatException | DOMException e) {
                        LoadProblems.add("Non int value for suit id: " + node.getTextContent().trim());
                    }
                }
                case ReaderUtils.XMLNodeShortSuitDescription ->
                    shortDescription = node.getTextContent().trim();
                case ReaderUtils.XMLNodeSuitDescription ->
                    suitDescription = node.getTextContent().trim();
                case ReaderUtils.textConstant -> {
                }
                default ->
                    LoadProblems.add("Unrecognized node: '"
                            + node.getNodeName()
                            + "' in Suit");
            }
        }

        boolean isArcana = false;
        Node arcana = suit.getAttributes().getNamedItem(ReaderUtils.XMLNodeSuitAttributeArcana);

        if (arcana != null) {
            isArcana = arcana.getNodeValue().equals(ReaderUtils.trueString);
        } else {
            LoadProblems.add("Suit: " + suitName + " has malformed arcana attribute.");
        }

        deck.AddSuit(new Suit(
                suitId,
                suitName,
                suitDescription,
                shortDescription,
                isArcana));
    }

    private void PopulateCards(Node cards) throws Exception {
        for (Node node : ReaderUtils.asList(cards.getChildNodes())) {
            if (node instanceof Comment || node == null) {
                continue;
            }

            if (!node.getNodeName().equals(ReaderUtils.XMLNodeCard)) {
                if (!node.getNodeName().equals(ReaderUtils.textConstant)) { // ignore text constants
                    LoadProblems.add("Unrecognized node: '"
                            + node.getNodeName()
                            + "' in Cards");
                }

                continue;
            }

            PopulateCard(node);
        }
    }

    private void PopulateCard(Node card) throws Exception {
        int cardNumber = -1;
        int cardSuitId = -1;
        String cardName = "";
        String shortDescription = "";
        String longDescription = "";
        String readingText = "";
        String invertedReadingText = "";
        String significatorText = "";
        Node cardReadingRelations = null;

        for (Node node : ReaderUtils.asList(card.getChildNodes())) {
            if (node instanceof Comment || node == null) {
                continue;
            }

            switch (node.getNodeName()) {
                case ReaderUtils.XMLNodeCardNumber -> {
                    try {
                        cardNumber = Integer.parseInt(node.getTextContent().trim());
                    } catch (NumberFormatException | DOMException e) {
                        LoadProblems.add("Non int value for card number: " + node.getTextContent().trim());
                    }
                }
                case ReaderUtils.XMLNodeCardSuitId -> {
                    try {
                        cardSuitId = Integer.parseInt(node.getTextContent().trim());
                    } catch (NumberFormatException | DOMException e) {
                        LoadProblems.add("Non int value for card suit: " + node.getTextContent().trim());
                    }
                }
                case ReaderUtils.XMLNodeCardName ->
                    cardName = node.getTextContent().trim();
                case ReaderUtils.XMLNodeShortCardDescription ->
                    shortDescription = node.getTextContent().trim();
                case ReaderUtils.XMLNodeLongCardDescription ->
                    longDescription = node.getTextContent().trim();
                case ReaderUtils.XMLNodeReadingText ->
                    readingText = node.getTextContent().trim();
                case ReaderUtils.XMLNodeInvertedReadingText ->
                    invertedReadingText = node.getTextContent().trim();
                case ReaderUtils.XMLNodeCardReadingRelations ->
                    cardReadingRelations = node;
                case ReaderUtils.XMLNodeSignificatorText ->
                    significatorText = node.getTextContent().trim();
                case ReaderUtils.textConstant -> {
                }
                case "CardFrontAsset" -> {
                } // legacy relic - ignore
                default ->
                    LoadProblems.add("Unrecognized node: '"
                            + node.getNodeName()
                            + "' in Card");
            }
        }

        boolean isCourt = false;
        Node court = card.getAttributes().getNamedItem(ReaderUtils.XMLNodeCardAttributeCourt);

        if (court != null) {
            isCourt = court.getNodeValue().equals(ReaderUtils.trueString);
        } else {
            LoadProblems.add("Card: " + cardName + " has malformed court attribute.");
        }

        Card newCard = new Card(
                cardSuitId,
                cardNumber,
                isCourt,
                cardName,
                longDescription,
                shortDescription,
                readingText,
                invertedReadingText,
                significatorText
        );

        AddCardRelations(cardReadingRelations, newCard);

        deck.AddCard(newCard);
    }

    private void AddCardRelations(Node relations, Card card) {
        for (Node node : ReaderUtils.asList(relations.getChildNodes())) {
            if (node instanceof Comment || node == null) {
                continue;
            }

            switch (node.getNodeName()) {
                case ReaderUtils.XMLNodeCardKeywords ->
                    card.SetKeywords(node.getTextContent().trim());
                case ReaderUtils.XMLNodeRelatedSuits ->
                    AddRelatedSuits(node, card);
                case ReaderUtils.XMLNodeRelatedCards ->
                    AddRelatedCards(node, card);
                case ReaderUtils.XMLNodePositionRelations ->
                    AddRelatedPositions(node, card);
                case ReaderUtils.textConstant -> {
                }
                default ->
                    LoadProblems.add("Unrecognized node: '"
                            + node.getNodeName()
                            + "' in Card");
            }
        }
    }

    private void AddRelatedPositions(Node relatedPositions, Card card) {
        var relatedPosition = new ArrayList<SpreadPositionRelation>();

        for (Node node : ReaderUtils.asList(relatedPositions.getChildNodes())) {
            if (node instanceof Comment || node == null) {
                continue;
            }

            switch (node.getNodeName()) {
                case ReaderUtils.XMLNodePositionRelation ->
                    relatedPosition.add(AddRelatedPosition(node));
                case ReaderUtils.textConstant -> {
                }
                default ->
                    LoadProblems.add("Unrecognized node: '"
                            + node.getNodeName()
                            + "' in Card");
            }
        }

        card.setPositionRelations(relatedPosition.toArray(new SpreadPositionRelation[0]));
    }

    private SpreadPositionRelation AddRelatedPosition(Node relatedPosition) {
        String spreadId = "";
        int positionId = -1;
        String readingText = "";
        String invertedText = "";

        for (Node node : ReaderUtils.asList(relatedPosition.getChildNodes())) {
            if (node instanceof Comment || node == null) {
                continue;
            }

            switch (node.getNodeName()) {
                case ReaderUtils.XMLNodePositionSpreadIdRelation ->
                    spreadId = node.getTextContent().trim();
                case ReaderUtils.XMLNodePositionIdRelation -> {
                    try {
                        positionId = Integer.parseInt(node.getTextContent().trim());
                    } catch (NumberFormatException | DOMException e) {
                        LoadProblems.add("Non int value for spread position relation: " + node.getTextContent().trim());
                    }
                }
                case ReaderUtils.XMLNodePositionRelationReadingText ->
                    readingText = node.getTextContent().trim();
                case ReaderUtils.XMLNodePositionRelationInvertedReadingText ->
                    invertedText = node.getTextContent().trim();
                case ReaderUtils.textConstant -> {
                }
                default ->
                    LoadProblems.add("Unrecognized node: '"
                            + node.getNodeName()
                            + "' in Card");
            }
        }

        return new SpreadPositionRelation(spreadId, positionId, readingText, invertedText);
    }

    private void AddRelatedCards(Node relations, Card card) {
        var cardRelations = new ArrayList<CardRelation>();

        for (Node node : ReaderUtils.asList(relations.getChildNodes())) {
            if (node instanceof Comment || node == null) {
                continue;
            }

            switch (node.getNodeName()) {
                case ReaderUtils.XMLNodeCardRelation ->
                    cardRelations.add(CreateCardRelation(node));
                case ReaderUtils.textConstant -> {
                }
                default ->
                    LoadProblems.add("Unrecognized node: '"
                            + node.getNodeName()
                            + "' in Card");
            }
        }

        card.setCardRelations(cardRelations.toArray(new CardRelation[0]));
    }

    private CardRelation CreateCardRelation(Node relation) {
        int suitId = -1;
        int number = -1;
        String readingText = "";
        String invertedReadingText = "";

        for (Node node : ReaderUtils.asList(relation.getChildNodes())) {
            if (node instanceof Comment || node == null) {
                continue;
            }

            switch (node.getNodeName()) {
                case ReaderUtils.XMLNodeCardNumberRelation -> {
                    try {
                        number = Integer.parseInt(node.getTextContent().trim());
                    } catch (NumberFormatException | DOMException e) {
                        LoadProblems.add("Non int value for suit card number (relation): " + node.getTextContent().trim());
                    }
                }
                case ReaderUtils.XMLNodeCardSuitRelation -> {
                    try {
                        suitId = Integer.parseInt(node.getTextContent().trim());
                    } catch (NumberFormatException | DOMException e) {
                        LoadProblems.add("Non int value for suit id (relation): " + node.getTextContent().trim());
                    }
                }
                case ReaderUtils.XMLNodeRelationReadingText ->
                    readingText = node.getTextContent().trim();
                case ReaderUtils.XMLNodeRelationInvertedReadingText ->
                    invertedReadingText = node.getTextContent().trim();
                case ReaderUtils.textConstant -> {
                }
                default ->
                    LoadProblems.add("Unrecognized node: '"
                            + node.getNodeName()
                            + "' in Card");
            }
        }

        return new CardRelation(suitId, number, readingText, invertedReadingText);
    }

    private void AddRelatedSuits(Node relations, Card card) {
        var relatedSuits = new ArrayList<Integer>();

        for (Node node : ReaderUtils.asList(relations.getChildNodes())) {
            if (node instanceof Comment || node == null) {
                continue;
            }

            switch (node.getNodeName()) {
                case ReaderUtils.XMLNodeRelatedSuit -> {
                    String suitString = node.getTextContent().trim();
                    if (!suitString.equals("")) {
                        int suitId = -1;

                        try {
                            suitId = Integer.parseInt(suitString);
                        } catch (NumberFormatException | DOMException e) {
                            LoadProblems.add("Non int value for suit id: " + node.getTextContent().trim());
                        }

                        relatedSuits.add(suitId);
                    }
                }
                case ReaderUtils.textConstant -> {
                }
                default ->
                    LoadProblems.add("Unrecognized node: '"
                            + node.getNodeName()
                            + "' in Card");
            }
        }

        card.setRelatedSuits(relatedSuits.stream().mapToInt(i -> i).toArray());
    }

    /// <summary>
    /// Throws exception if file not found
    /// </summary>
    /// <returns></returns>
    private byte[] GetFile(String fileName) throws Exception {
        if (!files.containsKey(fileName)) {
            throw new Exception("Deck archive corrupt. " + fileName + " file missing.");
        }

        return files.get(fileName);
    }
}
