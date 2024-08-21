import java.util.List;

public class CompositeBlockClass implements CompositeBlock {
	private List<Block> blocks;
	
	public CompositeBlockClass(List<Block> blocks) {
		this.blocks = blocks;
	}
	
	public List<Block> getBlocks() {
		return blocks;
	}
	
	public String getColor() {
		return "Composite block, color may vary";
	}
	
	public String getMaterial() {
		return "Composite block, material may vary";
	}
	
	
 
}
