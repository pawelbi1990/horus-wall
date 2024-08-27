import java.util.Arrays;

public class Test {
    
    public static void main (String[] args) {
    	Block block1 = new BlockClass(null, "stone");
    	Block block2 = new BlockClass("green", null);
    	Block block3 = new BlockClass(null, null);
    	Block block4 = new BlockClass("red", "concrete");
    	CompositeBlock hugeBlock1 = new CompositeBlockClass(Arrays.asList(block1, block2, block3));
    	CompositeBlock hugeBlock2 = new CompositeBlockClass(Arrays.asList(hugeBlock1, block2, block3, block4));
    	CompositeBlock nullBlock = new CompositeBlockClass(null);
    	Wall wall = new Wall(Arrays.asList(block1, block2, block3, hugeBlock1, hugeBlock2));
    	Wall nullWall = new Wall(null);
    	Wall nullWall2 = new Wall(Arrays.asList(nullBlock, hugeBlock2, hugeBlock2));
    	System.out.println("Iloœæ bloków w murze - powinienem zwrocic 12: "+wall.count());
    	//oczekiwane: 12, aktualne: 12
    	System.out.println("Kamienne bloki w murze - powinienem zwrocic liste 3 obiektow: "+wall.findBlocksByMaterial("stone"));
    	//oczekiwane 3, aktualne 3
    	System.out.println("Powinienem zwróciæ pusty array: "+wall.findBlocksByMaterial(null));
        /* oczekiwane: wywo³anie metody z nullem zwraca pusty array
 	       aktualne: wywo³anie metody z nullem zwraca pusty array */
    	System.out.println("Pierwszy czerwony blok w murze - powinienem zwrocic jeden obiekt Optional<Block>: "+wall.findBlockByColor("red"));
    	/* oczekiwane: znaleziono obiekt w zagniezdzonym podwojnie bloku z³o¿onym
    	   aktualne: znaleziono obiekt w zagniezdzonym podwojnie bloku z³o¿onym */
    	System.out.println("Powinienem zwróciæ Optional.empty: "+wall.findBlockByColor(null));
        /* oczekiwane: wywo³anie metody z nullem zwraca Optional:empty
	       aktualne: wywo³anie metody z nullem zwraca Optional:empty */
    	
    	/*Sprawdzam czy blocks null check dziala poprawnie*/
    	System.out.println("Null count check - powinienem zwrocic 0: "+nullWall.count());
    	System.out.println("Null color find check - powinienem zwrocic Optional.empty : "+nullWall.findBlockByColor("red"));
    	System.out.println("Null block find check - powinienem zwrocic []: "+nullWall.findBlocksByMaterial("stone"));
    	/* oczekiwane: 
    	   	Null count check: 0    	 
		   	Null color find check: Optional.empty
			Null block find check: []
		  aktualne: 
    	   	Null count check: 0    	 
		   	Null color find check: Optional.empty
			Null block find check: [] */
    	System.out.println("Null count check with nested composite block - powinienem zwrocic 12: "+nullWall2.count());
    	/* oczekiwane: 12, aktualne: 12 */
    	System.out.println("Null color find check with nested composite block - powinienem zwrocic 1 obiekt Optional<Block>: "+nullWall2.findBlockByColor("red"));
    	/* oczekiwane: znaleziono obiekt w zagniezdzonym bloku zlozonym
 	   	   aktualne: znaleziono obiekt w zagniezdzonym bloku zlozonym */
    	System.out.println("Null material find check with nested composite block - powinienem zwrocic liste 2 obiektow: "+nullWall2.findBlocksByMaterial("concrete"));
    	/* oczekiwane: znaleziono 2 obiekt w zagniezdzonych blokach zlozonych
	   	   aktualne: znaleziono 2 obiekty w zagniezdzonych blokach zlozonych */
    	
    	
    }
        
}