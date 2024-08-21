import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

interface Structure {
	// zwraca dowolny element o podanym kolorze
	Optional<Block> findBlockByColor(String color);
	// zwraca wszystkie elementy z danego materia�u
	List<Block> findBlocksByMaterial(String material);
	//zwraca liczb� wszystkich element�w tworz�cych struktur�
	int count();
}

interface Block {
	String getColor();
	String getMaterial();
}

interface CompositeBlock extends Block {
	List<Block> getBlocks();
}

public class Wall implements Structure {
	private List<Block> blocks;	
	
	public Wall(List<Block> blocks) {
		this.blocks = blocks;
	}	

	/* Musimy u�yc metody findBlockByColor rekursywnie, �eby zaadresowa� problem z zagniezdzonymi blokami zlozonymi, 
	jednak zgodnie z interfejsem metoda nie mo�e przyjmowa� argument�w innych ni� String material, 
	tak wi�c u�ywam metody pomocniczej recursiveFindBlocksByMaterial */
	public Optional<Block> findBlockByColor(String color) {
		return recursiveFindBlockByColor(blocks, color);
	}	
	
	private Optional<Block> recursiveFindBlockByColor(List<Block> blocks, String color) {
		if (color == null) {
			return Optional.empty(); //zabezpieczamy przed przekazaniem nulla jako argument
		}
        for (Block block : blocks) {
        	/* dla wszystkich blok�w w li�cie sprawdzamy kolor u�ywaj�c metody equalsIgnoreCase, 
        	celem pomini�cie sprawdzania wielko�ci znak�w w stringu */
        	if (color.equalsIgnoreCase(block.getColor())) {
        		return Optional.of(block);
        		//zwracamy pierwszy blok o danym kolorze
        	}
        	
        	if (block instanceof CompositeBlock) {
        		Optional<Block> nestedBlock = recursiveFindBlockByColor(((CompositeBlock) block).getBlocks(), color);
        		if (nestedBlock.isPresent()) {
        			return nestedBlock;
        		}
        	}
        }
        return Optional.empty();
        //jak w p�tli nie znajdziemy bloku o danym kolorze, zwracamy pust� instancj� Optional
        }
	
	/* Musimy u�yc metody findBlocksByMaterial rekursywnie, �eby zaadresowa� problem z zagniezdzonymi blokami zlozonymi, 
	jednak zgodnie z interfejsem metoda nie mo�e przyjmowa� argument�w innych ni� String material, 
	tak wi�c u�ywam metody pomocniczej recursiveFindBlocksByMaterial */	
	public List<Block> findBlocksByMaterial(String material) {
		return recursiveFindBlocksByMaterial(blocks, material);
	}
        
	private List<Block> recursiveFindBlocksByMaterial(List<Block> blocks, String material){
		if (material == null) {
			return new ArrayList<>();
		}
		List<Block> foundBlocks = new ArrayList<>();
		for (Block block :blocks) {
			//przeszukujemy list� blok�w w poszukiwaniu blok�w z danego materia�u, znalezione dodajemy to listy foundBlocks
			if (material.equalsIgnoreCase(block.getMaterial())) {
				foundBlocks.add(block);
			}			
			if (block instanceof CompositeBlock) {
				/* sprawdzamy czy blok jest instancj� CompositeBlock, je�li jest, 
				to rekursywnie wywo�ujemy metod� recursiveFinfBlocksByMaterial */					
					foundBlocks.addAll(recursiveFindBlocksByMaterial(((CompositeBlock) block).getBlocks(), material));									
				}
			}
		
		return foundBlocks;
		//zwracamy list� wszystkich blok�w o podanym kolorze, zar�wno tych z pojedynczych blok�w, jak i tych z�o�onych
		}
	
		/* Musimy u�yc metody count rekursywnie, �eby zaadresowa� problem z zagniezdzonymi blokami zlozonymi, 
		jednak zgodnie z interfejsem metoda count nie mo�e przyjmowa� argument�w, tak wi�c u�ywam metody pomocniczej recursiveCount */
		public int count() {
			return recursiveCount(blocks);
		}

		private int recursiveCount(List<Block> blocks) {		
			int blockCount = 0, compositeBlockFragmentCount = 0;
			/* zak�adam, �e przez wszystkie elementy rozumiemy pojedyncze bloki, r�wnie� te kt�re sk�adaj� si� na bloki z�o�one,
			tak wi�c u�yj� logiki podobnej jak w poprzednich metodach, zliczaj�c zar�wno pojedyncze bloki, jak i sk�adowe blok�w z�o�onych, 
			je�li jako element rozumiemy block LUB blok z�o�ony, prawdopodobnie wystarczy�oby zwr�ci� blocks.size() */
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



