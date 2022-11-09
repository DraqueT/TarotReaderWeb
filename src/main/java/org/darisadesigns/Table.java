/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns;

/**
 *
 * @author draqu
 */
public class Table {

    private final TableState tableState;
    private int position = 0;

    public Table(Deck _deck, Spread _spread) throws Exception {
        tableState = new TableState(_deck, _spread);
    }

    public void ReadAll() {
        try {
            while (!IsDone()) {
                ReadNext();
            }
        } catch (Exception e) {
            System.out.println("Reading error: " + e.getLocalizedMessage());
        }
    }

    /**
     * Returns true if all cards have been read
     * @return 
     */
    public boolean IsDone() {
        return position >= tableState.GetLength();
    }

    public void ReadNext() throws Exception {
        if (!IsDone()) {
            var curPos = tableState.GetTablePosition(position);
            curPos.card.setFlipped(true);

            System.out.println("\n");
            System.out.println(curPos.spreadPositon.getDescription());
            System.out.println("Card: " + curPos.card.getName());
            System.out.println(curPos.card.getReading(tableState, curPos.spreadPositon));

            position++;
        }
    }
}
