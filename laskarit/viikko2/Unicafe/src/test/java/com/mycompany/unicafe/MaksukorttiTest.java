package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoAlussaOikein() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void saldoKasvaaOikein() {
        kortti.lataaRahaa(550);
        
        assertEquals("saldo: 15.50", kortti.toString());
    }
    
    @Test
    public void saldoVaheneeOikeinKunOnRahaa() {
        kortti.otaRahaa(400);
        
        assertEquals("saldo: 6.0", kortti.toString());
    }
    
    @Test
    public void saldoEiMuutuJosEiOleRahaa() {
        kortti.otaRahaa(700);
        // saldo on nyt 3.0
        kortti.otaRahaa(500);

        assertEquals("saldo: 3.0", kortti.toString());
    }
    
    @Test
    public void otaRahaaPalauttaaTrueJosOnRahaa() {
        assertTrue(kortti.otaRahaa(600));
    }

    @Test
    public void otaRahaaPalauttaaFalseJosEiOleRahaa() {
        kortti.otaRahaa(700);
        // saldo on nyt 3.0
        assertFalse(kortti.otaRahaa(600));
    }
    
    @Test
    public void saldoPalauttaaSaldon() {
        assertEquals(1000, kortti.saldo());
    }
}
