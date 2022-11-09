/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author draqu
 */
public class SpreadLoader {

    /**
     * @return the loadProblems
     */
    public List<String> getLoadProblems() {
        return loadProblems;
    }
    private final Document document;
    private Spread spread;
    private final List<String> loadProblems;

    public SpreadLoader(String filePath) throws SAXException, IOException, ParserConfigurationException
    {
        var dbf = DocumentBuilderFactory.newInstance();
        var db = dbf.newDocumentBuilder();
        
        document = db.parse(new File(filePath));
        
        
        loadProblems = new ArrayList<>();
    }

    public Spread Load() throws Exception
    {
        NodeList spreadNode = document.getElementsByTagName("Spread");

        if (spreadNode.getLength() != 1)
        {
            throw new Exception("Malformed Spread XML");
        }

        for (var node : ReaderUtils.asList(spreadNode.item(0).getChildNodes()))  {
            if (node instanceof Comment || node == null)
            {
                continue;
            }

            switch (node.getNodeName())
            {
                case "SpreadAttributes" -> PopulateAttributes(node);
                case "Positions" -> PopulatePositions(node);
                case ReaderUtils.textConstant -> {}                default -> getLoadProblems().add("Unrecognized node: '"
                        + node.getNodeName()
                        + "' in Deck");
            }
        }

        getLoadProblems().addAll(spread.Validate());

        return spread;
    }

    private void PopulateAttributes(Node attributes)
    {
        String name = "";
        String description = "";
        String spreadId = "";
        String language = "";
        String author = "";
        String copyrightDate = "";
        float cardSizeVertical = -1;
        float cardSizeHorizontal = -1;

        for (var node : ReaderUtils.asList(attributes.getChildNodes())) {
            if (node instanceof Comment || node == null)
            {
                continue;
            }

            switch (node.getNodeName())
            {
                case "SpreadName" -> name = node.getTextContent().trim();
                case "SpreadDescription" -> description = node.getTextContent().trim();
                case "SpreadId" -> spreadId = node.getTextContent().trim();
                case "Language" -> language = node.getTextContent().trim();
                case "SpreadAuthor" -> author = node.getTextContent().trim();
                case "CopyrightDate" -> copyrightDate = node.getTextContent().trim();
                case "CardSizeVertical" -> {
                    try {
                        cardSizeVertical = Float.parseFloat(node.getTextContent().trim());
                    } catch (NumberFormatException | DOMException e) {
                        getLoadProblems().add("Non float value for card size vertical: " + node.getTextContent().trim());
                    }
                }
                case "CardSizeHorizontal" -> {
                    try {
                        cardSizeHorizontal = Float.parseFloat(node.getTextContent().trim());
                    } catch (NumberFormatException | DOMException e) {
                        getLoadProblems().add("Non float value for card size horizontal: " + node.getTextContent().trim());
                    }
                }
                case ReaderUtils.textConstant -> {}                default -> getLoadProblems().add("Unrecognized node: '"
                        + node.getNodeName()
                        + "' in DeckAttributes");
            }
        }

        spread = new Spread(name,
            description,
            spreadId,
            language,
            author,
            copyrightDate,
            cardSizeVertical,
            cardSizeHorizontal);
    }

    private void PopulatePositions(Node positions)
    {
        for (var node : ReaderUtils.asList(positions.getChildNodes())) {
            if (node instanceof Comment || node == null)
            {
                continue;
            }

            if (!node.getNodeName().equals("Position"))
            {
                if (!node.getNodeName().equals(ReaderUtils.textConstant)) { //ignore literals
                    getLoadProblems().add("Unrecognized node: '"
                            + node.getNodeName()
                            + "' in Positions");
                }
                
                continue;
            }

            PopulatePosition(node);
        }
    }

    private void PopulatePosition(Node position)
    {
        String name = "";
        int order = -1;
        String description = "";
        float xPos = -1;
        float yPos = -1;
        float rotation = -1;
        boolean faceDown = true;
        boolean significator = false;

        for (var node : ReaderUtils.asList(position.getChildNodes())) {
            if (node instanceof Comment || node == null)
            {
                continue;
            }

            switch (node.getNodeName())
            {
                case "PositionName" -> name = node.getTextContent().trim();
                case "Order" -> {                    
                    try {
                        order = Integer.parseInt(node.getTextContent().trim());
                    } catch (NumberFormatException | DOMException e) {
                        getLoadProblems().add("Non int value for spread posiotion order: " + node.getTextContent().trim());
                    }
                }
                case "PositionDescription" -> description = node.getTextContent().trim();
                case "HorizontalPosition" -> {
                    try {
                        xPos = Float.parseFloat(node.getTextContent().trim());
                    } catch (NumberFormatException | DOMException e) {
                        getLoadProblems().add("Non float value for card x position: " + node.getTextContent().trim());
                    }
                }
                case "VerticalPosition" -> {
                    try {
                        yPos = Float.parseFloat(node.getTextContent().trim());
                    } catch (NumberFormatException | DOMException e) {
                        getLoadProblems().add("Non float value for card y position: " + node.getTextContent().trim());
                    }
                }
                case "Rotation" -> {
                    try {
                        rotation = Float.parseFloat(node.getTextContent().trim());
                    } catch (NumberFormatException | DOMException e) {
                        getLoadProblems().add("Non float value for card rotation: " + node.getTextContent().trim());
                    }
                }
                case "StartFaceDown" -> faceDown = node.getTextContent().toLowerCase().trim().equals(ReaderUtils.trueString);
                case "Significator" -> significator = node.getTextContent().toLowerCase().trim().equals(ReaderUtils.trueString);
                case ReaderUtils.textConstant -> {}                default -> getLoadProblems().add("Unrecognized node: '"
                        + node.getNodeName()
                        + "' in DeckAttributes");
            }
        }

        spread.AddPosition(new SpreadPosition(name, description, xPos, yPos, rotation, order, faceDown, significator));
    }
}
