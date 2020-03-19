/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author sakorpi
 */
public class KassapaateTest {

    Kassapaate paate;
    Maksukortti kortti;

    public KassapaateTest() {
    }

    @Before
    public void setUp() {
        this.paate = new Kassapaate();
        this.kortti = new Maksukortti(1000);
    }

    @Test
    public void rahamaaraOikeinAlussa() {
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void myydytMaukkaatOikeinAlussa() {
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void myydytEdullisetOikeinAlussa() {
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void rahamaaraOikein() {
        paate.syoMaukkaasti(400);
        paate.syoMaukkaasti(400);
        paate.syoEdullisesti(240);
        assertEquals(101040, paate.kassassaRahaa());
    }

    @Test
    public void maukkaitaMyytyOikein() {
        paate.syoMaukkaasti(400);
        paate.syoMaukkaasti(400);

        assertEquals(2, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void edullisiaMyytyOikein() {
        paate.syoEdullisesti(240);

        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void maksuMaukasRiittavaVaihtoRahaOikein() {
        assertEquals(200, paate.syoMaukkaasti(600));
    }

    @Test
    public void maksuEdullinenRiittavaVaihtoRahaOikein() {
        assertEquals(60, paate.syoEdullisesti(300));
    }

    @Test
    public void maksuMaukasRiittavaMyytyjenMaaraKasvaa() {
        paate.syoMaukkaasti(500);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void maksuEdullinenRiittavaMyytyjenMaaraKasvaa() {
        paate.syoEdullisesti(500);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void maksuMaukasEiRiittavaVaihtorahaOikein() {
        assertEquals(300, paate.syoMaukkaasti(300));
    }

    @Test
    public void maksuEdullinenEiRiittavaVaihtorahaOikein() {
        assertEquals(200, paate.syoEdullisesti(200));
    }

    public void maksuMaukasEiRiittavaMyytyjenMaaraEiKasva() {
        paate.syoMaukkaasti(200);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    public void maksuEdullinenEiRiittavaMyytyjenMaaraEiKasva() {
        paate.syoEdullisesti(200);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }

    // Maksukorttitestit:
    @Test
    public void korttimaksuMaukasRiittava() {

        assertTrue(paate.syoMaukkaasti(kortti));
        assertEquals(600, kortti.saldo());
    }

    @Test
    public void korttimaksuEdullinenRiittava() {

        assertTrue(paate.syoEdullisesti(kortti));
        assertEquals(760, kortti.saldo());
    }

    @Test
    public void korttimaksuMaukasRiittavaMyytyjenMaaraKasvaa() {
        paate.syoMaukkaasti(kortti);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void korttimaksuEdullinenRiittavaMyytyjenMaaraKasvaa() {
        paate.syoEdullisesti(kortti);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void korttimaksuMaukasEiRiittava() {
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        // saldo on nyt 200
        paate.syoMaukkaasti(kortti);
        assertFalse(paate.syoMaukkaasti(kortti));
        assertEquals(200, kortti.saldo());
        assertEquals(2, paate.maukkaitaLounaitaMyyty());
    }

    @Test
    public void korttimaksuEdullinenEiRiittava() {
        paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti);
        paate.syoEdullisesti(kortti);
        // saldo on nyt 40
        paate.syoEdullisesti(kortti);
        assertFalse(paate.syoEdullisesti(kortti));
        assertEquals(40, kortti.saldo());
        assertEquals(4, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void kassanRahaMaaraEiMuutuKorttimaksulla() {
        paate.syoEdullisesti(kortti);
        paate.syoMaukkaasti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void latausKortilleMuuttaaKortinSaldoa() {
        paate.lataaRahaaKortille(kortti, 500);
        assertEquals(1500, kortti.saldo());
    }
    
    @Test
    public void latausKortilleMuuttaaKassanRahamaaraa() {
        paate.lataaRahaaKortille(kortti, 200);
        assertEquals(100200, paate.kassassaRahaa());
    }
    
    @Test
    public void negatiivinenSummaEiMuutaKortinEikaKassanSaldoa() {
       paate.lataaRahaaKortille(kortti, -500);
        assertEquals(1000, kortti.saldo());
        assertEquals(100000, paate.kassassaRahaa());
    }

}
