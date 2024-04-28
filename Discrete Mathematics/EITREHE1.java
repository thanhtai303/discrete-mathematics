import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.StringTokenizer;
import java.util.*;

public class EITREHE1 {
	static InputReader reader;

	public static void main(String[] args) throws IOException {
		reader = new InputReader(System.in);
		Vertex[] graph = makeGraph();
		System.out.println(findMaxLevel(graph));
	}

	public static Vertex[] makeGraph() {
		int numberOfNodes = reader.nextInt();
		Vertex[] graph = new Vertex[numberOfNodes];
		for (int i = 0; i < numberOfNodes; i++) {
			graph[i] = new Vertex(i);
		}
		for (int i = 0; i < numberOfNodes - 1; i++) {
			int u = reader.nextInt();
			int v = reader.nextInt();

			graph[u].addAdjecent(graph[v]);
			graph[v].addAdjecent(graph[u]);
		}
		graph[0].level=0;
		dfs(graph[0]);
		return graph;
	}

	public static void dfs(Vertex v) {
		v.discovered = true;
		for (Vertex i : v.adjecentVertexs) {
			if (!i.discovered) {
				i.level = i.level + v.level + 1;
				dfs(i);
				
			}
		}
	}

	public static int findMaxLevel(Vertex[] graph) {
		int max = graph[0].level;
		for (Vertex i : graph) {
			if (i.level > max) {
				max = i.level;
			}
		}
		return max;
	}

	static class Vertex {
		public int id;
		public List<Vertex> adjecentVertexs = new ArrayList<Vertex>();
		public boolean discovered;
		public int level;

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
