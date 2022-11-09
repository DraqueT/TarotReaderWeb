/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author draqu
 */
public class SpreadPositionRelation {

    private final String spreadId;
    private final int positionId;
    private final String readingText;
    private final String invertedReadingText;

    @Override
    public boolean equals(Object obj)
    {
        if (obj != null && obj instanceof SpreadPositionRelation positinoRelation)
        {
            return getSpreadId().equals(positinoRelation.getSpreadId())
                && getPositionId() == positinoRelation.getPositionId()
                && getReadingText().equals(positinoRelation.getReadingText())
                && getInvertedReadingText().equals(positinoRelation.getInvertedReadingText());
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.getSpreadId());
        hash = 41 * hash + this.getPositionId();
        return hash;
    }

    public SpreadPositionRelation(
            String _spreadId,
            int _positionId,
            String _readingText,
            String _invertedReadingText
    )
    {
        spreadId = _spreadId;
        positionId = _positionId;
        readingText = _readingText;
        invertedReadingText = _invertedReadingText;
    }

    public boolean IsValid()
    {
        return getPositionId() > -1 && !spreadId.isBlank();
    }

    public ArrayList<String> Validate()
    {
        var problems = new ArrayList<String>();

        if (getSpreadId().isBlank())
        {
            problems.add("Spread ID not set in relation.");
        }

        if (getPositionId() == -1)
        {
            problems.add("Position ID not set in relation.");
        }

        return problems;
    }
    
    /**
     * @return the spreadId
     */
    public String getSpreadId() {
        return spreadId;
    }

    /**
     * @return the positionId
     */
    public int getPositionId() {
        return positionId;
    }

    /**
     * @return the readingText
     */
    public String getReadingText() {
        return readingText;
    }

    /**
     * @return the invertedReadingText
     */
    public String getInvertedReadingText() {
        return invertedReadingText;
    }

    // TODO: This
//    public XmlNode GetXML(XmlDocument doc)
//    {
//        var position = ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodePositionRelation);
//        position.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodePositionSpreadIdRelation, SpreadId));
//        position.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodePositionIdRelation, PositionId.ToString()));
//        position.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodePositionRelationReadingText, ReadingText));
//        position.AppendChild(ReaderUtils.MakeXmlNode(doc, ReaderUtils.XMLNodePositionRelationInvertedReadingText, invertedReadingText));
//
//        return position;
//    }
}
