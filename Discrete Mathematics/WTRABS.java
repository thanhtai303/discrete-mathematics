import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.*;

public class WTRABS {
	static InputReader reader;

	public static void main(String[] args) throws IOException {
		reader = new InputReader(System.in);
		Vertex[] graph = makeGraph();
		output(graph);
	}

	public static void output(Vertex[] graph) {
		if (graph.length == 1) {
			System.out.print(graph[0].amountOfWater);
		} else {
			dfs1(graph[0]);
			dfs2(graph[0]);
			StringBuilder sb = new StringBuilder();
			for (Vertex i : graph) {
				if (i.neighbour.isEmpty()) {
					sb.append(i).append("\n");
				}
			}
			System.out.print(sb);
		}
	}

	public static Vertex[] makeGraph() {
		int numberOfVertex = reader.nextInt();
		Vertex[] graph = new Vertex[numberOfVertex];
		for (int i = 0; i < numberOfVertex; i++) {
			graph[i] = new Vertex(i);
			graph[i].amountOfWater = reader.nextDouble();
		}
		for (int i = 0; i < numberOfVertex - 1; i++) {
			int u = reader.nextInt();
			int v = reader.nextInt();

			graph[v].addNeighbour(graph[u]);
		}
		return graph;
	}

	public static void dfs2(Vertex v) {
		v.discovered2 = true;
		if (!v.neighbour.isEmpty()) {
			v.amountOfWater /= v.neighbour.size();
		}
		for (Vertex i : v.neighbour) {
			if (!i.discovered2) {
				i.amountOfWater += v.amountOfWater;
				dfs2(i);		
			}
			

		}
	}

	public static void dfs1(Vertex v) {
		v.discovered = true;
		for (Vertex i : v.neighbour) {
			if (!i.discovered) {
				dfs1(i);
				i.previous = v;
			}
		}
	}

	static class Vertex {
		public int id;
		public List<Vertex> neighbour = new ArrayList<Vertex>();
		public boolean discovered;
		public double amountOfWater;
		public Vertex previous = null;
		public boolean discovered2;

		public Vertex(int id) {
			this.id = id;
		}

		public void addNeighbour(Vertex v) {
			this.neighbour.add(v);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(this.id).append(" ").append(this.amountOfWater);
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
