package teamdjg.wildescape.carepackage;

import java.util.ArrayList;
import java.util.List;

public class CarePackageProfile {

	public String name = null;
	public PlayerRank openingsRank = null;
	public List<CarePackageItem> items = new ArrayList<>();
	
	public List<Integer> droppingsTimings = new ArrayList<>();
	public int droppingsAmount = 1;
	public Double droppingsAmountChance = 1D;
}
