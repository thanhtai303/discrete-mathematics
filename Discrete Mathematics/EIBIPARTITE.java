import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


public class EIBIPARTITE {
	static InputReader reader;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		reader = new InputReader(System.in);
		int testcase = reader.nextInt();
		for (int i = 0; i < testcase; i++) {
			Vertex[] graph = makeGraph();
		}
		System.out.print(sb);
	}

	public static Vertex[] makeGraph() {
		int numberOfVertexs = reader.nextInt();
		int numberOfEdges = reader.nextInt();
		Vertex[] graph = new Vertex[numberOfVertexs];
		for (int i = 0; i < numberOfVertexs; i++) {
			graph[i] = new Vertex(i);
		}
		for (int i = 0; i < numberOfEdges; i++) {
			int u = reader.nextInt();
			int v = reader.nextInt();

			graph[u].addAdjecent(graph[v]);
			graph[v].addAdjecent(graph[u]);
		}
		graph[0].group = 1;
		int count = 0;
		boolean check = true;
		int component = 0;
		for (int i = 0; i < graph.length; i++) {
			if (!graph[i].discovered) {
				Queue<Vertex> queue = new ArrayDeque<Vertex>();
				queue.add(graph[0]);
				graph[0].discovered = true;
				while (!queue.isEmpty()) {
					Vertex w = queue.poll();
					for (Vertex v : w.adjecentVertexs) {
						count++;
						if (!v.discovered) {
							v.discovered = true;
							queue.add(v);
						}
						if (v.group == w.group) {
							check = false;
						} else if (v.group == 0) {
							if (w.group == 1) {
								v.group = 2;
							} else {
								v.group = 1;
							}
						}
					}
				}
			}
		}
		if (numberOfEdges == 0) {
			check = true;
		} else if (count < graph.length) {
			check = false;
		}

		if (check) {
			sb.append("Yes").append("\n");
		} else {
			sb.append("No").append("\n");

		}
		return graph;
	}

	static class Vertex {
		public int id;
		public List<Vertex> adjecentVertexs = new ArrayList<Vertex>();
		public boolean discovered;
		public int group = 0; // 1=group left, 2 =group right

		public Vertex(int id) {
			this.id = id;
		}

		public void addAdjecent(Vertex v) {
			this.adjecentVertexs.add(v);
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
