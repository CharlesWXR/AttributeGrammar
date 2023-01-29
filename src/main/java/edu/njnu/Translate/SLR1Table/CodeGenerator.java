package edu.njnu.Translate.SLR1Table;

import edu.njnu.Translate.element.*;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CodeGenerator {
	private static final String TempPrefix = "T_";
	private static final String ExecPrefix = "Exe_";

	private int tempCount = 0;

	private Stack<TranslateElement> elementStack = new Stack<>();
	private Map<Integer, Integer> mapper = new HashMap<>();
	private PrintWriter writer;

	CodeGenerator(PrintWriter writer, List<Action> reduces) {
		this.writer = writer;
		List<TranslateSentence> sentences = reduces.stream()
				.map(TranslateSentence::new)
				.distinct()
				.collect(Collectors.toList());
		for (Action reduce : reduces) {
			AtomicInteger i = new AtomicInteger(0);
			TranslateSentence t = new TranslateSentence(reduce);
			sentences.stream()
					.filter(temp -> {
						i.getAndIncrement();
						return temp.equals(t);
					})
					.findFirst();
			this.mapper.put(reduce.content, i.get() - 1);
		}
	}

	public void genCode(int index, List<Symbol> input) throws Exception {
		List<TranslateElement> args = input.stream()
				.map(TranslateElement::new)
				.collect(Collectors.toList());
		int i = this.mapper.get(index);
		Method method = this.getClass().getDeclaredMethod(ExecPrefix + i, List.class);
		method.invoke(this, args);
	}

	private void genCode(String... args) {
		StringBuilder sb = new StringBuilder();
		for (String arg : args) {
			sb.append(arg + " ");
		}
		this.writer.println(sb.toString().trim());
		this.writer.flush();
	}

	private String newTemp() {
		this.tempCount++;
		return TempPrefix + this.tempCount;
	}

	private void Exe_0(List<TranslateElement> value) {
		TranslateElement element = new TranslateElement();
		element.place = newTemp();
		element.type = TranslateTypeEnum.Referer;
		this.elementStack.push(element);
		genCode((String) element.place, ":=", Integer.toString((int) value.get(0).place));
	}

	private void Exe_1(List<TranslateElement> value) {
		TranslateElement o2 = this.elementStack.pop();
		if (o2.type == TranslateTypeEnum.Empty)
			return;

		TranslateElement element = new TranslateElement();
		element.place = newTemp();
		element.type = TranslateTypeEnum.Referer;
		TranslateElement o1 = this.elementStack.pop();
		this.elementStack.push(element);
		genCode((String) element.place, ":=", (String) o1.place, "+", (String) o2.place);
	}

	private void Exe_2(List<TranslateElement> value) {
		TranslateElement element = new TranslateElement();
		element.place = newTemp();
		element.type = TranslateTypeEnum.Referer;
		this.elementStack.push(element);
		genCode((String) element.place, ":=", Double.toString((double) value.get(0).place));
	}

	private void Exe_3(List<TranslateElement> value) {
		TranslateElement element = new TranslateElement();
		element.type = TranslateTypeEnum.Empty;
		this.elementStack.push(element);
	}

	private void Exe_4(List<TranslateElement> value) {
		TranslateElement element = new TranslateElement();
		element.type = TranslateTypeEnum.Empty;
		this.elementStack.push(element);
	}

	private void Exe_5(List<TranslateElement> value) {
		TranslateElement o2 = this.elementStack.pop();
		if (o2.type == TranslateTypeEnum.Empty)
			return;

		TranslateElement element = new TranslateElement();
		element.place = newTemp();
		element.type = TranslateTypeEnum.Referer;
		TranslateElement o1 = this.elementStack.pop();
		this.elementStack.push(element);
		genCode((String) element.place, ":=", (String) o1.place, "*", (String) o2.place);
	}

	private void Exe_6(List<TranslateElement> value) {

	}

	private void Exe_7(List<TranslateElement> value) {
		TranslateElement o2 = this.elementStack.pop();
		if (o2.type == TranslateTypeEnum.Empty)
			return;
		TranslateElement o1 = this.elementStack.peek();
		genCode((String) o1.place, ":=", (String) o1.place, "+", (String) o2.place);
	}

	private void Exe_8(List<TranslateElement> value) {
		TranslateElement o2 = this.elementStack.pop();
		if (o2.type == TranslateTypeEnum.Empty)
			return;
		TranslateElement o1 = this.elementStack.peek();
		genCode((String) o1.place, ":=", (String) o1.place, "*", (String) o2.place);
	}
}