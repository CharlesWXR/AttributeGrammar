package edu.njnu.Translate.element;

public class TranslateElement {
	public Object place;
	public TranslateTypeEnum type;

	public TranslateElement() {
	}

	public TranslateElement(Symbol s) {
		this.place = s.value;
		this.type = TranslateTypeEnum.Value;
	}
}
