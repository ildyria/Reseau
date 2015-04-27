package calc;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayDeque;
import java.util.Deque;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.xml.ws.Endpoint;

@WebService
public class Calc {

	@WebMethod public double eval(String s){
		String[] termes = s.split(" ");
		Deque<Double> vals = new ArrayDeque<Double>();
		for(int i = 0; i < termes.length; ++i){
			double v2;
			double v1;
			switch(termes[i]){
				case "+": 
					vals.push(vals.pop() + vals.pop());
					break;
				case "-": 
					v2 = vals.pop();
					v1 = vals.pop();
					
					vals.push(v1 - v2);
					break;
				case "*": 
					vals.push(vals.pop() * vals.pop());
					break;
				case "/": 
					v2 = vals.pop();
					v1 = vals.pop();

					vals.push(v1 / v2);
					break;
				default: 
					vals.push(Double.parseDouble(termes[i]));
					break;
			}
		}
		return vals.pop();
	}



	public static void main(String[] args) {
		try {
			String s = "http://"+ InetAddress.getLocalHost().getHostAddress() + ":50042/myCalc/calc";
			Endpoint.publish(s, new Calc());
			System.out.println(s);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("COULDN'T LAUNCH SERVER");
		}
	}
}