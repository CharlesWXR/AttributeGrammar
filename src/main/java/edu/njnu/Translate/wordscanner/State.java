package edu.njnu.Translate.wordscanner;

public interface State {
	State next(char word);

	Object execute(String _content) throws Exception;
}
