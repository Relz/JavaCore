package main.java.ru.relz.javacore2017.Spreadsheet;

import java.util.HashMap;

public enum Column {
	A('A'),
	B('B'),
	C('C'),
	D('D'),
	E('E'),
	F('F'),
	G('G'),
	H('H'),
	I('I'),
	J('J'),
	K('K'),
	L('L'),
	M('M'),
	N('N'),
	O('O'),
	P('P'),
	Q('Q'),
	R('R'),
	S('S'),
	T('T'),
	U('U'),
	V('V'),
	W('W'),
	X('X'),
	Y('Y'),
	Z('Z');


	private final char character;

	Column(final char character) {
		this.character = character;
	}

	public char toCharacter() {
		return character;
	}

	private static HashMap<Character, Column> charsToColumns = new HashMap<>() {{
		put(A.character, A);
		put(B.character, B);
		put(C.character, C);
		put(D.character, D);
		put(E.character, E);
		put(F.character, F);
		put(G.character, G);
		put(H.character, H);
		put(I.character, I);
		put(J.character, J);
		put(K.character, K);
		put(L.character, L);
		put(M.character, M);
		put(N.character, N);
		put(O.character, O);
		put(P.character, P);
		put(Q.character, Q);
		put(R.character, R);
		put(S.character, S);
		put(T.character, T);
		put(U.character, U);
		put(V.character, V);
		put(W.character, W);
		put(X.character, X);
		put(Y.character, Y);
		put(Z.character, Z);
	}};

	private static HashMap<Integer, Column> ordinalsToColumns = new HashMap<>() {{
		put(A.ordinal(), A);
		put(B.ordinal(), B);
		put(C.ordinal(), C);
		put(D.ordinal(), D);
		put(E.ordinal(), E);
		put(F.ordinal(), F);
		put(G.ordinal(), G);
		put(H.ordinal(), H);
		put(I.ordinal(), I);
		put(J.ordinal(), J);
		put(K.ordinal(), K);
		put(L.ordinal(), L);
		put(M.ordinal(), M);
		put(N.ordinal(), N);
		put(O.ordinal(), O);
		put(P.ordinal(), P);
		put(Q.ordinal(), Q);
		put(R.ordinal(), R);
		put(S.ordinal(), S);
		put(T.ordinal(), T);
		put(U.ordinal(), U);
		put(V.ordinal(), V);
		put(W.ordinal(), W);
		put(X.ordinal(), X);
		put(Y.ordinal(), Y);
		put(Z.ordinal(), Z);
	}};

	public static Column get(char character) {
		return charsToColumns.get(character);
	}

	public static Column get(int ordinal) {
		return ordinalsToColumns.get(ordinal);
	}
}
