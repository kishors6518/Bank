package demo;






public class Driver {

	public static void main(String[] args) {
		CopyConstructor cc = new CopyConstructor("Ankit", 24, 7895850333l);
		CopyConstructor cc1 = new CopyConstructor(cc);
		
		System.out.println(cc);
		System.out.println(cc1);
		
		System.out.println(cc1.cc);
		
		
		

	}

}
