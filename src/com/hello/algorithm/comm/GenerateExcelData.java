package com.hello.algorithm.comm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateExcelData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		char a='A';
		List<String> names=new ArrayList<String>();
		List<String> goodNames=new ArrayList<String>();
		List<Long> prices=new ArrayList<Long>();
		List<Long> counts=new ArrayList<Long>();
		for(int i=0;i<26;i++){
			String shopName=(char)(a+i)+"oy";
			names.add(shopName);
		}
		for(int i=0;i<5;i++){
			goodNames.add((char)('A'+i)+(""+i));
		}
		for(int i=0;i<130;i++){
			prices.add(1L+i*5);
		}
		for(int i=0;i<50;i++){
			counts.add(i*5L);
		}
		for(String shop:names){
			for(String good:goodNames){
				StringBuffer buff=new StringBuffer(shop).append(",").append(good).append(",");
				Random ranP=new Random();
				buff.append(prices.get(ranP.nextInt(prices.size()-1))).append(",");
				Random ranC=new Random();
				buff.append(counts.get(ranC.nextInt(counts.size()-1)));
				System.out.println(buff.toString());
			}
		}
	}

}
