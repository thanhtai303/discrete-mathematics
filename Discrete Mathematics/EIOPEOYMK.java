import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class EIOPEOYMK {
	static InputReader reader;

	public static void main(String[] args) throws IOException {
		reader = new InputReader(System.in);
		People[] graph = makeGraph();
		System.out.println(checkQuerry(graph));
	}

	public static StringBuilder checkQuerry(People[] graph) {
		int checkObject = reader.nextInt();
		int numberOfQuerry = reader.nextInt();
		StringBuilder sb = new StringBuilder();
		graph[checkObject].level = 0;
		HashMap<Integer, List<People>> peopleMap = new HashMap<Integer, List<People>>();

		bfs(graph[checkObject]);

		for (People p : graph) {
			List<People> outputList = peopleMap.get(p.level);
			if (outputList == null) {
				outputList = new ArrayList<People>();
				peopleMap.put(p.level, outputList);
			}
			outputList.add(p);
		}

		for (int i = 0; i < numberOfQuerry; i++) {
			int checkLevel = reader.nextInt();
			List<People> outputList = peopleMap.get(checkLevel);
			if (outputList == null) {
				sb.append("-1").append("\n");
			} else {
				for (People p : outputList) {
					sb.append(p.id).append(" ");
				}
				sb.append("\n");
			}
		}

		return sb;
	}

	public static void bfs(People p) {
		Queue<People> queue = new ArrayDeque<People>();
		p.discovered = true;
		queue.add(p);
		while (!queue.isEmpty()) {
			People w = queue.poll();
			for (People i : w.friendList) {
				if (!i.discovered) {
					i.level = i.level + w.level + 1;
					queue.add(i);
					i.discovered = true;
				}

			}
		}
	}

	public static People[] makeGraph() {
		int numberOfPeople = reader.nextInt();
		int numberOfRelationship = reader.nextInt();
		People[] graph = new People[numberOfPeople];

		for (int i = 0; i < numberOfPeople; i++) {
			graph[i] = new People(i);
		}
		for (int i = 0; i < numberOfRelationship; i++) {
			int a = reader.nextInt();
			int b = reader.nextInt();

			graph[a].addFriend(graph[b]);
			graph[b].addFriend(graph[a]);
		}
		return graph;
	}

	static class People {
		public int id;
		public boolean discovered;
		public int level;
		public List<People> friendList = new ArrayList<People>();

		public People(int id) {
			this.id = id;
		}

		public void addFriend(People p) {
			this.friendList.add(p);
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
