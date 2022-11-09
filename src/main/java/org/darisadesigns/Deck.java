/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author draqu
 */
public class Deck {

    private final String name;
    private final String id;
    private final String description;
    private final String langId;
    private final String fileAuthor;
    private final String deckAuthor;
    private String fileCopyright;
    private final String deckCopyright;
    private final Map<String, DeckKeyword> deckKeywords = new HashMap<>();
    private final Map<String, Card> cards = new HashMap<>();
    private final Map<Integer, Suit> suits = new HashMap<>();

    public Deck(
            String _name,
            String _id,
            String _description,
            String _langId,
            String _fileAuthor,
            String _fileCopyright,
            String _deckAuthor,
            String _deckCopyrght
    ) {
        name = _name;
        id = _id;
        description = _description;
        langId = _langId;
        fileAuthor = _fileAuthor;
        fileCopyright = _fileCopyright;
        deckAuthor = _deckAuthor;
        deckCopyright = _deckCopyrght;
    }

    public void AddSuit(Suit newSuit) {
        getSuits().put(newSuit.getId(), newSuit);
    }

    /**
     *
     * @param newCard Adds a card to the deck.
     * @throws Exception Thrown when cards with conflicting ids or with
     * non-existent suits are added.
     */
    public void AddCard(Card newCard) throws Exception {
        String cardId = newCard.GetId();

        if (getCards().containsKey(cardId)) {
            Card overlap = getCards().get(cardId);
            throw new Exception("These cards have the same Suit/Number, which is disallowed: "
                    + newCard.getName()
                    + ", " + overlap.getName());
        }

        if (!suits.containsKey(newCard.getSuit())) {
            throw new Exception("Suit with id " + newCard.getSuit() + " does not exist. Please check definition for card: " + newCard.getName() + ".");
        }

        getCards().put(cardId, newCard);
    }

    /**
     * Returns list of all court cards in deck
     *
     * @return
     */
    public Card[] GetCourtCards() {
        var courtCards = new ArrayList<Card>();

        for (var key : cards.keySet()) {
            var card = getCards().get(key);

            if (card.isCourt()) {
                courtCards.add(card);
            }
        }

        return courtCards.toArray(new Card[0]);
    }

    /**
     * Returns shuffled cards
     *
     * @return
     */
    public List<Card> GetShuffledCards() {
        var rand = new Random();
        var cardList = GetCards();

        var count = cardList.size();
        var last = count - 1;
        for (var i = 0; i < last; ++i) {
            var r = rand.nextInt(i, count);
            Collections.swap(cardList, i, r);
        }

        var shuffledCards = new ArrayList<Card>();

        for (var card : cardList) {
            card.setInverted(rand.nextInt(0, 2) > 0);
            shuffledCards.add(card);
        }

        return shuffledCards;
    }

    /**
     * Returns cards in order they were first inserted into the deck
     *
     * @return
     */
    public List<Card> GetCards() {
        var cardList = new ArrayList<Card>();

        for (var card : cards.values()) {
            cardList.add(card);
        }

        return cardList;
    }

    /**
     * Fetches suit by id
     *
     * @param suitId
     * @return
     * @throws java.lang.Exception If suit id DNE.
     */
    public Suit GetSuit(int suitId) throws Exception {
        if (getSuits().containsKey(suitId)) {
            return getSuits().get(suitId);
        }

        throw new Exception("Unable to find suit with id: " + suitId);
    }

    public boolean IsValid() {
        return Validate().isEmpty();
    }

    public List<String> Validate() {
        var problems = new ArrayList<String>();

        if (getName().isBlank()) {
            problems.add("Deck has no name.");
        }
        if (getId().isBlank()) {
            problems.add("Deck has no ID set");
        }

        //TODO: THIS
//        if (!ReaderUtils.IsValidISO3CountryCode(LangId))
//        {
//            problems.Add("Not valid, three character ISO3 languageID: " + getLangId());
//        }
        for (var card : cards.values()) {
            problems.addAll(card.Validate());
        }

        for (var keyword : deckKeywords.values()) {
            problems.addAll(keyword.Validate());
        }

        for (var card : cards.values()) {
            for (var keyword : card.getKeywords()) {
                if (!deckKeywords.containsKey(keyword)) {
                    problems.add("Keyword on card missing in deck definition: " + keyword);
                }
            }
        }
        
        return problems;
    }

    @Override
    public boolean equals(Object obj) {
        Boolean ret = true;

        if (obj == null || !(obj instanceof Deck)) {
            ret = false;
        }

        Deck deck = (Deck) obj;
        
        if (deck == null) {
            return false;
        }

        if (ret) {
            ret = getName().equals(deck.getName())
                    && getId().equals(deck.getId())
                    && getDescription().equals(deck.getDescription())
                    && getLangId().equals(deck.getLangId())
                    && getFileAuthor().equals(deck.getFileAuthor())
                    && getFileCopyright().equals(deck.getFileCopyright())
                    && getDeckAuthor().equals(deck.getDeckAuthor())
                    && getDeckCopyright().equals(deck.getDeckCopyright())
                    && getCards().size() == deck.getCards().size()
                    && getSuits().size() == deck.getSuits().size()
                    && getDeckKeywords().size() == deck.getDeckKeywords().size()
                    && KeywordEquals(getDeckKeywords(), deck.getDeckKeywords());
        }

        if (ret) {
            for (var key : cards.keySet()) {
                if (deck.getCards().containsKey(key)
                        && getCards().get(key).equals(deck.getCards().get(key))) {
                    continue;
                }
                ret = false;
                break;
            }
        }

        var compSuits = deck.getSuits();
        if (ret) {
            for (var key : this.suits.keySet()) {
                if (compSuits != null && deck.getSuits().containsKey(key) && getSuits().get(key).equals(compSuits.get(key))) {
                    continue;
                }
                ret = false;
                break;
            }
        }

        return ret;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.name);
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    // TODO: THIS
//    public XmlNode GetXML(XmlDocument doc)
//    {
//        XmlNode deck = doc.CreateNode(XmlNodeType.Element, ReaderUtils.XMLNodeDeck, null);
//
//        // write attributes
//        XmlNode attributes = ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeDeckAttributes);
//        attributes.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeDeckName, getName()));
//        attributes.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeDeckId, getId()));
//        attributes.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeDeckDescription, getDescription()));
//        attributes.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeLanguage, getLangId()));
//        attributes.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeFileAuthor, getFileAuthor()));
//        attributes.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeFileCopyrightDate, getFileCopyright()));
//        attributes.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeDeckAuthor, getDeckAuthor()));
//        attributes.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeDeckCopyrightDate, getDeckCopyright()));
//        attributes.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeSalt, Salt));
//
//        deck.AppendChild(attributes);
//
//
//        // write Keyword Descriptions
//        XmlNode keywordDescriptions = ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeDeckKeywordDescriptions);
//        foreach (var value in )
//        {
//            XmlNode keywordDescription = ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeDeckKeywordDescription);
//            keywordDescription.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeDeckKeyword, value.Keyword));
//            keywordDescription.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeDeckKeywordText, value.KeywordText));
//            keywordDescriptions.AppendChild(keywordDescription);
//        }
//        deck.AppendChild(keywordDescriptions);
//
//        // write Suits
//        XmlNode suitsNode = ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeSuits);
//        foreach (var value in )
//        {
//            XmlNode suitNode = ReaderUtils.MakeXmlNode(
//                doc,
//                ReaderUtils.XMLNodeSuit,
//                "",
//                ReaderUtils.XMLNodeSuitAttributeArcana,
//                value.IsArcana ? ReaderUtils.trueString : ReaderUtils.falseString);
//            suitNode.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeSuitName, value.Name));
//            suitNode.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeSuitId, value.Id.ToString()));
//            suitNode.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeShortSuitDescription, value.ShortDescription));
//            suitNode.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeSuitDescription, value.Description));
//            suitsNode.AppendChild(suitNode);
//        }
//        deck.AppendChild(suitsNode);
//
//        // write Cards
//        XmlNode cardsNode = ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodeCards);
//        foreach (var value in )
//        {
//            cardsNode.AppendChild(value.GetXML(doc));
//        }
//        deck.AppendChild(cardsNode);
//
//
//
//        return deck;
//    }
    private boolean KeywordEquals(Map<String, DeckKeyword> dict1, Map<String, DeckKeyword> dict2) {
        if (dict1.size() != dict2.size()) {
            return false;
        }

        for (var key : dict1.keySet()) {
            if (!dict2.containsKey(key)) {
                return false;
            }

            if (!dict1.get(key).equals(dict2.get(key))) {
                return false;
            }
        }

        return true;
    }

    public String StaticValues() {
        return getName() + getId() + getFileAuthor() + getFileCopyright() + getDeckAuthor() + getDeckCopyright();
    }

    /**
     * @return the Name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the Id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the LangId
     */
    public String getLangId() {
        return langId;
    }

    /**
     * @return the FileAuthor
     */
    public String getFileAuthor() {
        return fileAuthor;
    }

    /**
     * @return the FileCopyright
     */
    public String getFileCopyright() {
        return fileCopyright;
    }

    /**
     * @param FileCopyright the FileCopyright to set
     */
    public void setFileCopyright(String FileCopyright) {
        this.fileCopyright = FileCopyright;
    }

    /**
     * @return the DeckAuthor
     */
    public String getDeckAuthor() {
        return deckAuthor;
    }

    /**
     * @return the DeckCopyright
     */
    public String getDeckCopyright() {
        return deckCopyright;
    }

    /**
     * @return the DeckKeywords
     */
    public Map<String, DeckKeyword> getDeckKeywords() {
        return deckKeywords;
    }

    /**
     * @return the Cards
     */
    public Map<String, Card> getCards() {
        return cards;
    }

    /**
     * @return the Suits
     */
    public Map<Integer, Suit> getSuits() {
        return suits;
    }
}
