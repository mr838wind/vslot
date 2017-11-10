package com.wdfall.vslot.pay_result;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Data;

@Data
public class PayResultOne {
	
	private long currentBet;
	//scatter pay
	private PayResultItem scatter;
	//line별 pay
	private Map<Integer, PayResultItem> lines = new HashMap<>();

	
	public long getScatterPay() {
		long result = 0;
		if(scatter != null) {
			result = scatter.getPay();
		}
		return result;
	}
	
	public long getLinePay(int lineNum) {
		long result = 0;
		PayResultItem currentItem = getLine(lineNum);
		if(currentItem != null) {
			result = currentItem.getPay();
		}
		return result;
	}
	
	public PayResultItem getLine(int lineNum) {
		return lines.get(lineNum);
	}
	
	//line별 결과 set
	public void setLineResult(int lineNum, PayResultItem item) {
		PayResultItem currentItem = getLine(lineNum);
		if(currentItem == null) {
			lines.put(lineNum, item);
		} else {
			//현재것과 비교, 더 큰 payout으로 넣는다.
			if(item.getPay() > currentItem.getPay()) {
				lines.put(lineNum, item);
			}
		}
	}
	
	public long getCurrentPayResult() {
		long pay = 0;
		// add scatter pay
		if(scatter != null) {
			pay = pay + scatter.getPay(); 
		}
		
		// add line별 pay
		for(Entry<Integer, PayResultItem> item : lines.entrySet()) {
			pay = pay + item.getValue().getPay();
		}
		
		return pay;
	}

	
}
