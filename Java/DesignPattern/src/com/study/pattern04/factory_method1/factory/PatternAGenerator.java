package com.study.pattern04.factory_method1.factory;

import com.study.pattern04.factory_method1.uint.Marine;

public class PatternAGenerator extends UnitGenerator{
	
	@Override
	public void createUnits() {
		units.add(new Marine());
		units.add(new Marine());
		units.add(new Marine());
		units.add(new Marine());
		units.add(new Marine());
		units.add(new Marine());

	}

}
