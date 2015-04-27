package calculatrice;

import java.util.Scanner;

import calc.*;

public class Calculatrice {

	public static void main(String[] args) {
		try{
			CalcService service = new CalcService();
			Calc port = service.getCalcPort();
			// TODO initialize WS operation arguments here
			
			Scanner sc = new Scanner(System.in);
			for(;;){
				String s = sc.nextLine();
				if(s == "exit") break;
				double result = port.eval(s);
				System.out.println("Result="+result);
			}
			sc.close();
		}
		catch(Exception ex){
			System.out.println("couldn't connect");
		}
		// TODO code application logic here
	}
}
