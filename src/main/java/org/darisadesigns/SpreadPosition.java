/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author draqu
 */
public class SpreadPosition {

    private final String name;
    private final String description;
    private final float horizontalPosition;
    private final float verticalPosition;
    private final float rotation;
    private final int order;
    private final boolean faceDown;
    private final boolean significator;

    public SpreadPosition(
            String _name,
            String _description,
            float _horizontalPosition,
            float _verticalPosition,
            float _rotation,
            int _order,
            boolean _faceDown,
            boolean _significator) {
        name = _name;
        description = _description;
        horizontalPosition = _horizontalPosition;
        verticalPosition = _verticalPosition;
        rotation = _rotation;
        order = _order;
        faceDown = _faceDown;
        significator = _significator;
    }

    public boolean IsValid() {
        return Validate().isEmpty();
    }

    public List<String> Validate() {
        var problems = new ArrayList<String>();

        if (getName().isBlank()) {
            problems.add("Spread position must have name");
        }

        if (getHorizontalPosition() < 0 || getHorizontalPosition() > 100) {
            problems.add("Spread position horizontal position must be between 0 and 100");
        }

        if (getVerticalPosition() < 0 || getVerticalPosition() > 100) {
            problems.add("Spread position vertical position must be between 0 and 100");
        }

        if (getOrder() < 0) {
            problems.add("Spread position order must be > 0");
        }

        return problems;
    }

    /**
     * @return the Name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the HorizontalPosition
     */
    public float getHorizontalPosition() {
        return horizontalPosition;
    }

    /**
     * @return the VerticalPosition
     */
    public float getVerticalPosition() {
        return verticalPosition;
    }

    /**
     * @return the Rotation
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * @return the Order
     */
    public int getOrder() {
        return order;
    }

    /**
     * @return the FaceDown
     */
    public boolean isFaceDown() {
        return faceDown;
    }

    /**
     * @return the significator
     */
    public boolean isSignificator() {
        return significator;
    }
}
