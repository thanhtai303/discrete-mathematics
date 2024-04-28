import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;

public class EIUSEFI2 {
	static InputReader reader;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		reader = new InputReader(System.in);
		HashMap<String, Vertex> graph = makeGraph();
		System.out.println(sb);
	}

	public static HashMap<String, Vertex> makeGraph() {
		int n = reader.nextInt();
		HashMap<String, Vertex> graph = new HashMap<String, Vertex>();
		for (int i = 1; i < n; i++) {
			String a = reader.next();
			String b = reader.next();
			if (!graph.containsKey(a) && !graph.containsKey(b)) {
				Vertex fileA = new Vertex(a);
				Vertex fileB = new Vertex(b);
				fileA.addNeighbour(fileB);
				fileB.addNeighbour(fileA);
				graph.put(a, fileA);
				graph.put(b, fileB);
			} else if (!graph.containsKey(a) && graph.containsKey(b)) {
				Vertex fileA = new Vertex(a);
				fileA.addNeighbour(graph.get(b));
				graph.get(b).addNeighbour(fileA);
				graph.put(a, fileA);

			} else if (graph.containsKey(a) && !graph.containsKey(b)) {
				Vertex fileB = new Vertex(b);
				fileB.addNeighbour(graph.get(a));
				graph.get(a).addNeighbour(fileB);
				graph.put(b, fileB);
			} else {
				graph.get(a).addNeighbour(graph.get(b));
				graph.get(b).addNeighbour(graph.get(a));
			}
			graph.get(a).neighbour.sort((s1, s2) -> s1.id.compareToIgnoreCase(s2.id));
			graph.get(b).neighbour.sort((s1, s2) -> s1.id.compareToIgnoreCase(s2.id));
		}
		String mainFolder = reader.next();
		String searchString = reader.next();
		dfs(graph.get(mainFolder), searchString);
		return graph;
	}

	public static void dfs(Vertex v, String searchString) {
		v.discovered = true;
		for (Vertex i : v.neighbour) {
			if (!i.discovered) {
				dfs(i, searchString);
				if (i.id.contains(searchString)) {
					if (i.neighbour.size() == 1)
						v.contain += i.contain + 1;
				}
				if (i.contain > 0) {
					v.contain += i.contain;
				}

			}
		}
		if (v.contain > 0) {
			sb.append(v.id).append(" ").append(v.contain).append("\n");
		}
	}

	static class Vertex {
		public boolean file;
		public boolean nodeFinal;
		public String id;
		public List<Vertex> neighbour = new ArrayList<Vertex>();
		public boolean discovered;
		public int contain;

		public Vertex(String id) {
			this.id = id;
		}

		public void addNeighbour(Vertex v) {
			this.neighbour.add(v);
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
