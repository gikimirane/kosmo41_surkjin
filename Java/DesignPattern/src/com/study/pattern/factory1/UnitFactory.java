
package com.study.pattern.factory1;

enum UnitType{
		Marine, Firebat, Medic
}

public class UnitFactory {
	public static Unit createUnit(UnitType type) {
		Unit unit = null;
		switch (type) {
		case Marine:
			//구체적인 생성방법 지정하여 생성
			//생성시 파라미터가 있가먄 생성자에 추가할 수 있다.
			unit = new Marine();
			break;
		case Firebat:
			unit = new Firebat();
			break;
		case Medic:
			unit = new Medic();
			break;
		}
		return unit;
	}
}
