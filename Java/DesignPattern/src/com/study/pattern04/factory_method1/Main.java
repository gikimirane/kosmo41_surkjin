package com.study.pattern04.factory_method1;

import java.util.List;

import com.study.pattern04.factory_method1.factory.PatternAGenerator;
import com.study.pattern04.factory_method1.factory.PatternBGenerator;
import com.study.pattern04.factory_method1.factory.UnitGenerator;
import com.study.pattern04.factory_method1.uint.Unit;

public class Main {

	public static void main(String[] args) {
		// 타입이 두가지만 있다는 것만 안다 
		UnitGenerator[] UnitGenerators = new UnitGenerator[2];
		UnitGenerators[0] = new PatternAGenerator();
		UnitGenerators[1] = new PatternBGenerator();
		
	//	DoMakeType(UnitGenerators[0]);
		DoMakeType(UnitGenerators[1]);


	}

	public static void DoMakeType(UnitGenerator ug) {
		ug.createUnits();
		
		List<Unit> units = ug.getUnits();
		for(Unit unit : units)
			unit.attack();
	}
}
