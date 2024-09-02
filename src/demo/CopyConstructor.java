package demo;

public class CopyConstructor {
	CopyConstructor cc ;
	String name;
	int age;
    long mob;
	public CopyConstructor() {
		
	}
	
	public CopyConstructor(String name,int age,long mob)
	{
		this.name = name;
		this.age = age;
		this.mob = mob;
	}
	
	public CopyConstructor(CopyConstructor cc)
	{
		this.name = cc.name;
		this.age  = cc.age;
		this.mob = cc.mob;
		this.cc = cc;
	}
	
	public String toString()
	{
		return "[ Name : "+this.name+" Age : "+this.age+" Mob : "+this.mob+" ]";
	}

}
