package talecraft.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayListHelper {
	public static ArrayList<String> createArrayListString(String... strings) {
		final ArrayList<String> list = new ArrayList<String>();
		for(String s : strings) {
			list.add(s);
		}
		return list;
		
	}
	
}
