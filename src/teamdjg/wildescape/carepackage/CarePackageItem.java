package teamdjg.wildescape.carepackage;

import org.bukkit.Material;

public class CarePackageItem {
	
	Material material;
	int amount;
	double amountChance;
	
	
	public CarePackageItem(Material material, int amount, double amountChance) {
		this.material = material;
		this.amount = amount;
		this.amountChance = amountChance;
	}
	
}
