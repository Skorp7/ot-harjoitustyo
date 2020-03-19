package com.mycompany.unicafe;

public class Paaohjelma {

    public static void main(String[] args) {
        Kassapaate unicafeExactum = new Kassapaate();
        Maksukortti kortti = new Maksukortti(1000);
        kortti.lataaRahaa(550);
       // unicafeExactum.syoEdullisesti(kortti);
        
        System.out.println( unicafeExactum.edullisiaLounaitaMyyty() );
        System.out.println(kortti.toString());
        
    }
}
