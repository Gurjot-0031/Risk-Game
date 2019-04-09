package risktest;

import Model.*;
import org.junit.*;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestBenevolentPlayer {

    int maxArmiesAndAdjacents = 0;
    Territory retTerr = null;
    AggressiveStrategyPlayer aP = new AggressiveStrategyPlayer();
    Game g = Game.getInstance();
    Territory territory = null;
    //Territory retTerr = null;

    Player p1 = new Player(1, "p1", Color.red, 40, "HUMAN");
    Player p2 = new Player(2, "p2", Color.blue, 40, "HUMAN");

    ArrayList<String> siamAdj = new ArrayList<>();
    ArrayList<String> japanAdj = new ArrayList<>();
    ArrayList<String> uralAdj = new ArrayList<>();
    ArrayList<String> alaskaAdj = new ArrayList<>();
    ArrayList<String> kamchatkaAdj = new ArrayList<>();

    ArrayList<String> chinaAdj = new ArrayList<>();
    ArrayList<String> indiaAdj = new ArrayList<>();
    ArrayList<String> indonesiaAdj = new ArrayList<>();
    ArrayList<String> mongoliaAdj = new ArrayList<>();
    ArrayList<String> siberiaAdj = new ArrayList<>();
    ArrayList<String> afghanistanAdj = new ArrayList<>();
    ArrayList<String> ukraineAdj = new ArrayList<>();
    ArrayList<String> albertaAdj = new ArrayList<>();
    ArrayList<String> northwestAdj = new ArrayList<>();

    ArrayList<Territory> p1Territories = new ArrayList<>();
    ArrayList<Territory> p2Territories = new ArrayList<>();

    //Siam,270,139,Asia,China,India,Indonesia
    //Japan,322,104,Asia,Kamchatka,Mongolia
    //Ural,241,68,Asia,Siberia,China,Afghanistan,Ukraine
    //Alaska,70,126,North America,Alberta,Kamchatka
    //Kamchatka,56,34,North America,Northwest

    //China,110,98,Asia,Siam,Ural
    //India,210,88,Asia,Siam
    //Indonesia,310,78,Asia,Siam
    //Mongolia,410,68,North America,Japan
    //Siberia,510,58,North America,Ural
    //Afghanistan,610,48,Asia,Ural
    //Ukraine,710,38,North America,Ural
    //Alberta,910,18,North America,Alaska
    //NorthWest,920,8,Kamchatka


    Territory sM = new Territory("Siam", 270, 139, siamAdj);//40
    Territory jN = new Territory("Japan", 322, 104, japanAdj);//40
    Territory uL = new Territory("Ural", 241, 68, uralAdj);//40
    Territory aA = new Territory("Alaska", 70, 126, alaskaAdj);//40
    Territory kA = new Territory("Kamchakta", 56, 34, kamchatkaAdj);//40

    Territory cH = new Territory("China", 110, 98, chinaAdj);//40
    Territory iA = new Territory("India", 210, 88, indiaAdj);//40
    Territory inA = new Territory("Indonesia", 310, 78, indonesiaAdj);//40
    Territory mA = new Territory("Mongalia", 410, 68, mongoliaAdj);//40
    Territory sA = new Territory("Siberia", 510, 58, siberiaAdj);//40
    Territory aN = new Territory("Afghanistan", 610, 48, afghanistanAdj);//40
    Territory uE = new Territory("Ukraine", 710, 38, ukraineAdj);//40
    Territory albA = new Territory("Alberta", 910, 18, albertaAdj);//40
    Territory nT = new Territory("Northwest", 920, 8, northwestAdj);//40

    BenevolentStrategyPlayer bP = new BenevolentStrategyPlayer();
    @Before
    public void setValues() {

        Continent asia = new Continent("Asia", 7);
        Continent northAmerica = new Continent("North America", 2);

        asia.addTerritory(sM);
        asia.addTerritory(jN);
        asia.addTerritory(uL);
        asia.addTerritory(cH);
        asia.addTerritory(iA);
        asia.addTerritory(inA);
        asia.addTerritory(aN);

        northAmerica.addTerritory(aA);
        northAmerica.addTerritory(kA);
        northAmerica.addTerritory(mA);
        northAmerica.addTerritory(sA);
        northAmerica.addTerritory(uE);
        northAmerica.addTerritory(albA);
        northAmerica.addTerritory(nT);


        chinaAdj.add("Siam");
        chinaAdj.add("Ural");

        cH.addAdjacent("Siam");
        cH.addAdjacent("Ural");

        indiaAdj.add("Siam");

        iA.addAdjacent("Siam");

        indonesiaAdj.add("Siam");

        inA.addAdjacent("Siam");

        mongoliaAdj.add("Japan");

        mA.addAdjacent("Japan");

        siberiaAdj.add("Ural");

        sA.addAdjacent("Ural");

        afghanistanAdj.add("Ural");

        aN.addAdjacent("Ural");

        ukraineAdj.add("Ural");

        uE.addAdjacent("Ural");

        albertaAdj.add("Alaska");

        albA.addAdjacent("Alaska");

        northwestAdj.add("Kamchatka");

        nT.addAdjacent("Kamchatka");

        siamAdj.add("China");
        siamAdj.add("India");
        siamAdj.add("Indonesia");

        sM.addAdjacent("China");
        sM.addAdjacent("India");
        sM.addAdjacent("Indonesia");

        japanAdj.add("Kamchatka");
        japanAdj.add("Mongolia");

        jN.addAdjacent("Kamchatka");
        jN.addAdjacent("Mongolia");

        uralAdj.add("Siberia");
        uralAdj.add("China");
        uralAdj.add("Afghanistan");
        uralAdj.add("Ukraine");

        uL.addAdjacent("Siberia");
        uL.addAdjacent("China");
        uL.addAdjacent("Afghanistan");
        uL.addAdjacent("Ukraine");

        alaskaAdj.add("Alberta");
        alaskaAdj.add("Kamachatka");

        aA.addAdjacent("Alberta");
        aA.addAdjacent("Kamchatka");

        kamchatkaAdj.add("America");
        kamchatkaAdj.add("Northwest");

        kA.addAdjacent("America");
        kA.addAdjacent("Northwest");

        File file = new File("testMap.map");
        Map map = new Map(file.getAbsolutePath());
        Game.getInstance().setMap(map);

        Game.getInstance().addPlayer(p1);
        Game.getInstance().addPlayer(p2);
        Game.getInstance().setTurn(1);
        //Game.getInstance().setCurrentPlayer(p1);
        //nI.setOwner(p1);
        sM.setOwner(p1);
        jN.setOwner(p1);
        uL.setOwner(p1);

        cH.setOwner(p1);
        iA.setOwner(p1);
        inA.setOwner(p1);
        mA.setOwner(p1);
        sA.setOwner(p1);

        aA.setOwner(p2);
        kA.setOwner(p2);

        aN.setOwner(p2);
        uE.setOwner(p2);
        albA.setOwner(p2);
        nT.setOwner(p2);

        p1Territories.add(sM);
        p1Territories.add(jN);
        p1Territories.add(uL);
        p1Territories.add(cH);
        p1Territories.add(iA);
        p1Territories.add(inA);
        p1Territories.add(mA);
        p1Territories.add(sA);

        p2Territories.add(aA);
        p2Territories.add(kA);
        p2Territories.add(aN);
        p2Territories.add(uE);
        p2Territories.add(albA);
        p2Territories.add(nT);

        sM.addArmy(5);
        jN.addArmy(8);
        uL.addArmy(11);
        cH.addArmy(12);
        iA.addArmy(2);
        inA.addArmy(1);
        mA.addArmy(3);
        sA.addArmy(3);

        aA.addArmy(8);
        kA.addArmy(2);
        aN.addArmy(5);
        uE.addArmy(15);
        albA.addArmy(9);
        nT.addArmy(11);

        p1.setTerritoriesOwned(p1Territories);
        p2.setTerritoriesOwned(p2Territories);
    }

    @Test
    public void testFortify() {

        assertEquals(bP.fortify("Siam"), "Benevolent Fortification completed");
    }

    @Test
    public void testgetStrongestTerritories() {

        assertEquals(0, bP.getStrongestTerritories().size());
    }

    @Test
    public void testgetweakestTerritories() {

        assertEquals(0, bP.getWeakestTerritories().size());
    }

    @Test
    public void testReinforcement() {
        //System.out.println(bP.reinforce("Siam"));
        //assertEquals(bP.reinforce("Siam"), "Benevolent Player Reinforcement completed");
    }

}
