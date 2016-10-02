package com.mycompany.coding2.Model;
import android.widget.*;
import com.mycompany.coding2.*;

public class ConverterSystem{
	private String from = "";
	public String to = "";
	private int degree, sum, fromSys = 0, toSys = 0, max = 1;
	private int[] nums;
	private char symbol;
	private boolean error = false;
	
	public ConverterSystem(String from, int fromSys, int toSys){
		this.from = from;
		this.fromSys = fromSys;
		this.toSys = toSys;
 		toOtherSystem(toTen());
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
		if(nums[i] > max)max=nums[i];
		if(max>=fromSys){
			error = true;
			break;
		}
		
	}
	
	degree = 0;
	sum = 0;
	for(int i = nums.length-1;i >=0; i--)
	sum+=nums[i]*Math.pow(fromSys, degree++);
	return sum;
	}
	
	private void toOtherSystem(int ten){
		int surplus=0; // Остаток от деления
		char c = ' '; // Один символ
		StringBuilder newReverseNum = new StringBuilder(); 
		do{  // 
			surplus = ten%toSys;
			if(surplus>9)
				c = (char)(surplus+55);
			else
				c = (char)(surplus+48);
			newReverseNum.append(c);
			ten/=toSys;
		}while(ten!=0);
		newReverseNum.reverse();
		if(!error)
			to=newReverseNum.toString();
		else to="Ошибка!";
	}
}
