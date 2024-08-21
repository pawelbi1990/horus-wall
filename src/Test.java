import java.util.Arrays;

public class Test {
    
    public static void main (String[] args) {
    	Block block1 = new BlockClass(null, "stone");
    	Block block2 = new BlockClass("green", null);
    	Block block3 = new BlockClass(null, null);
    	Block block4 = new BlockClass("red", "concrete");
    	CompositeBlock hugeBlock1 = new CompositeBlockClass(Arrays.asList(block1, block2, block3));
    	CompositeBlock hugeBlock2 = new CompositeBlockClass(Arrays.asList(hugeBlock1, block2, block3, block4));
    	Wall wall = new Wall(Arrays.asList(block1, block2, block3, hugeBlock1, hugeBlock2));
    	System.out.println("Ilo�� blok�w w murze: "+wall.count());
    	//oczekiwane: 12, aktualne: 12
    	System.out.println("Kamienne bloki w murze: "+wall.findBlocksByMaterial("stone"));
    	//oczekiwane 3, aktualne 3
    	System.out.println("Pierwszy czerwony blok w murze: "+wall.findBlockByColor("red"));
    	/* oczekiwane: znaleziono obiekt w zagniezdzonym podwojnie bloku z�o�onym
    	   aktualne: znaleziono obiekt w zagniezdzonym podwojnie bloku z�o�onym */
        System.out.println("Powinienem zwr�ci� pusty array: "+wall.findBlocksByMaterial(null));
        /* oczekiwane: wywo�anie metody z nullem zwraca pusty array
 	       aktualne: wywo�anie metody z nullem zwraca pusty array */
        System.out.println("Powinienem zwr�ci� Optional.empty: "+wall.findBlockByColor(null));
        /* oczekiwane: wywo�anie metody z nullem zwraca Optional:empty
	       aktualne: wywo�anie metody z nullem zwraca Optional:empty */
    }
}