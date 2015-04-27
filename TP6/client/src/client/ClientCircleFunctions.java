package client;

import hello.*;

public class ClientCircleFunctions {

	public static void main(String[] args) {
		try{
			CircleFunctionsService service = new CircleFunctionsService();
			CircleFunctions port = service.getCircleFunctionsPort();
			// TODO initialize WS operation arguments here
			double arg0 = 3.0;
			// TODO process result here
			double result = port.getArea(arg0);
			System.out.println("Result="+result);
		}
		catch(Exception ex){
			// TODO handle custom exception here
		}
		// TODO code application logic here
	}
}
