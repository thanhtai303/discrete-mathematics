import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

public class EIUEASPOST {
	static InputReader reader;

	public static void main(String[] args) throws IOException {
		reader = new InputReader(System.in);
		Vertex[] graph = makeGraph();
		StringBuilder sb = new StringBuilder();
		System.out.println(dfs(graph[0], sb).append("1"));
	}

	public static Vertex[] makeGraph() {
		int numberOfVertexs = reader.nextInt();
		Vertex[] graph = new Vertex[numberOfVertexs];
		for (int i = 0; i < numberOfVertexs; i++) {
			graph[i] = new Vertex(i+1);
		}
		for (int i = 0; i < numberOfVertexs; i++) {
			int leftVertex = reader.nextInt();
			if (leftVertex > 0) {
				graph[i].leftVertex = graph[leftVertex-1];
			} else {
				graph[i].leftVertex = null;
			}
			int rightVertex = reader.nextInt();
			if (rightVertex > 0) {
				graph[i].rightVertex = graph[rightVertex-1];
			} else {
				graph[i].rightVertex = null;
			}
		}
		return graph;
	}

	public static StringBuilder dfs(Vertex v, StringBuilder sb) {
		v.discovered = true;
		if (v.leftVertex != null && !v.leftVertex.discovered) {
			dfs(v.leftVertex, sb);
			sb.append(v.leftVertex.id).append(" ");
		}
		if (v.rightVertex != null && !v.rightVertex.discovered) {
			dfs(v.rightVertex, sb);
			sb.append(v.rightVertex.id).append(" ");
		}
		return sb;
	}

	static class Vertex {
		public int id;
		public Vertex leftVertex;
		public Vertex rightVertex;
		public boolean discovered;

		public Vertex(int id) {
			this.id = id;
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
