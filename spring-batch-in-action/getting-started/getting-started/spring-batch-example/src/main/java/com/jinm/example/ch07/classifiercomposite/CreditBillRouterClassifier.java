/**
 * 
 */
package com.jinm.example.ch07.classifiercomposite;


import org.springframework.classify.annotation.Classifier;

import com.jinm.example.ch07.CreditBill;
/**
 *
 * 2013-9-23上午08:25:06
 */
public class CreditBillRouterClassifier{
	@Classifier
	public String classify(CreditBill classifiable) {
		if(classifiable.getAmount() > 500){
			return "large";
		}else{
			return "small";
		}
	}
}
