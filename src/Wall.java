import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface Structure {
	// zwraca dowolny element o podanym kolorze
	Optional<Block> findBlockByColor(String color);
	// zwraca wszystkie elementy z danego materia³u
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
 klasa otrzymala rowniez gettery getColor() oraz getMaterial(), zwracajace statyczna wartosc */
interface CompositeBlock extends Block {
	List<Block> getBlocks();
}



public class Wall implements Structure {
	private List<Block> blocks;	
	
	//Definiuje konstruktor do klasy wall (do testow)
	public Wall(List<Block> blocks) {
		this.blocks = blocks;
	}	

	/* Musze uzyc metody findBlockByColor rekursywnie, zeby zaadresowaæ problem z zagniezdzonymi blokami zlozonymi, 
	jednak zgodnie z interfejsem metoda nie moze przyjmowac argumentow innych niz String material, 
	tak wiec uzywam metody pomocniczej recursiveFindBlocksByMaterial() przyjmujacej poza stringiem rowniez liste blokow */
	public Optional<Block> findBlockByColor(String color) {
		return recursiveFindBlockByColor(blocks, color);
	}	
	
	private Optional<Block> recursiveFindBlockByColor(List<Block> blocks, String color) {
		if (color == null || blocks == null) {
			return Optional.empty(); //zabezpieczam przed przekazaniem nulla jako argument
		}
		/* tworze strumien z listy blokow, strumien "trzyma" w sobie tylko bloki o podanym kolorze i zwraca pierwszy znaleziony
		 lub Optional.empty() jesli nie ma zadnego
		 w przypadku blokow zlozonych dzieki rekursji w strumieniu laduje lista blokow skladowych 
		 (lub pusty strumien, jesli w skladowych nie ma bloku o podanym kolorze) */
			return blocks.stream()
					.flatMap(block -> block instanceof CompositeBlock ? recursiveFindBlockByColor(((CompositeBlock) block).getBlocks(), color).stream() : Stream.of(block))
					.filter(block -> color.equalsIgnoreCase(block.getColor()))
					.findFirst();
        }
	
	/* Musze uzyc metody findBlocksByMaterial rekursywnie, zeby zaadresowac problem z zagniezdzonymi blokami zlozonymi, 
	jednak zgodnie z interfejsem metoda nie moze przyjmowac argumentow innych niz String material, 
	tak wiec uzywam metody pomocniczej recursiveFindBlocksByMaterial() przyjmujacej oprocz stringa rowniez liste blokow  */	
	public List<Block> findBlocksByMaterial(String material) {
		return recursiveFindBlocksByMaterial(blocks, material);
	}
        
	private List<Block> recursiveFindBlocksByMaterial(List<Block> blocks, String material){
		if (material == null || blocks == null) {
			return Collections.emptyList(); 
			/*zabezpieczam przed przekazaniem nulla jako argument, przy okazji refactora, zauwazylem ze
			 moznaby zamiast ArrayList zwrocic Collections.emptyList, niewielka optymalizacja ale zawsze optymalizacja :)
			 */
			 	
		}
		/* tworze strumien z listy blokow, strumien "trzyma" w sobie tylko bloki z podanego materialu i zbiera je do listy, 
		  w przypadku blokow zlozonych sumowany jest blok zlozony, jak rowniez suma blokow skladowych pochodzaca z rekursji */
			return blocks.stream()
					.flatMap(block -> block instanceof CompositeBlock ? Stream.concat(Stream.of(block), recursiveFindBlocksByMaterial(((CompositeBlock) block).getBlocks(), material).stream()) : Stream.of(block))
					.filter(block -> material.equalsIgnoreCase(block.getMaterial()))
					.collect(Collectors.toList());
		}
	
		/* Musze uzyc metody count rekursywnie, zeby zaadresowac problem z zagniezdzonymi blokami zlozonymi, 
		jednak zgodnie z interfejsem metoda count nie moze przyjmowaæ argumentow, tak wiêc uzywam metody pomocniczej recursiveCount(),
		ktora przyjmuje liste bloków */
		public int count() {
			return recursiveCount(blocks);
		}

		private int recursiveCount(List<Block> blocks) {
			if (blocks == null) { //zabezpieczamy przed nullem przekazanym jako lista blokow, nie psujac jednoczesnie rekursji
				return 0;
			}
			/*tworze strumien z listy blokow, konwertuje je z Block do int, w przypadku bloku zlozonego, licze jego skladowe
			 * rekursywnym wywolaniem metody, w przypadku bloku pojedynczego licze go jako 1, wszytkie integery sa sumowane */
			return blocks.stream()
					.mapToInt(block -> block instanceof CompositeBlock ? recursiveCount(((CompositeBlock) block).getBlocks()) : 1)
					.sum();
		}
}



