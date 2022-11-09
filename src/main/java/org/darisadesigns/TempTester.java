/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.darisadesigns;

/**
 *
 * @author draqu
 */
public class TempTester {
    public static void main(String[] Args) {
        try {
            DeckLoader loader = new DeckLoader("Decks/Lomisht/lomisht.xml");
            Deck deck = loader.LoadDeck();

            var spreadLoader = new SpreadLoader("Spreads/CelticCross.xml");
            var spread = spreadLoader.Load();
            
            var table = new Table(deck, spread);
            
            table.ReadAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
