/**
 * @filename FinalProject.java
 * @author Alex Euzent
 * @date 5/5/2014
 */
public class Main {
	public static void main(String[] args) {
		LMIS lmis = new LMIS();
		LMIS_GUI gui = new LMIS_GUI(lmis);
		Server server = new Server(lmis);
	}
}
