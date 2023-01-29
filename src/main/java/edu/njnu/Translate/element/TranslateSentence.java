package edu.njnu.Translate.element;

import java.util.List;
import java.util.Objects;

public class TranslateSentence {
	public Symbol leftSymbol;
	public List<Symbol> sentence;

	public TranslateSentence(Action a) {
		this.leftSymbol = a.leftSymbol;
		this.sentence = a.sentence.contents;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TranslateSentence that = (TranslateSentence) o;

		if (that.sentence.size() != this.sentence.size()) return false;

		return Objects.equals(leftSymbol, that.leftSymbol) &&
				this.sentence.containsAll(that.sentence);
	}

	@Override
	public int hashCode() {
		int res = this.leftSymbol.hashCode() << 8;
		for (Symbol symbol : this.sentence) {
			res += symbol.hashCode();
		}
		return res;
	}
}
