
public class BlockClass implements Block {
	private String color;
	private String material;
	
	public BlockClass(String color, String material) {
		this.color = color;
		this.material = material;
	}
	
	
	public String getColor() {
		return color;
	}
	
	public String getMaterial() {
		return material;
	}
	
	
}
