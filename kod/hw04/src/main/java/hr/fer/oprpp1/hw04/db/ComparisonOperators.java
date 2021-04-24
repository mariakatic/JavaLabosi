package hr.fer.oprpp1.hw04.db;

public class ComparisonOperators {
	
	public static final IComparisonOperator LESS = (v1, v2) -> {
		if (v1.compareTo(v2) < 0) return true;
		else return false;
	};
	
	public static final IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> {
		if (v1.compareTo(v2) <= 0) return true;
		else return false;
	};
	
	public static final IComparisonOperator GREATER = (v1, v2) -> {
		if (v1.compareTo(v2) > 0) return true;
		else return false;
	};
	
	public static final IComparisonOperator GREATER_OR_EQUALS = (v1, v2) -> {
		if (v1.compareTo(v2) >= 0) return true;
		else return false;
	};
	
	public static final IComparisonOperator EQUALS = (v1, v2) -> v1.equals(v2);
	
	public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> !v1.equals(v2);
	
	public static final IComparisonOperator LIKE = (v1, v2) -> {
		int i = 0, j = 0;
		while (i < v1.length() && j < v2.length() && v1.charAt(i) == v2.charAt(j)) {
			i++;
			j++;
		}
		if (v2.charAt(j) == '*') {
			j++;
			if (j < v2.length()) {
				String subs = v2.substring(j, v2.length());
				String subs2 = v1.substring(i, v1.length());
				if (subs2.endsWith(subs)) {
					return true;
				}
				
			} else {
				return true;
			}
		}
		
		return false;
	};

}
