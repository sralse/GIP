package GIP.GIPTest;

public class uID extends Settings{
	
	//TODO Desc
	public static int newID(String name) {
		if (uIDList == null) {
			uIDList.add(0);
			uNameList.add(name);
			return 0;
		}
		
		int i = uIDList.size();
		uIDList.add(i);
		System.out.println("New entity: " + name + " ID: " + i);
		return i;
		
	}
	
	//TODO Desc
	@Deprecated
	public static int getID(String s) {
		return uIDList.get(uNameList.indexOf(s));
	}
	
	//TODO Desc
	public static String getName(int i) {
		return uNameList.get(i);
	}

	//TODO Desc
	public static void setName(int i, String s) {
		if(uNameList.contains(s)) {
			System.err.println("Entity Name already taken! DO NOT USE getID()!");
			return;
		}
		uNameList.set(uNameList.indexOf(i), s);
		return;
	}
	
	

}
