package com.mycompany.coding2.Model;
import android.widget.*;
import com.mycompany.coding2.*;

public class ConverterSystem{
	private String from = "";
	public String to = "";
	private int fromSys = 0;
	private int toSys = 0;
	private int[] nums;
	private char symbol;
	private int degree, sum;
	
	public ConverterSystem(String from, int fromSys, int toSys){
	this.from = from;
	this.fromSys = fromSys;
	this.toSys = toSys;
 
	toOtherSystm(toTen());
	
	}

	private int toTen(){
	nums = new int[from.length()];
	for(int i = 0; i<nums.length; i++){
		symbol = from.charAt(i);
		if(symbol >= 48 && symbol <= 57)
			nums[i] = symbol-48;
		else if(symbol >= 65 && symbol <= 70)
			nums[i] = symbol-55;
		else
			MainActivity.toast.show();
		
	}
	
	degree = 0;
	sum = 0;
	for(int i = nums.length-1;i >=0; i--)
	sum+=nums[i]*Math.pow(fromSys, degree++);
	return sum;
	}
	
	private void toOtherSystm(int ten){
		int surplus=0;
		char c = ' ';
		StringBuilder newReverseNum = new StringBuilder("");
		do{
			surplus = ten%toSys;
			if(surplus>9)
				c = (char)(surplus+55);
			else
				c = (char)(surplus+48);
			newReverseNum.append(c);
			ten/=toSys;
		}while(ten!=0);
		newReverseNum.reverse();
		to=newReverseNum.toString();
	}
}
