import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

interface Structure {
	// zwraca dowolny element o podanym kolorze
	Optional<Block> findBlockByColor(String color);
	// zwraca wszystkie elementy z danego materia�u
	List<Block> findBlocksByMaterial(String material);
	//zwraca liczbe wszystkich elementow tworzacych strukture
	int count();
}

/*interfejs zaimplementowany w klasie BlockClass, settery poza scope zadania, tak wiec klasa posiada tylko konstruktor i gettery
  (do testow) */
interface Block {
	String getColor();
	String getMaterial();
}

/*interfejs zaimplementowany w klasie CompositeBlockClass, settery poza scope zadania, tak wiec klasa posiada tylko konstruktor i gettery
 (do testow), jako ze interfejs CompositeBlock dziedziczy z interfejsu Block, 
 otrzymal rowniez gettery getColor() oraz getMaterial(), zwracajace statyczna wartosc */
interface CompositeBlock extends Block {
	List<Block> getBlocks();
}



public class Wall implements Structure {
	private List<Block> blocks;	
	
	//Definiuje konstruktor do klasy wall (do testow)
	public Wall(List<Block> blocks) {
		this.blocks = blocks;
	}	

	/* Musze uzyc metody findBlockByColor rekursywnie, �eby zaadresowa� problem z zagniezdzonymi blokami zlozonymi, 
	jednak zgodnie z interfejsem metoda nie mo�e przyjmowa� argumentow innych ni� String material, 
	tak wiec uzywam metody pomocniczej recursiveFindBlocksByMaterial() przyjmujacej poza stringiem rowniez liste blokow */
	public Optional<Block> findBlockByColor(String color) {
		return recursiveFindBlockByColor(blocks, color);
	}	
	
	private Optional<Block> recursiveFindBlockByColor(List<Block> blocks, String color) {
		if (color == null) {
			return Optional.empty(); //zabezpieczamy przed przekazaniem nulla jako argument
		}
        for (Block block : blocks) {
        	/* dla wszystkich blokow w liscie sprawdzam kolor uzywaj�c metody equalsIgnoreCase, 
        	celem pominiecia sprawdzania wielkosci znak�w w stringu */
        	if (color.equalsIgnoreCase(block.getColor())) {
        		return Optional.of(block);
        		//zwraca pierwszy blok o danym kolorze
        	}
        	
        	if (block instanceof CompositeBlock) {
        		Optional<Block> nestedBlock = recursiveFindBlockByColor(((CompositeBlock) block).getBlocks(), color);
        		if (nestedBlock.isPresent()) {
        			return nestedBlock;
        		}
        	}
        }
        return Optional.empty();
        //jesli petla nie znajdzie bloku o danym kolorze, zwracana jest pusta instancja Optional
        }
	
	/* Musze uzyc metody findBlocksByMaterial rekursywnie, �eby zaadresowac problem z zagniezdzonymi blokami zlozonymi, 
	jednak zgodnie z interfejsem metoda nie mo�e przyjmowac argumentow innych ni� String material, 
	tak wiec uzywam metody pomocniczej recursiveFindBlocksByMaterial() przyjmujacej oprocz stringa rowniez liste blokow  */	
	public List<Block> findBlocksByMaterial(String material) {
		return recursiveFindBlocksByMaterial(blocks, material);
	}
        
	private List<Block> recursiveFindBlocksByMaterial(List<Block> blocks, String material){
		if (material == null) {
			return new ArrayList<>();
		}
		List<Block> foundBlocks = new ArrayList<>();
		for (Block block :blocks) {
			//przeszukuje liste blokow w poszukiwaniu blokow z danego materialu, znalezione dodaje to listy foundBlocks
			if (material.equalsIgnoreCase(block.getMaterial())) {
				foundBlocks.add(block);
			}			
			if (block instanceof CompositeBlock) {
				/* sprawdzam czy blok jest instancja CompositeBlock, jesli tak, 
				to rekursywnie wywoluje metode recursiveFinfBlocksByMaterial() */					
					foundBlocks.addAll(recursiveFindBlocksByMaterial(((CompositeBlock) block).getBlocks(), material));									
				}
			}
		
		return foundBlocks;
		//zwracam liste wszystkich blokow o podanym kolorze, zarowno tych z pojedynczych blokow, jak i tych zlozonych
		}
	
		/* Musze uzyc metody count rekursywnie, �eby zaadresowac problem z zagniezdzonymi blokami zlozonymi, 
		jednak zgodnie z interfejsem metoda count nie mo�e przyjmowa� argument�w, tak wi�c u�ywam metody pomocniczej recursiveCount(),
		ktora przyjmuje liste blok�w */
		public int count() {
			return recursiveCount(blocks);
		}

		private int recursiveCount(List<Block> blocks) {		
			int blockCount = 0, compositeBlockFragmentCount = 0;
			/* zakladam, �e przez wszystkie elementy rozumiemy pojedyncze bloki, r�wnie� te kt�re skladaj� sie na bloki zlozone,
			tak wi�c uzyje logiki podobnej jak w poprzednich metodach, zliczajac zarowno pojedyncze bloki, jak i skladowe blokow zlozonych, 
			jesli jako element rozumiemy block LUB blok zlozony, prawdopodobnie wystarczyloby zwrocic blocks.size() */
			for (Block block : blocks) {			
				if (block instanceof CompositeBlock) {
					compositeBlockFragmentCount += recursiveCount(((CompositeBlock) block).getBlocks());
				} else if (block instanceof Block) {
					blockCount++;
				}
			}
			blockCount += compositeBlockFragmentCount;
			return blockCount;
		}
}



