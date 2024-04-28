import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class EIPRF {
	static InputReader reader;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		reader = new InputReader(System.in);
		Vertex[] graph = makeGraph();
		System.out.println(sb);
	}

	public static Vertex[] makeGraph() {
		int numberOfVertex = reader.nextInt();
		int numberOfEdge = reader.nextInt();
		Vertex[] graph = new Vertex[numberOfVertex];

		for (int i = 0; i < numberOfVertex; i++) {
			graph[i] = new Vertex(i);
		}
		for (int i = 0; i < numberOfEdge; i++) {
			int u = reader.nextInt();
			int v = reader.nextInt();

			graph[u].addNeighbour(graph[v]);
		}
		graph[0].signalList.add(graph[0]);
		bfs(graph[0]);
		Vertex min = null;
		for (Vertex i : graph) {
			if (i.signal) {
				min=i;
				for (Vertex j : i.signalList) {
					sb.append(j.id).append(" ");
					
				}break;
			}
		}
		return graph;
	}

	public static void bfs(Vertex v) {
		Queue<Vertex> queue = new ArrayDeque<Vertex>();
		queue.add(v);
		v.discovered = true;
		while (!queue.isEmpty()) {
			Vertex w = queue.poll();
			for (Vertex i : w.neighbour) {
				if (!i.discovered) {
					queue.add(i);
					i.discovered = true;
					i.signalList.addAll(w.signalList);
					i.signalList.add(i);
					i.level=i.level+w.level+1;
					
				}
				if (i.id == 0) {
					w.signal = true;
				}
			}
		}
	}

	static class Vertex {
		public boolean signal;
		public List<Vertex> signalList = new ArrayList<Vertex>();
		public int level;
		public int id;
		public List<Vertex> neighbour = new ArrayList<Vertex>();
		public boolean discovered;

		public Vertex(int id) {
			this.id = id;
		}

		public void addNeighbour(Vertex v) {
			this.neighbour.add(v);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(this.id);
			return sb.toString();
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
