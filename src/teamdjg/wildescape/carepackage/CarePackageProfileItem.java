package teamdjg.wildescape.carepackage;

import org.bukkit.Material;

public class CarePackageProfileItem {

	public CarePackageProfileItem(Material material, int amount, double amountChance)
	{
		this.material = material;
		this.amount = amount;
		this.amountChance = amountChance;
	}
	
	Material material = null;
	int amount;
	double amountChance;
}
