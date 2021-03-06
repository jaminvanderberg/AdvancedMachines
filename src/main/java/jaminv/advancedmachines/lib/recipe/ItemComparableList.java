package jaminv.advancedmachines.lib.recipe;

import java.util.ArrayList;
import java.util.Comparator;

import javax.annotation.Nullable;

import com.google.common.base.MoreObjects;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 * Used to manage a list of ItemComparable objects that is sortable and comparable.
 * 
 * This list maintains a "sorted" state and only sorts when necessary.
 * The list is sorted (if necessary) when comparisons are performed.
 */
public class ItemComparableList  {
	protected ArrayList<ItemComparable> list = new ArrayList<ItemComparable>();
	boolean sorted = false;
	
	public static class ItemCompare implements Comparator<ItemComparable> {
		@Override
		public int compare(ItemComparable arg0, ItemComparable arg1) {
			return arg0.toString().compareTo(arg1.toString());
		}		
	}	
	
	public ItemComparableList() { sorted = true; }
	
	public ItemComparableList(ItemComparable item) {
		if (item == null || item.isEmpty()) { return; }
		add(item);
		sorted = true;
	}
	
	public ItemComparableList(@Nullable ItemStack[] stack, @Nullable FluidStack[] fluids) {
		if (stack != null) {
			for (ItemStack item : stack) {
				if (item == null || item.isEmpty()) { continue; }
				add(new ItemComparable(item));
			}
		}
		if (fluids != null) {
			for (FluidStack fluid : fluids) {
				if (fluid == null) { continue; }
				add(new ItemComparable(fluid));				
			}
		}
		if (list.size() > 1) { sort(); } else { sorted = true; }
	}
	
	public ItemComparableList(ItemStack[] stack) { this(stack, (FluidStack[])null); }
	public ItemComparableList(FluidStack[] fluids) { this((ItemStack[])null, fluids); }
	
	/**
	 * Add an Item to the List
	 * 
	 * Adding items clears the "sorted" state.
	 * 
	 * @param item
	 * @return
	 */
	public ItemComparableList add(ItemComparable item) {
		if (item.isEmpty()) { return this; }
		if (list.size() > 0) { sorted = false; }
		list.add(item);
		return this;
	}
	
	public ItemComparable remove(int index) {
		return list.remove(index);
	}
	
	public ItemComparableList sort() {
		if (sorted) { return this; }
		list.sort(new ItemCompare());
		sorted = true;
		return this;
	}
	
	public int size() {
		return list.size();
	}
	
	public ItemComparable get(int index) {
		return list.get(index);
	}

	public ItemComparableList copy() {
		ItemComparableList ret = new ItemComparableList();
		ret.list = (ArrayList<ItemComparable>) list.clone();
		ret.sorted = sorted;
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ItemComparableList)) { return false; }
		
		ItemComparableList comp = (ItemComparableList)obj;
		if (comp.list.size() != list.size()) { return false; }
		
		sort(); comp.sort();
		
		for (int i = 0; i < list.size(); i++) {
			if (!list.get(i).equals(comp.list.get(i))) { return false; }
		}
		
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		for (ItemComparable item : list) {
			hash += item.hashCode();
		}
		return hash;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("list", list).add("sorted", sorted).toString();
	}
}
