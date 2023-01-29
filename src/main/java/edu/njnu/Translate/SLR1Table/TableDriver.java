package edu.njnu.Translate.SLR1Table;


import edu.njnu.Translate.element.Action;
import edu.njnu.Translate.element.Symbol;
import edu.njnu.Translate.element.TranslateElement;
import edu.njnu.Translate.element.Word;
import edu.njnu.Translate.exception.DriverException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TableDriver {
	private Table table = new Table();
	private Stack<Integer> stateStack = new Stack<>();
	private Stack<Symbol> symbolStack = new Stack<>();

	private CodeGenerator codeGenerator;

	public TableDriver(PrintWriter writer) {
		init();
		this.codeGenerator = new CodeGenerator(writer, Table.getReduceRules());
	}

	public void init() {
		this.stateStack.clear();
		this.symbolStack.clear();
		this.stateStack.push(0);
		this.symbolStack.push(Symbol.End);
	}

	public boolean next(Word word) throws Exception {
		Object type = word.getType();
		Symbol input = this.table.getSymbolFromType(type);
		input.value = word.getContent();
		Action action = this.table.getAction(this.stateStack.peek(), input);

		switch (action.type) {
			case Accept: {
				System.out.println("Accepted!");
				break;
			}
			case Shift: {
				this.symbolStack.push(input);
				this.stateStack.push(action.content);
				break;
			}
			case Reduce: {
				List<Symbol> param = new ArrayList<>();
				for (int i = 0; i < action.sentence.contents.size(); i++) {
					if (!action.sentence.contents.get(i).equals(Symbol.Empty)) {
						this.stateStack.pop();
						param.add(this.symbolStack.pop());
					}
				}
				this.symbolStack.push(action.leftSymbol);
				Action a = this.table.getAction(this.stateStack.peek(), this.symbolStack.peek());
				this.stateStack.push(a.content);
				printReduce(action);
				codeGenerator.genCode(action.content, param);
				return false;
			}
			default: {
				throw new DriverException(DriverException.InvalidInput);
			}
		}
		return true;
	}

	private void printReduce(Action action) {
		System.out.print(action.leftSymbol.identifier);
		System.out.print("\t==>\t");
		for (Symbol symbol : action.sentence.contents) {
			System.out.print(symbol.identifier);
		}
		System.out.println("");
	}
}
