import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class EICHTTRE {
	static InputReader reader;

	public static void main(String[] args) throws IOException {
		reader = new InputReader(System.in);
		StringBuilder sb = new StringBuilder();
		int testcase = reader.nextInt();
		for (int i = 0; i < testcase; i++) {
			boolean output = makeGraph();
			if (output) {
				sb.append("YES").append("\n");
			} else {
				sb.append("NO").append("\n");
			}
		}
		System.out.print(sb);

	}

	public static boolean makeGraph() {
		int numberOfVertexs = reader.nextInt();
		int numberOfEdges = reader.nextInt();
		int u;
		int v;
		Vertex[] graph = new Vertex[numberOfVertexs];
		for (int i = 0; i < numberOfVertexs; i++) {
			graph[i] = new Vertex(i);
		}
		for (int i = 0; i < numberOfEdges; i++) {
			u = reader.nextInt();
			v = reader.nextInt();

			graph[u].addAdjecentVertex(graph[v]);
			graph[v].addAdjecentVertex(graph[u]);
		}
		if (numberOfEdges != numberOfVertexs - 1) {
			return false;
		}
		int count = dfs(graph[0], 0);
		if (count == graph.length) {
			return true;
		}
		return false;
	}

	public static int dfs(Vertex v, int count) {
		v.discovered = true;
		count++;
		for (Vertex i : v.adjecentVertexs) {
			if (!i.discovered) {
				count = dfs(i, count);
				dfs(i, count);
			}
		}
		return count;
	}

	static class Vertex {
		public int id;
		public HashSet<Vertex> adjecentVertexs = new HashSet<Vertex>();
		public boolean discovered;

		public Vertex(int id) {
			this.id = id;
		}

		public void addAdjecentVertex(Vertex v) {
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
