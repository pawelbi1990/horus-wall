import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

interface Structure {
	// zwraca dowolny element o podanym kolorze
	Optional<Block> findBlockByColor(String color);
	// zwraca wszystkie elementy z danego materia³u
	List<Block> findBlocksByMaterial(String material);
	//zwraca liczbê wszystkich elementów tworz¹cych strukturê
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

	/* Musimy u¿yc metody findBlockByColor rekursywnie, ¿eby zaadresowaæ problem z zagniezdzonymi blokami zlozonymi, 
	jednak zgodnie z interfejsem metoda nie mo¿e przyjmowaæ argumentów innych ni¿ String material, 
	tak wiêc u¿ywam metody pomocniczej recursiveFindBlocksByMaterial */
	public Optional<Block> findBlockByColor(String color) {
		return recursiveFindBlockByColor(blocks, color);
	}	
	
	private Optional<Block> recursiveFindBlockByColor(List<Block> blocks, String color) {
		if (color == null) {
			return Optional.empty(); //zabezpieczamy przed przekazaniem nulla jako argument
		}
        for (Block block : blocks) {
        	/* dla wszystkich bloków w liœcie sprawdzamy kolor u¿ywaj¹c metody equalsIgnoreCase, 
        	celem pominiêcie sprawdzania wielkoœci znaków w stringu */
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
        //jak w pêtli nie znajdziemy bloku o danym kolorze, zwracamy pust¹ instancjê Optional
        }
	
	/* Musimy u¿yc metody findBlocksByMaterial rekursywnie, ¿eby zaadresowaæ problem z zagniezdzonymi blokami zlozonymi, 
	jednak zgodnie z interfejsem metoda nie mo¿e przyjmowaæ argumentów innych ni¿ String material, 
	tak wiêc u¿ywam metody pomocniczej recursiveFindBlocksByMaterial */	
	public List<Block> findBlocksByMaterial(String material) {
		return recursiveFindBlocksByMaterial(blocks, material);
	}
        
	private List<Block> recursiveFindBlocksByMaterial(List<Block> blocks, String material){
		if (material == null) {
			return new ArrayList<>();
		}
		List<Block> foundBlocks = new ArrayList<>();
		for (Block block :blocks) {
			//przeszukujemy listê bloków w poszukiwaniu bloków z danego materia³u, znalezione dodajemy to listy foundBlocks
			if (material.equalsIgnoreCase(block.getMaterial())) {
				foundBlocks.add(block);
			}			
			if (block instanceof CompositeBlock) {
				/* sprawdzamy czy blok jest instancj¹ CompositeBlock, jeœli jest, 
				to rekursywnie wywo³ujemy metodê recursiveFinfBlocksByMaterial */					
					foundBlocks.addAll(recursiveFindBlocksByMaterial(((CompositeBlock) block).getBlocks(), material));									
				}
			}
		
		return foundBlocks;
		//zwracamy listê wszystkich bloków o podanym kolorze, zarówno tych z pojedynczych bloków, jak i tych z³o¿onych
		}
	
		/* Musimy u¿yc metody count rekursywnie, ¿eby zaadresowaæ problem z zagniezdzonymi blokami zlozonymi, 
		jednak zgodnie z interfejsem metoda count nie mo¿e przyjmowaæ argumentów, tak wiêc u¿ywam metody pomocniczej recursiveCount */
		public int count() {
			return recursiveCount(blocks);
		}

		private int recursiveCount(List<Block> blocks) {		
			int blockCount = 0, compositeBlockFragmentCount = 0;
			/* zak³adam, ¿e przez wszystkie elementy rozumiemy pojedyncze bloki, równie¿ te które sk³adaj¹ siê na bloki z³o¿one,
			tak wiêc u¿yjê logiki podobnej jak w poprzednich metodach, zliczaj¹c zarówno pojedyncze bloki, jak i sk³adowe bloków z³o¿onych, 
			jeœli jako element rozumiemy block LUB blok z³o¿ony, prawdopodobnie wystarczy³oby zwróciæ blocks.size() */
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



