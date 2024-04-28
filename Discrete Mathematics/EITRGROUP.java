import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class EITRGROUP {
	static InputReader reader;

	public static void main(String[] args) throws IOException {
		reader = new InputReader(System.in);
		People[] peopleList = makeTree();
		System.out.println(findMaxLevel(peopleList));
	}

	public static People[] makeTree() {
		int numberOfPeople = reader.nextInt();
		int numberOfRelationship = reader.nextInt();
		People[] peopleList = new People[numberOfPeople];

		for (int i = 0; i < numberOfPeople; i++) {
			peopleList[i] = new People(i);
		}
		for (int i = 0; i < numberOfRelationship; i++) {
			int a = reader.nextInt();
			int b = reader.nextInt();

			peopleList[a].addRelationship(peopleList[b]);
			peopleList[b].root = false;
			peopleList[b].addRelationship(peopleList[a]);

		}
		for (People i : peopleList) {
			if (i.root == true) {
				dfs(i);
				break;
			}
		}

		return peopleList;
	}

	public static void dfs(People p) {
		p.discovered = true;
		for (People i : p.relationship) {
			if (!i.discovered) {
				i.level = i.level + p.level + 1;
				dfs(i);
			}
		}
	}

	public static int findMaxLevel(People[] peopleList) {
		int max = peopleList[0].level;
		for (People i : peopleList) {
			if (i.level > max) {
				max = i.level;
			}
		}
		return max + 1;
	}

	static class People {
		public boolean root = true;
		public boolean discovered;
		public int id;
		public int level;
		public List<People> relationship = new ArrayList<People>();

		public People(int id) {
			this.id = id;
		}

		public void addRelationship(People p) {
			this.relationship.add(p);
		}

	}

	static class InputReader {
		private byte[] inbuf = new byte[2 << 23];
		public int lenbuf = 0, ptrbuf = 0;
		public InputStream is;

		public InputReader(InputStream stream) throws IOException {

			inbuf = new byte[2 << 23];
			lenbuf = 0;
			ptrbuf = 0;
			is = System.in;
			lenbuf = is.read(inbuf);
		}

		public InputReader(FileInputStream stream) throws IOException {
			inbuf = new byte[2 << 23];
			lenbuf = 0;
			ptrbuf = 0;
			is = stream;
			lenbuf = is.read(inbuf);
		}

		public boolean hasNext() throws IOException {
			if (skip() >= 0) {
				ptrbuf--;
				return true;
			}
			return false;
		}

		public String nextLine() throws IOException {
			int b = skip();
			StringBuilder sb = new StringBuilder();
			while (!isSpaceChar(b) && b != ' ') { // when nextLine, ()
				sb.appendCodePoint(b);
				b = readByte();
			}
			return sb.toString();
		}

		public String next() {
			int b = skip();
			StringBuilder sb = new StringBuilder();
			while (!(isSpaceChar(b))) { // when nextLine, (isSpaceChar(b) && b
										// != ' ')
				sb.appendCodePoint(b);
				b = readByte();
			}
			return sb.toString();
		}

		private int readByte() {
			if (lenbuf == -1)
				throw new InputMismatchException();
			if (ptrbuf >= lenbuf) {
				ptrbuf = 0;
				try {
					lenbuf = is.read(inbuf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (lenbuf <= 0)
					return -1;
			}
			return inbuf[ptrbuf++];
		}

		private boolean isSpaceChar(int c) {
			return !(c >= 33 && c <= 126);
		}

		private double nextDouble() {
			return Double.parseDouble(next());
		}

		public Character nextChar() {
			return skip() >= 0 ? (char) skip() : null;
		}

		private int skip() {
			int b;
			while ((b = readByte()) != -1 && isSpaceChar(b))
				;
			return b;
		}

		public int nextInt() {
			int num = 0, b;
			boolean minus = false;
			while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
				;
			if (b == '-') {
				minus = true;
				b = readByte();
			}

			while (true) {
				if (b >= '0' && b <= '9') {
					num = num * 10 + (b - '0');
				} else {
					return minus ? -num : num;
				}
				b = readByte();
			}
		}

		public long nextLong() {
			long num = 0;
			int b;
			boolean minus = false;
			while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
				;
			if (b == '-') {
				minus = true;
				b = readByte();
			}

			while (true) {
				if (b >= '0' && b <= '9') {
					num = num * 10 + (b - '0');
				} else {
					return minus ? -num : num;
				}
				b = readByte();
			}
		}
	}
}
